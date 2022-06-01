package com.example.minimental.fragments;

import static android.app.Activity.RESULT_OK;

import static androidx.core.content.FileProvider.getUriForFile;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minimental.BuildConfig;
import com.example.minimental.R;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class FileFragment extends Fragment {

    private StorageReference storageReference = null;
    private DatabaseReference databaseReference = null;
    List<Pictures> pictures=new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ImageButton addRowBtn;
    final int CAMERA_REQUEST = 1;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ImageView mImageView;
    File photo;
    Uri imageUri;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.files_fragment,container,false);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(pictures,rootView.getContext());
        recyclerView.setAdapter(adapter);


        mImageView = rootView.findViewById(R.id.test_ImageView);
        mImageView.setVisibility(View.INVISIBLE);


        //DataBase Storage
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        databaseReference = database.getReference().child("user_images");
        storageReference = firebaseStorage.getReference();





        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK && result.getData() != null){
//                    Bundle bundle = result.getData().getExtras();
//                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    mImageView.setImageBitmap(bitmap);
                    //mImageView.setImageBitmap(BitmapFactory.decodeFile(photo.getAbsolutePath()));
                    //uploadImage();



                    Pictures listpicture = new Pictures();
                    String name = getResources().getString(R.string.name_picture);
                    listpicture.setName(name + " " + (pictures.size() + 1));

                    byte[]  imageRv=new byte[0];
                    imageRv = conver_byte_array(mImageView);

                    listpicture.setPhotoPath(imageRv);

                    //imageRv = getImageUri(mImageView);
                    pictures.add(listpicture);
                    adapter.notifyDataSetChanged();


                    //uri.setImageBitmap(BitmapFactory.decodeFile(photo.getAbsolutePath()));
                    //Pictures picture = new Pictures(photo.getAbsolutePath());
//                    try {
//                        FileOutputStream fos = getContext().openFileOutput("pictures", Context.MODE_PRIVATE);
//                        ObjectOutputStream oos = new ObjectOutputStream(fos);
//                        oos.writeObject(pictures);
//                        oos.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                   // picture.setPhoto(uri.getPath());
//
//                    pictures.add();
//                    adapter.updateList(pictures);
//                    adapter.notifyDataSetChanged();
////                    Bundle bundle = result.getData().getExtras();
////                    Bitmap bm = (Bitmap) bundle.get("data");
////                    uriPath.setImageBitmap(bm);
////                    uriPath.setImageBitmap(BitmapFactory.decodeFile(photo.getAbsolutePath()));




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
                photo = new File(Environment.getExternalStorageDirectory(), "picture" + (pictures != null ? pictures.size() : 0)+".png");
                imageUri = getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider",photo);
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

                    activityResultLauncher.launch(takePictureIntent);

                }

            }
//                pictures.add(new Pictures());
//
//                    try {
//                        FileOutputStream fos = getContext().openFileOutput("pictures", Context.MODE_PRIVATE);
//                        ObjectOutputStream oos = new ObjectOutputStream(fos);
//                        oos.writeObject(pictures);
//                        oos.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                //mImageView.setImageBitmap(null);
//                //adapter.notifyDataSetChanged();
//                adapter.notifyItemInserted(pictures.size()-1);
//                recyclerView.scrollToPosition(pictures.size()-1);
//            }
        });



        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fileFragment_to_login_fragment);
            }
        });
        return rootView;
    }

    public String getImageUri(Context context, Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),bitmap,"title", null);
        return path;
    }

    public byte[] conver_byte_array(ImageView img)
    {
        Bitmap image = ((BitmapDrawable)img.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] bytearr = byteArrayOutputStream.toByteArray();
        return bytearr;
    }



    private void uploadImage(){
        if(imageUri != null)
        {
            StorageReference ref = storageReference.child(photo.getAbsolutePath());
            ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            databaseReference.push().setValue(uri.toString());
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),"Image uploaded faild", Toast.LENGTH_SHORT);
                }
            });
        }
    }

}



