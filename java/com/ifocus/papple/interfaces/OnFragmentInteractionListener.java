package com.ifocus.papple.interfaces;

/**
 * Created by iFocus_2 on 18-01-2016.
 */

import android.view.View;

import com.ifocus.papple.model.PostResult;

/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 * <p/>
 * See the Android Training lesson <a href=
 * "http://developer.android.com/training/basics/fragments/communicating.html"
 * >Communicating with Other Fragments</a> for more information.
 */
public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    void onListFragmentInteraction(PostResult item, View view);
}
