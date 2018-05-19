package com.example.abis.kayr.SearchRiverListing;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abis.kayr.R;
import com.example.abis.kayr.ShowData;
import com.example.abis.kayr.ShowIng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abis.
 */

public class AdapterClass_River extends RecyclerView.Adapter<AdapterClass_River.HolderView> {

    private List<Item> riverList;
    private Context context;

    public AdapterClass_River(List<Item> riverList, Context context) {
        this.riverList = riverList;
        this.context = context;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
        .inflate(R.layout.custom_rivers, parent, false);
        return new HolderView(v);
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
        holder.txRiver.setText(riverList.get(position).getRiverName());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, ShowIng.class);
                i.putExtra("River_Name", riverList.get(position).getRiverName());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return riverList.size();
    }
    public void setfilter(List<Item> listitem)
    {
        riverList=new ArrayList<>();
        riverList.addAll(listitem);
        notifyDataSetChanged();
    }
    class HolderView extends RecyclerView.ViewHolder{

        TextView txRiver;
        View v;

        public HolderView(View itemView) {
            super(itemView);
            v=itemView;
            this.txRiver = (TextView) itemView.findViewById(R.id.tx_RiverName);
        }
    }
}
