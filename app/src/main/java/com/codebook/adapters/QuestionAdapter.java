package com.codebook.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.codebook.R;
import com.codebook.loaders.CustomFontLoader;
import com.codebook.models.Answer;
import com.codebook.models.Question;
import com.codebook.persistence.DatabaseHelper;
import com.codebook.prettify.PrettifyHighlighter;

import java.util.ArrayList;


/**
 * Created by Narayan Acharya on 01/11/2015.
 */
public class QuestionAdapter extends BaseAdapter {

    private static final String TAG = "QuestionAdapter";
    private static final int ANSWER_TIME_OUT = 1000;
    private static final int CORRECT_TIME_OUT = 500;
    public static final int QUESTION_TIME_OUT = 30000;

    private boolean wasThisCorrect;
    LayoutInflater layoutInflater;
    ArrayList<Question> questions;
    ArrayList<Answer> answers;
    DatabaseHelper databaseHelper;
    MediaPlayer mediaPlayer;
    Context context;
    CountDownTimer countDownTimer;

    AdapterActivityInterface adapterActivityInterface;

    public interface AdapterActivityInterface {
        void showNextQuestion(float time, boolean wasThisCorrect);

        void updateTimer(int timePast);
    }

    public QuestionAdapter(Activity activity, ArrayList<Question> questions, ArrayList<Answer> answers) {
        this.context = activity;
        this.adapterActivityInterface = (AdapterActivityInterface) activity;
        layoutInflater = LayoutInflater.from(context);
        databaseHelper = DatabaseHelper.getDbInstance(context);
        this.questions = questions;
        this.answers = answers;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Question question = questions.get(position);
        final Answer answer = answers.get(position);
        final QuestionHolder questionHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_question, parent, false);
            questionHolder = new QuestionHolder();
            questionHolder.tvQuestionText = (TextView) convertView.findViewById(R.id.tvQuestionText);
            questionHolder.tvPrgmText = (TextView) convertView.findViewById(R.id.tvPrgmText);
            questionHolder.tvOptionA = (TextView) convertView.findViewById(R.id.tvOptionA);
            questionHolder.tvOptionB = (TextView) convertView.findViewById(R.id.tvOptionB);
            questionHolder.tvOptionC = (TextView) convertView.findViewById(R.id.tvOptionC);
            questionHolder.tvOptionD = (TextView) convertView.findViewById(R.id.tvOptionD);
            convertView.setTag(questionHolder);
        } else {
            questionHolder = (QuestionHolder) convertView.getTag();
        }

        setupQuestionContent(questionHolder, question);
        setupFont(questionHolder);

        questionHolder.tvOptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTicking();
                if (answer.getAnswer() == 1) {
                    playMusic(true);
                    questionHolder.tvOptionA.setBackgroundResource(R.drawable.prepare_button_correct);
                    answer.setCorrect(1);
                    wasThisCorrect = true;
                } else {
                    playMusic(false);
                    questionHolder.tvOptionA.setBackgroundResource(R.drawable.prepare_button_incorrect);
                    answer.setCorrect(0);
                    wasThisCorrect = false;
                    showCorrectAnswer(questionHolder, answer.getAnswer());
                }
                clearClickListeners(questionHolder);
                answer.setAttempted(answer.getAttempted() + 1);
                updateDatabase(answer);
                pleaseWaitAndThenShowNext(answer.getTime());
            }
        });

        questionHolder.tvOptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTicking();
                if (answer.getAnswer() == 2) {
                    playMusic(true);
                    questionHolder.tvOptionB.setBackgroundResource(R.drawable.prepare_button_correct);
                    answer.setCorrect(1);
                    wasThisCorrect = true;
                } else {
                    playMusic(false);
                    questionHolder.tvOptionB.setBackgroundResource(R.drawable.prepare_button_incorrect);
                    answer.setCorrect(0);
                    wasThisCorrect = false;
                    showCorrectAnswer(questionHolder, answer.getAnswer());
                }
                clearClickListeners(questionHolder);
                answers.get(position).setAttempted(answer.getAttempted() + 1);
                updateDatabase(answer);
                pleaseWaitAndThenShowNext(answer.getTime());
            }
        });

        questionHolder.tvOptionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTicking();
                if (answer.getAnswer() == 3) {
                    playMusic(true);
                    questionHolder.tvOptionC.setBackgroundResource(R.drawable.prepare_button_correct);
                    answer.setCorrect(1);
                    wasThisCorrect = true;
                } else {
                    playMusic(false);
                    questionHolder.tvOptionC.setBackgroundResource(R.drawable.prepare_button_incorrect);
                    answer.setCorrect(0);
                    wasThisCorrect = false;
                    showCorrectAnswer(questionHolder, answer.getAnswer());
                }
                clearClickListeners(questionHolder);
                answer.setAttempted(answer.getAttempted() + 1);
                updateDatabase(answer);
                pleaseWaitAndThenShowNext(answer.getTime());
            }
        });

        questionHolder.tvOptionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTicking();
                if (answer.getAnswer() == 4) {
                    playMusic(true);
                    questionHolder.tvOptionD.setBackgroundResource(R.drawable.prepare_button_correct);
                    answer.setCorrect(1);
                    wasThisCorrect = true;
                } else {
                    playMusic(false);
                    questionHolder.tvOptionD.setBackgroundResource(R.drawable.prepare_button_incorrect);
                    answer.setCorrect(0);
                    wasThisCorrect = false;
                    showCorrectAnswer(questionHolder, answer.getAnswer());
                }
                clearClickListeners(questionHolder);
                answer.setAttempted(answer.getAttempted() + 1);
                updateDatabase(answer);
                pleaseWaitAndThenShowNext(answer.getTime());
            }
        });
        Log.d(TAG, "starting to tick" + position);
        startTicking(questionHolder, answer);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return questions.get(position).getQuestionId();
    }

    @Override
    public Question getItem(int position) {
        return questions.get(position);
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    static class QuestionHolder {
        TextView tvQuestionText;
        TextView tvPrgmText;
        TextView tvOptionA;
        TextView tvOptionB;
        TextView tvOptionC;
        TextView tvOptionD;
    }

    private void setupQuestionContent(QuestionHolder questionHolder, Question question) {
        PrettifyHighlighter highlighter = new PrettifyHighlighter();
        String text = highlighter.highlight("java",question.getPrgmText());
        questionHolder.tvPrgmText.setTextColor(Color.parseColor("#ffffff"));
        questionHolder.tvQuestionText.setTextColor(Color.parseColor("#ffffff"));
        questionHolder.tvQuestionText.setText(escapeJavaString(question.getQuestionText()));
        questionHolder.tvPrgmText.setText(escapeJavaString(question.getPrgmText()));

        questionHolder.tvPrgmText.setBackgroundColor(Color.parseColor("#212121"));
        questionHolder.tvQuestionText.setBackgroundColor(Color.parseColor("#212121"));
        questionHolder.tvPrgmText.setMovementMethod(new ScrollingMovementMethod());
        questionHolder.tvOptionA.setText(question.getOptA());
        questionHolder.tvOptionB.setText(question.getOptB());
        questionHolder.tvOptionC.setText(question.getOptC());
        questionHolder.tvOptionD.setText(question.getOptD());
    }

    private void setupFont(QuestionHolder questionHolder) {
        questionHolder.tvQuestionText.setTypeface(CustomFontLoader.getTypeface(context, CustomFontLoader.QUICKSAND_BOLD));
        questionHolder.tvPrgmText.setTypeface(CustomFontLoader.getTypeface(context, CustomFontLoader.QUICKSAND_BOLD));
        questionHolder.tvOptionA.setTypeface(CustomFontLoader.getTypeface(context, CustomFontLoader.QUICKSAND_BOLD));
        questionHolder.tvOptionB.setTypeface(CustomFontLoader.getTypeface(context, CustomFontLoader.QUICKSAND_BOLD));
        questionHolder.tvOptionC.setTypeface(CustomFontLoader.getTypeface(context, CustomFontLoader.QUICKSAND_BOLD));
        questionHolder.tvOptionD.setTypeface(CustomFontLoader.getTypeface(context, CustomFontLoader.QUICKSAND_BOLD));
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
    private void playMusic(boolean right) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (right) {
            mediaPlayer = MediaPlayer.create(context,
                    R.raw.positive);
        } else {
            mediaPlayer = MediaPlayer.create(context,
                    R.raw.negative);
        }
        mediaPlayer.start();
    }

    private void updateDatabase(Answer answer) {
        databaseHelper.setAttemptedCountAndSolveTime(answer);
    }

    private void clearClickListeners(QuestionHolder questionHolder) {
        questionHolder.tvOptionA.setOnClickListener(null);
        questionHolder.tvOptionB.setOnClickListener(null);
        questionHolder.tvOptionC.setOnClickListener(null);
        questionHolder.tvOptionD.setOnClickListener(null);
    }

    private void showCorrectAnswer(final QuestionHolder questionHolder, final int answer) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                switch (answer) {
                    case 1:
                        questionHolder.tvOptionA.setBackgroundResource(R.drawable.prepare_button_correct);
                        break;
                    case 2:
                        questionHolder.tvOptionB.setBackgroundResource(R.drawable.prepare_button_correct);
                        break;
                    case 3:
                        questionHolder.tvOptionC.setBackgroundResource(R.drawable.prepare_button_correct);
                        break;
                    case 4:
                        questionHolder.tvOptionD.setBackgroundResource(R.drawable.prepare_button_correct);
                        break;
                    default:
                        break;
                }
            }
        }, CORRECT_TIME_OUT);

    }

    private void pleaseWaitAndThenShowNext(final float time) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                adapterActivityInterface.showNextQuestion(time, wasThisCorrect);
            }
        }, ANSWER_TIME_OUT);
    }

    public void startTicking(final QuestionHolder questionHolder, final Answer answer) {
        if (countDownTimer != null) {
            stopTicking();
        }
        countDownTimer = new CountDownTimer(QUESTION_TIME_OUT, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                double factor = Math.pow(10, -3);
                float time = (float) (Math.round(millisUntilFinished * factor) / factor);
                answer.setTime(QUESTION_TIME_OUT - time);
                adapterActivityInterface.updateTimer((int) time);
            }

            @Override
            public void onFinish() {
                adapterActivityInterface.updateTimer(0);
                stopTicking();
                wasThisCorrect = false;
                answer.setTime(QUESTION_TIME_OUT);
                updateDatabase(answer);
                // showCorrectAnswer(questionHolder, answer.getAnswer());
                pleaseWaitAndThenShowNext(answer.getTime());
            }
        };
        countDownTimer.start();
    }

    private void stopTicking() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}
