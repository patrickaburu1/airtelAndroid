package patrickcom.patto.fiona.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import patrickcom.patto.fiona.R;
import patrickcom.patto.fiona.model.Statement;

/**
 * Created by patto on 9/22/2017.
 */

public class MiniStatementAdapter extends RecyclerView.Adapter<MiniStatementAdapter.ViewHolder> {
    Context context;
    List<Statement> statement;

    public MiniStatementAdapter(List<Statement> statement, Context context){
        super();
        this.statement=statement;
        this.context=context;
    }


    @Override
    public MiniStatementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_mini_statement, parent, false);

        MiniStatementAdapter.ViewHolder viewHolder = new MiniStatementAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MiniStatementAdapter.ViewHolder holder, int position) {

        Statement getFareAdapter1 = statement.get(position);

        holder.no.setText(getFareAdapter1.getId());
        holder.code.setText(getFareAdapter1.getCode());
        holder.amount.setText(getFareAdapter1.getAmount());
        holder.date.setText(getFareAdapter1.getDate());
        //holder.type.setText(getFareAdapter1.getType());
        int type=getFareAdapter1.getType();
        if (type==1){
            holder.type.setText("Sent");
        }

        else if(type==2){
            holder.type.setText("Airtime");
        }
        else if(type==3){
            holder.type.setText("Withdrew");
        }
        else if(type==4){
            holder.type.setText("Deposit");
        }


    }

    //get counts
    @Override
    public int getItemCount() {

        return statement.size();
    }


    //data holders

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView code,amount,type,date,no;



        public ViewHolder(View itemView) {

            super(itemView);

            code = (TextView) itemView.findViewById(R.id.code);
            amount = (TextView) itemView.findViewById(R.id.statementAmount);
            type = (TextView) itemView.findViewById(R.id.statementType);
            date = (TextView) itemView.findViewById(R.id.stamentDate);
            no = (TextView) itemView.findViewById(R.id.inc);



        }
    }



}
