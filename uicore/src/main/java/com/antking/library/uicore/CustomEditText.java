package com.antking.library.uicore;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by 51003 on 9/29/2015.
 */
public class CustomEditText extends EditText {

    // Log Tag
    private static final String TAG = "CustomEditText";

    /**
     * Simple constructor to use when creating a CustomEditText from code.
     *
     * @param context The Context the view is running in, through which it can
     *        access the current theme, resources, etc.
     */
    public CustomEditText(Context context) {
        super(context);
    }

    /**
     * Constructor that is called when inflating a CustomEditText from XML.
     * This is called when a CustomEditText is being constructed from an XML file,
     * supplying attributes that CustomEditText specified in the XML file.
     * This version uses a default style of 0, so the only attribute values
     * applied are those in the Context's Theme and the given AttributeSet.
     *
     * <p>
     * The method onFinishInflate() will be called after all children have been
     * added.
     *
     * @param context The Context the CustomEditText is running in, through which
     *        it can access the current theme, resources, etc.
     * @param attrs The attributes of the XML tag that is inflating the CustomEditText.
     * @see #CustomEditText(Context, AttributeSet, int)
     */
    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithCustomAttributes(attrs, 0);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a
     * theme attribute. This constructor of CustomEditText allows it to use its
     * own base style when it is inflating.
     *
     * @param context The Context the CustomEditText is running in, through which
     *        it can access the current theme, resources, etc.
     * @param attrs The attributes of the XML tag that is inflating the CustomEditText.
     * @param defStyleAttr An attribute in the current theme that contains a
     *        reference to a style resource that supplies default values for
     *        the CustomEditText. Can be 0 to not look for defaults.
     * @see #CustomEditText(Context, AttributeSet)
     */
    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWithCustomAttributes(attrs, defStyleAttr);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a
     * theme attribute. This constructor of CustomEditText allows it to use its
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
     * AttributeSet you have supplied <code>&lt;CustomEditText * textColor="#ff000000"&gt;</code>
     * , then the button's text will <em>always</em> be black, regardless of
     * what is specified in any of the styles.
     *
     * @param context The Context the CustomEditText is running in, through which
     *        it can access the current theme, resources, etc.
     * @param attrs The attributes of the XML tag that is inflating the CustomEditText.
     * @param defStyleAttr An attribute in the current theme that contains a
     *        reference to a style resource that supplies default values for
     *        the CustomEditText. Can be 0 to not look for defaults.
     * @param defStyleRes A resource identifier of a style resource that
     *        supplies default values for the CustomEditText, used only if
     *        defStyleAttr is 0 or can not be found in the theme. Can be 0
     *        to not look for defaults.
     * @see #CustomEditText(Context, AttributeSet, int)
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initWithCustomAttributes(attrs, defStyleAttr);
    }


    /**
     * Initial CustomEditText with its own new attributes
     * @param attrs The attributes of the XML tag that is inflating the CustomEditText.
     */
    private void initWithCustomAttributes(AttributeSet attrs, int defStyleAttr){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);

        String font = typedArray.getString(R.styleable.CustomTextView_font);

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


}
