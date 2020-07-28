package myview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.toolstest.R;

import androidx.annotation.Nullable;

/**
 * 作者：jiangxiaolin on 2020/6/28
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class mydefview extends View {
    private Paint mPaint;
    private Bitmap mBitmap;

    /**以代码的方式动态添加VIEw
     * @param context
     */
    public mydefview(Context context) {
        super(context);
    }

    /**
     * 以XML布局文件方式使用，自动调用
     * @param context
     * @param attrs
     */
    public mydefview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initpaint();
    }

    public mydefview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public mydefview(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public void initpaint() {
        mPaint=new Paint();
        mPaint.setColor(Color.RED);   //设置颜色
        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        mPaint.setStrokeWidth(3);
        mBitmap= BitmapFactory.decodeResource(getResources(), R.drawable.xgd);
        mBitmap=Bitmap.createScaledBitmap(mBitmap,100,100,true);

        }


    /**当你的view需要显示的时候自动调用
     * canvas 画布
     *Paint   画笔
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
         Log.v("TAG","onDraw");


        canvas.drawCircle(50,50,50,mPaint);

//        mPaint.setStyle(Paint.Style.FILL); //设置实心
        canvas.drawRect(10,120,110,170,mPaint);

        canvas.drawBitmap(mBitmap,10,200,null);

//        canvas.drawARGB(255,255,218,185);

        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
         Log.v("TAG","onLayout");

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.v("TAG","onMeasure");


    }








}
