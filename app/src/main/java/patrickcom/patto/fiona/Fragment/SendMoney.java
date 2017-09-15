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
import android.widget.EditText;
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
import static patrickcom.patto.fiona.R.id.sendPhone;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendMoney extends Fragment {

    ProgressDialog pDialog;
    EditText phone,amount;
    Button sendButton;
    String phone1,amount1;
    public SendMoney() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_send_money, container, false);

        phone= (EditText) view.findViewById(R.id.sendPhone);
        amount= (EditText) view.findViewById(R.id.sendAmount);

        sendButton= (Button) view.findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });

        return view;
    }

    private void send(){

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        amount1=amount.getText().toString();
        phone1=phone.getText().toString();

        Map<String, String> params = new HashMap<String, String>();
        params.put("amount", amount1);
        params.put("phone", phone1);

        SharedPreferences editor= getActivity().getSharedPreferences(MyPREF,MODE_PRIVATE);
        int CUSTOMER_ID=editor.getInt("ID",0);


        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URLs.SEND+"/"+CUSTOMER_ID, params,
                new Response.Listener<JSONObject>() {
                    int success;

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            success = response.getInt("id");
                            if (success >1) {


                                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                                f.replace(R.id.content, new AirtelMoney(), getString(R.string.app_name));
                                f.commit();
                                Toast.makeText(getActivity(), "Successfully Sent", Toast.LENGTH_SHORT).show();
                            } else if (success ==1)
                                {


                                Toast.makeText(getActivity(), "Insufficient Balance", Toast.LENGTH_SHORT).show();

                            }
                            else if (success ==0)
                            {
                                Toast.makeText(getActivity(), "Mobile Number Does'nt Exist", Toast.LENGTH_SHORT).show();

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
