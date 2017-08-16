package Layout;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mnjru.tabbed_1828_dictionary.R;

import Utilities.HistoryAdapter;
import Utilities.HistoryDBHelper;
import Utilities.TaskHistory;
import Utilities.Topic;

/**
 * Created by mnjru on 5/21/2017.
 */

public class SubHistory extends Fragment {
    ListView listview;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container,false);
        listview = (ListView)view.findViewById(R.id.list_history);
        registerForContextMenu(listview);
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.list_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo contextMenuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch(item.getItemId()){
            case R.id.id_delete:

                String topicName = ((Topic)listview.getItemAtPosition(contextMenuInfo.position)).getTopic().toString();
                HistoryDBHelper historyDBHelper = new HistoryDBHelper(getContext());
                SQLiteDatabase db = historyDBHelper.getWritableDatabase();
                historyDBHelper.deleteTopicHistory(topicName,db);
                HistoryAdapter historyAdapter = ((HistoryAdapter)((HeaderViewListAdapter)listview.getAdapter()).getWrappedAdapter());
                historyAdapter.remove(topicName);
              //  historyAdapter.notifyDataSetChanged();


                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    public void onResume() {
        Log.e("DEBUG","onResume of SubHistory");
        TaskHistory taskHistory = new TaskHistory(getContext());
        taskHistory.execute("get_hist");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("DEBUG","onPause of SubHistory");
        super.onPause();
    }
}
