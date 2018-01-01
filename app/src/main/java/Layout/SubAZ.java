package Layout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mnjru.tabbed_1828_dictionary.DisplayTopics;
import com.example.mnjru.tabbed_1828_dictionary.MainActivity;
import com.example.mnjru.tabbed_1828_dictionary.R;

import org.w3c.dom.Text;

/**
 * Created by mnjru on 5/21/2017.
 */

public class SubAZ  extends Fragment {

    public final static String ALPHA = "alpha_message";
    ListView listView;
    ArrayAdapter listAdapter;
    private String[] items = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P",
    "Q","R","S","T","U","V","W","X","Y","Z"};
   // public SubAZ(){}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_az,container,false);
        listView = (ListView) view.findViewById(R.id.list_az);

        // Hide the FloatingActionButton


        // override the ArrayAdapter creation to set text size
        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_list_item,android.R.id.text1, items){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                // Set the text size from Preference
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,getSharedfontSize());
                // Return the view
                return view;
            }
        };

        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               String alpha = listView.getItemAtPosition(position).toString();
               Intent intent = new Intent(getContext(), DisplayTopics.class);
                intent.putExtra(ALPHA,alpha);
                startActivity(intent);

            }
        });
        return view;
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
        // Set up a new OnClickListener for the FAB
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.fab.hide();
        mainActivity.fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "This is the Alpha Screen", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private int getSharedfontSize(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        int szText = Integer.parseInt(sharedPreferences.getString("textsize_list","-1"));
        switch(szText){
            case -1:
                // return default text size
                int szdefault = 14;
                return szdefault;
            default:
                // using the int array from Main Activity
                return MainActivity.fSize[szText];

        }
    }


}
