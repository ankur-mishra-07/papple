package com.ifocus.papple.views.textviewplus;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Abhishek on 12-01-2016.
 */
public class Typefaces {

    private static ConcurrentHashMap<String, Typeface> cache = new ConcurrentHashMap<>();

    public static Typeface get(Context context, String assetPath){

        synchronized (cache){
            if(!cache.containsKey(assetPath)){
                try{
                    Typeface t = Typeface.createFromAsset(context.getAssets(), assetPath);
                    cache.put(assetPath, t);
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }

            }

            return cache.get(assetPath);
        }

    }

}