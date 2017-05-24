package Layout;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mnjru.tabbed_1828_dictionary.R;

import Utilities.DicDatabaseHelper;

/**
 * Created by mnjru on 5/21/2017.
 */

public class SubSearch extends Fragment{
    AutoCompleteTextView autoComplete;
    ArrayAdapter<String> autoAdapter;
    TextView txtview;
    String[] item = new String[] {"Please search..."};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        // instantiate the TextView
        txtview = (TextView) view.findViewById(R.id.textView1);
        txtview.setMovementMethod(new ScrollingMovementMethod());
        //find Imagebutton and register onClick event
        ImageButton ibutton = (ImageButton) view.findViewById(R.id.imageButton1);
        ibutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                autoComplete = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextView1);
                String topic = autoComplete.getText().toString();
                getCompleteDefinition(topic);
            }
        });
        try{


            autoComplete = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView1);
            autoComplete.setThreshold(3);
            autoComplete.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    DicDatabaseHelper db = new DicDatabaseHelper(getContext());
                    // query the database based on the user input
                   item = db.getAutosearchArray(s.toString());

                    // update the adapter
                    autoAdapter.notifyDataSetChanged();
                    autoAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, item);
                    autoComplete.setAdapter(autoAdapter);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            //autoAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,item);
            //autoComplete.setAdapter(autoAdapter);

            // Listening for the enter key in virtual keyboard
            autoComplete.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ( (actionId == EditorInfo.IME_ACTION_DONE) ||
                            ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN ))){
                        String topic = autoComplete.getText().toString();
                        getCompleteDefinition(topic);
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            });

        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e("MainActivity",e.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity",e.toString());
        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void getCompleteDefinition(String input)
    {
        //TODO make input proper case before searching database
        DicDatabaseHelper db = new DicDatabaseHelper(getContext());
        // query the database based on the user input
        txtview.setText(db.getDefnition(input));
        // Scroll the TextView to the top
        txtview.scrollTo(0, 0);
    }
}
