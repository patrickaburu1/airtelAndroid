package patrickcom.patto.fiona.Fragment;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import patrickcom.patto.fiona.Network.URLs;
import patrickcom.patto.fiona.R;
import patrickcom.patto.fiona.adapter.MiniStatementAdapter;
import patrickcom.patto.fiona.model.Statement;

import static android.content.Context.MODE_PRIVATE;
import static patrickcom.patto.fiona.Login.MyPREF;

/**
 * A simple {@link Fragment} subclass.
 */
public class MiniStatement extends Fragment {

    List<Statement> statement;
    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;
    ProgressDialog pDialog;

    RequestQueue requestQueue ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_mini_statement, container, false);
        statement = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.miniStatementRecycler);

        //progressBar = (ProgressBar) view.findViewById(R.id.progressTransactio);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        mini();

        return view;
    }

    private void mini() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...!");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        SharedPreferences editor= getActivity().getSharedPreferences(MyPREF,MODE_PRIVATE);
        int CUSTOMER_ID=editor.getInt("ID",0);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLs.MINISTATEMENT+"/"+CUSTOMER_ID,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        pDialog.dismiss();

                        json_parse_data(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                        f.replace(R.id.content, new AirtelMoney(),"Airtel");
                        f.commit();

                        Toast.makeText(getActivity(), "Something went wrong!PLEASE CHECK YOUR NETWORK CONNECTION", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(jsonArrayRequest);
    }

    public void  json_parse_data(JSONArray array){
        for(int i = 0; i<array.length(); i++) {

            Statement GetDataAdapter2 = new Statement();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter2.setAmount(json.getString("amount"));
                GetDataAdapter2.setCode(json.getString("code"));
                GetDataAdapter2.setDate(json.getString("created_at"));
                GetDataAdapter2.setType(json.getInt("type"));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            statement.add(GetDataAdapter2);
        }

        recyclerViewadapter = new MiniStatementAdapter(statement, getActivity());

        recyclerView.setAdapter(recyclerViewadapter);
    }


}
