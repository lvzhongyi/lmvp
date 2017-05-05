package com.lvzhongyi.mvp.utils;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author lvzhongyi
 *         <p>
 *         description
 *         date 16/8/23
 *         email lvzhongyiforchrome@gmail.com
 *         </p>
 */
public class StrUtils {

    public static String getText(TextView view) {
        if (view == null) {
            return "";
        }
        if (view.getText() == null) {
            return "";
        }
        return view.getText().toString().replace(" ", "");
    }

    public static long getLong(TextView view) {
        if (TextUtils.isDigitsOnly(getText(view))) {
            return Long.parseLong(getText(view));
        } else {
            return -1;
        }
    }

    public static int getInt(TextView view) {
        return (int) getLong(view);
    }

    public static short getShort(TextView view) {
        return (short) getLong(view);
    }

    public static byte getByte(TextView view) {
        return (byte) getLong(view);
    }
}
