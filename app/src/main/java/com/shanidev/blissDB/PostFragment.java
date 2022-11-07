package com.shanidev.blissDB;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.utilities.Pair;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shanidev.zesapp.AdopterPosts;
import com.shanidev.zesapp.R;
import com.shanidev.zesapp.posts;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

    public class PostActivity<PostFragment> extends AppCompatActivity {

        private static final int GALLERY_REQUEST_CODE = 34567;
        private Uri imageUri=null;
        private ImageView postImage;
        private EditText etPost;
        private ProgressBar pb;
        private FirebaseAuth mAuth;
        private RelativeLayout homeLayout;
        private DatabaseReference mPhotosDatabase;
        private StorageReference mPhotosStrorage;
        private String userId;
        private Object download_url;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_post2);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            postImage=findViewById(R.id.postImage);
            etPost=findViewById(R.id.etPost);
            pb=findViewById(R.id.sendProgress);
            homeLayout=findViewById(R.id.homeLayout);
            mAuth=FirebaseAuth.getInstance();
            userId=mAuth.getUid();
            mPhotosDatabase=FirebaseDatabase.getInstance().getReference().child("PhotoHub/Blog");
            mPhotosStrorage=FirebaseStorage.getInstance().getReference().child("PhotoHub/BlogImages");



        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.post_menu,menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId()==R.id.menu_attachment)
            {
                Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST_CODE);
            }else if (item.getItemId()==R.id.menu_send)
            {
                final String post=etPost.getText().toString();
                if (!TextUtils.isEmpty(post) && imageUri!=null) {
                    pb.setVisibility(View.VISIBLE);
                    final StorageReference newPhoto=mPhotosStrorage.child(imageUri.getLastPathSegment());
                    newPhoto.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful())
                            {
                                final String myKey=mPhotosDatabase.push().getKey();

                                String datem=getDateTime();
                            /*final Map<String,String> newPost=new HashMap<>();
                            newPost.put("postid",myKey);
                            newPost.put("postedby",mAuth.getUid());
                            newPost.put("postedon",getDateTime());
                            newPost.put("postdetails",post);
                            newPost.put("postimage",download_url);*/

                                DatabaseReference newDatabase=mPhotosDatabase.child(myKey);

                                newDatabase.child("postid").setValue(myKey);
                                newDatabase.child("postedby").setValue(userId);
                                newDatabase.child("postedon").setValue(datem);
                                newDatabase.child("postdetails").setValue(post);
                                newDatabase.child("postlikes");
                                newDatabase.child("postviews");
                                newDatabase.child("postcomments");

                                newDatabase.child("postimage").setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @SuppressLint("RestrictedApi")
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful())
                                        {


                                            pb.setVisibility(View.GONE);
                                            Pair[] pairs=new Pair[1];
                                            pairs[0]=new Pair<View,String>(homeLayout,"etTransition");

                                            ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(AdopterPosts.this,pairs);



                                            startActivity(new Intent(PostFragment.this,HomeFragment.class),options.toBundle());

                                        }
                                    }
                                });








                            }else {
                                Toast.makeText(PostActivity.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    //here is where to send
                    //Toast.makeText(this, "Ready to send..", Toast.LENGTH_SHORT).show();
                }else {
                    pb.setVisibility(View.GONE);

                    etPost.setError("Type some message for the for your photo...");
                    etPost.requestFocus();
                }
            }

            return super.onOptionsItemSelected(item);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode==GALLERY_REQUEST_CODE && resultCode==RESULT_OK)
            {
                imageUri=data.getData();
                postImage.setImageURI(imageUri);


            }
        }

        private String getDateTime() {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            return dateFormat.format(date);
        }
    }