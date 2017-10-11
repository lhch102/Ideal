package com.lhch.ideal.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lhch.ideal.R;
import com.lhch.ideal.db.TopList;

import java.util.List;

/**
 * 榜单控制器
 * Created by Administrator on 2017/10/2.
 */
public class TopListAdapter extends RecyclerView.Adapter<TopListAdapter.ViewHolder> {

    List<TopList> mTopList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView topImage;
        TextView topName;
        public ViewHolder(View itemView) {
            super(itemView);
            topImage = (ImageView) itemView.findViewById(R.id.top_image);
            topName = (TextView) itemView.findViewById(R.id.top_name);
        }
    }

    public TopListAdapter(List<TopList> topLists) {
        mTopList = topLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TopList topList = mTopList.get(position);
        holder.topName.setBackgroundResource(topList.getImageId());
        holder.topName.setText(topList.getTopName());

    }

    @Override
    public int getItemCount() {
        return mTopList.size();
    }


}
