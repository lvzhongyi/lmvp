package com.lvzhongyi.mvp.core;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * @author lvzhongyi
 *         <p>
 *         description
 *         date 16/7/22
 *         email lvzhongyiforchrome@gmail.com
 *         </p>
 */
public abstract class BaseActivity<V extends IBaseV, P extends BasePresenter> extends
        AppCompatActivity
        implements IBaseV {
    protected P p;

    @SuppressWarnings("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutId();
        setContentView(layoutId);
        ButterKnife.bind(new BaseActivity<V, P>() {
            @Override
            protected P createPresenter() {
                return null;
            }

            @Override
            protected int getLayoutId() {
                return 0;
            }

            @Override
            protected void initView() {

            }

            @Override
            protected void initData() {

            }

            @Override
            protected void initListener() {

            }
        });
        initViews();
        p = createPresenter();
        p.attachView(this);
        initData();
        initListener();
    }

    protected abstract P createPresenter();

    protected abstract
    @IdRes
    int getLayoutId();

    protected abstract void initView();
    protected void initViews(){ }

    protected void initData(){}

    protected abstract void initListener();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (p != null) {
            p.detchV();
        }
    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId 添加fragment的容器
     * @param fragment        容器
     */
    @Override
    public void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(containerViewId,null);
        fragmentTransaction.commit();
    }

    /**
     * 跳转到下一个Activity
     *
     * @param target 将要跳转的Class
     */
    @Override
    public void toActivity(Class<?> target) {
        Intent intent = new Intent();
        intent.setClass(this, target);
        startActivity(intent);
    }

    /**
     * 跳转到下一个Activity,并销毁本activity
     *
     * @param target 将要跳转的Class
     */
    @Override
    public void toActivityFinish(Class<?> target) {
        Intent intent = new Intent();
        startActivity(intent);
        intent.setClass(this, target);
        new AppCompatActivity().finish();
    }

    /**
     * 跳转下一个activity返回时携带结果
     *
     * @param target
     * @param tag
     */
    public void toActivityResult(Class<?> target, int tag) {
        Intent intent = new Intent();
        startActivityForResult(intent, tag);
        intent.setClass(this, target);
    }

    /**
     * 显示dialogFragment
     *
     * @param fragment
     */
    public synchronized void showDialogFragment(DialogFragment fragment) {
        if (fragment != null) {
            fragment.show(getSupportFragmentManager(), fragment.getClass().getSimpleName());
        }
    }

    public void shortToast(String msg) {
        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void longToast(String msg) {
        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public AppCompatActivity getAppCompatActivity() {
        return this;
    }
}
