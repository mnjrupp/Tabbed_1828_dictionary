package Utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by mnjru on 5/27/2017.
 */

public class HistoryDBHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "HistoryTopics.db";

    private static final int DATABASE_VERSION = 1;

    private static final String TOPIC_NAME = "Topic";

    private  static final String LAST_ACCESSED_DTE = "Last_access_dte";

    public HistoryDBHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        // you can use an alternate constructor to specify a database location
        // (such as a folder on the sd card)
        // you must ensure that this folder is available and you have permission
        // to write to it
        //super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);
    }


      @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }

    public void addHistory(Topic topic){

    }

}
