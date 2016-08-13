package com.example.luo_pc.view.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.luo_pc.view.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by luo-pc on 2016/8/12.
 */
public class CustomTitleView extends View {
    private final static String TAG = "CustomTitleView";
    /**
     * 文本
     */
    private String mTitleText;
    /**
     * 文本的颜色
     */
    private int mTitleTextColor;
    /**
     * 文本的大小
     */
    private int mTitleTextSize;

    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;

    /**
     * 新增一个背景色
     */
    private int mTitleBackColor;

    public CustomTitleView(Context context) {
        this(context,null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 获取我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyleAttr, 0);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomTitleView_titleText:
                    mTitleText = a.getString(attr);
                    break;

                case R.styleable.CustomTitleView_titleTextColor:
                    //默认颜色为黑色
                    mTitleTextColor = a.getColor(attr, Color.BLACK);
                    break;

                case R.styleable.CustomTitleView_titleTextSize:
                    //默认设置为16sp,TypeValue也可以把sp转化为px
                    mTitleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()
                    ));
                    break;

                case R.styleable.CustomTitleView_titleBackGroudColor:
                    mTitleBackColor = a.getColor(attr,Color.YELLOW);
            }
        }

        a.recycle();

        /**
         * 获取绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取随机字符串
                mTitleText = randomText();
                postInvalidate();
            }
        });
    }


    private String randomText() {
        Random random = new Random();
        Set<Integer> set = new HashSet<>();
        while (set.size() < 4) {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }

        StringBuilder sb = new StringBuilder();
        for (Integer i : set) {
            sb.append("" + i);
        }
        text = sb.toString();

        return sb.toString();
    }

    private String text;

    public String getText() {
        return text;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = 0;
        Log.i(TAG,"onMeasure");
        //设置宽度
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        switch (specMode) {
            case MeasureSpec.EXACTLY:   //精准模式，包含指定大小和match_parent
                width = getPaddingLeft() + getPaddingRight() + specSize;
                break;
            case MeasureSpec.AT_MOST:   //一般为wrap_content
                width = getPaddingLeft() + getPaddingRight() + mBound.width();
                break;
        }

        //设置高度
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                height = getPaddingTop() + getPaddingBottom() + specSize;
                break;
            case MeasureSpec.AT_MOST:
                height = getPaddingTop() + getPaddingBottom() + mBound.height();
                break;
        }
        setMeasuredDimension(width, height);
    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG,"onDraw");
        mPaint.setColor(mTitleBackColor);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText, getWidth() / 2f - mBound.width() / 2f, getHeight() / 2f + mBound.height() / 2f, mPaint);
    }
}
