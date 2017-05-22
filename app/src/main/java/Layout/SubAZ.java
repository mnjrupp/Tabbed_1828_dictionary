package Layout;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mnjru.tabbed_1828_dictionary.R;

/**
 * Created by mnjru on 5/21/2017.
 */

public class SubAZ  extends Fragment{
    public SubAZ(){}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_az,container,false);
        return view;
    }
}
