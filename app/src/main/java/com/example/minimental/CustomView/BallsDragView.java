package com.example.minimental.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class BallsDragView extends View {

    Paint yellowPaint;
    Paint blackPaint;
    Paint redPaint;
    Paint bluePaint;
    Paint transperentPaint;
    Circle yellowBall;
    Circle blackBall;
    RectF blueRect;
    RectF redRect;
    RectF leftBorder;
    RectF rightBorder;
    RectF topBorder;
    RectF bottomBorder;
    int currentBorderID;
    final int leftBorderID = 0;
    final int rightBorderID = 1;
    final int bottomBorderID = 2;
    final int topBorderID = 3;
    private Canvas thisCanvas;
    float scale = getResources().getDisplayMetrics().density;
    private float width = this.getWidth();
    private float height = this.getHeight();

    public BallsDragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //this.measure(0,0);
        width = this.getWidth();
        height = this.getHeight();
        yellowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setStyle(Paint.Style.FILL);
        blackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.FILL);
        redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        redPaint.setColor(Color.RED);
        redPaint.setStrokeWidth(10f*scale);
        redPaint.setStyle(Paint.Style.STROKE);
        bluePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStrokeWidth(10f*scale);
        bluePaint.setStyle(Paint.Style.STROKE);
        transperentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        transperentPaint.setColor(Color.TRANSPARENT);
        yellowBall = new Circle(200f*scale , 30f*scale , 20f*scale);
        blackBall = new Circle(200f*scale , 250 * scale , 20f*scale);
        blueRect = new RectF(30f*scale ,200f*scale , 130f*scale , 300f*scale);
        redRect = new RectF(270f*scale ,200f*scale , 370f*scale , 300f*scale);
    }

    private void initializeBorders()
    {
        bottomBorder = new RectF(0 , height-(10f*scale) , width , height);
        topBorder = new RectF(0 , 0 , width , 10f*scale);
        leftBorder = new RectF(0 , 0 , 10f*scale , height);
        rightBorder = new RectF(width-(10f*scale) , 0 , width , height);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = this.getWidth();
        height = this.getHeight();
        initializeBorders();
        canvas.drawRect(0 , height -(2f*scale) , width , height,blackPaint);

        canvas.drawCircle(yellowBall.getCenterX() , yellowBall.getCenterY() , yellowBall.getRadius() , yellowPaint);
        canvas.drawCircle(blackBall.getCenterX() , blackBall.getCenterY() , blackBall.getRadius() , blackPaint);
        canvas.drawRect(blueRect , bluePaint);
        canvas.drawRect(redRect , redPaint);
        canvas.drawRect(bottomBorder , transperentPaint);
        canvas.drawRect(topBorder , transperentPaint);
        canvas.drawRect(leftBorder , transperentPaint);
        canvas.drawRect(rightBorder , transperentPaint);
    }
    public void moveCurrentBall(Circle c ,float x , float y)
    {
        c.setCenterX(x);
        c.setCenterY(y);
        invalidate();
    }

    public Circle getYellowBall() {
        return yellowBall;
    }
    public Circle getBlackBall(){
        return blackBall;
    }
    public boolean SquareSuroundsCircle(RectF rect , Circle ball)
    {
        boolean squareSurounds = false;
        if(rect.left < ball.centerX - ball.radius && rect.right > ball.centerX + ball.radius)
        {
            if(rect.top < ball.centerY -ball.radius && rect.bottom > ball.centerY + ball.radius)
            {
                squareSurounds = true;
            }
        }
        return squareSurounds;
    }
    public RectF getBlueRect()
    {
        return blueRect;
    }
    public RectF getRedRect()
    {
        return redRect;
    }


    public class Circle
    {
        private float centerX;
        private float centerY;
        final private float initialCenterX;
        final private float initialCenterY;
        private float radius;

        public Circle(float x , float y , float r)
        {
            centerX = x;
            centerY = y;
            radius = r;
            initialCenterX = x;
            initialCenterY = y;
        }
        public void setCenterX(float centerX) {
            this.centerX = centerX;
        }

        public void setCenterY(float centerY) {
            this.centerY = centerY;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }

        public float getCenterX() {
            return centerX;
        }

        public float getCenterY() {
            return centerY;
        }

        public float getRadius() {
            return radius;
        }

        public boolean envelopsPoint(float x , float y)
        {
            boolean ballTouched = false;
            if(centerX + radius > x && centerX - radius < x)
            {
                if(centerY + radius > y && centerY - radius < y)
                {
                    ballTouched = true;
                }
            }
            return ballTouched;
        }

        public boolean touchesBorder()
        {
            boolean touches = false;
            float moveBackGap = 6f * scale;
            if(centerX - radius <= leftBorder.right)
            {
                centerX += moveBackGap;
                currentBorderID = leftBorderID;
                touches = true;
            }
            if(centerX + radius >= rightBorder.left)
            {
                centerX -= moveBackGap;
                currentBorderID = rightBorderID;
                touches = true;
            }
            if(centerY + radius >= bottomBorder.top)
            {
                centerY -= moveBackGap;
                currentBorderID = bottomBorderID;
                touches = true;
            }
            if(centerY - radius <= topBorder.bottom)
            {
                centerY += moveBackGap;
                currentBorderID = topBorderID;
                touches = true;
            }
            invalidate();
            return touches;
        }
        public void resetPosition()
        {
            setCenterX(initialCenterX);
            setCenterY(initialCenterY);
        }
        public void moveInside()
        {
            float moveBackGap = 6f * scale;
            switch(currentBorderID)
            {
                case leftBorderID:
                    centerX += moveBackGap;
                    break;
                case rightBorderID:
                    centerX -= moveBackGap;
                    break;
                case bottomBorderID:
                    centerY -= moveBackGap;
                    break;
                case topBorderID:
                    centerY += moveBackGap;
                    break;
            }
        }
        public void changeColor()
        {
            if(this == blackBall)
            {
                blackPaint.setColor(Color.GREEN);
            }
            else if(this == yellowBall)
            {
                yellowPaint.setColor(Color.MAGENTA);
            }
        }
    }
    /*public class Square extends RectF
    {
        private float side;
        private RectF rect;

        private Square(float s , RectF r)
        {
            side = s;
            r.left
        }
    }*/
}
