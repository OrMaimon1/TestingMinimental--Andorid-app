package com.example.minimental.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.minimental.R;

public class MilkDragView extends View {

    private Paint yellow = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint green = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint red = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint blue = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint yellowFill = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint transperent = new Paint(Paint.ANTI_ALIAS_FLAG);
    Rect r = new Rect(10 , 100 , 40 , 200);

    private Drawable tableDrawable;
    private Drawable milkDrawable;
    private Drawable chickenDrawable; //GreenBottle
    private Drawable grapeDrawable;   // RedBottle
    private Drawable canDrawable;
    private Drawable fridgeDrawable;
    private DrawableProxy milkDrawableProxy;
    private DrawableProxy grapeDrawableProxy;
    private DrawableProxy chickenDrawableProxy;
    private DrawableProxy canDrawableProxy;
    int scale = (int)getResources().getDisplayMetrics().density;
    int canvasWidth;
    int canvasHeight;
    private Rect milkBorderRect;
    private Rect grapeBorderRect;
    private Rect chickenBorderRect;
    private Rect canBorderRect;
    private Rect leftBorder;
    private Rect rightBorder;
    private Rect topBorder;
    private Rect bottomBorder;
    private Rect redNapkin;
    private Rect blueNapkin;
    private Rect greenNapkin;
    private Rect yellowNapkin;
    private Rect positionBorderRect;
    private boolean fridgeIsOpen= false;


    public MilkDragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        yellow.setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.STROKE);
        yellow.setStrokeWidth(5f);
        green.setColor(Color.GREEN);
        green.setStyle(Paint.Style.FILL);
        blue.setColor(Color.BLUE);
        blue.setStyle(Paint.Style.FILL);
        red.setColor(Color.RED);
        red.setStyle(Paint.Style.FILL);
        yellowFill.setColor(Color.YELLOW);
        yellowFill.setStyle(Paint.Style.FILL);
        transperent.setColor(Color.TRANSPARENT);
        milkDrawable = getResources().getDrawable(R.drawable.ic_milk_svgrepo_com);
        chickenDrawable = getResources().getDrawable(R.drawable.ic_chicken);
        grapeDrawable = getResources().getDrawable(R.drawable.ic_grapes);
        canDrawable = getResources().getDrawable(R.drawable.ic_can);
        fridgeDrawable = getResources().getDrawable(R.drawable.ic_closefridge);
        tableDrawable = getResources().getDrawable(R.drawable.ic_table_3);
        milkDrawableProxy = new DrawableProxy(milkDrawable , 70*scale , 350*scale , 40*scale , 70*scale);
        milkBorderRect = new Rect(65 * scale , 345*scale , 111 * scale , 416 * scale);
        milkDrawableProxy.setInitialBorderRectPosition(milkBorderRect);

        grapeDrawableProxy = new DrawableProxy(grapeDrawable, 135*scale , 250*scale ,40*scale , 70*scale );
        grapeBorderRect = new Rect(130*scale , 245*scale , 176*scale , 321*scale);
        grapeDrawableProxy.setInitialBorderRectPosition(grapeBorderRect);

        chickenDrawableProxy = new DrawableProxy(chickenDrawable, 90*scale , 250*scale ,40*scale , 70*scale );
        chickenBorderRect = new Rect(85*scale , 245*scale , 131*scale , 321*scale);
        chickenDrawableProxy.setInitialBorderRectPosition(chickenBorderRect);

        canDrawableProxy = new DrawableProxy(canDrawable , 135*scale , 345*scale , 40 * scale , 50  *scale);
        canBorderRect = new Rect(130*scale  , 340*scale , 171*scale , 395*scale);
        canDrawableProxy.setInitialBorderRectPosition(canBorderRect);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        canvasWidth = this.getWidth();
        canvasHeight = this.getHeight();
        initializeCanvasObjects();
        canvas.drawRect(milkBorderRect, transperent);
        canvas.drawRect(grapeBorderRect, transperent);
        canvas.drawRect(chickenBorderRect, transperent);
        canvas.drawRect(canBorderRect, transperent);
        canvas.drawRect(positionBorderRect , transperent);
        //fridgeDrawable.draw(canvas);
        tableDrawable.draw(canvas);
        if(!fridgeIsOpen)
        {
            milkDrawableProxy.getDrawableItem().draw(canvas);
            grapeDrawableProxy.getDrawableItem().draw(canvas);
            chickenDrawableProxy.getDrawableItem().draw(canvas);
            canDrawableProxy.getDrawableItem().draw(canvas);
            fridgeDrawable.draw(canvas);
        }
        if(fridgeIsOpen)
        {
//            fridgeDrawable = getResources().getDrawable(R.drawable.ic_fridge_open);
            fridgeDrawable.draw(canvas);
            milkDrawableProxy.getDrawableItem().draw(canvas);
            grapeDrawableProxy.getDrawableItem().draw(canvas);
            chickenDrawableProxy.getDrawableItem().draw(canvas);
            canDrawableProxy.getDrawableItem().draw(canvas);
        }

        canvas.drawRect(greenNapkin , green);
        canvas.drawRect(redNapkin , red);
        canvas.drawRect(blueNapkin , blue);
        canvas.drawRect(yellowNapkin , yellowFill);
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
            returnToFridgeIfNotOnTable();
        }
        invalidate();
    }

    public boolean isFrdigeOpen()
    {
        return fridgeIsOpen;
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
    public Rect getChickenBorderRect()
    {
        return chickenBorderRect;
    }
    public Rect getGrapeBorderRect()
    {
        return grapeBorderRect;
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

    public void moveChickenBorderRect(int x , int y)
    {
        chickenBorderRect.set(x - (25*scale) , y - (40 * scale) , x + (25 * scale) , y+(40*scale));
        chickenDrawableProxy.moveItem(chickenBorderRect.left + (10*scale) , chickenBorderRect.top + (10*scale));
        invalidate();
    }

    public void moveGrapeBorderRect(int x , int y)
    {
        grapeBorderRect.set(x - (25*scale) , y - (40 * scale) , x + (25 * scale) , y+(40*scale));
        grapeDrawableProxy.moveItem(grapeBorderRect.left + (10*scale) , grapeBorderRect.top + (10*scale));
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
        bottomBorder = new Rect(0 , canvasHeight-(10*scale) , canvasWidth , canvasHeight);
        topBorder = new Rect(0 , 0 , canvasWidth , 10*scale);
        leftBorder = new Rect(0 , 0 , 10*scale , canvasHeight);
        rightBorder = new Rect(canvasWidth-(10*scale) , 0 , canvasWidth , canvasHeight);
        fridgeDrawable.setBounds(0 , canvasHeight/3 , canvasWidth/2 , canvasHeight);
        tableDrawable.setBounds(canvasWidth*1/2 , canvasHeight/2 , canvasWidth , canvasHeight);
        positionBorderRect = new Rect(tableDrawable.getBounds().left , tableDrawable.getBounds().top + (20*scale),
                tableDrawable.getBounds().right , tableDrawable.getBounds().top+(130*scale));
        int top = tableDrawable.getBounds().top + (90*scale);
        int bottom = top + (10*scale);
        greenNapkin = new Rect(tableDrawable.getBounds().left + (20*scale) ,top  ,
                tableDrawable.getBounds().left + (70*scale) , bottom);
        redNapkin = new Rect(greenNapkin.right + (3*scale) , top ,
                 greenNapkin.right+ (53*scale) , bottom);
        yellowNapkin = new Rect(redNapkin.right + (3 * scale), top,
                redNapkin.right + (53 * scale), bottom);
        blueNapkin = new Rect(yellowNapkin.right + (3*scale) , top ,
                yellowNapkin.right + (53*scale) , bottom);

    }
    public DrawableProxy getMilkDrawable()
    {
      return milkDrawableProxy;
    }
    public DrawableProxy getRedBottleDrawable()
    {
        return grapeDrawableProxy;
    }
    public DrawableProxy getGreenBottleDrawable()
    {
        return chickenDrawableProxy;
    }

    public boolean milkIsInPosition()
    {
        boolean inPosition = false;
        if(milkDrawable.getBounds().left >= positionBorderRect.left && milkDrawable.getBounds().right <= positionBorderRect.right)
        {
            if(milkDrawable.getBounds().top >= positionBorderRect.top && milkDrawable.getBounds().bottom <= positionBorderRect.bottom)
            {
                if(milkDrawableProxy.getObjectsCenterPointX() >= redNapkin.left && milkDrawableProxy.getObjectsCenterPointX() <= redNapkin.right) {
                    inPosition = true;
                }
            }
        }
        return  inPosition;
    }

    public boolean grapeIsInPosition()
    {
        boolean inPosition = false;
        if(grapeDrawable.getBounds().left >= positionBorderRect.left && grapeDrawable.getBounds().right <= positionBorderRect.right)
        {
            if(grapeDrawable.getBounds().top >= positionBorderRect.top && grapeDrawable.getBounds().bottom <= positionBorderRect.bottom)
            {
                if(grapeDrawableProxy.getObjectsCenterPointX() >= blueNapkin.left && grapeDrawableProxy.getObjectsCenterPointX() <= blueNapkin.right) {
                    inPosition = true;
                }
            }
        }
        return  inPosition;
    }

    public boolean chickenIsInPosition()
    {
        boolean inPosition = false;
        if(chickenDrawable.getBounds().left >= positionBorderRect.left && chickenDrawable.getBounds().right <= positionBorderRect.right)
        {
            if(chickenDrawable.getBounds().top >= positionBorderRect.top && chickenDrawable.getBounds().bottom <= positionBorderRect.bottom)
            {
                if(chickenDrawableProxy.getObjectsCenterPointX() >= greenNapkin.left && chickenDrawableProxy.getObjectsCenterPointX()  <= greenNapkin.right) {
                    inPosition = true;
                }
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
                if(canDrawableProxy.getObjectsCenterPointX() >= yellowNapkin.left && canDrawableProxy.getObjectsCenterPointX() <= yellowNapkin.right) {
                    inPosition = true;
                }
            }
        }
        return  inPosition;
    }

    public void returnToFridgeIfNotOnTable()
    {
        Rect[] objectsBorderRects = new Rect[]{milkBorderRect , grapeBorderRect , chickenBorderRect , canBorderRect};
        DrawableProxy[] drawableProxys = new DrawableProxy[]{milkDrawableProxy , grapeDrawableProxy , chickenDrawableProxy , canDrawableProxy};
        for(int i = 0; i< objectsBorderRects.length; i++) {
            if(!itemOnTable(objectsBorderRects[i]))
            {
                Rect rectNewPosition = drawableProxys[i].getInitialBorderRectPosition();
                objectsBorderRects[i].set(rectNewPosition.left , rectNewPosition.top , rectNewPosition.right , rectNewPosition.bottom);
                int x = ((objectsBorderRects[i].right - objectsBorderRects[i].left) / 2);
                int y = ((objectsBorderRects[i].bottom - objectsBorderRects[i].top) / 2);
                drawableProxys[i].moveItem(objectsBorderRects[i].left  , objectsBorderRects[i].top  );
            }
        }
    }

    public boolean itemOnTable(Rect itemBorderRect)
    {
        boolean onTable = false;
        if (itemBorderRect.left >= positionBorderRect.left - (50*scale) && itemBorderRect.right  <= positionBorderRect.right + (50*scale)) {
            if (itemBorderRect.top >= positionBorderRect.top - (50*scale) && itemBorderRect.bottom  <= positionBorderRect.bottom + (50*scale)) {
                onTable = true;
            }
        }
        return onTable;
    }

    public void returnToPlaceIfItemIsOutOfBounds()
    {
        Rect[] objectsBorderRects = new Rect[]{milkBorderRect , grapeBorderRect , chickenBorderRect , canBorderRect};
        DrawableProxy[] drawableProxys = new DrawableProxy[]{milkDrawableProxy , grapeDrawableProxy , chickenDrawableProxy , canDrawableProxy};

        for(int i = 0; i<objectsBorderRects.length ; i++)
        {
            if(objectsBorderRects[i].left < leftBorder.right || objectsBorderRects[i].right > rightBorder.left
            || objectsBorderRects[i].top < topBorder.bottom || objectsBorderRects[i].bottom > bottomBorder.top)
            {
                Rect rectNewPosition = drawableProxys[i].getInitialBorderRectPosition();
                objectsBorderRects[i].set(rectNewPosition.left , rectNewPosition.top , rectNewPosition.right , rectNewPosition.bottom);
                drawableProxys[i].moveItem(objectsBorderRects[i].left  , objectsBorderRects[i].top  );
            }
        }
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
        private Rect initialBorderRectPosition;
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

        public Rect getInitialBorderRectPosition()
        {
            return initialBorderRectPosition;
        }

        public void setInitialBorderRectPosition(Rect borderRect)
        {
            initialBorderRectPosition = new Rect(borderRect.left , borderRect.top , borderRect.right , borderRect.bottom);
        }

        public int getObjectsCenterPointX()
        {
            return getDrawableItem().getBounds().left + itemWidth/2;
        }
        public void moveItem(int x , int y)
        {
            leftSide = x;
            topSide = y;
            getDrawableItem().setBounds(leftSide,topSide, leftSide+ itemWidth , topSide+ itemHeight);
            invalidate();
        }
    }
}
