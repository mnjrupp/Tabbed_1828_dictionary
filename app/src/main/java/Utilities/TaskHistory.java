package Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mnjru.tabbed_1828_dictionary.DisplayTopicDef;
import com.example.mnjru.tabbed_1828_dictionary.R;

/**
 * Created by mnjru on 5/24/2017.
 */

public class TaskHistory extends AsyncTask<String,Topic,String> {

    private Context ctx;
    HistoryDBHelper historyDBHelper;
    HistoryAdapter historyAdapter;
    public final static String TOPIC = "topic";
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
            historyDBHelper = new HistoryDBHelper(ctx);
            SQLiteDatabase db = historyDBHelper.getWritableDatabase();
            Topic topic = new Topic(topicname,last_access,1);
            try {
                //If the topic is in history table, update instead of add
                if(historyDBHelper.getAccessedCount(topicname)>0){
                    historyDBHelper.updateHistory(topic,db);
                }else {
                    historyDBHelper.addHistory(topic, db);
                }
            }catch (SQLException mSQLException){
                Log.e("TaskHistory","Error when adding history " + mSQLException.toString());
            }
            return "add_hist";


        }
        else if(method.equals("get_hist")){
            // initialize the view from the Activity
            listView = (ListView) activity.findViewById(R.id.list_history);
            historyDBHelper = new HistoryDBHelper(ctx);
            SQLiteDatabase db = historyDBHelper.getReadableDatabase();
            Cursor cursor = historyDBHelper.getInformation(db);
            historyAdapter = new HistoryAdapter(ctx, R.layout.raw_history_row);
            String topicname,last_access;
            int count;
            while(cursor.moveToNext()){
                topicname=cursor.getString(cursor.getColumnIndex("topic"));
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
            if(listView!=null) {
                View header = View.inflate(ctx,R.layout.history_header, null);
                listView.addHeaderView(header);
                listView.setAdapter(historyAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String topic = ((TextView) view.findViewById(R.id.text_topic)).getText().toString();
                        if(!topic.isEmpty()) {
                            Intent intent = new Intent(ctx, DisplayTopicDef.class);
                            intent.putExtra(TOPIC, topic);
                            activity.startActivity(intent);
                        }else{/* empty topic: do nothing*/}
                    }
                });

            }
        }
        else{
           // do nothing at this time
        }
    }
}
