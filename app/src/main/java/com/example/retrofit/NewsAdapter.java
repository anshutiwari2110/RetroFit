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
    private NewsURLClickListener listener;
    String urlValue;

    public NewsAdapter(Context context, ArrayList<Articles> articles) {
        this.articles = articles;
        this.context = context;
    }

    public void setListener(NewsURLClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsHolder(LayoutInflater.from(context).inflate(R.layout.cell_for_news,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        Articles currentArticle = articles.get(position);

        holder.mTvPublishedAt.setText(currentArticle.publishedAt);
        holder.mTvTitle.setText(currentArticle.title);
        holder.mTvDescription.setText(currentArticle.description);
        holder.mTvURL.setText(currentArticle.url);
        Glide.with(context).load(currentArticle.urlToImage).into(holder.mIvNewsImage);
        urlValue = holder.mTvURL.getText().toString();
        holder.mTvURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onURLClicked(urlValue);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {
        private TextView mTvPublishedAt;
        private TextView mTvTitle;
        private TextView mTvDescription;
        private ImageView mIvNewsImage;
        private TextView mTvURL;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);

            mTvPublishedAt = itemView.findViewById(R.id.tv_publishedAt);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvDescription = itemView.findViewById(R.id.tv_description);
            mIvNewsImage = itemView.findViewById(R.id.iv_news_image);
            mTvURL = itemView.findViewById(R.id.tv_url);


        }
    }

    public interface NewsURLClickListener{
        void onURLClicked(String url);
    }
}
