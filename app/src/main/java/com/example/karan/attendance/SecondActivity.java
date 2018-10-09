package com.example.karan.attendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private CheckBox C1;
    private CheckBox C2;
    private CheckBox C3;
    private Spinner Class;
    private Spinner Subject;
    private Button Submit;

    DatabaseReference databasemain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        databasemain= FirebaseDatabase.getInstance().getReference("Attend");


        firebaseAuth=FirebaseAuth.getInstance();
        Class=(Spinner)findViewById(R.id.spclass);
        Subject=(Spinner)findViewById(R.id.spsubject);
        C1=(CheckBox)findViewById(R.id.checkBox);
        C2=(CheckBox)findViewById(R.id.checkBox2);
        C3=(CheckBox)findViewById(R.id.checkBox3);
        Submit=(Button)findViewById(R.id.btnSubmit);


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validate();
            }
        });


    }

    public String Roll1;
    public String Roll2;
    public String Roll3;

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox:
                if (checked){
                    Roll1="Present";
                }

            else{
                    Roll1="Absent";
                }
                break;
            case R.id.checkBox2:
                if (checked){
                    Roll2="Present";
                }
            else{
                    Roll2="Absent";
                }
                break;
            case R.id.checkBox3:
                if (checked){
                    Roll3="Present";
                }
                else{
                    Roll3="Absent";
                }
                    break;
            // TODO: Veggie sandwich
        }
    }
    private void validate(){





        String class1=Class.getSelectedItem().toString();
        String subject1=Subject.getSelectedItem().toString();

        if(!TextUtils.isEmpty(Roll1) || !TextUtils.isEmpty(Roll2) || !TextUtils.isEmpty(Roll3)){
            String id=databasemain.push().getKey();

            Valid valid=new Valid(id,class1,subject1,Roll1,Roll2,Roll3);

            databasemain.child(id).setValue(valid);

            Toast.makeText(this,"Attendance recorded",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Enter valid details",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.logoutMenu:{
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(SecondActivity.this,MainActivity.class));
            }

        }
        return super.onOptionsItemSelected(item);
    }


}
