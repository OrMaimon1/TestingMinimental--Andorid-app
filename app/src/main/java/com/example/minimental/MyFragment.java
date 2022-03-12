package com.example.minimental;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

class MyFragment extends Fragment {

    private MyLifecycleObserver mObserver;
    final int SPEECH_RECQGNITON_RESULT = 3;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        mObserver = new MyLifecycleObserver(requireActivity().getActivityResultRegistry());
        getLifecycle().addObserver(mObserver);
    }
//    @Override
//    void onCreate(Bundle savedInstanceState) {
//        // ... super?
//        super.onCreate(savedInstanceState);
//        mObserver = new MyLifecycleObserver(requireActivity().getActivityResultRegistry());
//        getLifecycle().addObserver(mObserver);
//        //Button speechBtn
//        //dont know where to put the button or i didnt get it
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button selectButton = view.findViewById(R.id.select_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mObserver.Speech_Recqniton();
            }
        });
    }

    //  @Override
   // void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Button selectButton = findViewById(R.id.select_button);
        //selectButton.setOnClickListener(new OnClickListener() {
          //  @Override
          //  public void onClick(View view) {
        //        mObserver.Speech_Recqniton();
         //   }
      //  });
   // }
}
