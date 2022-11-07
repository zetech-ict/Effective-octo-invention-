package com.shanidev.zesapp;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shanidev.blissDB.DashBoard;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Sign_Up extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Uri filePath;
    private String user_Id;
    private String passed_User_Id;
    private Image url;
    ActivityResultLauncher<String> nGetContent;

    EditText nSignUpMail, nSignUpPass, nSignUpPassConfirm, nRegister_Username;
    Button nSignUpBtn;
    TextView nLogInActBtn, nImageError;
    CircleImageView nProfilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        nLogInActBtn = findViewById(R.id.logInAct);
        nProfilePic = findViewById(R.id.profile_pic);
        nImageError = findViewById(R.id.image_error);
        nRegister_Username = findViewById(R.id.register_Username);
        nSignUpBtn = findViewById(R.id.signUpBtn);
        nSignUpMail = findViewById(R.id.signUpUserMail);
        nSignUpPass = findViewById(R.id.signUpUserPass);
        nSignUpPassConfirm = findViewById(R.id.signUpUserPassConfirm);

        nImageError.setVisibility(View.INVISIBLE);
        nSignUpBtn.setOnClickListener(view -> {
            if (nProfilePic.getDrawable() == null) {
                nImageError.requestFocus();
                nImageError.setVisibility(View.VISIBLE);
            } else if (nRegister_Username.getText().toString().trim().equals("") || nRegister_Username.getText().toString().trim().length() < 6) {
                nRegister_Username.setError(Sign_Up.this.getString(R.string.userNameError));
                nRegister_Username.requestFocus();
            } else if (Objects.requireNonNull(nSignUpMail.getText().toString().trim().equals(""))) {
                nSignUpMail.setError(Sign_Up.this.getString(R.string.mailError));
                nSignUpMail.requestFocus();
            } else if (Objects.requireNonNull(nSignUpPass.getText().toString().trim().equals("") || nSignUpPass.getText().toString().trim().length() < 8)) {
                nSignUpPass.setError(Sign_Up.this.getString(R.string.pass1Error));
                nSignUpPass.requestFocus();
            } else if (!nSignUpPassConfirm.getText().toString().trim().equals(nSignUpPass.getText().toString().trim())) {
                nSignUpPassConfirm.setError(Sign_Up.this.getString(R.string.pass2Error));
                nSignUpPassConfirm.requestFocus();
            } else {
                signNewUser();
            }
    });
        nGetContent=

                registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>()

                {
                    @Override
                    public void onActivityResult (Uri result){
                        nProfilePic.setImageURI(result);
                    }
                });

        nProfilePic.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                nGetContent.launch("image/*");
              //uploadProfileStorage();
            }
        });

        nLogInActBtn.setOnClickListener(view ->

    {
        Intent intent = new Intent(getApplicationContext(), Log_In.class);
        startActivity(intent);
        finish();
    });
}

    private void signNewUser() {

        mAuth.createUserWithEmailAndPassword(nSignUpMail.getText().toString(), nSignUpPass.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Sign_Up.this, "Authentication successful!", Toast.LENGTH_SHORT).show();
                        signNewUserDatabase();
                    } else {
                        Toast.makeText(Sign_Up.this, "Authentication failed because: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void uploadProfileStorage() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child("images.jpg" + UUID.randomUUID().toString());
        mountainsRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Sign_Up.this, "Image Uploaded!",
                        Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Sign_Up.this, "Image Failed to upload!",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void signNewUserDatabase() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String dbUserName = nRegister_Username.getText().toString();
        String dbMail = nSignUpMail.getText().toString();
        String dbPass = nSignUpPass.getText().toString();
        Map<String, Object> user = new HashMap<>();
        user.put("Username", dbUserName);
        user.put("E-Mail", dbMail);
        user.put("Password", dbPass);

        db.collection("Users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Sign_Up.this, "Account created!",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), DashBoard.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Sign_Up.this, "Firestore error: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}