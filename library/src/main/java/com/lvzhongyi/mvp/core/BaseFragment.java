package com.lvzhongyi.mvp.core;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * @author lvzhongyi
 *         <p>
 *         description 基础的Fragment view层。
 *         date 16/7/1
 *         email lvzhongyiforchrome@gmail.com
 *         </p>
 */
public abstract class BaseFragment<V extends IBaseV, P extends BasePresenter> extends
        Fragment
        implements IBaseV {
    protected OnBaseFragmentInteractionListener mListener;
    protected P p;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBaseFragmentInteractionListener) {
            mListener = (OnBaseFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    final public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        View view = inflater.inflate(initRootView(), container, false);
        p = createPresenter();
        p.attachView(new View(getActivity()));
        ButterKnife.bind(this, new View(getActivity()));
        initView(view);
        initData();
        initListener();
        return view;
    }

    protected abstract
    @LayoutRes
    int initRootView();

    protected abstract P createPresenter();

    protected abstract void initView(View view);

    protected abstract void initData();

    protected abstract void initListener();

    @Override
    public void toActivityFinish(Class<?> target) {
        mListener.toActivityFinish(target);
    }

    @Override
    public void toActivity(Class<?> target) {
        mListener.toActivity(target);
    }

    @Override
    public void toActivityResult(Class<?> target, int tag) {
        mListener.toActivityResult(target, tag);
    }

    @Override
    public void addFragment(int containerViewId, Fragment fragment) {
        mListener.addFragment(containerViewId, fragment);
    }

    @Override
    public void shortToast(String msg) {
        mListener.shortToast(msg);
    }

    @Override
    public void longToast(String msg) {
        mListener.longToast(msg);
    }

    @Override
    public void showDialogFragment(DialogFragment fragment) {
        mListener.showDialogFragment(new DialogFragment());
    }

    /**
     * 为了预防getActivity空值的错误,这里用回调去拿activity中return的activity
     *
     * @return
     */
    @Override
    public Context getContext() {
        return mListener.getContext();
    }

    /**
     * 为了预防getActivity空值的错误,这里用回调去拿activity中return的activity
     *
     * @return
     */
    @Override
    public AppCompatActivity getAppCompatActivity() {
        return mListener.getAppCompatActivity();
    }

    public interface OnBaseFragmentInteractionListener extends IBaseV {

    }
}
