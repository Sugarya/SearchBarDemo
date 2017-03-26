package com.sugary.searchviewtest.utils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Ethan on 17/03/16.
 * 键盘管理工具类
 */
public class KeyboardUtils {

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(View view,Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
        if (isOpen) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        }
    }

    /**
     * 显示软键盘
     * */
    public static void showSoftInput(View view,Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 关闭软键盘
     * @param activity
     */
    public static void closeInputKeyboard(Activity activity) {
        if(activity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(inputMethodManager != null) {
                boolean active = inputMethodManager.isActive();
                if (active) {
                    View currentFocus = activity.getCurrentFocus();
                    if (currentFocus != null) {
                        IBinder windowToken = currentFocus.getWindowToken();
                        if (windowToken != null) {
                            inputMethodManager.hideSoftInputFromWindow(
                                    windowToken,
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                }
            }
        }
    }

}
