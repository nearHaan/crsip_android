package com.duk.crsipandroid.utils;


import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

public class CameraSurfaceView extends SurfaceView {

    private static final String TAG = "CameraSurfaceView";
    private int previewWidth = -1;
    private int previewHeight = -1;

    public CameraSurfaceView(Context context) {
        super(context);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setPreviewSize(int width, int height) {
        if (width > 0 && height > 0) {
            previewWidth = width;
            previewHeight = height;
            requestLayout();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);

        if (previewWidth > 0 && previewHeight > 0) {
            // Calculate aspect ratios
            float previewAspectRatio = (float) previewWidth / previewHeight;
            float viewAspectRatio = (float) width / height;

            int finalWidth, finalHeight;

            if (previewAspectRatio > viewAspectRatio) {
                // Preview is wider than view, fit to width
                finalWidth = width;
                finalHeight = (int) (width / previewAspectRatio);
            } else {
                // Preview is taller than view, fit to height
                finalHeight = height;
                finalWidth = (int) (height * previewAspectRatio);
            }

            Log.d(TAG, "Preview size: " + previewWidth + "x" + previewHeight);
            Log.d(TAG, "View size: " + width + "x" + height);
            Log.d(TAG, "Final size: " + finalWidth + "x" + finalHeight);

            setMeasuredDimension(finalWidth, finalHeight);
        } else {
            // No preview size set yet, use default
            setMeasuredDimension(width, height);
        }
    }

    public void setCameraParameters(Camera camera) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size previewSize = parameters.getPreviewSize();
            if (previewSize != null) {
                setPreviewSize(previewSize.width, previewSize.height);
            }
        }
    }
}