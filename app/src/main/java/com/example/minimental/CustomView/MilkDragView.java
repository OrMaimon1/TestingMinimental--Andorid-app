package com.example.minimental.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.minimental.R;

public class MilkDragView extends View {

    private Paint yellow = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint transperent = new Paint(Paint.ANTI_ALIAS_FLAG);
    Rect r = new Rect(10 , 100 , 40 , 200);

    private Drawable tableDrawable;
    private Drawable milkDrawable;
    private Drawable fridgeDrawable;
    private DrawableProxy milkDrawableProxy;
    int scale = (int)getResources().getDisplayMetrics().density;
    int canvasWidth;
    int canvasHeight;
    private Rect milkBorderRect;
    private Rect positionBorderRect;
    private boolean fridgeIsOpen= false;


    public MilkDragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        yellow.setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.STROKE);
        yellow.setStrokeWidth(5f);
        transperent.setColor(Color.TRANSPARENT);
        milkDrawable = getResources().getDrawable(R.drawable.ic_blue_milk);
        fridgeDrawable = getResources().getDrawable(R.drawable.ic_closefridge);
        tableDrawable = getResources().getDrawable(R.drawable.ic_dresser);
        milkDrawableProxy = new DrawableProxy(milkDrawable , 60*scale , 350*scale , 40*scale , 70*scale);
        milkBorderRect = new Rect(50 * scale , 345*scale , 101 * scale , 416 * scale);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        canvasWidth = this.getWidth();
        canvasHeight = this.getHeight();
        initializeCanvasObjects();
        canvas.drawRect(milkBorderRect, transperent);
        canvas.drawRect(positionBorderRect , yellow);
        fridgeDrawable.draw(canvas);
        if(fridgeIsOpen)
        {
//            fridgeDrawable = getResources().getDrawable(R.drawable.ic_fridge_open);
            fridgeDrawable.draw(canvas);
            milkDrawableProxy.getDrawableItem().draw(canvas);
        }
        tableDrawable.draw(canvas);
    }

    public Drawable getFridgeDrawble()
    {
        return fridgeDrawable;
    }

    public void setFridgeState(boolean state)
    {
        fridgeIsOpen = state;
        invalidate();
    }

    public boolean isClickOnFridge(int x, int y)
    {
        boolean isClick = false;
        if(fridgeDrawable.getBounds().left <= x && fridgeDrawable.getBounds().right>= x) {

            if (fridgeDrawable.getBounds().bottom >= y && fridgeDrawable.getBounds().top <= y) {
                fridgeDrawable = getResources().getDrawable(R.drawable.ic_fridge_open);
                isClick = true;
            }
        }
        return isClick;
    }



    public Rect getMilkBorderRect()
    {
        return milkBorderRect;
    }
    public void moveBorderRect(int x , int y)
    {
        milkBorderRect.set(x - (25*scale) , y - (40 * scale) , x + (25 * scale) , y+(40*scale));
        milkDrawableProxy.moveItem(milkBorderRect.left + (10*scale) , milkBorderRect.top + (10*scale));
        invalidate();
    }
    public boolean pointInsideRect(int x , int y)
    {
        boolean inside = false;
        if(milkBorderRect.left <= x && milkBorderRect.right >= x)
        {
            if(milkBorderRect.top <= y && milkBorderRect.bottom >= y)
            {
                inside = true;
            }
        }
        return inside;
    }
    private void initializeCanvasObjects()
    {
        fridgeDrawable.setBounds(0 , canvasHeight/3 , canvasWidth/2 , canvasHeight);
        tableDrawable.setBounds(canvasWidth*2/3 , canvasHeight/2 , canvasWidth , canvasHeight);
        positionBorderRect = new Rect(tableDrawable.getBounds().left , tableDrawable.getBounds().top - (29*scale),
                tableDrawable.getBounds().right , tableDrawable.getBounds().top+(60*scale));
    }
    public DrawableProxy getMilkDrawable()
    {
      return milkDrawableProxy;
    }

    public boolean isInPosition()
    {
        boolean inPosition = false;
        if(milkDrawable.getBounds().left >= positionBorderRect.left && milkDrawable.getBounds().right <= positionBorderRect.right)
        {
            if(milkDrawable.getBounds().top >= positionBorderRect.top && milkDrawable.getBounds().bottom <= positionBorderRect.bottom)
            {
                inPosition = true;
            }
        }
        return  inPosition;
    }

    public void changeBorderColor()
    {
        yellow.setColor(Color.BLUE);
        invalidate();
    }
    /*public void moveDrawable(DrawableProxy drawableProxy , int x , int y)
    {
        drawableProxy.leftSide = x;
        drawableProxy.topSide = y;
        drawableProxy.getDrawableItem().setBounds(drawableProxy.leftSide * scale,drawableProxy.topSide * scale
                , (drawableProxy.leftSide+drawableProxy.itemWidth)  *scale ,
                (drawableProxy.topSide+drawableProxy.itemHeight) * scale);
        invalidate();
    }*/


    public class DrawableProxy
    {
        private Drawable drawableItem;
        final int itemWidth;
        final int itemHeight;
        private int leftSide;
        private int topSide;
        public DrawableProxy(Drawable drawable , int left, int top, int width, int height)
        {
            drawableItem = drawable;
            leftSide = left;
            topSide = top;
            itemWidth = width;
            itemHeight = height;
            drawableItem.setBounds(left , top , left + width , top + height);
        }

        public Drawable getDrawableItem()
        {
            return drawableItem;
        }
        public void moveItem(int x , int y)
        {
            leftSide = x;
            topSide = y;
            getDrawableItem().setBounds(leftSide,topSide, leftSide+ itemWidth , topSide+ itemHeight);
            invalidate();
        }
        public boolean containsPoint(float x, float y)
        {
            boolean contains = false;
            if(leftSide < x && leftSide+itemWidth > x)
            {
                if(topSide < y && topSide + itemHeight > y)
                {
                    contains = true;
                }
            }
            return contains;
        }
    }
}
