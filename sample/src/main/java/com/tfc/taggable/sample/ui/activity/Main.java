package com.tfc.taggable.sample.ui.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tfc.taggable.sample.R;
import com.tfc.taggable.style.ClickableMention;
import com.tfc.taggable.widget.TaggableTextView;
import com.tfc.taggable.style.ClickableHashTag;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main extends AppCompatActivity implements ClickableHashTag.OnClickHashTagListener, ClickableMention.OnClickMentionListener {

    @Bind(R.id.a_main_textinputedittext_hashtag)
    TextInputEditText mTextInputEditTextHashTag;
    @Bind(R.id.a_main_taggabletextview_hashtag)
    TaggableTextView mTextViewHashTagResults;

    @Bind(R.id.a_main_textinputedittext_mention)
    TextInputEditText mTextInputEditTextCallOut;
    @Bind(R.id.a_main_taggabletextview_mention)
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

    @OnClick(R.id.a_main_btn_mention)
    public void onClickCallOutButton() {
        String text = mTextInputEditTextCallOut.getText().toString();

        ClickableMention clickableHashTag = new ClickableMention(this);
        clickableHashTag.setOnClickMentionListener(this);

        mTextViewCallOutResults.setMentionText(clickableHashTag, text);
    }

    @OnClick(R.id.a_main_btn_taggable)
    public void onClickTaggableButton() {
        String text = mTextInputEditTextTaggable.getText().toString();

        ClickableHashTag clickableHashTag = new ClickableHashTag(this);
        clickableHashTag.setOnClickHashTagListener(this);

        ClickableMention clickableMention = new ClickableMention(this);
        clickableMention.setOnClickMentionListener(this);

        mTextViewTaggableResults.setTaggableText(clickableHashTag, clickableMention, text);
    }

    @Override
    public void onClickHashTag() {
        Toast.makeText(this, "HashTag Clicked", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClickMention() {
        Toast.makeText(this, "Mention Clicked", Toast.LENGTH_LONG).show();
    }
}
