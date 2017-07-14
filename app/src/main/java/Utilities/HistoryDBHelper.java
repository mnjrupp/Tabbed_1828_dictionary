package Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by mnjru on 5/27/2017.
 */

public class HistoryDBHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "HistoryTopics.db";
    private static final String TABLE_NAME = "topic_history";

    private static final int DATABASE_VERSION = 1;

    private static final String TOPIC_NAME = "topic";
    private static final String COUNT = "count";
    private  static final String LAST_ACCESSED_DTE = "last_accessed_dte";

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

    public void addHistory(Topic topic,SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TOPIC_NAME,topic.getTopic());
        contentValues.put(LAST_ACCESSED_DTE,topic.getLast_accessed());
        // Get current count if topic is in database
        contentValues.put(COUNT,topic.getCount()+getAccessedCount(topic.getTopic()));
        db.insert(TABLE_NAME,null,contentValues);

    }

    public boolean topicExists(String topic,SQLiteDatabase db){

        return false;
    }

    public Cursor getInformation(SQLiteDatabase db){
        Cursor cursor;
        String[] columns = {TOPIC_NAME,LAST_ACCESSED_DTE,COUNT};
        cursor = db.query(TABLE_NAME,columns,null,null,null,null,LAST_ACCESSED_DTE);
        return cursor;


    }
    public int getAccessedCount(String topic){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        int accessedCount = 0;
        String SQL = "SELECT * FROM "+TABLE_NAME + " WHERE "+TOPIC_NAME+"='"+topic+"';";
        cursor = db.rawQuery(SQL,null);
        if(cursor.moveToFirst()){
            accessedCount = cursor.getInt(cursor.getColumnIndex("count"));
        }
        return accessedCount;
    }

}
