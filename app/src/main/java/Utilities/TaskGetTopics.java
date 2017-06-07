package Utilities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

/**
 * Created by mnjru on 6/6/2017.
 */

public class TaskGetTopics extends AsyncTask<String,Void,Void> {

    private Context ctx;

    TaskGetTopics(Context context){
        this.ctx=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        String method = params[0];
        DicDatabaseHelper dicDatabaseHelper = new DicDatabaseHelper(ctx);
        Cursor cursor;
        if(method.equals("get_topics")){

            String alpha = params[1];
            dicDatabaseHelper.getReadableDatabase();
            cursor = dicDatabaseHelper.getTopicsbyAlpha(alpha);


        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
