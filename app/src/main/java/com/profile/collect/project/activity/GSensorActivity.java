package com.profile.collect.project.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;

import com.profile.collect.project.R;

/**
 * Created by gouzhouping on 20-1-7.
 */

public class GSensorActivity extends Activity implements SensorEventListener {

    private SensorManager sm;
    private GSensitiveView gsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gsView = new GSensitiveView(this);
        setContentView(gsView);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }


    private static class GSensitiveView extends ImageView {

        private Bitmap image;
        private double rotation;
        private Paint paint;

        public GSensitiveView(Context context) {
            super(context);
            BitmapDrawable drawble = (BitmapDrawable) context.getResources().getDrawable(R.mipmap.budaow);
            image = drawble.getBitmap();
            paint = new Paint();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // super.onDraw(canvas);

            double w = image.getWidth();
            double h = image.getHeight();

            Rect rect = new Rect();
            getDrawingRect(rect);

            int degrees = (int) (180 * rotation / Math.PI);
            canvas.rotate(degrees, rect.width() / 2, rect.height() / 2);
            canvas.drawBitmap(image, //
                    (float) ((rect.width() - w) / 2),//
                    (float) ((rect.height() - h) / 2),//
                    paint);
        }

        public void setRotation(double rad) {
            rotation = rad;
            invalidate();
        }

    }

    public void onSensorChanged(SensorEvent event) {
        if (Sensor.TYPE_ACCELEROMETER != event.sensor.getType()) {
            return;
        }

        float[] values = event.values;
        float ax = values[0];
        float ay = values[1];

        double g = Math.sqrt(ax * ax + ay * ay);
        double cos = ay / g;
        if (cos > 1) {
            cos = 1;
        } else if (cos < -1) {
            cos = -1;
        }
        double rad = Math.acos(cos);
        if (ax < 0) {
            rad = 2 * Math.PI - rad;
        }

        int uiRot = getWindowManager().getDefaultDisplay().getRotation();
        double uiRad = Math.PI / 2 * uiRot;
        rad -= uiRad;

        gsView.setRotation(rad);
    }


    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // do nothing
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sm != null) {
            sm.unregisterListener(this);
            sm = null;
        }
    }
}
