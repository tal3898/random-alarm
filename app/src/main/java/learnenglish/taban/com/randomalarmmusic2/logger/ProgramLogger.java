package learnenglish.taban.com.randomalarmmusic2.logger;

import android.util.Log;

public class ProgramLogger {
    private static final String tag = "MAIN-LOGGER";

    public static void info(String message) {
        Log.i(tag, message);
    }
}
