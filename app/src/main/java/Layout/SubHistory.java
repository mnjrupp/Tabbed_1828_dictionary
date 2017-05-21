package Layout;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mnjru.tabbed_1828_dictionary.R;

/**
 * Created by mnjru on 5/21/2017.
 */

public class SubHistory extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container,false);
        return view;
    }
}
