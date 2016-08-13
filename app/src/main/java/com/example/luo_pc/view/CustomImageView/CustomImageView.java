package com.example.luo_pc.view.CustomImageView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.luo_pc.view.R;

/**
 * Created by luo-pc on 2016/8/13.
 */
public class CustomImageView extends View {
    private final static String TAG = "CustomImageView";

    /**
     * 图片的文本介绍
     */
    private String mDesc;
    /**
     * 文本的颜色
     */
    private int mDescColor;
    /**
     * 文本大小
     */
    private int mDescSize;
    /**
     * 图片
     */
    private Bitmap mImage;
    /**
     * 图片缩放模式
     */
    private int mImageScale;
    private static final int IMAGE_SCALE_FITXY = 0;
    private static final int IMAGE_SCALE_CENTER = 1;

    /**
     * 对文本的约束
     */
    private Rect mTextBound;
    /**
     * 控件整体约束
     */
    private Rect rect;
    /**
     * 宽
     */
    private int mWidth;
    /**
     * 高
     */
    private int mHeight;
    /**
     * 画板
     */
    private Paint mPaint;


    String a;

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义样式属性
        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyleAttr, 0);

        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomImageView_titleText:
                    mDesc = a.getString(attr);
                    break;

                case R.styleable.CustomImageView_titleTextColor:
                    mDescColor = a.getColor(attr, Color.BLACK);
                    break;

                case R.styleable.CustomImageView_titleTextSize:
                    mDescSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;

                case R.styleable.CustomImageView_image:
                    mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;

                case R.styleable.CustomImageView_imageScaleType:
                    mImageScale = a.getInt(attr, IMAGE_SCALE_CENTER);
            }
        }

        a.recycle();
        rect = new Rect();
        mPaint = new Paint();
        mTextBound = new Rect();
        mPaint.setTextSize(mDescSize);
        //绘制字体需要的范围
        mPaint.getTextBounds(mDesc, 0, mDesc.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取宽度和specMode
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        switch (specMode) {
            case MeasureSpec.EXACTLY:       //包括指定大小和match_parent
                mWidth = specSize;
                break;

            case MeasureSpec.AT_MOST:       //wrap_content
                //图片的宽
                int imgWidth = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
                //字体的宽
                int desWidth = getPaddingLeft() + getPaddingRight() + mTextBound.width();

                int desire = Math.max(imgWidth, desWidth);
                mWidth = Math.min(desire, specSize);
        }

        //获取高度和specMode
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (specMode) {
            case MeasureSpec.EXACTLY:
                mHeight = specSize;
                break;

            case MeasureSpec.AT_MOST:
                int tempHeight = getPaddingTop() + getPaddingBottom() + mImage.getHeight() + mTextBound.height();
                mHeight = Math.min(tempHeight, specSize);
                break;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //边框

        //当前设置的宽度小于字体需要的宽度

    }

}
