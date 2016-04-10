package com.tfc.taggable.style;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class ClickableHashTag extends ClickableSpan {
    public interface OnClickHashTagListener {
        void onClickHashTag();
    }

    private final Context mContext;
    private int mTextColor;
    private boolean mEnableUnderlineText;
    private Typeface mTypeface;
    private OnClickHashTagListener mListener;

    public ClickableHashTag(Context context) {
        mContext = context;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTextColor = context.getColor(android.R.color.black);
        } else {
            mTextColor = ContextCompat.getColor(context, android.R.color.black);
        }
        mEnableUnderlineText = false;
        mTypeface = Typeface.DEFAULT;
    }

    public OnClickHashTagListener getOnClickHashTagListener() {
        return mListener;
    }

    public void setTextColor(int color) {
        mTextColor = color;
    }

    public void setUnderlineText(boolean enable) {
        mEnableUnderlineText = enable;
    }

    public void setTypeface(Typeface typeface) {
        mTypeface = typeface;
    }

    public void setOnClickHashTagListener(OnClickHashTagListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Couldn't set listener: OnClickHashTagListener is null");
        }
        mListener = listener;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(mTextColor);
        ds.setUnderlineText(mEnableUnderlineText);
        ds.setTypeface(mTypeface);
    }

    @Override
    public void onClick(View widget) {
        if (mListener != null) {
            mListener.onClickHashTag();
        }
    }
}