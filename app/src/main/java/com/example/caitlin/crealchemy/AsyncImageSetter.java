package com.example.caitlin.crealchemy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AsyncImageSetter extends AsyncTask<Void, Void, Bitmap> {

    private ImageView img;
    private int image_resId;
    private Bitmap bmp;
    private Context context;
    private boolean cancel = false;
    private int sampleSize;
    private TextView name;


    public AsyncImageSetter(Context context, ImageView img, int image_ResId,
                            Bitmap bmp, TextView name) {

        this.img = img;
        this.image_resId = image_ResId;
        this.bmp = bmp;
        this.context = context;
        this.name = name;

    }

    public void cancel() {
        cancel = true;
    }

    @Override
    protected void onPreExecute() {
    /*--- we hide the Views from the user until the content is ready. This will prevent
     * the user from seeing an image being "transformed" into the next one (as a result of
     * View recycling) on slow devices.
     */
        img.setVisibility(View.GONE);
        name.setVisibility(View.GONE);
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(Void... params) {

        if (!cancel) {
            try {
                return decodeAndScale(bmp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {

        img.setVisibility(View.VISIBLE);
        try {
            img.setImageBitmap(result);
        } catch (Exception e) {
            img.setImageResource(R.drawable.placeholder);
        }
        name.setVisibility(View.VISIBLE);
        super.onPostExecute(result);
    }

    private Bitmap decodeAndScale(Bitmap bmp) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = setSampleSize();

        return BitmapFactory.decodeResource(context.getResources(), image_resId,
                options);

    }

    public static int getScreenWidth(Activity a) {

        Display display = a.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = a.getResources().getDisplayMetrics().density;
        float dpWidth = outMetrics.widthPixels / density;

        return (int) dpWidth;
    }
    private int setSampleSize() {
        if (getScreenWidth((Activity) context) >= 320) {
            sampleSize = 2;
        }
        return sampleSize;
    }
}