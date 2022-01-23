package com.rngesus.mywallet.StudyBuddy.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rngesus.mywallet.R;
import com.rngesus.mywallet.StudentUser;
import com.rngesus.mywallet.User;
import com.rngesus.mywallet.databinding.ActivitySBSignupStudentBinding;
import com.rngesus.mywallet.databinding.ActivitySbSignupBinding;

public class SB_Signup_student extends AppCompatActivity {
    ActivitySBSignupStudentBinding binding3;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding3= ActivitySBSignupStudentBinding.inflate(getLayoutInflater());
        setContentView(binding3.getRoot());

        binding3.btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(SB_Signup_student.this, "In student", Toast.LENGTH_SHORT).show();
                createUser();


            }
        });




    }
//    Password.getText().toString().contentEquals(confirmPassword.getText())
    private void createUser()
    {
        if (!TextUtils.isEmpty(binding3.editTextTextPassword.getText()) && binding3.editTextTextPassword.length() >= 8) {
            if (!TextUtils.isEmpty(binding3.editTextTextPassword2.getText())) {
                if (binding3.CBStudent.isChecked()) {
                    if(binding3.editTextTextPassword.getText().toString().contentEquals(binding3.editTextTextPassword2.getText().toString()))
                    {
                        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        String Number = currentFirebaseUser.getPhoneNumber();
                        mDatabase = FirebaseDatabase.getInstance();
                        String num = Number.substring(0, 3);
                        mDbRef = mDatabase.getReference("Student/" + num);
                        String password=binding3.editTextTextPassword.getText().toString();
                        StudentUser user1 = new StudentUser("jahanzaib",false,Number,password);
                        mDbRef.child(Number).setValue(user1);
                    }


                    else
                    {
                        binding3.editTextTextPassword.setError("Password Mismatch");

                    }

                } else {
                    Toast.makeText(this, "  Please Select One Field", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else
        {
            Toast.makeText(this, "Field cannot be empty or Password must be greater than 8", Toast.LENGTH_SHORT).show();
        }
    }

    public void Teacher(View view) {
        binding3.CBStudent.setChecked(false);
        startActivity(new Intent(getApplicationContext(),SB_Signup.class));
        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
    }

    public void Student(View view) {
        binding3.CBTeacher.setChecked(false);
    }
}