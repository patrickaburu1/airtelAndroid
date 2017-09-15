package patrickcom.patto.fiona.Fragment;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import patrickcom.patto.fiona.Network.AppController;
import patrickcom.patto.fiona.Network.CustomRequest;
import patrickcom.patto.fiona.Network.URLs;
import patrickcom.patto.fiona.R;

import static android.content.Context.MODE_PRIVATE;
import static patrickcom.patto.fiona.Login.MyPREF;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyAirtime extends Fragment {


  RadioButton my,other;
    LinearLayout myNo,otherNo;
    EditText myAmount,otherPhone,otherAmount;
    Button myContinue,otherContinue;
    String amount,phone;
    ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_buy_airtime, container, false);

        myAmount= (EditText) view.findViewById(R.id.buyMyAmount);
        myContinue= (Button) view.findViewById(R.id.buyMyButton);

        otherPhone= (EditText) view.findViewById(R.id.buyOtherPhone);
        otherAmount= (EditText) view.findViewById(R.id.buyOtherAmount);
        otherContinue= (Button) view.findViewById(R.id.buyOtherButton);

        my= (RadioButton) view.findViewById(R.id.radioMyNumber);
        other= (RadioButton) view.findViewById(R.id.radioOtherNumber);

        myNo= (LinearLayout) view.findViewById(R.id.linearMyNumber);
        otherNo = (LinearLayout) view.findViewById(R.id.linearOtherNumber);

        my.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                otherNo.setVisibility(View.VISIBLE);
                myNo.setVisibility(View.GONE);
            }
        });

        other.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                myNo.setVisibility(View.VISIBLE);
                otherNo.setVisibility(View.GONE);


            }
        });

        myContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyMy();
            }
        });
        otherContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyOther();
            }
        });

        return view;
    }

    private void buyMy(){

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        amount=myAmount.getText().toString();


        Map<String, String> params = new HashMap<String, String>();
        params.put("amount", amount);


        SharedPreferences editor= getActivity().getSharedPreferences(MyPREF,MODE_PRIVATE);
        int CUSTOMER_ID=editor.getInt("ID",0);


        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URLs.BUY_MY+"/"+CUSTOMER_ID, params,
                new Response.Listener<JSONObject>() {
                    int success;

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            success = response.getInt("id");
                            if (success >=1) {


                                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                                f.replace(R.id.content, new AirtelMoney(), getString(R.string.app_name));
                                f.commit();
                                Toast.makeText(getActivity(), "Successfully Bought", Toast.LENGTH_SHORT).show();
                            } else if (success ==0)
                            {


                                Toast.makeText(getActivity(), "Insufficient Balance", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                pDialog.dismiss();
                Log.d("Response: ", response.toString());
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("OoPS...!!")
                        .setContentText("PLEASE TRY AGAIN"+response)
                        .show();

            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    private void buyOther(){

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        amount=otherAmount.getText().toString();
        phone=otherPhone.getText().toString();

        Map<String, String> params = new HashMap<String, String>();
        params.put("amount", amount);
        params.put("phone", phone);

        SharedPreferences editor= getActivity().getSharedPreferences(MyPREF,MODE_PRIVATE);
        int CUSTOMER_ID=editor.getInt("ID",0);


        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URLs.BUY_OTHER+"/"+CUSTOMER_ID, params,
                new Response.Listener<JSONObject>() {
                    int success;

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            success = response.getInt("id");
                            if (success >=1) {


                                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                                f.replace(R.id.content, new AirtelMoney(), getString(R.string.app_name));
                                f.commit();
                                Toast.makeText(getActivity(), "Successfully Bought", Toast.LENGTH_SHORT).show();

                            } else if (success ==0)
                            {


                                Toast.makeText(getActivity(), "Insufficient Balance", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                pDialog.dismiss();
                Log.d("Response: ", response.toString());
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("OoPS...!!")
                        .setContentText("PLEASE TRY AGAIN"+response)
                        .show();

            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
}

