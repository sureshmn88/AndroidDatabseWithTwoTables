package com.example.intel.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class MobileAdapter extends RecyclerView.Adapter<MobileAdapter.MyViewHlder> {

    Context mContext;
    ArrayList<String> mList;

    public MobileAdapter(Context mContext, ArrayList<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mobiledata,null);
        MyViewHlder viewHlder=new MyViewHlder(view);
        return viewHlder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHlder holder, int position) {
        mList.get(position);
        holder.mobileTxt.setText(mList.get(position));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHlder extends RecyclerView.ViewHolder {
        TextView mobileTxt;
        public MyViewHlder(View itemView) {
            super(itemView);
            mobileTxt=(TextView)itemView.findViewById(R.id.mobileadapter_txt);

        }
    }
}
