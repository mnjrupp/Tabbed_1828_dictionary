package Utilities;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.mnjru.tabbed_1828_dictionary.R;

/**
 * Created by mnjru on 5/24/2017.
 */

public class TaskHistory extends AsyncTask<String,Topic,String> {

    private Context ctx;
    HistoryDBHelper historyDBHelper;
    HistoryAdapter historyAdapter;
    // Need Activity to display in the SubHistory activity
    Activity activity;
    ListView listView;
    public TaskHistory(Context context) {
        this.ctx=context;
        activity = (Activity)context;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


   @Override
    protected String doInBackground(String... params) {
        String method = params[0];


        if(method.equals("add_hist")){
            String topicname,last_access;

            topicname = params[1];
            last_access = params[2];

            Topic topic = new Topic(topicname,last_access,1);
            try {
                SQLiteDatabase db = historyDBHelper.getWritableDatabase();
                historyDBHelper.addHistory(topic, db);
            }catch (SQLException mSQLException){
                Log.e("TaskHistory","Error when adding history " + mSQLException.toString());
            }
            return "add_hist";


        }
        else if(method.equals("get_hist")){
            // initialize the view from the Activity
            listView = (ListView) activity.findViewById(R.id.list_history);
            SQLiteDatabase db = historyDBHelper.getReadableDatabase();
            Cursor cursor = historyDBHelper.getInformation(db);
            historyAdapter = new HistoryAdapter(ctx, R.layout.raw_history_row);
            String topicname,last_access;
            int count;
            while(cursor.moveToNext()){
                topicname=cursor.getString(cursor.getColumnIndex("Topic"));
                last_access = cursor.getString(cursor.getColumnIndex("last_accessed_dte"));
                count = cursor.getInt(cursor.getColumnIndex("count"));
                Topic topic = new Topic(topicname,last_access,count);
                publishProgress(topic);
            }
                return "get_hist";
        }
        return null;
    }


    protected void onProgressUpdate(Topic... values) {
        historyAdapter.add(values[0]);

    }

    @Override
    protected void onPostExecute(String s) {
        if(s.equals("get_hist")){
            listView.setAdapter(historyAdapter);
        }
        else{
           // do nothing at this time
        }
    }
}
