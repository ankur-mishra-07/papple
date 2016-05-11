package com.ifocus.papple.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsic;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * Created by Abhishek on 11-01-2016.
 */
public class ImageHelper {
    public float MAX_BLUR_RADIUS = 25f;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap generateBlurredBitmap(Context context, float radius, Bitmap bitmap){
        Bitmap blurredBitmap = Bitmap.createBitmap(bitmap);

        RenderScript renderScript = RenderScript.create(context);

        Allocation tmpIn = Allocation.createFromBitmap(renderScript, bitmap);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, blurredBitmap);

        ScriptIntrinsicBlur intrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        intrinsicBlur.setRadius((radius > 0)? Math.min(radius, this.MAX_BLUR_RADIUS): MAX_BLUR_RADIUS);
        intrinsicBlur.setInput(tmpIn);
        intrinsicBlur.forEach(tmpOut);
        tmpOut.copyTo(blurredBitmap);
        return blurredBitmap;

    }

}
