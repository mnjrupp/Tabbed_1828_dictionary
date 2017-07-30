package Layout;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mnjru.tabbed_1828_dictionary.R;

import Utilities.TaskHistory;

/**
 * Created by mnjru on 5/21/2017.
 */

public class SubHistory extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container,false);

        return view;
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
