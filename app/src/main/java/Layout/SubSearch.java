package Layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mnjru.tabbed_1828_dictionary.MainActivity;
import com.mnjru.tabbed_1828_dictionary.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import Utilities.DicDatabaseHelper;
import Utilities.TaskHistory;
import Utilities.ClipboardUtil;
import static Utilities.KeyboardUtility.hideKeyboardFrom;
import static androidx.core.content.ContextCompat.getSystemService;

/**
 * Created by mnjru on 5/21/2017.
 */

public class SubSearch extends Fragment{
    private Context context;
    AutoCompleteTextView autoComplete;
    ArrayAdapter<String> autoAdapter;
    TextView txtview;
    ImageButton btnclear;
    String[] item = new String[] {"Please search..."};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_search,container,false);

        // instantiate the TextView
        txtview = view.findViewById(R.id.textView1);
        // Set font size from Preferences
        txtview.setTextSize(TypedValue.COMPLEX_UNIT_SP, getSharedfontSize());
        txtview.setMovementMethod(new ScrollingMovementMethod());
        //find Search Imagebutton and register onClick event
        ImageButton ibutton = view.findViewById(R.id.imageButton1);
        ibutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // v is the button but we need the view returned from inflater earlier
                // necessary to make view final
                autoComplete = view.findViewById(R.id.autoCompleteTextView1);
                String topic = autoComplete.getText().toString();

                //get definition
                getCompleteDefinition(topic);
            }
        });
        // Copy button
        ImageButton ibuttonCopy = view.findViewById(R.id.imageButton2);
        ibuttonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardUtil cm = new ClipboardUtil();
                if(!txtview.getText().toString().isEmpty()) {
                    cm.copyToClipboard(context, txtview.getText().toString());
                    Snackbar.make(v, "Copied to clipboard", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        try{


            autoComplete = view.findViewById(R.id.autoCompleteTextView1);
            autoComplete.setThreshold(3);

            /*Add clear button */
            btnclear = (ImageButton)view.findViewById(R.id.btnclear);


            /*need to get x and y value of AutoCompleteTextView
             * Set the button inside the view
             * */
            btnclear.setRight(autoComplete.getRight());
            btnclear.setBottom(autoComplete.getBottom());
            btnclear.setVisibility(View.GONE);
            /*Set up the click listener*/
            btnclear.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    autoComplete.setText("");
                    btnclear.setVisibility(View.GONE);
                }
            });



            autoComplete.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                  /* Show the clear button if text in search box*/
                    if(s.length() != 0) {
                        btnclear.setVisibility(View.VISIBLE);
                       // Log.i("btnclear","inside onTextChanged >> btnclear.Visibility = VISIBLE");
                    } else {
                        btnclear.setVisibility(View.GONE);
                       // Log.i("btnclear","inside onTextChanged >> btnclear.Visibility = GONE");
                    }
                    DicDatabaseHelper db = new DicDatabaseHelper(getContext());
                    // query the database based on the user input
                   item = db.getAutosearchArray(s.toString());

                    // update the adapter

                    autoAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, item);
                    autoAdapter.notifyDataSetChanged();
                    autoComplete.setAdapter(autoAdapter);

                    // onItemClickListener to capture topic selected in dropdown
                    autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //String selected = (String) parent.getItemAtPosition(position);
                            String selected = ((TextView)view).getText().toString();


                            // get definition
                            getCompleteDefinition(selected);
                            
                        }
                    });
                }

                @Override
                public void afterTextChanged(Editable s) {

                }

            });

            // Listening for the enter key in virtual keyboard
            autoComplete.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ( (actionId == EditorInfo.IME_ACTION_DONE) ||
                            ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN ))){
                        String topic = autoComplete.getText().toString();
                        getCompleteDefinition(topic);
                        // hide keyboard
                        hideKeyboardFrom(getContext());
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            });


            autoComplete.setOnFocusChangeListener(new View.OnFocusChangeListener(){
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(v==autoComplete){
                        if(hasFocus) {
                            // TODO
                        }else{
                            //hideKeyboard
                            hideKeyboardFrom(requireContext());
                        }
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
        context=getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isResumed()){
            onResume();
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
        super.onResume();
        if(!getUserVisibleHint()){
            return;
        }
        /*hide the floating action button*/
        MainActivity mainActivity = (MainActivity)getActivity();
        if (mainActivity != null) {
            mainActivity.fab.setVisibility(View.GONE);
        }
        // Set up a new OnClickListener for the FAB
       /* MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ClipboardUtil cm = new ClipboardUtil();
                cm.copyToClipboard(context,txtview.getText().toString());
                Snackbar.make(v, "Copied to clipboard", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mainActivity.fab.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_copy_def));
        mainActivity.fab.show(); */
    }

    public void getCompleteDefinition(String input)
    {
        //make input proper case before searching database
        DicDatabaseHelper db = new DicDatabaseHelper(context);
        // query the database based on the user input
        String definition = db.getDefnition(input);
        //TODO
        // Need to search definition for extra topic
        // inside of format [ See *]
        // using Regex to highlight in click link color (possibly blue)

        db.close();
        // if empty, we don't want to proceed
        if(!definition.isEmpty()) {
            txtview.setText(definition);
            // Scroll the TextView to the top
            txtview.scrollTo(0, 0);


            Date dNow = new Date();
            SimpleDateFormat ft =
                    new SimpleDateFormat("E yyyy.MM.dd");
            TaskHistory taskHistory = new TaskHistory(getContext());
            taskHistory.execute("add_hist", input, ft.format(dNow));
        }
    }
    private int getSharedfontSize() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int szText = Integer.parseInt(sharedPreferences.getString("textsize_list", "-1"));
        switch (szText) {
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
