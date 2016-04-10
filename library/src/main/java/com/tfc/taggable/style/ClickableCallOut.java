package com.tfc.taggable.style;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ClickableCallOut extends ClickableSpan {
    public interface OnClickCallOutListener {
        void onClickCallOut();
    }

    private final Context mContext;
    private OnClickCallOutListener mListener;

    public ClickableCallOut(Context ctx) {
        super();
        mContext = ctx;
    }

    public void setOnClickCallOutListener(OnClickCallOutListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Couldn't set listener: OnClickCallOutListener is null");
        }
        mListener = listener;
    }

    public OnClickCallOutListener getOnClickCallOutListener() {
        return mListener;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setARGB(255, 51, 51, 51);
        ds.setTypeface(Typeface.DEFAULT_BOLD);

    }

    @Override
    public void onClick(View widget) {
        if (mListener != null) {
            mListener.onClickCallOut();
        }
    }
}