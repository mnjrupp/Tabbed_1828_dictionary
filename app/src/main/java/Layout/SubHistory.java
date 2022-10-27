package Layout;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

import com.mnjru.tabbed_1828_dictionary.MainActivity;
import com.mnjru.tabbed_1828_dictionary.R;

import Utilities.HistoryAdapter;
import Utilities.HistoryDBHelper;
import Utilities.TaskHistory;
import Utilities.Topic;

import static Utilities.KeyboardUtility.hideKeyboardFrom;

/**
 * Created by mnjru on 5/21/2017.
 */

public class SubHistory extends Fragment {
    ListView listview;
    HistoryDBHelper historyDBHelper;
    HistoryAdapter historyAdapter;
    SQLiteDatabase db;
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
        // If header, we want to return false
        if(contextMenuInfo.position==0){return false;}
        switch(item.getItemId()){
            case R.id.id_delete:

                String topicName = ((Topic)listview.getItemAtPosition(contextMenuInfo.position)).getTopic().toString();
                historyDBHelper = new HistoryDBHelper(getContext());
                db = historyDBHelper.getWritableDatabase();
                historyDBHelper.deleteTopicHistory(topicName,db);
                historyAdapter = ((HistoryAdapter)((HeaderViewListAdapter)listview.getAdapter()).getWrappedAdapter());
                historyAdapter.remove(topicName);
                return true;
            case R.id.id_delete_all:
                historyDBHelper = new HistoryDBHelper(getContext());
                db = historyDBHelper.getWritableDatabase();
                historyDBHelper.deleteHistoryAll(db);
                historyAdapter = ((HistoryAdapter)((HeaderViewListAdapter)listview.getAdapter()).getWrappedAdapter());
                historyAdapter.clear();
                historyAdapter.notifyDataSetChanged();
                //listview.refreshDrawableState();
                Snackbar.make(getActivity().findViewById(R.id.list_history), "History cleared out", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isResumed()){
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!getUserVisibleHint()){
            return;
        }
        // Hide keyboard
        hideKeyboardFrom(getContext());
        //Log.e("DEBUG","onResume of SubHistory");
        TaskHistory taskHistory = new TaskHistory(getContext());
        taskHistory.execute("get_hist");
        //TODO Remove Floating Toolbar for Delete history
        // and move to settings page

        // Set up a new OnClickListener for the FAB
       /* MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                HistoryDBHelper historyDBHelper = new HistoryDBHelper(getContext());
                SQLiteDatabase db = historyDBHelper.getWritableDatabase();
                historyDBHelper.deleteHistoryAll(db);
                HistoryAdapter historyAdapter = ((HistoryAdapter)((HeaderViewListAdapter)listview.getAdapter()).getWrappedAdapter());
                historyAdapter.clear();
                historyAdapter.notifyDataSetChanged();
                //listview.refreshDrawableState();
                Snackbar.make(v, "History cleared out", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mainActivity.fab.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_action_delete_all));
        mainActivity.fab.show();
        */

    }


    @Override
    public void onPause() {

        super.onPause();
    }
}
