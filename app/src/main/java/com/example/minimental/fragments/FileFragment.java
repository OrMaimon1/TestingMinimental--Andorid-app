package com.example.minimental.fragments;

import static android.app.Activity.RESULT_OK;

import static androidx.core.content.FileProvider.getUriForFile;

import android.app.Activity;
import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
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

import com.example.minimental.BuildConfig;
import com.example.minimental.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;


public class FileFragment extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ImageButton addRowBtn;
    final int CAMERA_REQUEST = 1;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ImageView mImageView;
    String getPhotoPath;
    File photo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.files_fragment,container,false);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Pictures> pictures = new ArrayList<>();
        mImageView =rootView.findViewById(R.id.photo_imageView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(pictures);
        recyclerView.setAdapter(adapter);

//        try {
//            FileInputStream fis = getContext().openFileInput("pictures");
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            pictures = (List<Pictures>)ois.readObject();
//            ois.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        }catch (ClassNotFoundException e){
//            e.printStackTrace();
//        }

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK && result.getData() != null){

                    Bundle bundle = result.getData().getExtras();
                    Bitmap bm = (Bitmap) bundle.get("data");
                    mImageView.setImageBitmap(bm);
                    mImageView.setImageBitmap(BitmapFactory.decodeFile(photo.getAbsolutePath()));
                    adapter.notifyDataSetChanged();


                }
            }
        });

        adapter.setListener(new RecyclerViewAdapter.PictureAdapterListener() {
            @Override
            public void onTakePhotoPress(int position) {

                Pictures picture = pictures.get(position);
                photo = new File(Environment.getExternalStorageDirectory(), "picture" + (pictures != null ? pictures.size() : 0)+".jpg");
                Uri imageUri = getUriForFile(rootView.getContext(), BuildConfig.APPLICATION_ID + ".provider",photo);
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Bundle newExtras = new Bundle();
                if (imageUri != null) {
                    newExtras.putParcelable(MediaStore.EXTRA_OUTPUT, imageUri);
                } else {
                    newExtras.putBoolean("return-data", true);
                }
                //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                if(takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null)
                {
                    //mImageView.setImageBitmap(null);
                    //adapter.notifyItemChanged(position);
                activityResultLauncher.launch(takePictureIntent);


                }

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

                pictures.add(new Pictures(getPhotoPath));
                    try {
                        FileOutputStream fos = getContext().openFileOutput("pictures", Context.MODE_PRIVATE);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(pictures);
                        oos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                //mImageView.setImageBitmap(null);
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

//public class MyFileProvider extends FileProvider {
//    public MyFileProvider() {
//        super(R.xml.provider_paths);
//    }
//}