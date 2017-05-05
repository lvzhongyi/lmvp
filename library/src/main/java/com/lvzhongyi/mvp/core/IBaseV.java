package com.lvzhongyi.mvp.core;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * @author lvzhongyi
 *         <p>
 *         description
 *         date 16/7/22
 *         email lvzhongyiforchrome@gmail.com
 *         </p>
 */
public interface IBaseV {
    Context getContext();

    AppCompatActivity getAppCompatActivity();

    void toActivityFinish(Class<?> target);

    void toActivity(Class<?> target);

    void toActivityResult(Class<?> target, int tag);

    void addFragment(int containerViewId, Fragment fragment);

    void shortToast(String msg);

    void longToast(String msg);

    void showDialogFragment(DialogFragment fragment);
}
