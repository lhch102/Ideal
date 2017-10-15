package com.lhch.ideal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lhch.ideal.R;
import com.lhch.ideal.db.MovieInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 电影信息控制器
 * Created by Administrator on 2017/10/2.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> implements View.OnClickListener {

    List<MovieInfo> mMovieList;
    public Context mContext;
    private OnItemClickListener mOnItemClickListener = null;

    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView movieName;
        public ViewHolder(View itemView) {
            super(itemView);
            movieImage = (ImageView) itemView.findViewById(R.id.top_image);
            movieName = (TextView) itemView.findViewById(R.id.top_name);
        }
    }

    public MovieAdapter(List<MovieInfo> movieList) {
        mMovieList = movieList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        MovieInfo movie = mMovieList.get(position);
//        Picasso.with(mContext).load(movie.getImages()).into(viewHolder.movieImage);
        viewHolder.movieImage.setImageResource(movie.getImagesId());
        viewHolder.movieName.setText(movie.getChineseName());
        //将position保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
