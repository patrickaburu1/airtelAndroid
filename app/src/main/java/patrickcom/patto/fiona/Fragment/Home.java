package patrickcom.patto.fiona.Fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import patrickcom.patto.fiona.Login;
import patrickcom.patto.fiona.R;

import static android.content.Context.MODE_PRIVATE;
import static patrickcom.patto.fiona.Login.MyPREF;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    Button myprofile,airtelMoney,tellFriend,logout;
    TextView loggedUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        myprofile= (Button) view.findViewById(R.id.myProfile);
        airtelMoney= (Button) view.findViewById(R.id.airtelMoney);
        tellFriend= (Button) view.findViewById(R.id.tellfriend);
        logout= (Button) view.findViewById(R.id.logout);

        loggedUser= (TextView) view.findViewById(R.id.loggedUser);

        airtelMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                f.replace(R.id.content, new AirtelMoney(),"Airtel");
                f.commit();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor= getActivity().getSharedPreferences(MyPREF,MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();

                Intent i = new Intent(getActivity().getApplicationContext(),Login.class);
                startActivity(i);
                //finish();
            }
        });

        SharedPreferences editor= getActivity().getSharedPreferences(MyPREF,MODE_PRIVATE);
        String user=editor.getString("USER",null);

        loggedUser.setText(user);

        return view;
    }

}
