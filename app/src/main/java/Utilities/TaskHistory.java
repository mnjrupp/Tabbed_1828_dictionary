package Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mnjru.tabbed_1828_dictionary.DisplayTopicDef;
import com.example.mnjru.tabbed_1828_dictionary.MainActivity;
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
            db.close();
            return "add_hist";


        }
        else if(method.equals("get_hist")){
            // initialize the view from the Activity
            listView = (ListView) activity.findViewById(R.id.list_history);
            historyDBHelper = new HistoryDBHelper(ctx);
            SQLiteDatabase db = historyDBHelper.getReadableDatabase();
            Cursor cursor = historyDBHelper.getInformation(db);
            historyAdapter = new HistoryAdapter(ctx, R.layout.raw_history_row){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    // Set the text size from Preference
                    int szText = getSharedfontSize();
                    View view = super.getView(position, convertView, parent);
                    TextView tv = (TextView) view.findViewById(R.id.text_topic);
                    TextView tv2=(TextView) view.findViewById(R.id.text_last_accessed);
                    TextView tv3 = (TextView) view.findViewById(R.id.text_count);

                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, szText);
                    tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, szText);
                    tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, szText);
                    // Return the view
                    return view;
                }
            };
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
                // Check for header in listview.
                // Do not add again
                if(listView.getHeaderViewsCount()==0) {
                    View header = View.inflate(ctx, R.layout.history_header, null);
                    listView.addHeaderView(header);
                }
                // register context menu in listview
               // activity.registerForContextMenu(listView);
                listView.setAdapter(historyAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // don't want the header
                        if(id>=0) {
                            String topic = ((TextView) view.findViewById(R.id.text_topic)).getText().toString();
                            if (!topic.isEmpty()) {
                                Intent intent = new Intent(ctx, DisplayTopicDef.class);
                                intent.putExtra(TOPIC, topic);
                                activity.startActivity(intent);
                            } else {/* empty topic: do nothing*/}
                        }
                    }
                });

            }
        }
        else{
           // do nothing at this time
        }
    }
    private int getSharedfontSize() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        int szText = Integer.parseInt(sharedPreferences.getString("textsize_list", "-1"));
        switch (szText) {
            case -1:
                // return default text size
                int szdefault = 14;
                return szdefault;
            default:
                // using the int array from Main Activity
                return MainActivity.fSize[szText];

        }
    };
}
