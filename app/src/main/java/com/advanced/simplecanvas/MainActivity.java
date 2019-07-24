package com.advanced.simplecanvas;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Canvas mCanvas;
    private final Paint mPaint = new Paint();
    private final Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);
    private ImageView mImageView;

    private final Rect mRect = new Rect();
    private final Rect mBounds = new Rect();

    private static final int OFFSET = 120;
    private int mOffset = OFFSET;

    private static final int MULTIPLIER = 100;

    private int mColorBackground;
    private int mColorRectangle;
    private int mColorAccent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mColorBackground = ResourcesCompat.getColor(getResources(), R.color.colorBackground, null);
        mColorRectangle = ResourcesCompat.getColor(getResources(), R.color.colorRectangle, null);
        mColorAccent = ResourcesCompat.getColor(getResources(), R.color.colorAccent, null);

        mPaint.setColor(mColorBackground);

        mPaintText.setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
        mPaintText.setTextSize(70);

        mImageView = findViewById(R.id.my_image_view);
    }

    public void drawSomething(View view) {
        int width = view.getWidth();
        int height = view.getHeight();
        int halfWidth = width / 2;
        int halfHeight = height / 2;

        // SOS: an image-view can take an image resource, an image uri, an image drawable etc. We want
        // to draw to it, therefore we pass it a bitmap w setImageBitmap. Then we create a canvas that
        // points to that bitmap, so that we can draw on it...
        if (mOffset == OFFSET) {
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            mImageView.setImageBitmap(bitmap);
            mCanvas = new Canvas(bitmap);
            mCanvas.drawColor(mColorBackground);
            mCanvas.drawText(getString(R.string.keep_tapping), 100, 100, mPaintText);
            mOffset += OFFSET;
        } else {
            if (mOffset < halfWidth && mOffset < halfHeight) {
                // Change the color by subtracting an integer.
                mPaint.setColor(mColorRectangle - MULTIPLIER * mOffset);
                mRect.set(mOffset, mOffset, width - mOffset, height - mOffset);
                mCanvas.drawRect(mRect, mPaint);
                mOffset += OFFSET;
            } else {
                mPaint.setColor(mColorAccent);
                mCanvas.drawCircle(halfWidth, halfHeight, halfWidth / 3f, mPaint);
                String text = getString(R.string.done);
                // SOS: mBounds is a rectangle w (0,0) as its top-left corner
                mPaintText.getTextBounds(text, 0, text.length(), mBounds);
                int x = halfWidth - mBounds.centerX();
                int y = halfHeight - mBounds.centerY();
                mCanvas.drawText(text, x, y, mPaintText);
            }
        }

        view.invalidate();
    }
}
