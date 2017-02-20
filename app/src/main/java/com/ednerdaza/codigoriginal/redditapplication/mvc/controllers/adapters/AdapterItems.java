package com.ednerdaza.codigoriginal.redditapplication.mvc.controllers.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ednerdaza.codigoriginal.redditapplication.R;
import com.ednerdaza.codigoriginal.redditapplication.mvc.controllers.interfaces.DelegateItemAdapter;
import com.ednerdaza.codigoriginal.redditapplication.mvc.models.entities.Children;
import com.ednerdaza.codigoriginal.redditapplication.mvc.models.entities.Data;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by administrador on 16/02/17.
 */
public class AdapterItems extends RecyclerView.Adapter<AdapterItems.ItemsViewHolder> {

    // delegate para que reciba el onclick desde fuera del adapter
    private DelegateItemAdapter mDelegateItemAdapter;
    public DelegateItemAdapter getDelegate() {
        return mDelegateItemAdapter;
    }
    public void setDelegate(DelegateItemAdapter mDelegateItemAdapter) {
        this.mDelegateItemAdapter = mDelegateItemAdapter;
    }

    List<Children> items;
    Activity context;

    //region CONSTRUCTOR

        public AdapterItems(Activity context, List<Children> items){
            this.context = context;
            this.items = items;
        }

    //endregion

    //region METODOS DEL ADAPTER

        @Override
        public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main, parent, false);
            ItemsViewHolder itemsViewHolder = new ItemsViewHolder(v);
            return itemsViewHolder;
        }

        @Override
        public void onBindViewHolder(final ItemsViewHolder holder, final int position) {

            String kind = items.get(position).getKind();
            Data data = items.get(position).getData();


            holder.tvTitle.setText(data.getTitle());
            holder.tvSummary.setText(data.getTitle());
            if(!data.getThumbnail().equals("")) {
                Picasso.with(context).load(data.getThumbnail())
                        .centerInside()
                        .fit()
                        .config(Bitmap.Config.RGB_565)
                        .placeholder(R.drawable.redditlogo)
                        .error(R.drawable.errorimage)
                        .into(holder.ivImageUrl);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(mDelegateItemAdapter!=null){
                        mDelegateItemAdapter.onItemClicked(items.get(position));
                    }

                }
            });

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

    //endregion

    //region CLASE ESTATICA HOLDER Y SUS METODOS

        public static class ItemsViewHolder extends RecyclerView.ViewHolder {
            CardView cvRoot;
            TextView tvTitle;
            TextView tvSummary;
            ImageView ivImageUrl;

            ItemsViewHolder(View itemView) {
                super(itemView);
                cvRoot = (CardView)itemView.findViewById(R.id.cv_layout);
                tvTitle = (TextView)itemView.findViewById(R.id.tv_title);
                tvSummary = (TextView)itemView.findViewById(R.id.tv_summary);
                ivImageUrl = (ImageView)itemView.findViewById(R.id.iv_image_url);
            }
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

    //endregion

}
