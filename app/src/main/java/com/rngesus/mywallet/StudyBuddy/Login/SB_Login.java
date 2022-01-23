package com.rngesus.mywallet.StudyBuddy.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rngesus.mywallet.MainActivity;
import com.rngesus.mywallet.R;
import com.rngesus.mywallet.StudyBuddy.Login.Student.StudentMainLooby;
import com.rngesus.mywallet.StudyBuddy.Login.Teacher.TeacherMainLooby;
import com.rngesus.mywallet.User;
import com.rngesus.mywallet.databinding.ActivitySBLoginBinding;
import com.rngesus.mywallet.databinding.ActivitySbSignupBinding;

public class SB_Login extends AppCompatActivity {
    ActivitySBLoginBinding binding3;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding3= ActivitySBLoginBinding.inflate(getLayoutInflater());
        setContentView(binding3.getRoot());
        binding3.btnSign.setEnabled(true);
        binding3.tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SB_Login.this,SB_Signup_student.class);
                startActivity(intent);
            }
        });
        binding3.btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }
    private void loginUser() {
        if (binding3.CBStudent.isChecked()||binding3.CBTeacher.isChecked())
        {
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            String CNumber=currentFirebaseUser.getPhoneNumber();
            if(!TextUtils.isEmpty(binding3.SBPhone.getText())) {
                String Number = binding3.SBPhone.getText().toString();

                    mDatabase = FirebaseDatabase.getInstance();
                    String num = Number.substring(0, 3);
                    mDbRef = mDatabase.getReference("Student/"+num);
                    mDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(final DataSnapshot dataSnapshot) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                String value=data.child("phoneNumber").getValue(String.class);
                                String password=data.child("password").getValue(String.class);
                                Toast.makeText(SB_Login.this, "d="+value, Toast.LENGTH_SHORT).show();
                                if(Number.equals(value) && binding3.Password.getText().toString().contentEquals(password))
                                {
                                    Toast.makeText(SB_Login.this, "FOUND USER", Toast.LENGTH_SHORT).show();

                                    if(binding3.CBStudent.isChecked())
                                    {
                                        Intent intent=new Intent(SB_Login.this,StudentMainLooby.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Intent intent=new Intent(SB_Login.this,TeacherMainLooby.class);
                                        startActivity(intent);
                                    }

                                }
                                else
                                {
                                    Toast.makeText(SB_Login.this, "No User Found", Toast.LENGTH_SHORT).show();
                                }

                            }


                        }

                        @Override
                        public void onCancelled(final DatabaseError databaseError) {
                        }
                    });

            }
            else
            {
                Toast.makeText(this, "Please Fill Above Text", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "  Please Select One Field", Toast.LENGTH_SHORT).show();
        }
    }
}