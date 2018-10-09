    package com.example.karan.attendance;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

    public class RegistrationActivity extends AppCompatActivity {

    private EditText userName;
    private EditText userPassword;
    private EditText userEmail;
    private Button regButton;
    private TextView userLogin;

    private FirebaseAuth firebaseAuth;

        private void setupUIViews(){
            userName=(EditText)findViewById(R.id.etUserName);
            userPassword=(EditText)findViewById(R.id.etUserPassword);
            userEmail=(EditText)findViewById(R.id.etUserEmail);
            userLogin=(TextView)findViewById(R.id.tvUserLogin);
            regButton=(Button)findViewById(R.id.btnRegister);
        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        firebaseAuth =FirebaseAuth.getInstance();
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //upload database
                    String user_email=userEmail.getText().toString().trim();
                    String user_password=userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                //Toast.makeText(RegistrationActivity.this,"Registered",Toast.LENGTH_SHORT).show();
                               // startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                                sendEmailVerification();
                            }
                            else{
                                Toast.makeText(RegistrationActivity.this,"Registeration failed",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
            }
        });
    }



    private boolean validate(){
        Boolean result = false;

        String name=userName.getText().toString();
        String password=userPassword.getText().toString();
        String email=userEmail.getText().toString();

        if(name.isEmpty() || password.isEmpty() || email.isEmpty())
        {
            Toast.makeText(this,"please enter all details",Toast.LENGTH_SHORT).show();
        }
        else
        {
            result = true;
        }
        return result;
    }

    private void sendEmailVerification(){
        final FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                      if(task.isSuccessful()){
                          Toast.makeText(RegistrationActivity.this,"Successfully registered,varification message has been sent",Toast.LENGTH_SHORT).show();
                          firebaseAuth.signOut();
                          finish();
                          startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                      }
                      else{
                          Toast.makeText(RegistrationActivity.this,"Verification mail not sent",Toast.LENGTH_SHORT).show();
                      }
                }
            });
        }
    }
}