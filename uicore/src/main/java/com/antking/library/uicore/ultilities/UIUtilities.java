package com.antking.library.uicore.ultilities;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;

import com.antking.library.uicore.R;

/**
 * Created by thoitran on 8/4/16.
 */
public class UIUtilities {
    // Tag for debug log
    private static final String TAG = "Utilities";

    private static final float BITMAP_SCALE = 0.4f;
    private static final float BLUR_RADIUS = 7.5f;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    /**
    * Blur filter with Android RenderScript
    * 'Cause ScriptIntrinsicBlur only accept radius between 0 and 25 while we currently have so many
    * devices with high pixel density, so we will down scale bitmap and blur it first,
    * then up scale it back to original size and blur once again to clear result of scale process.
    * @param context
    * @param bmSource source bitmap to blur
    * @param radius gaussian blur radius
    * */
    public static Bitmap blur(Context context, Bitmap bmSource, float radius){
        Log.e(TAG, "Start blur");
        int scaleFactor = context.getResources().getDimensionPixelOffset(R.dimen.blur_effect_scale_factor);
        float bitmapScale = scaleFactor / 100f;
        int width = Math.round(bmSource.getWidth() * bitmapScale);
        int height = Math.round(bmSource.getHeight() * bitmapScale);

        // BLUR STATE 1
        // Downscale bitmap
        Bitmap state1InputBitmap = Bitmap.createScaledBitmap(bmSource, width, height, false);

        Bitmap state1Result = Bitmap.createBitmap(state1InputBitmap);

        // Using system script to blur
        final RenderScript state1RenderScript = RenderScript.create(context);
        final Allocation state1Input  = Allocation.createFromBitmap(state1RenderScript, state1InputBitmap
                , Allocation.MipmapControl.MIPMAP_NONE
                , Allocation.USAGE_SCRIPT);
        final Allocation state1Output = Allocation.createFromBitmap(state1RenderScript, state1Result);
        final ScriptIntrinsicBlur state1Script
                = ScriptIntrinsicBlur.create(state1RenderScript, Element.U8_4(state1RenderScript));

        state1Script.setRadius(radius);
        state1Script.setInput(state1Input);
        state1Script.forEach(state1Output);
        state1Output.copyTo(state1Result);

        // BLUR STATE 2
        // Upscale state 1 result to original size
        Bitmap state2InputBitmap = Bitmap.createScaledBitmap(state1Result
                , bmSource.getWidth(), bmSource.getHeight(), false);

        Bitmap state2Result = Bitmap.createBitmap(state2InputBitmap);
        final RenderScript state2RenderScript = RenderScript.create(context);
        final Allocation state2Input = Allocation.createFromBitmap(state2RenderScript, state2InputBitmap
                , Allocation.MipmapControl.MIPMAP_NONE
                , Allocation.USAGE_SCRIPT);
        final Allocation state2Output = Allocation.createFromBitmap(state2RenderScript, state2InputBitmap);
        final ScriptIntrinsicBlur state2Script = ScriptIntrinsicBlur.create(state2RenderScript, Element.U8_4(state2RenderScript));

        state2Script.setRadius(radius);
        state2Script.setInput(state2Input);
        state2Script.forEach(state2Output);
        state2Output.copyTo(state2Result);

        return state2Result;
    }

    /**
     * Get and crop bitmap from a view
     * @param view source view to get bitmap
     * @param x left of crop region
     * @param y top of crop region
     * @param width width of crop region
     * @param height height of crop region
     * */
    public static Bitmap loadBitmapFromView(View view,int x, int y, int width, int height){
//        v.measure(0, 0);
        Log.e(TAG, " Crop "
                + " x = " + x
                + "\n y = " + y
                + "\n width = " + width
                + "\n height = " + height);
        Log.e(TAG, " w = " + view.getWidth() + " h = " + view.getHeight());

        Bitmap bitmap = Bitmap.createBitmap( view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(canvas);

        return Bitmap.createBitmap(bitmap, x, y, width, height);
    }

    /**
     * Get bitmap from a view
     * @param view source view to get bitmap
     * */
    public static Bitmap loadBitmapFromView(View view) throws NullPointerException{
//        v.measure(0, 0);
        Log.e(TAG, " w = " + view.getWidth() + " h = " + view.getHeight());

        Bitmap bitmap = Bitmap.createBitmap( view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(canvas);

        return bitmap;
    }

    /**
     * Crop bitmap by a rounded rectangle
     * @param bitmap source bitmap
     * @param cornerRadius rounded rectangle corner radius
     * */
    public static Bitmap roundedCrop(Bitmap bitmap, int cornerRadius){
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight()
                    , Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
//        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
//                bitmap.getWidth() / 2, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            return output;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
    /**
     * Crop bitmap by a circle
     * @param bitmap source bitmap
     * */
    public static Bitmap circleCrop(Bitmap bitmap){
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight()
                , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * Over bitmap with a colour
     * @param bitmap source bitmap
     * @param color colour to over with
     * */
    public static Bitmap overByColor(Bitmap bitmap, int color){
        try {
            Bitmap output = Bitmap.createBitmap(bitmap
                    , 0, 0, bitmap.getWidth(), bitmap.getHeight());

            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();

            paint.setColor(color);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            canvas.drawPaint(paint);

            return output;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
