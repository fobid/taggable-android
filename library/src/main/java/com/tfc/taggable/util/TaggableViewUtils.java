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
package com.tfc.taggable.util;

import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.widget.TextView;

import com.tfc.taggable.style.ClickableCallOut;
import com.tfc.taggable.style.ClickableHashTag;
import com.tfc.taggable.style.ClickableUrlSpan;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author @Fobid
 */
public class TaggableViewUtils {

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
        Matcher matcher = pattern.matcher(trim(body));

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

    public static void makeHashtagTextView(ClickableHashTag clickableHashTag, TextView textView,
                                           String hashTag, String text) {
        SpannableString commentsContent = new SpannableString(text);

        ArrayList<int[]> hashtagSpans = getSpans(hashTag, '#');
        int size = hashtagSpans.size();

        for (int i = 0; i < size; i++) {
            int[] span = hashtagSpans.get(i);
            int hashTagStart = span[0];
            if (hashTagStart < 0) {
                continue;
            }
            int hashTagEnd = span[1];
            if (hashTagStart >= hashTagEnd) {
                continue;
            }

            commentsContent.setSpan(clickableHashTag, hashTagStart, hashTagEnd, 0);
            setClickableUrl(commentsContent, text);

        }
        textView.setText(commentsContent);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static void makeCalloutTextView(ClickableCallOut callOut, TextView textView,
                                           String category, String text) {
        SpannableString commentsContent = new SpannableString(text);

        ArrayList<int[]> hashtagSpans = getSpans(category, '@');
        int size = hashtagSpans.size();

        for (int i = 0; i < size; i++) {
            int[] span = hashtagSpans.get(i);
            int callOutStart = span[0];
            if (callOutStart < 0) {
                continue;
            }
            int callOutEnd = span[1];
            if (callOutStart >= callOutEnd) {
                continue;
            }

            commentsContent.setSpan(callOut, callOutStart, callOutEnd, 0);
            setClickableUrl(commentsContent, text);

        }
        textView.setText(commentsContent);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
