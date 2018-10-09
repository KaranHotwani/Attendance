package com.example.karan.attendance;

import android.app.ProgressDialog;
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

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button Login;
    private TextView Info;
    private int counter=5;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;//message to be displayed till log in is in process

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name=(EditText)findViewById(R.id.etname);
        Password=(EditText)findViewById(R.id.etPassword);
        Info=(TextView)findViewById(R.id.tvinfo);
        Login=(Button)findViewById(R.id.button);

        userRegistration=(TextView)findViewById(R.id.tvRegister);

        Info.setText("No of attempts remaining :5");

        firebaseAuth =FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        FirebaseUser user =firebaseAuth.getCurrentUser();//checks if user is already logged in

        if(user !=null){
            finish();//finish current activity
            startActivity(new Intent(MainActivity.this,SecondActivity.class ));//redirect to new one
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(),Password.getText().toString());
            }
        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            }
        });

    }



    private void validate(String userName ,String  userPassword)
    {
        progressDialog.setMessage("Authentication in process");
        progressDialog.show();;
        firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                progressDialog.dismiss();
                checkEmailVerfication();
               // Toast.makeText(MainActivity.this," login successful",Toast.LENGTH_SHORT).show();

            }
            else{
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this," login failed",Toast.LENGTH_SHORT).show();
                counter--;

                Info.setText("No of attempts remaining :"+ String.valueOf(counter));
                if(counter==0)
                {
                    Login.setEnabled(false);
                }
            }
            }
        });
    }

    private void checkEmailVerfication(){
        FirebaseUser firebaseUser=firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag=firebaseUser.isEmailVerified();
        if(emailflag){
            finish();
            startActivity(new Intent(MainActivity.this,SecondActivity.class));
        }
        else{
            Toast.makeText(this,"verify your email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

}
