package com.nv95.fbchat.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

import java.lang.ref.WeakReference;

/**
 * Source: http://stackoverflow.com/questions/25628258/align-text-around-imagespan-center-vertical
 */

public class CenteredImageSpan extends ImageSpan {

    // Extra variables used to redefine the Font Metrics when an ImageSpan is added
    private int initialDescent = 0;
    private int extraSpace = 0;

    public CenteredImageSpan(Context context, Bitmap b) {
        super(context, b);
    }

    //
    // Following methods are overriden from DynamicDrawableSpan.
    //

    // Method used to redefined the Font Metrics when an ImageSpan is added
    @Override
    public int getSize(Paint paint, CharSequence text,
                       int start, int end,
                       Paint.FontMetricsInt fm) {
        Drawable d = getCachedDrawable();
        Rect rect = d.getBounds();

        if (fm != null) {
            // Centers the text with the ImageSpan
            if (rect.bottom - (fm.descent - fm.ascent) >= 0) {
                // Stores the initial descent and computes the margin available
                initialDescent = fm.descent;
                extraSpace = rect.bottom - (fm.descent - fm.ascent);
            }

            fm.descent = extraSpace / 2 + initialDescent;
            fm.bottom = fm.descent;

            fm.ascent = -rect.bottom + fm.descent;
            fm.top = fm.ascent;
        }

        return rect.right;
    }

    // Redefined locally because it is a private member from DynamicDrawableSpan
    private Drawable getCachedDrawable() {
        WeakReference<Drawable> wr = mDrawableRef;
        Drawable d = null;

        if (wr != null)
            d = wr.get();

        if (d == null) {
            d = getDrawable();
            mDrawableRef = new WeakReference<>(d);
        }

        return d;
    }

    private WeakReference<Drawable> mDrawableRef;
}