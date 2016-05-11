package com.ifocus.papple.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.location.Address;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.ResultReceiver;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ifocus.papple.R;
import com.ifocus.papple.Utils.AndroidUtils;
import com.ifocus.papple.Utils.RetroService;
import com.ifocus.papple.activity.ContractorHomeActivity;
import com.ifocus.papple.activity.HomeActivity;
import com.ifocus.papple.database.DBUserDetailsHelper;
import com.ifocus.papple.helpers.Constant;
import com.ifocus.papple.helpers.PreferenceHelper;
import com.ifocus.papple.helpers.Utils;
import com.ifocus.papple.interfaces.IRetroTaskCompleteListener;
import com.ifocus.papple.manager.Eventmanager;
import com.ifocus.papple.manager.NetWorkHandler;
import com.ifocus.papple.service.GPSTracker;
import com.ifocus.papple.service.GeocodeAddressIntentService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventCreation interface
 * to handle interaction events.
 * Use the {@link EventCreation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventCreation extends Fragment implements View.OnClickListener, IRetroTaskCompleteListener {

    private AddressResultReceiver mResultReceiver;
    private DBUserDetailsHelper dbHelper;
    private EditText eventTopic, eventDesc;
    private Spinner eventCategory;
    private TextView attach_media, attach_loc;
    private Button createEvent;
    ProgressBar progressBar;
    private Uri imageUri;
    private ImageView myloc;
    private ImageView postPic;
    private View view;
    private CardView card_view_image;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final int CAMERA_REQUEST = 100;
    private PreferenceHelper saveCred;
    private String post_type = "";

    boolean fetchAddress;
    int fetchType = Constant.USE_ADDRESS_LOCATION;
    // GPSTracker class
    GPSTracker gps;
    /**
     * Interface for result
     */
    private IRetroTaskCompleteListener taskCompleted;
    /**
     * PostParams
     */
    private Map<String, String> postParams = new HashMap<>();

    // Retrofit object for initializing the base url
    private Retrofit retrofit;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventCreation() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EventCreation newInstance(int columnCount) {
        EventCreation fragment = new EventCreation();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            dbHelper = new DBUserDetailsHelper(getActivity(), null, null, 1);
            saveCred = new PreferenceHelper(getActivity());
            mResultReceiver = new AddressResultReceiver(null);

            //RetroFit Creation
            retrofit = RetroService.returnRestadapter(getActivity());
            taskCompleted = this;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_creation, container, false);
        eventTopic = (EditText) view.findViewById(R.id.eventTopic);
        eventDesc = (EditText) view.findViewById(R.id.desc);
        myloc = (ImageView) view.findViewById(R.id.myloc);
        attach_loc = (TextView) view.findViewById(R.id.attach_loc);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        eventCategory = (Spinner) view.findViewById(R.id.select_category);
        attach_media = (TextView) view.findViewById(R.id.attach_media);
        createEvent = (Button) view.findViewById(R.id.createEvent);
        postPic = (ImageView) view.findViewById(R.id.postPic);
        card_view_image = (CardView) view.findViewById(R.id.card_view_image);
        card_view_image.setVisibility(View.GONE);
        createEvent.setOnClickListener(this);
        attach_media.setOnClickListener(this);
        myloc.setOnClickListener(this);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attach_media:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); //create a file to save the image
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //set the image file name
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
            case R.id.createEvent:
                insertPost();
                break;
            case R.id.myloc:
                checkGpsStatus();
                break;

        }
    }

    private void checkGpsStatus() {
        // create class object
        gps = new GPSTracker(getActivity());

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            getCurrentLocation(latitude, longitude);
            // \n is for new line
            Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    private void getCurrentLocation(double latitude, double longitude) {
        fetchAddress = true;
        fetchType = Constant.USE_ADDRESS_LOCATION;
        Intent intent = new Intent(getActivity(), GeocodeAddressIntentService.class);
        intent.putExtra(Constant.RECEIVER, mResultReceiver);
        intent.putExtra(Constant.FETCH_TYPE_EXTRA, fetchType);

        if (latitude == 0 || longitude == 0) {
            Toast.makeText(getActivity(),
                    "Please enter both latitude and longitude",
                    Toast.LENGTH_LONG).show();
            return;
        }
        intent.putExtra(Constant.LOCATION_LATITUDE_DATA_EXTRA, latitude);
        intent.putExtra(Constant.LOCATION_LONGITUDE_DATA_EXTRA, longitude);

        progressBar.setVisibility(View.VISIBLE);
        Log.e("", "Starting Service");
        getActivity().startService(intent);
    }

    private void insertPost() {
        dbHelper.insertPosts(String.valueOf(saveCred.getUserId()),
                eventDesc.getText().toString(),
                "0",
                "0",
                "",
                "",
                eventCategory.getSelectedItem().toString()
                , "",
                compressImage(imageUri.getPath()));
        if (saveCred.getLoginRole().equalsIgnoreCase(getString(R.string.user))) {
            post_type = "issue";
        } else if (saveCred.getLoginRole().equalsIgnoreCase(getString(R.string.contractor))) {
            post_type = "event";
        } else {
            AndroidUtils.showToast(getString(R.string.error_user_type), getActivity());
        }

        uploadPosttoServer(saveCred.getUserId(), eventDesc.getText().toString(), eventTopic.getText().toString(), eventCategory.getSelectedItem().toString(), post_type);
    }

    private void uploadPosttoServer(String userId, String event_desc, String event_topic, String event_category, String post_type) {
        if (AndroidUtils.isNetworkAvailable(getActivity())) {

            saveCred.putLogintrue(true);
            //Making Retro Fit call
            getActivity().setProgressBarIndeterminateVisibility(true);

            // call Network Handler
            postParams.clear();
            postParams.put(getString(R.string.user_id), userId);
            postParams.put(getString(R.string.event_topic), event_topic);
            postParams.put(getString(R.string.event_desc), event_desc);
            postParams.put(getString(R.string.event_category), event_category);
            postParams.put(getString(R.string.post_type), post_type);
            postParams.put(getString(R.string.post_status), getString(R.string.created));
            new Eventmanager(getActivity(), Constant.EVENT_CREATION, taskCompleted, retrofit, postParams, false);

        } else {
            AndroidUtils.showToast(getString(R.string.networkError), getActivity());
        }
    }

    /**
     * Create a File Uri for saving image (can be sued to save video to)
     **/
    private Uri getOutputMediaFileUri(int mediaTypeImage) {
        return Uri.fromFile(getOutputMediaFile(mediaTypeImage));
    }

    /**
     * Create a File  for saving image (can be sued to save video to)
     **/
    private File getOutputMediaFile(int mediaType) {
        //To be safe, is necessary to check if SDCard is mounted

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                (String) getResources().getText(R.string.app_name));

        //create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("Capture", "failed to create directory");
                return null;
            }
        }

        //Create a media file name

        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;

        if (mediaType == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        } else {
            return null;
        }
        return mediaFile;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
                card_view_image.setVisibility(View.VISIBLE);

                // bimatp factory
                BitmapFactory.Options options = new BitmapFactory.Options();
                // downsizing image as it throws OutOfMemory Exception for larger
                // images
                options.inSampleSize = 8;

                Bitmap bitmap = BitmapFactory.decodeFile(compressImage(imageUri.getPath()),
                        options);
                postPic.setImageBitmap(bitmap);
                //Utils.loadImage(getActivity(), imageUri.getPath(), postPic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getActivity().getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    @Override
    public void onResponseLanded(int serviceCode, String Response) {
        if (Response != null) {
            getActivity().setProgressBarIndeterminateVisibility(false);
            if (saveCred.getLoginRole().equalsIgnoreCase(getString(R.string.user))) {
                Utils.doIntent(getActivity(), HomeActivity.class);
            } else if (saveCred.getLoginRole().equalsIgnoreCase(getString(R.string.contractor))) {
                Utils.doIntent(getActivity(), ContractorHomeActivity.class);
            } else {
                AndroidUtils.showToast(getString(R.string.error_user_type), getActivity());
            }
        }
    }

    @Override
    public void onFailureLanded(int serviceCode, String Failure) {
        getActivity().setProgressBarIndeterminateVisibility(false);
    }


    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {
            if (resultCode == Constant.SUCCESS_RESULT) {
                final Address address = resultData.getParcelable(Constant.RESULT_ADDRESS);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        attach_loc.setVisibility(View.VISIBLE);
                        myloc.setVisibility(View.GONE);
                        attach_loc.setText("Address: " + resultData.getString(Constant.RESULT_DATA_KEY));
                    }
                });
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        attach_loc.setVisibility(View.VISIBLE);
                        myloc.setVisibility(View.GONE);
                        attach_loc.setText(resultData.getString(Constant.RESULT_DATA_KEY));
                    }
                });
            }
        }
    }

}
