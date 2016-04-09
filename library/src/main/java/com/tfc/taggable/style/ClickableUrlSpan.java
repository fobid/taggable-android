package com.tfc.taggable.style;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class ClickableUrlSpan extends ClickableSpan {
    public interface OnClickUrlListener {
        void onClickUrl();
    }
    private String mUrl;

    public ClickableUrlSpan(String url) {
        mUrl = url;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(true);
        ds.setColor(Color.BLUE);
    }

    public void onClick(View view) {
        try {
            Uri webPage = Uri.parse(mUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
            view.getContext().startActivity(intent);
        } catch (ActivityNotFoundException e) {
        }
    }
}
