package com.antking.library.uicore;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.antking.library.uicore.ultilities.CustomLeadingMarginSpan;

/**
 * Display text to the users just like standard {@link TextView} but with some new additional feature.
 */
public class CustomTextView extends TextView {

    // Log Tag
    private static final String TAG = "CustomTextView";

    private int mImageWidth, mImageHeight;

    // Custom attributes
    private boolean mAutoFit = false;

    /**
     * Simple constructor to use when creating a CustomTextView from code.
     *
     * @param context The Context the view is running in, through which it can
     *        access the current theme, resources, etc.
     */
    public CustomTextView(Context context) {
        super(context);
    }

    /**
     * Constructor that is called when inflating a CustomTextView from XML.
     * This is called when a CustomTextView is being constructed from an XML file,
     * supplying attributes that CustomTextView specified in the XML file.
     * This version uses a default style of 0, so the only attribute values
     * applied are those in the Context's Theme and the given AttributeSet.
     *
     * <p>
     * The method onFinishInflate() will be called after all children have been
     * added.
     *
     * @param context The Context the CustomTextView is running in, through which
     *        it can access the current theme, resources, etc.
     * @param attrs The attributes of the XML tag that is inflating the CustomTextView.
     * @see #CustomTextView(Context, AttributeSet, int)
     */
    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithCustomAttributes(attrs, 0);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a
     * theme attribute. This constructor of CustomTextView allows it to use its
     * own base style when it is inflating.
     *
     * @param context The Context the CustomTextView is running in, through which
     *        it can access the current theme, resources, etc.
     * @param attrs The attributes of the XML tag that is inflating the CustomTextView.
     * @param defStyleAttr An attribute in the current theme that contains a
     *        reference to a style resource that supplies default values for
     *        the CustomTextView. Can be 0 to not look for defaults.
     * @see #CustomTextView(Context, AttributeSet)
     */
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWithCustomAttributes(attrs, defStyleAttr);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a
     * theme attribute. This constructor of CustomTextView allows it to use its
     * own base style when it is inflating.
     * <p>
     * When determining the final value of a particular attribute, there are
     * four inputs that come into play:
     * <ol>
     * <li>Any attribute values in the given AttributeSet.
     * <li>The style resource specified in the AttributeSet (named "style").
     * <li>The default style specified by <var>defStyleAttr</var>.
     * <li>The default style specified by <var>defStyleRes</var>.
     * <li>The base values in this theme.
     * </ol>
     * <p>
     * Each of these inputs is considered in-order, with the first listed taking
     * precedence over the following ones. In other words, if in the
     * AttributeSet you have supplied <code>&lt;CustomTextView * textColor="#ff000000"&gt;</code>
     * , then the button's text will <em>always</em> be black, regardless of
     * what is specified in any of the styles.
     *
     * @param context The Context the CustomTextView is running in, through which
     *        it can access the current theme, resources, etc.
     * @param attrs The attributes of the XML tag that is inflating the CustomTextView.
     * @param defStyleAttr An attribute in the current theme that contains a
     *        reference to a style resource that supplies default values for
     *        the CustomTextView. Can be 0 to not look for defaults.
     * @param defStyleRes A resource identifier of a style resource that
     *        supplies default values for the CustomTextView, used only if
     *        defStyleAttr is 0 or can not be found in the theme. Can be 0
     *        to not look for defaults.
     * @see #CustomTextView(Context, AttributeSet, int)
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initWithCustomAttributes(attrs, defStyleAttr);
    }

    /**
     * Initial CustomTextView with its own new attributes
     * @param attrs The attributes of the XML tag that is inflating the CustomTextView.
     */
    private void initWithCustomAttributes(AttributeSet attrs, int defStyleAttr){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);

        String font = typedArray.getString(R.styleable.CustomTextView_font);
        mAutoFit = typedArray.getBoolean(R.styleable.CustomTextView_autoFit, false);
        mImageWidth = typedArray.getDimensionPixelSize(R.styleable.CustomTextView_imageWidth, 0);
        mImageHeight = typedArray.getDimensionPixelSize(R.styleable.CustomTextView_imageHeight, 0);

        // If font was not found in attribute set and already have defined style,then
        // we will try to get it from the style.
        if ((font == null || font.length() == 0) && (defStyleAttr > 0)) {
            int[] attributes = {R.styleable.CustomTextView_font};
            typedArray = getContext().obtainStyledAttributes(defStyleAttr, attributes);
            font = typedArray.getString(R.styleable.CustomTextView_font);
        }

        // If font was found and valid, then we will use it to set typeface
        if (font != null && font != "") {
            if (isInEditMode()){
                font = "/" + font;
            }
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), font);
            setTypeface(typeface);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        makeSpan();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mAutoFit) {
            int lineCount = getLineCount();
            int maxLine = getMaxLines();
            if (lineCount > maxLine) {

                // Get the end of text
                int lineEnd = getLayout().getLineEnd(maxLine-1);
//                Log.e(TAG, "Line end = " + lineEnd);
//                Log.e(TAG, "Line end = " + getText().length());

                // Calculate the scale of font size
                float scale = (float) lineEnd / getText().length();
//                Log.e(TAG, "Scale = " + scale);

                // Calculate font size
                float fontSize = getTextSize() * scale;
//                Log.e(TAG, "Font size = " + fontSize);

                // Set font size
                setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
            }
            invalidate();
        }
    }

    /**
     * This method builds the text layout
     */
    private void makeSpan() {

        // Get current text
        String plainText=getText().toString();

        if ((mImageHeight != 0 || mImageWidth != 0) && plainText != null && plainText.length() > 0) {
            int allTextStart = 0;
            int allTextEnd = plainText.length() - 1;

            // Calculate the lines number = image height.
            // You can improve it... it is just an example
            int lines;

            //float textLineHeight = mTextView.getPaint().getTextSize();
            float fontSpacing = getPaint().getFontSpacing();
            lines = (int) (mImageHeight / fontSpacing);
            if (lines * fontSpacing < mImageHeight) {
                lines += 1;
            }

            //Build the layout with LeadingMarginSpan2
            CustomLeadingMarginSpan span = new CustomLeadingMarginSpan(lines, mImageWidth + 10);

            SpannableString mSpannableString = new SpannableString(plainText);
            mSpannableString.setSpan(span, allTextStart, allTextEnd,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            setText(mSpannableString);
        }
    }

    /**
     * @return if auto adjusting feature was enable
     */
    public boolean isAutoFit() {
        return mAutoFit;
    }

    /**
     * Set CustomTextView auto adjust text size to fit with max lines
     * @param autoFit set true to enable auto adjusting feature
     */
    public void setAutoFit(boolean autoFit) {
        this.mAutoFit = autoFit;
    }
}
