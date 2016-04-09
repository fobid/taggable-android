package com.tfc.taggable.sample.ui.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tfc.taggable.sample.R;
import com.tfc.taggable.widget.TaggableTextView;
import com.tfc.taggable.style.ClickableHashTag;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main extends AppCompatActivity implements ClickableHashTag.OnClickHashTagListener {

    @Bind(R.id.a_main_textinputedittext_hashtag)
    TextInputEditText mTextInputEditTextHashTag;

    @Bind(R.id.a_main_textinputedittext)
    TextInputEditText mTextInputEditText;

    @Bind(R.id.a_main_taggabletextview)
    TaggableTextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.a_main_btn)
    public void onClickButton(View v) {
        String hashTag = mTextInputEditTextHashTag.getText().toString();

        String text = mTextInputEditText.getText().toString();

        ClickableHashTag clickableHashTag = new ClickableHashTag(this);
        clickableHashTag.setOnClickHashTagListener(this);

        mTextView.setHashTagText(clickableHashTag, hashTag, hashTag + text);
    }

    @Override
    public void onClickHashTag() {
        Toast.makeText(this, "HashTag Clicked", Toast.LENGTH_LONG).show();
    }
}
