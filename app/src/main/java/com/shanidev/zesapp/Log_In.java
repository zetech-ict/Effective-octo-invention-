package com.shanidev.zesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shanidev.blissDB.DashBoard;

public class Log_In extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Button nLogInBtn;
    TextView nForgot, nErrorTxt, nSignInAct;
    EditText nLogInMail, nLogInPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        nForgot = findViewById(R.id.forgot_Pass);


        nLogInMail = findViewById(R.id.logInUserMail);
        nLogInPass = findViewById(R.id.logInUserPass);
        nErrorTxt = findViewById(R.id.logInError);

        nLogInBtn = findViewById(R.id.logInBtn);

        nSignInAct = findViewById(R.id.signInAct);

        nForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Log_In.this, "Lol! Me too.", Toast.LENGTH_SHORT).show();
            }
        });

        nLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nLogInMail.getText().toString().trim().equals("")) {
                    nLogInMail.setError(Log_In.this.getString(R.string.mailError));
                    nLogInMail.requestFocus();
                } else if (nLogInPass.getText().toString().trim().equals("") || nLogInPass.getText().toString().trim().length() < 4) {
                    nLogInPass.setError(Log_In.this.getString(R.string.mailError));
                    nLogInPass.requestFocus();
                } else {

                    logInUser();

                }
            }

        });


        nSignInAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Sign_Up.class);
                startActivity(intent);
            }
        });
    }

    private void logInUser() {

        mAuth.signInWithEmailAndPassword(nLogInMail.getText().toString(), nLogInPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(Log_In.this, "Log In Successful!",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), DashBoard.class);
                    startActivity(intent);
                    finish();
                } else {
                    nErrorTxt.setVisibility(View.VISIBLE);
                } // ...
            }
        });
    }
}