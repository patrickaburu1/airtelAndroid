package patrickcom.patto.fiona.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import patrickcom.patto.fiona.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AirtelMoney extends Fragment {

RelativeLayout send,buy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_airtel_money, container, false);

        send= (RelativeLayout) view.findViewById(R.id.sendMoney);
        buy= (RelativeLayout) view.findViewById(R.id.relativeBuyAitime);

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

        return view;
    }

}
