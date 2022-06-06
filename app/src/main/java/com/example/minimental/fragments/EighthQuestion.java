package com.example.minimental.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.minimental.CustomView.BallsDragView;
import com.example.minimental.EightQuestion;
import com.example.minimental.R;
import com.example.minimental.Services.MediaPlayerService;
import com.example.minimental.ViewModels.SharedViewModel;

public class EighthQuestion extends Fragment {
    
    //View blackCircle, yellowCircle, redSquare, blueCircle;
    private SharedViewModel sharedViewModel;
    private EightQuestion eightQuestion = new EightQuestion();
    private Boolean eigthCurrectOrder = false;
    private RelativeLayout dragLayout;
    private BallsDragView ballsDragView;
    private BallsDragView.Circle yellowBall;
    private BallsDragView.Circle blackBall;
    private BallsDragView.Circle blueBall;
    private BallsDragView.Circle greenBall;
    private BallsDragView.Circle currentBallTouched;
    //private BallsDragView.Circle currentBallInvolvedInQuestion;
    private RectF blueRect;
    private RectF redRect;
    private float x = 0, y = 0;
    private SharedViewModel viewModel;
    private float blueCircleCenterX;
    private float blueCircleCenterY;
    private float redSquareCenterX;
    private float redSquareCenterY;
    private boolean ball1 = false ,ball2 = false;
    private TextView eight_instTx;
    private Integer Version;


    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.eighth_question,container,false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        eight_instTx = rootView.findViewById(R.id.eighthQuestion_instructions);
        //ImageButton speakerButton = rootView.findViewById(R.id.eight_question_speaker);
        ballsDragView = rootView.findViewById(R.id.ball_drag_view);
        yellowBall = ballsDragView.getYellowBall();
        blackBall = ballsDragView.getBlackBall();
        blueBall = ballsDragView.getBlueBall();
        greenBall = ballsDragView.getGreenBall();
        //blueRect = ballsDragView.getBlueRect();
        redRect = ballsDragView.getRedRect();
        //dragLayout = rootView.findViewById(R.id.eight_drag_layout);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        Version = sharedViewModel.getVersion().getValue(); // this is the test version
        if (Version == null || Version == 0) //only for now some users dosnt have version alredy asked to add
        {
            Version = 1;
        }
        if(Version == 1){
            //blackBall
        }
        if(Version == 2){
            eight_instTx.setText(R.string.eighth_question_instructions2);
            //YellowBall
        }
        if(Version == 3){
            eight_instTx.setText(R.string.eighth_question_instructions3);
            //blueBall
        }
        if(Version == 4){
            eight_instTx.setText(R.string.eighth_question_instructions4);
            //greenBall
        }

        /*blackCircle = (View) rootView.findViewById(R.id.viewblackcircle);
        yellowCircle = (View) rootView.findViewById(R.id.viewYellowCircle);
        redSquare = (View) rootView.findViewById(R.id.square_view);
        blueCircle = (View) rootView.findViewById(R.id.circle_view);*/

       /* redSquareCenterX = redSquare.getX();
        redSquareCenterY = redSquare.getY();
        blueCircleCenterX = blueCircle.getX();
        blueCircleCenterY = blueCircle.getY();

        blackCircle.setOnTouchListener(new MyTouchListener());
        yellowCircle.setOnTouchListener(new MyTouchListener());*/

       // redSquare.setOnDragListener(new MyDragListener());
        //blueCircle.setOnDragListener(new MyDragListener());


        ballsDragView.setOnTouchListener(new ballsDragTouchListner());


//        speakerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startMediaService();
//            }
//        });


       nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                nxtBtn.startAnimation(animation);
                if (ball1 == true)
                {
                    eigthCurrectOrder = true;

                }
                sharedViewModel.setCurrectPicOrderEighth(eigthCurrectOrder);
                Navigation.findNavController(view).navigate(R.id.action_eighthQuestion_to_ninthQuestion);
            }
        });

        return rootView ;

    }

    private void startMediaService()
    {
        Intent intent = new Intent(getContext() , MediaPlayerService.class);
        intent.putExtra("Link" , "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Questions%20Instructions%2FMyRec_0526_1315%D7%A7%D7%A8%D7%99%D7%90%D7%94%20%D7%95%D7%91%D7%99%D7%A6%D7%95%D7%A2.mp3?alt=media&token=db7c7b3d-ddce-4c88-a56e-afc5347d7203");
        getContext().startService(intent);
    }

    private class ballsDragTouchListner implements View.OnTouchListener
    {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();

            if(yellowBall.envelopsPoint(x,y))
            {
                currentBallTouched = yellowBall;
            }
            else if(blackBall.envelopsPoint(x,y))
            {
                currentBallTouched = blackBall;
            }
            else if(blueBall.envelopsPoint(x,y))
            {
                currentBallTouched = blueBall;
            }
            else if(greenBall.envelopsPoint(x,y))
            {
                currentBallTouched = greenBall;
            }
            if(currentBallTouched != null) {
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_MOVE:
                        if(!currentBallTouched.touchesBorder()) {
                            ballsDragView.moveCurrentBall(currentBallTouched, x, y);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if(ballsDragView.SquareSuroundsCircle(redRect , blackBall) && Version == 1)
                        {
                            blackBall.changeColor();
                            ball1 = true;
                        }
                        else if(ballsDragView.SquareSuroundsCircle(redRect , yellowBall) && Version == 2)
                        {
                            yellowBall.changeColor();
                            ball1 = true;

                        }
                        else if(ballsDragView.SquareSuroundsCircle(redRect , blueBall) && Version == 3)
                        {
                            blueBall.changeColor();
                            ball1 = true;
                        }
                        else if(ballsDragView.SquareSuroundsCircle(redRect , greenBall) && Version == 4)
                        {
                            greenBall.changeColor();
                            ball1 = true;
                        }
                        else {
                            ball1 = false;
                        }
                        if(currentBallTouched.touchesBorder()) {
                            ballsDragView.moveCurrentBall(currentBallTouched, currentBallTouched.getCenterX(), currentBallTouched.getCenterY());
                        }
                        break;
                }
                if(currentBallTouched.touchesBorder())
                {
                    currentBallTouched.moveInside();
                }
                currentBallTouched = null;
            }
            return true;
        }
    }

   /* final class MyTouchListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getActionMasked()){
                case MotionEvent.ACTION_DOWN:
                    x = event.getX();
                    y = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float movedX, movedY;
                    movedX = event.getX();
                    movedY = event.getY();

                    float distanceX = movedX - x;
                    float distanceY = movedY - y;

                    view.setX(view.getX()+distanceX);
                    view.setY(view.getY()+distanceY);

                    break;
            }
            return true;
        }
    }*/

    /*public boolean blackCirecleInPlace(float locationX , float locationY)
    {
        boolean inPlace = true;
        int[] location = new int[2];
        blueCircle.getLocationOnScreen(location);
        int height = blueCircle.getBottom() - blueCircle.getTop();
        int radius = height / 2;
        int blueCircleX = location[0];
        int blueCircleY = location[1];
        if(!((blackCircle.getY() < blueCircleY + radius) && (blackCircle.getY() > blueCircleY - radius)) )
        {
            inPlace = false;
        }
        if(! ( (blackCircle.getX() > blueCircleX -radius) && (blackCircle.getX() < blueCircleX + radius) ))
        {
            inPlace = false;
        }
        return inPlace;
    }*/

    /*class MyDragListener implements View.OnDragListener {
        //Drawable enterShape = getResources().getDrawable(R.drawable.square_shape);
        //Drawable normalShape = getResources().getDrawable(R.drawable.square_shape);
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.invalidate();
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.invalidate();
                    //v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    View owner = (View) view.getParent();
                    //owner.removeView(view);
                    //ConstraintLayout container = (ConstraintLayout) view;
                    //container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.invalidate();
                default:
                    break;
            }
            return true;
        }
    }*/
}
