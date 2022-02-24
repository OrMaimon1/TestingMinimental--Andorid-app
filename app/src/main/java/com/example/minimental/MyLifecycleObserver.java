package com.example.minimental;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public class MyLifecycleObserver implements DefaultLifecycleObserver {
    private final ActivityResultRegistry mRegistry;
    private ActivityResultLauncher<String> mGetContent;

    MyLifecycleObserver(@NonNull ActivityResultRegistry registry) {
        mRegistry = registry;
    }

    public void onCreate(@NonNull LifecycleOwner owner) {
        // ...

        //mGetContent = mRegistry.register(“key”, owner, new ActivityResultContracts.GetContent(),
               // new ActivityResultCallback<Uri>() {
                 //   @Override
              //      public void onActivityResult(Uri uri) {
                        // Handle the returned Uri
                    }
        //        });
    }

   // public void selectImage() {
  //      // Open the activity to select an image
    //    mGetContent.launch("image/*");
  //  }
//}
