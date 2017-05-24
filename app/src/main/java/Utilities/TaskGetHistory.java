package Utilities;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by mnjru on 5/24/2017.
 */

public class TaskGetHistory extends AsyncTask<String,Void,String> {

    private Context cxt;

    public TaskGetHistory() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... params) {
        return null;
    }
}
