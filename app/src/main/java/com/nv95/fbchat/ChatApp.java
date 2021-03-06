package com.nv95.fbchat;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nv95.fbchat.components.BubbleDrawable;
import com.nv95.fbchat.utils.DayNightPalette;
import com.nv95.fbchat.utils.LayoutUtils;

/**
 * Created by nv95 on 08.08.16.
 */

public class ChatApp extends Application {

    private static DayNightPalette mAppPalette;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean dark = prefs.getBoolean("dark",false);
        int color = prefs.getInt("color", -1);
        if (color == -1) {
            color = 217;
        }
        mAppPalette = DayNightPalette.fromValue(color, dark);
        BubbleDrawable.pointerSize = LayoutUtils.DpToPx(getResources(), 18);
        initImageLoader(this);
    }

    public static DayNightPalette getApplicationPalette() {
        return mAppPalette;
    }

    public static ImageLoader initImageLoader(Context c) {
        if (ImageLoader.getInstance().isInited())
            return ImageLoader.getInstance();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(c)
                .defaultDisplayImageOptions(getImageLoaderOptions())
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // 2 Mb
                .build();

        ImageLoader.getInstance().init(config);
        return ImageLoader.getInstance();
    }

    public static DisplayImageOptions getImageLoaderOptions() {
        return getImageLoaderOptionsBuilder().build();
    }

    public static DisplayImageOptions.Builder getImageLoaderOptionsBuilder() {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(false)
                .displayer(new FadeInBitmapDisplayer(400, true, true, false));
    }

    public static void restart(Activity activity) {
        Intent mStartActivity = new Intent(activity, activity.getClass());
        PendingIntent mPendingIntent = PendingIntent.getActivity(activity, 48568, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)activity.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        Toast.makeText(activity, R.string.restarting, Toast.LENGTH_LONG).show();
        activity.finish();
    }
}
