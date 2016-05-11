package com.ifocus.papple.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ifocus.papple.R;

/**
 * Created by Abhishek on 13-01-2016.
 */
@SuppressLint("ValidFragment")
public class LoadingDialogFragment extends DialogFragment {

    private String loadingText = "Loading...";
    private int backgroundColor = Color.WHITE;

    public LoadingDialogFragment(String loadingText, int backgroundColor){
        if(loadingText != null && loadingText.equals(""))
            this.loadingText = loadingText;

        if(backgroundColor != 0 )
            this.backgroundColor = backgroundColor;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LinearLayout layout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_loading, null);
        TextView tv = (TextView) layout.findViewById(R.id.loadingText);
        tv.setText(loadingText);

        layout.setBackgroundColor(backgroundColor);
        builder.setView(layout);
        builder.setCancelable(false);
        return builder.create();
    }
}
