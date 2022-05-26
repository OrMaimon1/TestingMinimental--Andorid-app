package com.example.minimental.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minimental.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;


public class FileFragment extends Fragment {

    File photo;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ImageButton addRowBtn;
    final int CAMERA_REQUEST = 1;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ImageView mImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.files_fragment,container,false);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Pictures> pictures = new ArrayList<>();
        mImageView = rootView.findViewById(R.id.photo_imageView);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(pictures);
        recyclerView.setAdapter(adapter);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK && result.getData() != null){
                            Bundle bundle = result.getData().getExtras();
                            Uri uri = (Uri) bundle.get("data");
                            mImageView.setImageURI(uri);
                        }
                    }
                });


                adapter.setListener(new RecyclerViewAdapter.PictureAdapterListener() {
                    @Override
                    public void onTakePhotoPress(int position) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if(intent.resolveActivity(getActivity().getPackageManager()) != null)
                        {
                            photo = new File(Environment.getExternalStorageDirectory(), "picture" + (pictures != null ? pictures.size() : 0) + ".jpg");
                            Uri imageUri = FileProvider.getUriForFile(rootView.getContext(),"${applicationId}.provider",photo);
                            //
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            activityResultLauncher.launch(intent);
                        }
                        Pictures picture = pictures.get(position);
                        adapter.notifyItemChanged(position);
                    }
                });

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(direction == ItemTouchHelper.RIGHT){
                    pictures.remove(viewHolder.getAdapterPosition());
                    adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                }
            }
        };
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        //
        addRowBtn = rootView.findViewById(R.id.add_button);

        addRowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pictures.add(new Pictures());
                adapter.notifyDataSetChanged();
            }
        });



        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fileFragment_to_login_fragment);
            }
        });
        return rootView;
    }


}
