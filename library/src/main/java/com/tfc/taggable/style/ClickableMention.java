package com.tfc.taggable.style;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class ClickableMention extends ClickableSpan {
    public interface OnClickMentionListener {
        void onClickMention();
    }

    private final Context mContext;
    private OnClickMentionListener mListener;

    public ClickableMention(Context ctx) {
        super();
        mContext = ctx;
    }

    public void setOnClickMentionListener(OnClickMentionListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Couldn't set listener: OnClickMentionListener is null");
        }
        mListener = listener;
    }

    public OnClickMentionListener getOnClickMentionListener() {
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
            mListener.onClickMention();
        }
    }
}