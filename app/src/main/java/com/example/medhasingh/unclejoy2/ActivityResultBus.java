package com.example.medhasingh.unclejoy2;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Created by medha singh on 7/22/2016.
 */
public class ActivityResultBus extends Bus {
    private static ActivityResultBus instance;

    public static ActivityResultBus getInstance() {
        if (instance == null)
            instance = new ActivityResultBus();
        return instance;
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public void postQueue(final Object obj) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ActivityResultBus.getInstance().post(obj);
            }
        });
    }

}
