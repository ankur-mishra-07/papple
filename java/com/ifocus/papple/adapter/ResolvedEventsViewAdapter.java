package com.ifocus.papple.adapter;

/**
 * Created by iFocus_2 on 19-01-2016.
 */

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifocus.papple.R;
import com.ifocus.papple.helpers.Utils;
import com.ifocus.papple.interfaces.CustomClick;
import com.ifocus.papple.interfaces.OnFragmentInteractionListener;
import com.ifocus.papple.model.PostData;
import com.ifocus.papple.model.PostResult;

import java.util.List;


public class ResolvedEventsViewAdapter extends RecyclerView.Adapter<ResolvedEventsViewAdapter.ViewHolder> implements CustomClick {

    private final List<PostResult> mValues;
    private final OnFragmentInteractionListener mListener;
    private Context context;
    private final List<PostData> mServerValues;

    public ResolvedEventsViewAdapter(List<PostData> data, List<PostResult> items, OnFragmentInteractionListener listener, Context context) {
        mValues = items;
        this.mServerValues = data;
        mListener = listener;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.resolved_fragments_events, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (position < mServerValues.size()) {
            holder.mItem2 = mServerValues.get(position);
            holder.mIdView.setText(mServerValues.get(position).getPostDescription());
            holder.mContentView.setText(mServerValues.get(position).getUser_name());
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

        }
        holder.report.setOnClickListener(new ViewClickListener(position, this, holder));
        holder.upVote.setOnClickListener(new ViewClickListener(position, this, holder));
        holder.downVote.setOnClickListener(new ViewClickListener(position, this, holder));

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public void onCustomClick(int position, View view, EventsViewAdapter.ViewHolder holder) {

    }

    @Override
    public void onCustomClick(int position, View v, ResolvedEventsViewAdapter.ViewHolder holder) {
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
            default:
                break;
        }
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
        public final TextView category;
        public final ImageView postPic;
        public PostResult mItem;
        public PostData mItem2;

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
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
