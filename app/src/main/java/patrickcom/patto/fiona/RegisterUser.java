package patrickcom.patto.fiona;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class RegisterUser extends AppCompatActivity {

    EditText fname,lname,phone,password;
    Button register;
    String fname1,lname1,phone1,password1;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        fname= (EditText) findViewById(R.id.registerFirstName);
        lname= (EditText) findViewById(R.id.registerLastName);
        phone= (EditText) findViewById(R.id.registerPhone);
        password= (EditText) findViewById(R.id.registerPassword);

        register= (Button) findViewById(R.id.createAccount);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
                //validate();
            }
        });

    }

    //register
    public void createAccount(){

        pDialog = new ProgressDialog(RegisterUser.this);
        pDialog.setMessage("Creating Account...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        fname1=fname.getText().toString();
        lname1=lname.getText().toString();
        phone1=phone.getText().toString();
        password1=password.getText().toString();


        Map<String, String> params = new HashMap<String, String>();
        params.put("phone", phone1);
        params.put("fname", fname1);
        params.put("lname", lname1);
        params.put("password", password1);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URLs.REGISTER_USER, params,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        //google.setVisibility(View.INVISIBLE);
                        try {
                            Log.d("Login step one!", response.toString());

                            final int success = response.getInt("id");
                            // final int status = response.getInt("status");

                            if (success >= 1) {
                                Log.d("Register Successful!", response.toString());

                                int id = response.getInt("id");

                                new SweetAlertDialog(RegisterUser.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Successfully")
                                        .setContentText("Created, Login To Continue")
                                        .show();

                                Intent i = new Intent(getApplicationContext(), Login.class);
                                startActivity(i);
                                finish();


                            }

                            else {
                                new SweetAlertDialog(RegisterUser.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("OOPS")
                                        .setContentText("Something went wrong wrong please try again later")
                                        .show();


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                pDialog.dismiss();
                //  google.setVisibility(View.INVISIBLE);
                Log.d("Response: ", response.toString());
//                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
//                        .setTitleText("OOPS")
//                        .setContentText("INCORRECT USERNAME OR PASSWORD")
//                        .show();
                Toast.makeText(RegisterUser.this, "NETWORK ERROR", Toast.LENGTH_SHORT).show();

            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
    @Override
    public void onBackPressed() {
    }
}
