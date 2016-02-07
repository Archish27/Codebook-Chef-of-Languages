package com.codebook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.codebook.R;
import com.codebook.loaders.CustomFontLoader;
import com.codebook.models.Question;
import com.codebook.persistence.DatabaseHelper;
import com.codebook.prettify.PrettifyHighlighter;

import java.util.ArrayList;

/**
 * Created by Archish on 1/30/2016.
 */
public class PracsActivity extends AppCompatActivity {
    DatabaseHelper db;
    ArrayList<Question> questions;
    FloatingActionButton fab;
    TextView title,program,output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pracs);
        Bundle extras = getIntent().getExtras();
        int position=extras.getInt("position");
        int catId=extras.getInt("categoryid");
        int levelId=extras.getInt("levelid");
        db=DatabaseHelper.getDbInstance(this);
        questions=db.getPracsQuestions(position, catId, levelId);
        title=(TextView)findViewById(R.id.tvTitle);
        program=(TextView)findViewById(R.id.tvPrgmText);
        output=(TextView)findViewById(R.id.tvOutput);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        Log.d("SizeofQuestions", String.valueOf(questions.size()));
        final Question ques=questions.get(0);
        PrettifyHighlighter highlighter = new PrettifyHighlighter();
        String text = highlighter.highlight("java",escapeJavaString(ques.getPrgmText()));
        title.setTextColor(getResources().getColor(R.color.text_color));
        program.setTextColor(getResources().getColor(R.color.text_color));
        output.setTextColor(getResources().getColor(R.color.text_color));
        title.setText(ques.getQuestionText());
        program.setText(escapeJavaString(ques.getPrgmText()));
        output.setText(PrepareQuestionsActivity.x+escapeJavaString(ques.getOptA()));
        title.setTypeface(CustomFontLoader.getTypeface(getApplicationContext(), CustomFontLoader.RALEWAY_BOLD));
        output.setTypeface(CustomFontLoader.getTypeface(getApplicationContext(), CustomFontLoader.QUICKSAND_BOLD));
        program.setMovementMethod(new ScrollingMovementMethod());
        output.setMovementMethod(new ScrollingMovementMethod());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, program.getText().toString() + "\n" + output.getText().toString());
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });
        Log.d("Values",ques.getQuestionText()+" ");
    }
    public String escapeJavaString(String st) {

        StringBuilder sb = new StringBuilder(st.length());

        for (int i = 0; i < st.length(); i++) {
            char ch = st.charAt(i);
            if (ch == '\\') {
                char nextChar = (i == st.length() - 1) ? '\\' : st
                        .charAt(i + 1);
                // Octal escape?
                if (nextChar >= '0' && nextChar <= '7') {
                    String code = "" + nextChar;
                    i++;
                    if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
                            && st.charAt(i + 1) <= '7') {
                        code += st.charAt(i + 1);
                        i++;
                        if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
                                && st.charAt(i + 1) <= '7') {
                            code += st.charAt(i + 1);
                            i++;
                        }
                    }
                    sb.append((char) Integer.parseInt(code, 8));
                    continue;
                }
                switch (nextChar) {
                    case '\\':
                        ch = '\\';
                        break;
                    case 'b':
                        ch = '\b';
                        break;
                    case 'f':
                        ch = '\f';
                        break;
                    case 'n':
                        ch = '\n';
                        break;
                    case 'r':
                        ch = '\r';
                        break;
                    case 't':
                        ch = '\t';
                        break;
                    case '\"':
                        ch = '\"';
                        break;
                    case '\'':
                        ch = '\'';
                        break;
                    // Hex Unicode: u????
                    case 'u':
                        if (i >= st.length() - 5) {
                            ch = 'u';
                            break;
                        }
                        int code = Integer.parseInt(
                                "" + st.charAt(i + 2) + st.charAt(i + 3)
                                        + st.charAt(i + 4) + st.charAt(i + 5), 16);
                        sb.append(Character.toChars(code));
                        i += 5;
                        continue;
                }
                i++;
            }
            sb.append(ch);
        }
        return sb.toString();
    }


}
