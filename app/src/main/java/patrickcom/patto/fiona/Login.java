package patrickcom.patto.fiona;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import patrickcom.patto.fiona.Network.AppController;
import patrickcom.patto.fiona.Network.CustomRequest;
import patrickcom.patto.fiona.Network.URLs;

import static android.R.attr.id;

public class Login extends AppCompatActivity {

    EditText phone,password;
    Button login;
    LinearLayout registerUser;

    String phone1,password1;
    ProgressDialog pDialog;
    public static final String MyPREF="mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone= (EditText) findViewById(R.id.phone);
        password= (EditText) findViewById(R.id.password);

        login= (Button) findViewById(R.id.login);

        registerUser= (LinearLayout) findViewById(R.id.linearRegister);

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start register activity
                Intent i = new Intent(getApplicationContext(), RegisterUser.class);
                startActivity(i);
                //finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  validate();
                loginn();
            }
        });

    }
    //login
    public void loginn(){

        pDialog = new ProgressDialog(Login.this);
        pDialog.setMessage("Signing In...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        phone1 = phone.getText().toString();
        password1 = password.getText().toString();

        Map<String, String> params = new HashMap<String, String>();
        params.put("phone", phone1);
        params.put("password", password1);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URLs.LOGIN, params,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        //google.setVisibility(View.INVISIBLE);
                        try {
                            Log.d("Login step one!", response.toString());

                            final int success = response.getInt("role");
                            // final int status = response.getInt("status");

                            if (success == 1) {
                                Log.d("Login Successful!", response.toString());

                                int id = response.getInt("id");
                                String user = response.getString("firstName")+"  "+response.getString("lastName");

                                //sharedprefference
                                SharedPreferences.Editor editor = getSharedPreferences(MyPREF, MODE_PRIVATE).edit();
                                editor.putInt("ID",id);
                                editor.putInt("ROLE",success);
                                editor.putString("USER",user);
                                editor.commit();

                              //  Toast.makeText(Login.this, "user ", Toast.LENGTH_LONG).show();

                                //Staring MainActivity
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                finish();

                            }

                            else if(success == 2){

                                //sharedprefference
                                SharedPreferences.Editor editor = getSharedPreferences(MyPREF, MODE_PRIVATE).edit();
                                editor.putInt("ID",id);
                                editor.putInt("ROLE",success);
                                editor.commit();


                               // Toast.makeText(LoginActivity.this, "admin "+success, Toast.LENGTH_LONG).show();
                                Intent j = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(j);
                                finish();
                            }
                            else if(success==0)
                            {
                                Toast.makeText(Login.this, "Credentials Doesnt Match", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Login.this, "NETWORK ERROR", Toast.LENGTH_SHORT).show();

            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
    @Override
    public void onBackPressed() {
    }
}

