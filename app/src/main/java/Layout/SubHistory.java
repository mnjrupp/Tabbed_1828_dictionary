package Layout;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
        TaskHistory taskHistory = new TaskHistory(getContext());
        taskHistory.execute("get_hist");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container,false);
        return view;
    }
}
