package com.codebook.loaders;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Narayan Acharya on 16/11/2015.
 */
public class CustomFontLoader {

    public static final int FONT_AWESOME = 0;
    public static final int RALEWAY_LIGHT = 1;
    public static final int RALEWAY_MEDIUM = 2;
    public static final int RALEWAY_REGULAR = 3;
    public static final int RALEWAY_BOLD = 4;
    public static final int QUICKSAND_BOLD = 5;
    public static final int QUICKSAND_REGULAR = 6;

    private static final int NUM_OF_CUSTOM_FONTS = 7;

    private static boolean fontsLoaded = false;

    private static Typeface[] fonts = new Typeface[NUM_OF_CUSTOM_FONTS];

    private static String[] fontPath = {
            "fonts/fontawesome-webfont.ttf",
            "fonts/Raleway-Light.ttf",
            "fonts/Raleway-Medium.ttf",
            "fonts/Raleway-Regular.ttf",
            "fonts/Raleway-Bold.ttf",
            "fonts/QUICKSAND-BOLD.OTF",
            "fonts/QUICKSAND-REGULAR.OTF"
    };


    /**
     * Returns a loaded custom font based on it's identifier.
     *
     * @param context        - the current context
     * @param fontIdentifier = the identifier of the requested font
     * @return Typeface object of the requested font.
     */
    public static Typeface getTypeface(Context context, int fontIdentifier) {
        if (!fontsLoaded) {
            loadFonts(context);
        }
        return fonts[fontIdentifier];
    }


    private static void loadFonts(Context context) {
        for (int i = 0; i < NUM_OF_CUSTOM_FONTS; i++) {
            fonts[i] = Typeface.createFromAsset(context.getAssets(), fontPath[i]);
        }
        fontsLoaded = true;

    }
}
