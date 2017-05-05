package com.lvzhongyi.mvp.core;

import android.support.annotation.NonNull;

/**
 * @author lvzhongyi
 *         <p>
 *         description
 *         date 16/8/16
 *         email lvzhongyiforchrome@gmail.com
 *         </p>
 */
public abstract class BaseModel<P> {
    private P p;
    private P presenter;

    public void attachPresenter(@NonNull P presenter) {
        p = this.presenter;
    }


    public boolean isPresenterAttached() {
        return p != null;
    }

    public void detchV() {
        if (p != null) {
            p = null;
        }
        destroy();
    }

    protected abstract void destroy();

}
