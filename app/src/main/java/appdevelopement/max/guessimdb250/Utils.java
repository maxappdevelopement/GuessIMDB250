package appdevelopement.max.guessimdb250;

import android.content.Context;

public class Utils {

    public static String getUniqeID(Context context) {

        final String androidId;

        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        return androidId;
    }
}
