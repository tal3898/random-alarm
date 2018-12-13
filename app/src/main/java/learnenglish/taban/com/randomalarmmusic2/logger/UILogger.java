package learnenglish.taban.com.randomalarmmusic2.logger;

import android.util.Log;

public class UILogger {
    private static String tag = "UI";

    public static void info(String message) {
        Log.i(tag, message);
    }
}
