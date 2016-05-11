package com.ifocus.papple.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jarvis on 11/19/2015.
 */
public class AndroidUtils {

    private static File mDir;

    private static File mFile;

    public static final String MY_ACCESS_KEY_ID = "AKIAJIGS4QN7UGDZPIGQ";
    public static final String MY_SECRET_KEY = "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";
    public static final String ImgBucket = "niyopay";


    public static SecureRandom random = new SecureRandom();

    /**
     * Show Toast
     */
    public static void showToast(String msg, Activity activity) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Show Snackbar
     */
    public static void showSnackBar(String msg, View view) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    /**
     * Network Validation
     */
    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivity = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Email validation
     */
    public static boolean eMailValidation(String emailstring) {
        if (null == emailstring || emailstring.length() == 0) {
            return false;
        }
        Pattern emailPattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher emailMatcher = emailPattern.matcher(emailstring);
        return emailMatcher.matches();
    }


    /*public static Dialog showProgress(Activity activity, boolean show) {
        Dialog loadDialog = new Dialog(activity);
        Window window = loadDialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        loadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loadDialog.setContentView(R.layout.load_layout);
        return loadDialog;
    }
*/
    public static void createFileInternalStorage(Context context, String filename) {
        mFile = new File(context.getFilesDir(), filename);
        try {
            if (!mFile.exists()) {

                mFile.mkdir();
                mFile.createNewFile();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createCRNDirectory(Context context, String CRN_Number) {
        mDir = new File(context.getFilesDir(), CRN_Number);
        try {
            if (!mDir.exists()) {
                mDir.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createAssetsDirectory(Context context, String CRN_Number, String folderName) {
        mDir = new File(context.getFilesDir() + "/" + CRN_Number, folderName);
        try {
            if (!mDir.exists()) {
                mDir.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getAssetDirectory(Context context, String CRN_Number, String folderName) {
        mDir = new File(context.getFilesDir() + "/" + CRN_Number, folderName);
        return mDir.getPath();
    }

    public static String getCRNDirectory(Context context, String CRN_Number) {
        mDir = new File(context.getFilesDir() + "/" + CRN_Number);
        return mDir.getPath();
    }

    public static String saveToInternalSorage(Context context, Bitmap bitmapImage, String filePath, String fileName) {
      /*  ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir(context.getFilesDir() + "/" + filePath, Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();*/

        ContextWrapper cw = new ContextWrapper(context);
/* /data/data/com.techment.neyoagent/files/NP0000003590/poi*/
// path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir(filePath, Context.MODE_PRIVATE);
// Create imageDir
        File mypath = new File(directory, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }

    public static void deleteCRNDirectory(Context context, String filepath) {
        mDir = new File(context.getFilesDir() + "/" + filepath);
        try {
            if (mDir.exists()) {
                mDir.delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generateRandomNumber() {
        return new BigInteger(130, random).toString(32);
    }

  /*  public static void loadImage(Context context, String url, ImageView imgView) {
        Picasso.with(context).load(url).placeholder(R.drawable.progress_animation).into(imgView);
    }
*/
    public static boolean checkLenth(String string) {
        if (string != null) {
            return true;
        }
        return false;
    }


}
