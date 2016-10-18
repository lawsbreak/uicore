package com.antking.library.uicore;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.antking.library.uicore.ultilities.UIUtilities;

/**
 * Created by thoitran on 8/4/16.
 * RelativeLayout with blur background
 */
public class BlurView extends RelativeLayout {
    // Tag for debug log
    private static final String TAG = "BlurView";
    private static final int SHAPE_RECTANGLE = 0;
    private static final int SHAPE_CIRCLE = 1;
    Context mContext;
    Bitmap mBitmap;
    View mSourceView;
    int viewSourceId;
    int mOverColor;
    int mShapeType;
    int mCornerRadius;
    int mBlurRadius;
    boolean mNeedRegenerateBackground = true;

    public BlurView(Context context) {
        super(context);
        if (!isInEditMode()) {
            init(context, null);
        }
    }

    public BlurView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    public BlurView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BlurView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    /**
     * Init view with custom attributes
     * @param context
     * @param attrs
     * */
    private void init(Context context, AttributeSet attrs){
        this.mContext = context;
        if (attrs != null){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BlurView);
            viewSourceId = typedArray.getResourceId(R.styleable.BlurView_view_source, 0);
            mShapeType = typedArray.getInt(R.styleable.BlurView_shape, SHAPE_RECTANGLE);
            if (mShapeType == SHAPE_RECTANGLE){
                mCornerRadius = typedArray.getDimensionPixelOffset(R.styleable.BlurView_corner_radius, 0);
            }
            mOverColor  = typedArray.getColor(R.styleable.BlurView_over_color, Color.TRANSPARENT);
            mBlurRadius = typedArray.getInteger(R.styleable.BlurView_blur_radius, 25);
            if (mBlurRadius > 25) mBlurRadius = 25;
            if (mBlurRadius < 0) mBlurRadius = 0;
        }
    }

    /**
     * Generate Blur Background
     * */
    private void generateBackground(){
        try {
            mSourceView = getRootView().findViewById(viewSourceId);
            mBitmap = UIUtilities.blur(mContext
                    , UIUtilities.loadBitmapFromView(mSourceView
                            , getLeft()
                            , getTop()
                            , getMeasuredWidth(), getMeasuredHeight())
                    , mBlurRadius);
        }catch (Exception e){
            e.printStackTrace();
        }

        mNeedRegenerateBackground = false;
        setBackground(new BitmapDrawable(getResources()
                , UIUtilities.overByColor(mShapeType == SHAPE_RECTANGLE?
                UIUtilities.roundedCrop(mBitmap, mCornerRadius) : UIUtilities.circleCrop(mBitmap)
                , mOverColor)));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        generateBackground();
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
