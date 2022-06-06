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
    private Paint green = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint transperent = new Paint(Paint.ANTI_ALIAS_FLAG);
    Rect r = new Rect(10 , 100 , 40 , 200);

    private Drawable tableDrawable;
    private Drawable milkDrawable;
    private Drawable greenBottle;
    private Drawable redBottle;
    private Drawable canDrawable;
    private Drawable fridgeDrawable;
    private DrawableProxy milkDrawableProxy;
    private DrawableProxy redBottleDrawableProxy;
    private DrawableProxy greenBottleDrawableProxy;
    private DrawableProxy canDrawableProxy;
    int scale = (int)getResources().getDisplayMetrics().density;
    int canvasWidth;
    int canvasHeight;
    private Rect milkBorderRect;
    private Rect redBottleBorderRect;
    private Rect greenBottleBorderRect;
    private Rect canBorderRect;
    private Rect redNapkin;
    private Rect yellowNapkin;
    private Rect blueNapkin;
    private Rect greenNapkin;
    private Rect positionBorderRect;
    private boolean fridgeIsOpen= false;


    public MilkDragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        yellow.setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.STROKE);
        yellow.setStrokeWidth(5f);
        green.setColor(Color.GREEN);
        green.setStyle(Paint.Style.FILL);
        transperent.setColor(Color.TRANSPARENT);
        milkDrawable = getResources().getDrawable(R.drawable.ic_blue_milk);
        greenBottle = getResources().getDrawable(R.drawable.ic_chicken);
        redBottle = getResources().getDrawable(R.drawable.ic_grapes);
        canDrawable = getResources().getDrawable(R.drawable.ic_can);
        fridgeDrawable = getResources().getDrawable(R.drawable.ic_closefridge);
        tableDrawable = getResources().getDrawable(R.drawable.ic_table2);
        milkDrawableProxy = new DrawableProxy(milkDrawable , 70*scale , 350*scale , 40*scale , 70*scale);
        milkBorderRect = new Rect(65 * scale , 345*scale , 111 * scale , 416 * scale);

        redBottleDrawableProxy = new DrawableProxy(redBottle , 135*scale , 250*scale ,40*scale , 70*scale );
        redBottleBorderRect = new Rect(130*scale , 245*scale , 176*scale , 321*scale);

        greenBottleDrawableProxy = new DrawableProxy(greenBottle , 90*scale , 250*scale ,40*scale , 70*scale );
        greenBottleBorderRect = new Rect(85*scale , 245*scale , 131*scale , 321*scale);

        canDrawableProxy = new DrawableProxy(canDrawable , 135*scale , 345*scale , 40 * scale , 50  *scale);
        canBorderRect = new Rect(130*scale  , 340*scale , 171*scale , 395*scale);



    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        canvasWidth = this.getWidth();
        canvasHeight = this.getHeight();
        initializeCanvasObjects();
        canvas.drawRect(milkBorderRect, transperent);
        canvas.drawRect(redBottleBorderRect, transperent);
        canvas.drawRect(greenBottleBorderRect, transperent);
        canvas.drawRect(canBorderRect, transperent);
        canvas.drawRect(positionBorderRect , yellow);
        fridgeDrawable.draw(canvas);
        if(!fridgeIsOpen)
        {
            milkDrawableProxy.getDrawableItem().draw(canvas);
            redBottleDrawableProxy.getDrawableItem().draw(canvas);
            greenBottleDrawableProxy.getDrawableItem().draw(canvas);
            canDrawableProxy.getDrawableItem().draw(canvas);
            fridgeDrawable.draw(canvas);
        }
        if(fridgeIsOpen)
        {
//            fridgeDrawable = getResources().getDrawable(R.drawable.ic_fridge_open);
            fridgeDrawable.draw(canvas);
            milkDrawableProxy.getDrawableItem().draw(canvas);
            redBottleDrawableProxy.getDrawableItem().draw(canvas);
            greenBottleDrawableProxy.getDrawableItem().draw(canvas);
            canDrawableProxy.getDrawableItem().draw(canvas);
        }
        tableDrawable.draw(canvas);
        canvas.drawRect(greenNapkin , green);
    }

    public Drawable getFridgeDrawble()
    {
        return fridgeDrawable;
    }

    public void setFridgeState(boolean state)
    {
        fridgeIsOpen = !fridgeIsOpen;
        if(fridgeIsOpen)
        {
            fridgeDrawable = getResources().getDrawable(R.drawable.ic_fridge_open);
        }
        else
        {
            fridgeDrawable = getResources().getDrawable(R.drawable.ic_closefridge);
        }
        invalidate();
    }

    public boolean isClickOnFridge(int x, int y)
    {
        boolean isClick = false;
        if(fridgeDrawable.getBounds().left <= x && fridgeDrawable.getBounds().right>= x) {

            if (fridgeDrawable.getBounds().bottom >= y && fridgeDrawable.getBounds().top <= y) {

                isClick = true;
            }
        }
        return isClick;
    }



    public Rect getMilkBorderRect()
    {
        return milkBorderRect;
    }
    public Rect getGreenBottleBorderRect()
    {
        return greenBottleBorderRect;
    }
    public Rect getRedBottleBorderRect()
    {
        return redBottleBorderRect;
    }
    public Rect getCanBorderRect()
    {
        return canBorderRect;
    }
    public void moveMilkBorderRect(int x , int y)
    {
        milkBorderRect.set(x - (25*scale) , y - (40 * scale) , x + (25 * scale) , y+(40*scale));
        milkDrawableProxy.moveItem(milkBorderRect.left + (10*scale) , milkBorderRect.top + (10*scale));
        invalidate();
    }

    public void moveGreenBottleBorderRect(int x , int y)
    {
        greenBottleBorderRect.set(x - (25*scale) , y - (40 * scale) , x + (25 * scale) , y+(40*scale));
        greenBottleDrawableProxy.moveItem(greenBottleBorderRect.left + (10*scale) , greenBottleBorderRect.top + (10*scale));
        invalidate();
    }

    public void moveRedBottleBorderRect(int x , int y)
    {
        redBottleBorderRect.set(x - (25*scale) , y - (40 * scale) , x + (25 * scale) , y+(40*scale));
        redBottleDrawableProxy.moveItem(redBottleBorderRect.left + (10*scale) , redBottleBorderRect.top + (10*scale));
        invalidate();
    }

    public void moveCanBorederRect(int x , int y)
    {
        canBorderRect.set(x - (25*scale) , y - (40 * scale) , x + (25 * scale) , y+(40*scale));
        canDrawableProxy.moveItem(canBorderRect.left + (10*scale) , canBorderRect.top + (10*scale));
        invalidate();
    }

    public boolean pointInsideMilkRect(int x , int y)
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
        positionBorderRect = new Rect(tableDrawable.getBounds().left , tableDrawable.getBounds().top + (40*scale),
                tableDrawable.getBounds().right , tableDrawable.getBounds().top+(150*scale));
        int top = tableDrawable.getBounds().top + (30*scale);
        int bottom = top + (10*scale);
        greenNapkin = new Rect(tableDrawable.getBounds().left ,top  ,
                tableDrawable.getBounds().left + (50*scale) , bottom);
    }
    public DrawableProxy getMilkDrawable()
    {
      return milkDrawableProxy;
    }
    public DrawableProxy getRedBottleDrawable()
    {
        return redBottleDrawableProxy;
    }
    public DrawableProxy getGreenBottleDrawable()
    {
        return greenBottleDrawableProxy;
    }

    public boolean milkIsInPosition()
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

    public boolean redBottleIsInPosition()
    {
        boolean inPosition = false;
        if(redBottle.getBounds().left >= positionBorderRect.left && redBottle.getBounds().right <= positionBorderRect.right)
        {
            if(redBottle.getBounds().top >= positionBorderRect.top && redBottle.getBounds().bottom <= positionBorderRect.bottom)
            {
                inPosition = true;
            }
        }
        return  inPosition;
    }

    public boolean greenBottleIsInPosition()
    {
        boolean inPosition = false;
        if(greenBottle.getBounds().left >= positionBorderRect.left && greenBottle.getBounds().right <= positionBorderRect.right)
        {
            if(greenBottle.getBounds().top >= positionBorderRect.top && greenBottle.getBounds().bottom <= positionBorderRect.bottom)
            {
                inPosition = true;
            }
        }
        return  inPosition;
    }

    public boolean canIsInPosition()
    {
        boolean inPosition = false;
        if(canDrawable.getBounds().left >= positionBorderRect.left && canDrawable.getBounds().right <= positionBorderRect.right)
        {
            if(canDrawable.getBounds().top >= positionBorderRect.top && canDrawable.getBounds().bottom <= positionBorderRect.bottom)
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
        private Rect borderRect;
        public DrawableProxy(Drawable drawable , int left, int top, int width, int height)
        {
            drawableItem = drawable;
            leftSide = left;
            topSide = top;
            itemWidth = width;
            itemHeight = height;
            drawableItem.setBounds(left , top , left + width , top + height);
        }

        public void setBorderRect(Rect r)
        {
            borderRect = r;
        }
        public Rect getBorderRect()
        {
            return borderRect;
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
