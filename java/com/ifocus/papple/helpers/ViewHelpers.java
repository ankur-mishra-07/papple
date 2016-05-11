package com.ifocus.papple.helpers;

/**
 * Created by iFocus_2 on 20-01-2016.
 */

import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhishek on 19-01-2016.
 */
public class ViewHelpers {

    public static List<EditText> validatePresenceEditText(boolean setError, String errorString, EditText... editTexts) {
        List<EditText> invalidEditTexts = new ArrayList<>();
        errorString = (errorString == null || errorString.equals("")) ? "Bad entry" : errorString;
        for (int i = 0; i < editTexts.length; i++) {
            EditText editText = editTexts[i];
            String text = String.valueOf(editText.getText());
            if (text == null || text.equals("")) {
                invalidEditTexts.add(editText);
                if (setError && (editText.getError() == null || editText.getError().equals("")))
                    editTexts[i].setError(errorString);
            }
        }
        return invalidEditTexts;

    }


}