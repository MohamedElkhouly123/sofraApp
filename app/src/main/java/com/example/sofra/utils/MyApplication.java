package com.example.sofra.utils;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private static List<IMemoryInfo> memInfoList = new ArrayList<IMemoryInfo>();

    public static abstract interface IMemoryInfo {
        public void goodTimeToReleaseMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
//don't compare with == as intermediate stages also can be reported, always better to check >= or <=
        if (level >= ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL) {
            try {
                // Activity at the front will get earliest than activity at the
                // back
                for (int i = memInfoList.size() - 1; i >= 0; i--) {
                    try {
                        memInfoList.get(i).goodTimeToReleaseMemory();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param implementor
     *            interested listening in memory events
     */
    public static void registerMemoryListener(IMemoryInfo implementor) {
        memInfoList.add(implementor);
    }

    public static void unregisterMemoryListener(IMemoryInfo implementor) {
        memInfoList.remove(implementor);
    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//    }
//
//    @Override
//    public void onTrimMemory(int level) {
//        super.onTrimMemory(level);
//    }
//
//    private static class Callback2  implements ComponentCallbacks2 {
//        private int mLevel;
//
//        @Override
//        public void onTrimMemory(int level) {
//            mLevel = level;
//        }
//
//        @Override
//        public void onConfigurationChanged(@NonNull Configuration newConfig) {
//
//        }
//
//        @Override
//        public void onLowMemory() {
//
//        }
//    }
    }
