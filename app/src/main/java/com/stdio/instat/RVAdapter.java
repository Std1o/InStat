package com.stdio.instat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.stdio.instat.models.Video;

import java.text.DecimalFormat;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.DataViewHolder> {

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPeriod, tvQuality, tvSize;

        DataViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPeriod = itemView.findViewById(R.id.tvPeriod);
            tvQuality = itemView.findViewById(R.id.tvQuality);
            tvSize = itemView.findViewById(R.id.tvSize);
        }
    }

    List<Video> dataList;
    Context mContext;

    RVAdapter(List<Video> dataList, Context context) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
        DataViewHolder pvh = new DataViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(DataViewHolder viewHolder, final int position) {
        Video video = dataList.get(position);
        viewHolder.tvName.setText(video.getName());
        viewHolder.tvPeriod.setText(mContext.getString(R.string.period, video.getPeriod()));
        viewHolder.tvQuality.setText(mContext.getString(R.string.quality, video.getQuality()));
        //converting byte size to megabyte size
        double size = (double) video.getSize()/1048576;

        DecimalFormat decimalFormat = new DecimalFormat( "#.#" );
        String strSize = decimalFormat.format(size);
        viewHolder.tvSize.setText(mContext.getString(R.string.size, strSize));

        viewHolder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, PlayerActivity.class);
            intent.putExtra("videoURL", video.getUrl());
            mContext.startActivity(intent);
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}