package com.example.retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    Context context;
    ArrayList<Articles> articles;

    public NewsAdapter(Context context, ArrayList<Articles> articles) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsHolder(LayoutInflater.from(context).inflate(R.layout.cell_for_news,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        Articles currentArticle = articles.get(position);

        holder.mTvAuthor.setText(currentArticle.author);
        holder.mTvTitle.setText(currentArticle.title);
        holder.mTvContent.setText(currentArticle.content);
        Glide.with(context).load(currentArticle.urlToImage).into(holder.mIvNewsImage);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {
        private TextView mTvAuthor;
        private TextView mTvTitle;
        private TextView mTvContent;
        private ImageView mIvNewsImage;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);

            mTvAuthor = itemView.findViewById(R.id.tv_author);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvContent = itemView.findViewById(R.id.tv_content);
            mIvNewsImage = itemView.findViewById(R.id.iv_news_image);

        }
    }
}
