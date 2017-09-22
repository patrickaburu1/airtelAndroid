package patrickcom.patto.fiona.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import patrickcom.patto.fiona.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AirtelMoney extends Fragment {

RelativeLayout send,buy,withdraw,myaccount;
    EditText miniStatement,fullStatement;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_airtel_money, container, false);

        send= (RelativeLayout) view.findViewById(R.id.sendMoney);
        buy= (RelativeLayout) view.findViewById(R.id.relativeBuyAitime);
        withdraw= (RelativeLayout) view.findViewById(R.id.relativeWithdraw);
        myaccount= (RelativeLayout) view.findViewById(R.id.relativeMyaccount);
        miniStatement= (EditText) view.findViewById(R.id.miniStatement);
        fullStatement= (EditText) view.findViewById(R.id.fullStatement);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                f.replace(R.id.content, new SendMoney(),"Airtel");
                f.commit();
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                f.replace(R.id.content, new BuyAirtime(),"Airtel");
                f.commit();
            }
        });

        myaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                f.replace(R.id.content, new MyAccount(),"Airtel");
                f.commit();
            }
        });

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        miniStatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                f.replace(R.id.content, new MiniStatement(),"Airtel");
                f.commit();
            }
        });

        return view;
    }

}
