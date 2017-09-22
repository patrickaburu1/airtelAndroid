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
import android.widget.LinearLayout;
import android.widget.TextView;
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
public class MyAccount extends Fragment {
LinearLayout checkBalance,realBalance;
    TextView balance;
    ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view= inflater.inflate(R.layout.fragment_my_account, container, false);
        checkBalance= (LinearLayout) view.findViewById(R.id.checkBalance);
        realBalance= (LinearLayout) view.findViewById(R.id.realBalance);

        balance= (TextView) view.findViewById(R.id.myAccountBalance);

        checkBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realBalance.setVisibility(View.VISIBLE);

                getBalance();
            }
        });
        realBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realBalance.setVisibility(View.GONE);
            }
        });

        return view;
    }

    private void getBalance() {

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();



        Map<String, String> params = new HashMap<String, String>();

        SharedPreferences editor= getActivity().getSharedPreferences(MyPREF,MODE_PRIVATE);
        int CUSTOMER_ID=editor.getInt("ID",0);


        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URLs.CHECK_BALANCE+"/"+CUSTOMER_ID, params,
                new Response.Listener<JSONObject>() {
                    int success,balance1;

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
//                            success = response.getInt("id");
//                            if (success >=1) {
                                 balance1= response.getInt("balance");
                                balance.setText("KSH:: "+balance1);

                          //  }

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
