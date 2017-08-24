package com.example.job.nguo;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

/**
 * Created by JOB on 8/24/2017.
 */

public class ImageRequester {

    private static volatile ImageRequester instance;
    private RequestQueue mRequestQueue;
    private static ImageLoader loader;
    private static Context context;
    private int maxSize;


    private ImageRequester(Context ctx, ImageLoader imageLoader){

        context = ctx;
        loader = imageLoader;

        mRequestQueue = getmRequestQueue();
        mRequestQueue.start();

        maxSize = calculateMaxBytes(ctx);
        loader = new ImageLoader(getmRequestQueue(), new ImageLoader.ImageCache() {

            final LruCache<String,Bitmap> lruCache = new LruCache<String,Bitmap>(maxSize){
                @Override
                protected int sizeOf(String url, Bitmap bitmap) {
                    return bitmap.getByteCount();
                }
            };
            @Override
            public synchronized Bitmap getBitmap(String url) {
                return lruCache.get(url);
            }

            @Override
            public synchronized void putBitmap(String url, Bitmap bitmap) {
                 lruCache.put(url,bitmap);
            }
        });
    }

    public static synchronized ImageRequester getInstance() {
        if (instance == null) {
            synchronized (ImageRequester.class){
                instance = new ImageRequester(context,instance.getLoader());
            }
        }
        return instance;
    }

    public static void setImagFromUrlr(NetworkImageView view, String url){
        view.setImageUrl(url, loader);
    }

    public static void setInstance(ImageRequester instance) {
        ImageRequester.instance = instance;
    }

    public RequestQueue getmRequestQueue() {
        if (mRequestQueue == null) {
            new Volley();
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }

    public ImageLoader getLoader() {
        return loader;
    }

    public Context getContext() {
        return context;
    }

    private int calculateMaxBytes(Context ctx){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        final int screenBytes = displayMetrics.widthPixels * displayMetrics.heightPixels * 4;
        return screenBytes * 3;
    }
    //Capture all other additional requests that arise
    public <T> void addToRequestQueue(Request<T> req) {
        getmRequestQueue().add(req);
    }
}
