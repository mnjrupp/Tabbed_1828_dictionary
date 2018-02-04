package Utilities;

import android.app.Activity;
import android.content.Intent;

import com.example.mnjru.tabbed_1828_dictionary.R;

/**
 * Created by mnjru on 1/6/2018.
 */

public class ThemeUtility {
    private static int sTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_MATERIAL_LIGHT = 1;
    public final static int THEME_CONSOLE_DARK = 2;
    public final static int THEME_BLUE_LIGHT = 3;
    public final static int THEME_YELLOW_OLD = 4;

    public static void changetoTheme(Activity activity, int theme){
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity,activity.getClass()));
        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }

    public static void onActivityCreateSetTheme(Activity activity){
        switch (sTheme){
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.AppTheme);
                break;
            case THEME_MATERIAL_LIGHT:
                activity.setTheme(R.style.Theme_Material_Light);
                break;
            case THEME_CONSOLE_DARK:
                activity.setTheme(R.style.Theme_Console_Dark);
                break;
            case THEME_BLUE_LIGHT:
                //activity.setTheme(R.style.Theme_Blue_lite);
                break;
            case THEME_YELLOW_OLD:
                //activity.setTheme(R.style.Theme_Yellow_old);
        }
    }
}
