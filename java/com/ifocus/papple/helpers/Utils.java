package com.ifocus.papple.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.ifocus.papple.R;
import com.ifocus.papple.activity.ResolvedIssue;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by iFocus_2 on 19-01-2016.
 */
public class Utils {
    public static void doIntent(Activity activity, Class resultClass) {
        Intent in = new Intent(activity, resultClass);
        activity.startActivity(in);
    }

    public static void loadImage(Context context, String url, ImageView imgView) {
        Picasso.with(context).load(new File(url)).placeholder(R.drawable.progress_animation).error(R.drawable.logo).into(imgView);
    }
}
