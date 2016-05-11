package com.ifocus.papple.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ifocus.papple.R;
import com.ifocus.papple.Utils.AndroidUtils;
import com.ifocus.papple.Utils.RetroService;
import com.ifocus.papple.helpers.Constant;
import com.ifocus.papple.helpers.PreferenceHelper;
import com.ifocus.papple.helpers.Utils;
import com.ifocus.papple.interfaces.CustomClick;
import com.ifocus.papple.interfaces.IRetroTaskCompleteListener;
import com.ifocus.papple.interfaces.OnFragmentInteractionListener;
import com.ifocus.papple.manager.Eventmanager;
import com.ifocus.papple.manager.ResolveIssue;
import com.ifocus.papple.model.PostData;
import com.ifocus.papple.model.PostResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Retrofit;

public class EventsViewAdapter extends RecyclerView.Adapter<EventsViewAdapter.ViewHolder> implements CustomClick, IRetroTaskCompleteListener {

    private final List<PostResult> mValues;
    private final OnFragmentInteractionListener mListener;
    private Activity context;
    private final List<PostData> mServerValues;
    private int pos;
    /**
     * Interface for result
     */
    private IRetroTaskCompleteListener taskCompleted;
    /**
     * PostParams
     */
    private Map<String, String> postParams = new HashMap<>();

    private PreferenceHelper saveCred;

    // Retrofit object for initializing the base url
    private Retrofit retrofit;

    public EventsViewAdapter(List<PostData> data, List<PostResult> items, OnFragmentInteractionListener listener, Activity context) {
        this.mValues = items;
        this.mServerValues = data;
        this.mListener = listener;
        this.context = context; //RetroFit Creation
        retrofit = RetroService.returnRestadapter(this.context);
        saveCred = new PreferenceHelper(this.context);
        taskCompleted = this;
    }

   /* public EventsViewAdapter(List<PostData> data, OnFragmentInteractionListener mListener, FragmentActivity activity) {
        this.mServerValues = data;
        this.mListener = mListener;
        this.context = activity;
        mValues = null;
    }*/

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_events, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (position < mServerValues.size()) {
            holder.mData = mServerValues.get(position);
            holder.mIdView.setText(mServerValues.get(position).getPostDescription());
            holder.mContentView.setText(String.valueOf(mServerValues.get(position).getUser_name()));
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.mItem, v);
                    }
                }
            });
            holder.upVoteCount.setText(mValues.get(position).getUPCOUNT());
            holder.downVoteCount.setText(mValues.get(position).getDOWNCOUNT());
            holder.topic.setText(mServerValues.get(position).getPostName());
            holder.category.setText(mServerValues.get(position).getPostCategory());
            if (!mValues.get(position).getPICPATH().isEmpty()) {
                Utils.loadImage(context, mValues.get(position).getPICPATH(), holder.postPic);
            } else {
                holder.postPic.setImageResource(R.drawable.logo);
            }
            holder.report.setOnClickListener(new ViewClickListener(position, this, holder));
            holder.upVote.setOnClickListener(new ViewClickListener(position, this, holder));
            holder.downVote.setOnClickListener(new ViewClickListener(position, this, holder));
        } else {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).getDETAILS());
            holder.mContentView.setText(mValues.get(position).getUSERNAME());
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.mItem, v);
                    }
                }
            });
            holder.upVoteCount.setText(mValues.get(position).getUPCOUNT());
            holder.downVoteCount.setText(mValues.get(position).getDOWNCOUNT());
            holder.topic.setText(mValues.get(position).getTOPIC());
            holder.category.setText(mValues.get(position).getCATEGORY());
            if (!mValues.get(position).getPICPATH().isEmpty()) {
                Utils.loadImage(context, mValues.get(position).getPICPATH(), holder.postPic);
            } else {
                holder.postPic.setImageResource(R.drawable.logo);
            }
            holder.report.setOnClickListener(new ViewClickListener(position, this, holder));
            holder.upVote.setOnClickListener(new ViewClickListener(position, this, holder));
            holder.downVote.setOnClickListener(new ViewClickListener(position, this, holder));
        }

        if (saveCred.getLoginRole().equalsIgnoreCase(context.getString(R.string.user))) {
            holder.react.setVisibility(View.GONE);
        } else if (saveCred.getLoginRole().equalsIgnoreCase(context.getString(R.string.contractor))) {
            holder.react.setVisibility(View.VISIBLE);
            holder.react.setOnClickListener(new ViewClickListener(position, this, holder));
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public void onCustomClick(int position, View v, ViewHolder holder) {
        switch (v.getId()) {
            case R.id.report:
                Snackbar.make(v, "Report is clicked" + position, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.upVote:
                mValues.get(position).setUPCOUNT(String.valueOf(Integer.parseInt(mValues.get(position).getUPCOUNT()) + 1));
                holder.upVoteCount.setText(mValues.get(position).getUPCOUNT());
                break;
            case R.id.downVote:
                mValues.get(position).setDOWNCOUNT(String.valueOf(Integer.parseInt(mValues.get(position).getDOWNCOUNT()) + 1));
                holder.downVoteCount.setText(mValues.get(position).getDOWNCOUNT());
                break;

            case R.id.react:
                pos = position;
                callResponDialog(position);
                break;
            default:
                break;
        }
    }

    private void callResponDialog(final int position) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog);
        // Set dialog title
        dialog.setTitle(context.getString(R.string.respond_dialog));

        // set values for custom dialog components - text, image and button
        EditText text = (EditText) dialog.findViewById(R.id.react_msg);
        Button declineButton = (Button) dialog.findViewById(R.id.submit);
        // if decline button is clicked, close the custom dialog
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AndroidUtils.isNetworkAvailable(context)) {

                    saveCred.putLogintrue(true);
                    //Making Retro Fit call
                    context.setProgressBarIndeterminateVisibility(true);

                    // call Network Handler
                    postParams.clear();
                    postParams.put(context.getString(R.string.user_id), String.valueOf(saveCred.getUserId()));
                    postParams.put(context.getString(R.string.post_id), String.valueOf(mServerValues.get(position).getPostId()));
                    new ResolveIssue(context, Constant.RESOLVE, taskCompleted, retrofit, postParams, false);

                } else {
                    AndroidUtils.showToast(context.getString(R.string.networkError), context);
                }
                // Close dialog
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onCustomClick(int position, View view, ResolvedEventsViewAdapter.ViewHolder holder) {

    }

    @Override
    public void onResponseLanded(int serviceCode, String Response) {
        removeAt(pos);
    }

    @Override
    public void onFailureLanded(int serviceCode, String Failure) {

    }

    public void removeAt(int position) {
        PostData current = mServerValues.get(position);
        mServerValues.remove(current);
        notifyItemRemoved(position);
    }

    public class ViewClickListener implements View.OnClickListener {
        private int position;
        private CustomClick customClick;
        private ViewHolder holder;

        ViewClickListener(int position, CustomClick customClick, ViewHolder holder) {
            this.position = position;
            this.customClick = customClick;
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            customClick.onCustomClick(position, v, holder);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView upVote;
        public final TextView upVoteCount;
        public final TextView downVote;
        public final TextView downVoteCount;
        public final TextView report;
        public final TextView topic;
        public final TextView react;
        public final TextView category;
        public final ImageView postPic;
        public PostResult mItem;
        public PostData mData;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            upVote = (TextView) view.findViewById(R.id.upVote);
            upVoteCount = (TextView) view.findViewById(R.id.upVoteCount);
            downVote = (TextView) view.findViewById(R.id.downVote);
            downVoteCount = (TextView) view.findViewById(R.id.downVoteCount);
            report = (TextView) view.findViewById(R.id.report);
            topic = (TextView) view.findViewById(R.id.topic);
            postPic = (ImageView) view.findViewById(R.id.postPic);
            category = (TextView) view.findViewById(R.id.category);
            react = (TextView) view.findViewById(R.id.react);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
