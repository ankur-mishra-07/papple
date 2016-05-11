package com.ifocus.papple.factory;

import android.app.Dialog;

/**
 * Created by iFocus_2 on 03-05-2016.
 */

public class DialogFactory {
    public static Dialog createDialog(String className){
        if(className.equalsIgnoreCase("alert")){
            return new DialogAlert().buildDialog();
        }
        else if(className.equalsIgnoreCase("toast")){
            return new DialogToast().buildDialog();
        }
        //else ...
        return createDialog("toast");
    }
}
