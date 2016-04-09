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
    public interface OnClickCalloutListener {
        void onClickCallout();
    }
    private Context mContext;

    public ClickableCallOut(Context ctx) {
        super();
        mContext = ctx;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setARGB(255, 51, 51, 51);
        ds.setTypeface(Typeface.DEFAULT_BOLD);

    }

    @Override
    public void onClick(View widget) {
        TextView tv = (TextView) widget;
        Spanned s = (Spanned) tv.getText();
        int start = s.getSpanStart(this);
        int end = s.getSpanEnd(this);
        String theWord = s.subSequence(start + 1, end).toString();
        Toast.makeText(mContext, String.format("Here's a cool person: %s", theWord), 10).show();

    }
}