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
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.tfc.taggable.R;
import com.tfc.taggable.style.ClickableMention;
import com.tfc.taggable.style.ClickableHashTag;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * author @Fobid
 */
public class TaggableTextView extends TextView {

    private boolean mIsClickableUrl;
    private String mHashTagText;
    private String mMentionText;
    private String mTaggableText;
    private String mHashTagHandlerName;
    private String mMentionHandlerName;
    private ClickableHashTag mClickableHashTag;
    private ClickableMention mClickableMention;

    public TaggableTextView(Context context) {
        super(context);
    }

    public TaggableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context, attrs);
    }

    public TaggableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TaggableTextView);

        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            if (R.styleable.TaggableTextView_setClickableUrl == attr) {
                mIsClickableUrl = a.getBoolean(attr, true);
            } else if (R.styleable.TaggableTextView_setHashTagText == attr) {
                mHashTagText = a.getString(attr);
            } else if (R.styleable.TaggableTextView_setMentionText == attr) {
                mMentionText = a.getString(attr);
            } else if (R.styleable.TaggableTextView_setTaggableText == attr) {
                mTaggableText = a.getString(attr);
            } else if (R.styleable.TaggableTextView_onClickHashTag == attr) {
                if (context.isRestricted()) {
                    throw new IllegalStateException("The taggable:onClickHashTag attribute cannot "
                            + "be used within a restricted context");
                }
                mHashTagHandlerName = a.getString(attr);
            } else if (R.styleable.TaggableTextView_onClickMention == attr) {
                if (context.isRestricted()) {
                    throw new IllegalStateException("The taggable:onClickMention attribute cannot "
                            + "be used within a restricted context");
                }
                mMentionHandlerName = a.getString(attr);
            }
        }

        if (!TextUtils.isEmpty(mHashTagText)) {
            mClickableHashTag = new ClickableHashTag(getContext());
        }
        if (!TextUtils.isEmpty(mMentionText)) {
            mClickableMention = new ClickableMention(getContext());
        }
        if (!TextUtils.isEmpty(mTaggableText)) {
            mClickableHashTag = new ClickableHashTag(getContext());
            mClickableMention = new ClickableMention(getContext());
        }
        if (!TextUtils.isEmpty(mHashTagHandlerName)) {
            mClickableHashTag.setOnClickHashTagListener(new DeclaredOnClickHashTagListener(this, mHashTagHandlerName));
        }
        if (!TextUtils.isEmpty(mMentionHandlerName)) {
            mClickableMention.setOnClickMentionListener(new DeclaredOnClickMentionListener(this, mMentionHandlerName));
        }

        a.recycle();
    }

    public void setClickableUrl(boolean isClickableUrl) {
        mIsClickableUrl = isClickableUrl;
    }

    public boolean isClickableUrl() {
        return mIsClickableUrl;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mClickableHashTag != null && mClickableMention != null) {
            setTaggableText(mClickableHashTag, mClickableMention, mTaggableText);
        } else if (mClickableHashTag != null && mClickableMention == null) {
            setHashTagText(mClickableHashTag, mHashTagText);
        } else if (mClickableMention != null && mClickableHashTag == null) {
            setMentionText(mClickableMention, mMentionText);
        }

        super.onDraw(canvas);
    }

    public void setHashTagText(ClickableHashTag clickableHashTag, String text) {
        if (clickableHashTag == null) {
            clickableHashTag = new ClickableHashTag(getContext());
        }
        mClickableHashTag = clickableHashTag;

        TaggableViewUtils.makeHashtagTextView(this, mClickableHashTag, text, mIsClickableUrl);
    }

    public void setMentionText(ClickableMention clickableMention, String text) {
        if (clickableMention == null) {
            clickableMention = new ClickableMention(getContext());
        }
        mClickableMention = clickableMention;

        TaggableViewUtils.makeMentionTextView(this, clickableMention, text, mIsClickableUrl);
    }

    public void setTaggableText(ClickableHashTag clickableHashTag, ClickableMention clickableMention, String text) {
        if (clickableHashTag == null) {
            clickableHashTag = new ClickableHashTag(getContext());
        }
        if (clickableMention == null) {
            clickableMention = new ClickableMention(getContext());
        }
        mClickableHashTag = clickableHashTag;
        mClickableMention = clickableMention;

        TaggableViewUtils.makeTaggableTextView(this, clickableHashTag, clickableMention, text, mIsClickableUrl);
    }

    private static class DeclaredOnClickHashTagListener implements ClickableHashTag.OnClickHashTagListener {
        private final View mHostView;
        private final String mMethodName;

        private Method mMethod;

        public DeclaredOnClickHashTagListener(@NonNull View hostView, @NonNull String methodName) {
            mHostView = hostView;
            mMethodName = methodName;
        }

        @Override
        public void onClickHashTag() {
            if (mMethod == null) {
                mMethod = resolveMethod(mHostView.getContext(), mMethodName);
            }

            try {
                mMethod.invoke(mHostView.getContext());
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(
                        "Could not execute non-public method for taggable:onClickHashTag", e);
            } catch (InvocationTargetException e) {
                throw new IllegalStateException(
                        "Could not execute method for taggable:onClickHashTag", e);
            }
        }

        @NonNull
        private Method resolveMethod(@Nullable Context context, @NonNull String name) {
            while (context != null) {
                try {
                    if (!context.isRestricted()) {
                        return context.getClass().getMethod(mMethodName);
                    }
                } catch (NoSuchMethodException e) {
                    // Failed to find method, keep searching up the hierarchy.
                }

                if (context instanceof ContextWrapper) {
                    context = ((ContextWrapper) context).getBaseContext();
                } else {
                    // Can't search up the hierarchy, null out and fail.
                    context = null;
                }
            }

            final int id = mHostView.getId();
            final String idText = id == NO_ID ? "" : " with id '"
                    + mHostView.getContext().getResources().getResourceEntryName(id) + "'";
            throw new IllegalStateException("Could not find method " + mMethodName
                    + " in a parent or ancestor Context for taggable:onClickHashTag "
                    + "attribute defined on view " + mHostView.getClass() + idText);
        }
    }

    private static class DeclaredOnClickMentionListener implements ClickableMention.OnClickMentionListener {
        private final View mHostView;
        private final String mMethodName;

        private Method mMethod;

        public DeclaredOnClickMentionListener(@NonNull View hostView, @NonNull String methodName) {
            mHostView = hostView;
            mMethodName = methodName;
        }

        @Override
        public void onClickMention() {
            if (mMethod == null) {
                mMethod = resolveMethod(mHostView.getContext(), mMethodName);
            }

            try {
                mMethod.invoke(mHostView.getContext());
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(
                        "Could not execute non-public method for taggable:onClickMention", e);
            } catch (InvocationTargetException e) {
                throw new IllegalStateException(
                        "Could not execute method for taggable:onClickMention", e);
            }
        }

        @NonNull
        private Method resolveMethod(@Nullable Context context, @NonNull String name) {
            while (context != null) {
                try {
                    if (!context.isRestricted()) {
                        return context.getClass().getMethod(mMethodName);
                    }
                } catch (NoSuchMethodException e) {
                    // Failed to find method, keep searching up the hierarchy.
                }

                if (context instanceof ContextWrapper) {
                    context = ((ContextWrapper) context).getBaseContext();
                } else {
                    // Can't search up the hierarchy, null out and fail.
                    context = null;
                }
            }

            final int id = mHostView.getId();
            final String idText = id == NO_ID ? "" : " with id '"
                    + mHostView.getContext().getResources().getResourceEntryName(id) + "'";
            throw new IllegalStateException("Could not find method " + mMethodName
                    + " in a parent or ancestor Context for taggable:onClickMention "
                    + "attribute defined on view " + mHostView.getClass() + idText);
        }
    }
}
