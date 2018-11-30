package com.example.michael.newsapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.michael.newsapp.API.ApiClient;
import com.example.michael.newsapp.API.ApiInterface;
import com.example.michael.newsapp.Model.article;
import com.example.michael.newsapp.Model.articleWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Michael on 23/05/2018.
 */

public class MainActivity extends AppCompatActivity {

    public final static String API_KEY = "96a8026933f8451498c79a5e50067dcf";

    private boolean loading = false;
    private ViewPager viewPager;
    public static ApiInterface apiService;
    private TabLayout tabLayout;
    private SharedPreferences prefs;
    public static HashSet<String> likedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Favorite.getInstance();
        Favorite.generateLikeArrayList(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private int num_page = 3;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return NewsFeedFragment.newInstance(0, "Accident");
                case 1:
                    return NewsFeedFragment.newInstance(1, "Crime");
                case 2:
                    return NewsFeedFragment.newInstance(2, "Tech");
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return num_page;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Accident";
                case 1:
                    return "Crime";
                case 2:
                    return "Tech";
                default:
                    return null;
            }
        }
    }
}
