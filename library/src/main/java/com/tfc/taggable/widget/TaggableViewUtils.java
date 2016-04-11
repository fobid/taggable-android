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

import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.widget.TextView;

import com.tfc.taggable.style.ClickableMention;
import com.tfc.taggable.style.ClickableHashTag;
import com.tfc.taggable.style.ClickableUrlSpan;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author @Fobid
 */
class TaggableViewUtils {

    public static String substringWithinMaxLines(String str, int maxLines) {
        int maxLength = 130;

        String[] strArr = str.split("\n");
        int arrLength = strArr.length;

        String newString = "";

        if (arrLength > maxLines) {
            for (int i = 0; i <= maxLines; i++) {
                if (i >= arrLength) {
                    break;
                }
                if (i == maxLines && arrLength > maxLines) {
                    newString += strArr[i] + "...";
                } else {
                    newString += strArr[i] + "\n";
                }
            }

            if (newString.length() > maxLength) {
                newString = newString.substring(0, maxLength - 3) + "...";
            }
            return newString;
        } else {
            newString = str;

            if (newString.length() > maxLength) {
                newString = newString.substring(0, maxLength - 3) + "...";
            }
            return newString;
        }
    }

    public static String trim(String text) {
        if (text == null) {
            return "";
        }
        String[] textArr = text.split(" ");
        String newText = "";
        for (String item : textArr) {
            newText += item;
        }
        return newText;
    }

    private static ArrayList<int[]> getSpans(String body, char prefix) {
        ArrayList<int[]> spans = new ArrayList<>();

        Pattern pattern = Pattern.compile(prefix + "\\w+");
//        Matcher matcher = pattern.matcher(trim(body));
        Matcher matcher = pattern.matcher(body);

        // Check all occurrences
        while (matcher.find()) {
            int[] currentSpan = new int[2];
            currentSpan[0] = matcher.start();
            currentSpan[1] = matcher.end();
            spans.add(currentSpan);
        }

        return spans;
    }

    private static void setClickableUrl(SpannableString spannableString, String text) {
        Matcher urlMatcher = Patterns.WEB_URL.matcher(text);

        int i = 0;
        while (urlMatcher.find()) {
            String url = urlMatcher.group(i);
            int start = urlMatcher.start(i);
            if (start < 0) {
                continue;
            }
            int end = urlMatcher.end(i++);
            if (start >= end) {
                continue;
            }
            spannableString.setSpan(new ClickableUrlSpan(url), start, end, 0);
        }
    }

    static void makeHashtagTextView(final TextView textView,
                                    final ClickableHashTag clickableHashTag,
                                    final String text,
                                    final boolean isClickableUrl) {

        SpannableString spannableString = new SpannableString(text);

        ArrayList<int[]> hashTagSpans = getSpans(text, '#');
        int size = hashTagSpans.size();

        for (int i = 0; i < size; i++) {
            ClickableHashTag clickableHashTagSpan = new ClickableHashTag(textView.getContext());
            final ClickableHashTag.OnClickHashTagListener listener = clickableHashTag.getOnClickHashTagListener();

            if (listener != null) {
                clickableHashTagSpan.setOnClickHashTagListener(listener);
            }

            int[] span = hashTagSpans.get(i);
            int hashTagStart = span[0];
            if (hashTagStart < 0) {
                continue;
            }
            int hashTagEnd = span[1];
            if (hashTagStart >= hashTagEnd) {
                continue;
            }

            spannableString.setSpan(clickableHashTagSpan, hashTagStart, hashTagEnd, 0);

            if (isClickableUrl) {
                setClickableUrl(spannableString, text);
            }

        }
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    static void makeMentionTextView(final TextView textView,
                                    final ClickableMention clickableMention,
                                    final String text,
                                    final boolean isClickableUrl) {

        SpannableString spannableString = new SpannableString(text);

        ArrayList<int[]> mentionSpans = getSpans(text, '@');
        int size = mentionSpans.size();

        for (int i = 0; i < size; i++) {
            ClickableMention clickableMentionSpan = new ClickableMention(textView.getContext());
            final ClickableMention.OnClickMentionListener listener = clickableMention.getOnClickMentionListener();

            if (listener != null) {
                clickableMentionSpan.setOnClickMentionListener(listener);
            }

            int[] span = mentionSpans.get(i);
            int callOutStart = span[0];
            if (callOutStart < 0) {
                continue;
            }
            int callOutEnd = span[1];
            if (callOutStart >= callOutEnd) {
                continue;
            }

            spannableString.setSpan(clickableMentionSpan, callOutStart, callOutEnd, 0);

            if (isClickableUrl) {
                setClickableUrl(spannableString, text);
            }

        }
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    static void makeTaggableTextView(final TextView textView,
                                     final ClickableHashTag clickableHashTag,
                                     final ClickableMention clickableMention,
                                     final String text,
                                     final boolean isClickableUrl) {

        SpannableString spannableString = new SpannableString(text);

        ArrayList<int[]> hashTagSpans = getSpans(text, '#');
        int hashTagSpansSize = hashTagSpans.size();

        for (int i = 0; i < hashTagSpansSize; i++) {
            ClickableHashTag clickableHashTagSpan = new ClickableHashTag(textView.getContext());
            final ClickableHashTag.OnClickHashTagListener listener = clickableHashTag.getOnClickHashTagListener();

            if (listener != null) {
                clickableHashTagSpan.setOnClickHashTagListener(listener);
            }

            int[] span = hashTagSpans.get(i);
            int hashTagStart = span[0];
            if (hashTagStart < 0) {
                continue;
            }
            int hashTagEnd = span[1];
            if (hashTagStart >= hashTagEnd) {
                continue;
            }

            spannableString.setSpan(clickableHashTagSpan, hashTagStart, hashTagEnd, 0);

            if (isClickableUrl) {
                setClickableUrl(spannableString, text);
            }

        }

        ArrayList<int[]> callOutSpans = getSpans(text, '@');
        int callOutSpansSize = callOutSpans.size();

        for (int i = 0; i < callOutSpansSize; i++) {
            ClickableMention clickableMentionSpan = new ClickableMention(textView.getContext());
            final ClickableMention.OnClickMentionListener listener = clickableMention.getOnClickMentionListener();

            if (listener != null) {
                clickableMentionSpan.setOnClickMentionListener(listener);
            }

            int[] span = callOutSpans.get(i);
            int callOutStart = span[0];
            if (callOutStart < 0) {
                continue;
            }
            int callOutEnd = span[1];
            if (callOutStart >= callOutEnd) {
                continue;
            }

            spannableString.setSpan(clickableMentionSpan, callOutStart, callOutEnd, 0);

            if (isClickableUrl) {
                setClickableUrl(spannableString, text);
            }

        }

        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
