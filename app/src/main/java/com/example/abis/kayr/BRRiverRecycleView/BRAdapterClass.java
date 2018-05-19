package com.example.abis.kayr.BRRiverRecycleView;

/**
 * Created by abis.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.abis.kayr.R;
import com.example.abis.kayr.BRActivities.NewData;
import com.example.abis.kayr.loginRegister.ProfileActivity;

import java.util.List;

public class BRAdapterClass extends RecyclerView.Adapter<BRAdapterClass.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;

    public BRAdapterClass(List<ListItem> listItems, Context context) {
        this.context=context;
        this.listItems=listItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.br_rivercardview, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem listItem=listItems.get(position);
        holder.RiverName.setText(listItem.getRiverName());
        holder.LastUpdate.setText(listItem.getLastUpdate());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, NewData.class);
                i.putExtra("RiverName", listItem.getRiverName());
                i.putExtra("UserID", ProfileActivity.mUserID);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
       public TextView RiverName, LastUpdate;
      public LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            RiverName=(TextView)view.findViewById(R.id.RiverName);
            LastUpdate=(TextView)view.findViewById(R.id.LastUpdate);
            linearLayout=(LinearLayout)view.findViewById(R.id.linear_layout);
        }
    }
}
