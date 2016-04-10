package com.tfc.taggable.sample.ui.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tfc.taggable.sample.R;
import com.tfc.taggable.style.ClickableCallOut;
import com.tfc.taggable.widget.TaggableTextView;
import com.tfc.taggable.style.ClickableHashTag;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main extends AppCompatActivity implements ClickableHashTag.OnClickHashTagListener, ClickableCallOut.OnClickCallOutListener {

    @Bind(R.id.a_main_textinputedittext_hashtag)
    TextInputEditText mTextInputEditTextHashTag;
    @Bind(R.id.a_main_taggabletextview_hashtag)
    TaggableTextView mTextViewHashTagResults;

    @Bind(R.id.a_main_textinputedittext_callout)
    TextInputEditText mTextInputEditTextCallOut;
    @Bind(R.id.a_main_taggabletextview_callout)
    TaggableTextView mTextViewCallOutResults;

    @Bind(R.id.a_main_textinputedittext_taggable)
    TextInputEditText mTextInputEditTextTaggable;
    @Bind(R.id.a_main_taggabletextview_taggable)
    TaggableTextView mTextViewTaggableResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.a_main_btn_hashtag)
    public void onClickHashTagButton(View v) {
        String text = mTextInputEditTextHashTag.getText().toString();

        ClickableHashTag clickableHashTag = new ClickableHashTag(this);
        clickableHashTag.setOnClickHashTagListener(this);

        mTextViewHashTagResults.setHashTagText(clickableHashTag, text);
    }

    @OnClick(R.id.a_main_btn_callout)
    public void onClickCallOutButton() {
        String text = mTextInputEditTextCallOut.getText().toString();

        ClickableCallOut clickableHashTag = new ClickableCallOut(this);
        clickableHashTag.setOnClickCallOutListener(this);

        mTextViewCallOutResults.setCalloutText(clickableHashTag, text);
    }

    @OnClick(R.id.a_main_btn_taggable)
    public void onClickTaggableButton() {
        String text = mTextInputEditTextTaggable.getText().toString();

        ClickableHashTag clickableHashTag = new ClickableHashTag(this);
        clickableHashTag.setOnClickHashTagListener(this);

        ClickableCallOut clickableCallOut = new ClickableCallOut(this);
        clickableCallOut.setOnClickCallOutListener(this);

        mTextViewTaggableResults.setTaggableText(clickableHashTag, clickableCallOut, text);
    }

    @Override
    public void onClickHashTag() {
        Toast.makeText(this, "HashTag Clicked", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClickCallOut() {
        Toast.makeText(this, "CallOut Clicked", Toast.LENGTH_LONG).show();
    }
}
