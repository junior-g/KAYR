package com.example.abis.kayr.RiverPlansDetails;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abis.kayr.R;
import com.example.abis.kayr.stateRivers;

import java.util.List;

/**
 * Created by abis.
 */

public class PlanAdapterClass extends RecyclerView.Adapter<PlanAdapterClass.ViewHolder> {
   private List<PlanListItems> planListItems;
   private Context context;

    public PlanAdapterClass(List<PlanListItems> planListItems, Context context) {
        this.planListItems = planListItems;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plan_card_view, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PlanListItems planListItem=planListItems.get(position);
        holder.txTitle.setText(planListItem.getTitle());
        holder.txDiscription.setText("Water Bodies:-"+planListItem.getDiscription());
        //holder.imageView.setImageResource(planListItem.getImgSrc());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, stateRivers.class);
                i.putExtra("state", planListItem.getTitle().toLowerCase());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return planListItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
       public ImageView imageView;
      public   TextView txTitle, txDiscription;
      View v;
        public ViewHolder(View view)
        {
            super(view);
            v=view;
            //imageView=(ImageView)view.findViewById(R.id.mImageView);
            txTitle=(TextView)view.findViewById(R.id.tx_Title);
            txDiscription=(TextView)view.findViewById(R.id.tx_discription);

        }
    }
}
