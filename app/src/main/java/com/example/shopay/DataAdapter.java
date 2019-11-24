package com.example.shopay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    public  List<history> getdatalist;

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mview;
        TextView txtprice, txtproduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           mview=itemView;
           txtprice= mview.findViewById(R.id.txt1);
            txtproduct=mview.findViewById(R.id.txt2);
        }
    }



    public DataAdapter(List<history> histories) {

        getdatalist=histories;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.datashow,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtprice.setText(getdatalist.get(position).getPrice());
        holder.txtproduct.setText(getdatalist.get(position).getProduct());


    }



    @Override
    public int getItemCount() {
        return getdatalist.size();
    }
}
