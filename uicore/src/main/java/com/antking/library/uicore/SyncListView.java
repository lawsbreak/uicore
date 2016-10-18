package com.antking.library.uicore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by thoitran on 4/28/16.
 * - Used to create 2 horizontal scroll views and sync their scrolling
 * - Use @setViewPartner to set the second view synced with this
 */
public class SyncListView extends FloatingGroupExpandableListView {
    public boolean selfScroll = true;
    public boolean selfTouch = true;
    protected SyncListView viewPartner = null;

    public SyncListView(Context context) {
        super(context);
    }

    public SyncListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SyncListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public SyncListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    /*
    * Get View Partner
    */
    public SyncListView getViewPartner() {
        return viewPartner;
    }

    /*
    * Set View Partner
    * @param viewPartner SyncHorizontalScrollView which will sync scrolling with current view
    */
    public void setViewPartner(SyncListView viewPartner) {
        this.viewPartner = viewPartner;
    }

    /*
    * Override onInterceptTouchEvent to sync event with partner
    */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /* Event from partner then do not generate new one on partner */
        if (!selfScroll) {
            selfScroll = true;
        /* Event from itself then generate new one on partner*/
        }else {
            /* Clone event */
            MotionEvent event = MotionEvent.obtain(ev);
            /* Re-set location to view partner region */
            event.setLocation(viewPartner.getLeft() + 1, ev.getY());
            /* Info partner that it was not itself event */
            viewPartner.selfScroll = false;
            /* Generate event on partner */
            viewPartner.onInterceptTouchEvent(event);
        }
        /* Don't forget call default process with event ^^ */
        return super.onInterceptTouchEvent(ev);
    }

    /*
    * Override onTouchEvent to sync event with partner
    */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        /* Event from partner then do not generate new one on partner */
        if (!selfTouch) {
            selfTouch = true;
        /* Event from itself then generate new one on partner*/
        }else {
            /* Clone event */
            MotionEvent event = MotionEvent.obtain(ev);
            /* Re-set location to view partner region */
            event.setLocation(viewPartner.getLeft() + 1, ev.getY());
            /* Info partner that it was not itself event */
            viewPartner.selfTouch = false;
            /* Generate event on partner */
            viewPartner.onTouchEvent(event);
        }
        /* Don't forget call default process with event ^^ */
        return super.onTouchEvent(ev);
    }
}
