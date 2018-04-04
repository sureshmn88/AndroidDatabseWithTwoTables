package com.example.intel.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

class ProductData extends RecyclerView.Adapter<ProductData.MyViewHolder> {
    Context mContext;
    OnClickListener onClickListener;
    ArrayList<Product> mList;


    public ProductData(Context mContext, ArrayList<Product> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public interface OnClickListener {
        void onLayoutClick(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.createlistdata,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product item=mList.get(position);
        holder.name.setText(item.getId() + " - " + item.getName());
        holder.email.setText(item.getEmail());
        String mobileNo="";
        for (int i=0;i<item.getPhoneno().size();i++){
            mobileNo=mobileNo+item.getPhoneno().get(i)+" - ";
        }
            holder.mobile.setText(mobileNo);



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,email,mobile;
        LinearLayout mLayout;
        public MyViewHolder(View itemView) {
            super(itemView);

            name=(TextView)itemView.findViewById(R.id.name_txt);
            mobile=(TextView)itemView.findViewById(R.id.phone_txt);
            email=(TextView)itemView.findViewById(R.id.email_txt);
            mLayout=(LinearLayout)itemView.findViewById(R.id.layout_list);
            mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener!=null)
                        onClickListener.onLayoutClick(getAdapterPosition());
                }
            });
        }

    }
}
