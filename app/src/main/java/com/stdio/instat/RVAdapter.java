package com.stdio.instat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.stdio.instat.models.Video;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.DataViewHolder> {

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvDescription;

        DataViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
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
        viewHolder.tvTitle.setText(video.getName());
        viewHolder.tvDescription.setText(String.valueOf(video.getPeriod()));
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