package ng.codeimpact.firebaseandroid.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by Nsikak  Thompson on 2/23/2017.
 */

public class ChronicaProRegularHelper {
    private static Typeface mChronicaProRegularFont = null;

    private static Typeface getChronicaProRegular(Context context) {
        if (mChronicaProRegularFont == null)
            mChronicaProRegularFont = Typeface.createFromAsset(context.getAssets(), "fonts/ChronicaPro-Regular.otf");
        return mChronicaProRegularFont;
    }

    public static TextView applyFont(Context context, TextView textView) {
        if (textView != null)
            textView.setTypeface(getChronicaProRegular(context));
        return textView;
    }


}