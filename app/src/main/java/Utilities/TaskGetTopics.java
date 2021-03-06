package Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mnjru.tabbed_1828_dictionary.MainActivity;
import com.mnjru.tabbed_1828_dictionary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mnjru on 6/6/2017.
 */

public class TaskGetTopics extends AsyncTask<String,String,String> {

    private Context ctx;
    ArrayAdapter<String> topicAdapter;
    Activity activity;
    ListView listView;
    TextView textView;
    List list = new ArrayList();

    public TaskGetTopics(Context context) {
        this.ctx = context;
        activity = (Activity) context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String method = params[0];
        DicDatabaseHelper dicDatabaseHelper = new DicDatabaseHelper(ctx);
        Cursor cursor;
        if (method.equals("get_topics")) {

            listView = (ListView) activity.findViewById(R.id.list_topics_alpha);
            textView = (TextView) activity.findViewById(R.id.text_alpha);
            String alpha = params[1];
            dicDatabaseHelper.getReadableDatabase();
            cursor = dicDatabaseHelper.getTopicsbyAlpha(alpha);


            // display in listview

            String topicname;

            while (cursor.moveToNext()) {
                topicname = cursor.getString(cursor.getColumnIndex("Topic"));
                publishProgress(topicname);

            }
            return alpha;

        }
        dicDatabaseHelper.close();
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {

        list.add(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        topicAdapter = new ArrayAdapter<String>(ctx,R.layout.custom_list_item,android.R.id.text1 , list) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                // Set the text size from Preference
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, getSharedfontSize());
                // Return the view
                return view;
            }
        };
        topicAdapter.notifyDataSetChanged();
        listView.setAdapter(topicAdapter);
        textView.setText(result);
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
