package com.example.michael.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michael.newsapp.Model.article;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Michael on 23/05/2018.
 */

public class NewsFeedAdapter extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 1;
    private final int VIEW_HEAD = 0;
    private List<article> Articles;
    private List<article> Headlines;
    private int rowLayout;
    private Context context;

    public static class NewsFeedHolder extends RecyclerView.ViewHolder {
        ImageView newImage;
        TextView newTitle;
        ImageView favBtn;

        public NewsFeedHolder(View v) {
            super(v);
            newImage = (ImageView) v.findViewById(R.id.newsImage);
            newTitle = (TextView) v.findViewById(R.id.newsTitle);
            favBtn = (ImageView) v.findViewById(R.id.favouriteBtn);
        }
    }

    public static class HeadlineFeedHolder extends RecyclerView.ViewHolder {
        RecyclerView rv;

        public HeadlineFeedHolder(View v) {
            super(v);
            rv = v.findViewById(R.id.headline_rv);
        }
    }

    public NewsFeedAdapter(List<article> Articles, List<article> Headlines, int rowLayout, Context context) {
        this.Articles = Articles;
        this.Headlines = Headlines;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    public NewsFeedAdapter(List<article> Headlines, int rowLayout, Context context) {
        this.Articles = Headlines;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    rowLayout, parent, false);

            vh = new NewsFeedHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.headlines_item, parent, false);

            vh = new HeadlineFeedHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NewsFeedHolder) {
            ((NewsFeedHolder) holder).newTitle.setText(Articles.get(position).getTitle());
            Picasso.get()
                    .load(Articles.get(position).getUrlToImage())
                    .into(((NewsFeedHolder) holder).newImage);

            ((NewsFeedHolder) holder).favBtn.setImageResource(R.drawable.ic_fav);

            if(Favorite.getLikedList() != null) {
                if(Favorite.getLikedList().contains(Articles.get(position).getUrl())) ((NewsFeedHolder) holder).favBtn.setImageResource(R.drawable.ic_fav_fill);
            }

            ((NewsFeedHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra(WebViewActivity.NEWS_URL, Articles.get(position).getUrl());
                    context.startActivity(intent);
                }
            });

            ((NewsFeedHolder) holder).favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!Favorite.getLikedList().contains(Articles.get(position).getUrl())) {
                        Favorite.AddLike(Articles.get(position).getUrl());
                        ((NewsFeedHolder) holder).favBtn.setImageResource(R.drawable.ic_fav_fill);
                    } else{
                        Favorite.RemoveLike(Articles.get(position).getUrl());
                        ((NewsFeedHolder) holder).favBtn.setImageResource(R.drawable.ic_fav);
                    }
                    Favorite.saveArrayList(context);
                }
            });
        } else {
            LinearLayoutManager LayoutManager = new LinearLayoutManager(context);
            LayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            ((HeadlineFeedHolder)holder).rv.setLayoutManager(LayoutManager);
            NewsFeedAdapter adapter = new NewsFeedAdapter(Headlines, R.layout.news_feed_item, context);
            ((HeadlineFeedHolder)holder).rv.setAdapter(adapter);
        }
    }

    @Override
    public int getItemCount() {
        return Articles.size();
    }

    @Override
    public int getItemViewType(int position) {
        return Articles.get(position) != null ? VIEW_ITEM : VIEW_HEAD;
    }
}
