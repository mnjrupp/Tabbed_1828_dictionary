package Layout;

import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mnjru.tabbed_1828_dictionary.R;

/**
 * Created by mnjru on 5/21/2017.
 */

public class SubAZ  extends Fragment{
    ListView listView;
    ArrayAdapter<String> listAdapter;
    private String[] items = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P",
    "Q","R","S","T","U","V","W","X","Y","Z"};
    public SubAZ(){}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_az,container,false);
        listView = (ListView) view.findViewById(R.id.list_az);
        listAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, items);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        return view;
    }
}
