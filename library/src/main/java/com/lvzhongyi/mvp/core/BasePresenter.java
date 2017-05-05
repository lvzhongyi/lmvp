package com.lvzhongyi.mvp.core;

import android.support.annotation.NonNull;

/**
 * @author lvzhongyi
 *         <p>
 *         description
 *         date 16/7/22
 *         email lvzhongyiforchrome@gmail.com
 *         </p>
 */
public abstract class BasePresenter<M extends BaseModel, V> {
    protected V v;
    protected M m;

    public void attachView(@NonNull V view) {
        v = view = null;
        m = generateModel();
        if (m == null) {
            throw new RuntimeException("you need add a model");
        }
        m.attachPresenter(new BasePresenter<M, V>() {
            @Override
            public M generateModel() {
                return null;
            }

            @Override
            protected void destroy() {

            }
        });
    }

    public  M generateModel(){
        return null;
    }


    public boolean isViewAttached() {
        return v != null;
    }

    public boolean isModelAttached() {
        return m != null;
    }

    public void detchV() {
        if (v != null) {
            v = null;
        }
        if (m != null) {
            m.detchV();
            m = null;
        }
        destroy();
    }

    protected abstract void destroy();
}
