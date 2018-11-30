package com.example.michael.newsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashSet;

/**
 * Created by Michael on 24/05/2018.
 */

public class Favorite {
    private static Favorite INSTANCE = null;
    private static HashSet<String> likedList;

    private Favorite() {
    }

    public static Favorite getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Favorite();
        }
        return(INSTANCE);
    }

    public static void generateLikeArrayList(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("liked", null);
        Type type = new TypeToken<HashSet<String>>() {}.getType();
        likedList = gson.fromJson(json, type);
        if(likedList == null) likedList = new HashSet<>();
    }

    public static void saveArrayList(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(likedList);
        editor.putString("liked", json);
        editor.apply();
    }

    public static HashSet<String> getLikedList() {
        return likedList;
    }

    public static void AddLike(String s) {
        likedList.add(s);
    }

    public static void RemoveLike(String s) {
        likedList.remove(s);
    }

}
