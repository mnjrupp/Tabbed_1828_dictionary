package Layout;

import static Utilities.KeyboardUtility.hideKeyboardFrom;

import androidx.fragment.app.Fragment;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.mnjru.tabbed_1828_dictionary.DisplayTopics;
//import com.mnjru.tabbed_1828_dictionary.MainActivity;
import com.mnjru.tabbed_1828_dictionary.R;

/**
 * Created by mnjru on 5/21/2017.
 */

public class SubAZ  extends Fragment implements View.OnClickListener{

    public final static String ALPHA = "alpha_message";
    //ListView listView;
    //ArrayAdapter listAdapter;
    //private String[] items = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P",
    //"Q","R","S","T","U","V","W","X","Y","Z"};
   // public SubAZ(){}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_az,container,false);
        //listView = (ListView) view.findViewById(R.id.fragment);


        // override the ArrayAdapter creation to set text size
       /*listAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_list_item,android.R.id.text1, items){
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
        });*/
        Button buttonA = view.findViewById(R.id.buttonA);Button buttonB = view.findViewById(R.id.buttonB);Button buttonC = view.findViewById(R.id.buttonC);
        Button buttonD = view.findViewById(R.id.buttonD);Button buttonE = view.findViewById(R.id.buttonE);Button buttonF = view.findViewById(R.id.buttonF);
        Button buttonG = view.findViewById(R.id.buttonG);Button buttonH = view.findViewById(R.id.buttonH);Button buttonI = view.findViewById(R.id.buttonI);
        Button buttonJ = view.findViewById(R.id.buttonJ);Button buttonK = view.findViewById(R.id.buttonK);Button buttonL = view.findViewById(R.id.buttonL);
        Button buttonM = view.findViewById(R.id.buttonM);Button buttonN = view.findViewById(R.id.buttonN);Button buttonO = view.findViewById(R.id.buttonO);
        Button buttonP = view.findViewById(R.id.buttonP);Button buttonQ = view.findViewById(R.id.buttonQ);Button buttonR = view.findViewById(R.id.buttonR);
        Button buttonS = view.findViewById(R.id.buttonS);Button buttonT = view.findViewById(R.id.buttonT);Button buttonU = view.findViewById(R.id.buttonU);
        Button buttonV = view.findViewById(R.id.buttonV);Button buttonW = view.findViewById(R.id.buttonW);Button buttonX = view.findViewById(R.id.buttonX);
        Button buttonY = view.findViewById(R.id.buttonY);Button buttonZ = view.findViewById(R.id.buttonZ);

        buttonA.setOnClickListener(this);buttonB.setOnClickListener(this);buttonC.setOnClickListener(this);
        buttonD.setOnClickListener(this);buttonE.setOnClickListener(this);buttonF.setOnClickListener(this);
        buttonG.setOnClickListener(this);buttonH.setOnClickListener(this);buttonI.setOnClickListener(this);
        buttonJ.setOnClickListener(this);buttonK.setOnClickListener(this);buttonL.setOnClickListener(this);
        buttonM.setOnClickListener(this);buttonN.setOnClickListener(this);buttonO.setOnClickListener(this);
        buttonP.setOnClickListener(this);buttonQ.setOnClickListener(this);buttonR.setOnClickListener(this);
        buttonS.setOnClickListener(this);buttonT.setOnClickListener(this);buttonU.setOnClickListener(this);
        buttonV.setOnClickListener(this);buttonW.setOnClickListener(this);buttonX.setOnClickListener(this);
        buttonY.setOnClickListener(this);buttonZ.setOnClickListener(this);
        // Hide keyboard
        hideKeyboardFrom(getContext());
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
        // Hide keyboard
        hideKeyboardFrom(getContext());
        //if(!getUserVisibleHint()){
        //
       // }
        // Set up a new OnClickListener for the FAB
      //  MainActivity mainActivity = (MainActivity)getActivity();
      // mainActivity.fab.hide();
        /*mainActivity.fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "This is the Alpha Screen", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

   /* @RequiresApi(api = Build.VERSION_CODES.M)
    private int getSharedfontSize(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        int szText = Integer.parseInt(sharedPreferences.getString("textsize_list","-1"));
        switch(szText){
            case -1:
                // return default text size
                return 14;
            default:
                // using the int array from Main Activity
                return MainActivity.fSize[szText];

        }
    }*/


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        Button buttonPressed = (Button)v;
        String buttonText = buttonPressed.getText().toString();
        Intent intent = new Intent(getContext(), DisplayTopics.class);
        intent.putExtra(ALPHA,buttonText);
        startActivity(intent);


    }
}
