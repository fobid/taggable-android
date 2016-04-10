/*
 * Copyright Fobid. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tfc.taggable.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.tfc.taggable.style.ClickableCallOut;
import com.tfc.taggable.style.ClickableHashTag;

/**
 * author @Fobid
 */
public class TaggableTextView extends TextView {

    private boolean mIsClickableUrl;
    private ClickableHashTag mClickableHashTag;
    private ClickableCallOut mClickableCallOut;

    public TaggableTextView(Context context) {
        super(context);
    }

    public TaggableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TaggableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setClickableUrl(boolean isClickableUrl) {
        mIsClickableUrl = isClickableUrl;
    }

    public void setHashTagText(ClickableHashTag clickableHashTag, String text) {
        if (clickableHashTag == null) {
            clickableHashTag = new ClickableHashTag(getContext());
        }
        mClickableHashTag = clickableHashTag;

        TaggableViewUtils.makeHashtagTextView(this, mClickableHashTag, text, mIsClickableUrl);
    }

    public void setCalloutText(ClickableCallOut clickableCallOut, String text) {
        if (clickableCallOut == null) {
            clickableCallOut = new ClickableCallOut(getContext());
        }
        mClickableCallOut = clickableCallOut;

        TaggableViewUtils.makeCalloutTextView(this, clickableCallOut, text, mIsClickableUrl);
    }

    public void setTaggableText(ClickableHashTag clickableHashTag, ClickableCallOut clickableCallOut, String text) {
        if (clickableHashTag == null) {
            clickableHashTag = new ClickableHashTag(getContext());
        }
        if (clickableCallOut == null) {
            clickableCallOut = new ClickableCallOut(getContext());
        }
        mClickableHashTag = clickableHashTag;
        mClickableCallOut = clickableCallOut;

        TaggableViewUtils.makeTaggableTextView(this, clickableHashTag, clickableCallOut, text, mIsClickableUrl);
    }
}
