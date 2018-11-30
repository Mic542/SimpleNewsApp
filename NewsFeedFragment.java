package com.example.michael.newsapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.michael.newsapp.Model.article;
import com.example.michael.newsapp.Model.articleWrapper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.michael.newsapp.MainActivity.API_KEY;
import static com.example.michael.newsapp.MainActivity.apiService;

/**
 * Created by Michael on 23/05/2018.
 */

public class NewsFeedFragment extends Fragment {
    private View view;
    private Call<articleWrapper> call;
    private Call<articleWrapper> headlinesCall;
    private List<article> articles;
    private List<article> headlines;
    private NewsFeedAdapter adapter;
    private int page;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean loading;
    private int visibleItemCount;
    private int totalItemCount;
    private int pastVisiblesItems;
    private ArrayList<String> liked;

    private String title;//String for tab title

    private static RecyclerView recyclerView;

    public static NewsFeedFragment newInstance(int page, String title) {
        NewsFeedFragment fragment = new NewsFeedFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news_feed, container, false);
        setRecyclerView();
        LoadNewsFeeds(title, page);
        LoadHeadlines(page);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("title");
        page = 1;
    }

    private void setRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);//Linear Items
        articles = new ArrayList<>();
        headlines = new ArrayList<>();
        adapter = new NewsFeedAdapter(articles, headlines, R.layout.news_feed_item, getActivity());
        recyclerView.setAdapter(adapter);// set adapter on recyclerview

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0)
                {
                    visibleItemCount = mLinearLayoutManager.getChildCount();
                    totalItemCount = mLinearLayoutManager.getItemCount();
                    pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();
                    loading = true;

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;
                            page++;
                            LoadNewsFeeds(title, page);
                        }
                    }
                }
            }
        });
    }

    private void LoadHeadlines(final int p) {
        headlinesCall = apiService.getTopHeadlines("id", "5", p, API_KEY);
        headlinesCall.enqueue(new Callback<articleWrapper>() {
            @Override
            public void onResponse(Call<articleWrapper> call, Response<articleWrapper> response) {
                headlines.addAll(response.body().getArticles());
            }

            @Override
            public void onFailure(Call<articleWrapper> call, Throwable t) {
                Toast.makeText(getContext(), "Error retrieving News", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void LoadNewsFeeds(final String q, final int p) {
        call = apiService.getAllNews(q, "10", p, API_KEY);
        call.enqueue(new Callback<articleWrapper>() {
            @Override
            public void onResponse(Call<articleWrapper> call, Response<articleWrapper> response) {
                int statusCode = response.code();

                for(int i = 0; i <response.body().getArticles().size(); i++) {
                    if(i != 0 && i%5 == 0) {
                        articles.add(null);
                    }
                    articles.add(response.body().getArticles().get(i));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<articleWrapper> call, Throwable t) {
                Toast.makeText(getContext(), "Error retrieving News", Toast.LENGTH_LONG).show();
            }
        });
    }
}
