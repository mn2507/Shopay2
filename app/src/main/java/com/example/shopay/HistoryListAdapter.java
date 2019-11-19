package com.example.shopay;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoryListAdapter  extends ArrayAdapter<history> {


    public HistoryListAdapter(Context context, List<history> object){
        super(context,0, object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.listview,parent,false);
        }

        TextView price = (TextView) convertView.findViewById(R.id.textView1);
        TextView product = (TextView) convertView.findViewById(R.id.textView2);

        history mission = getItem(position);

        product.setText(mission.getProduct());
        price.setText(mission.getPrice());


        return convertView;
    }

}
