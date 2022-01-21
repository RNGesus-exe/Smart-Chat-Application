package com.rngesus.mywallet.StudyBuddy.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Telephony;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rngesus.mywallet.MainActivity;
import com.rngesus.mywallet.R;
import com.rngesus.mywallet.User;
import com.rngesus.mywallet.databinding.ActivityNumberVerifyBinding;
import com.rngesus.mywallet.databinding.ActivitySbSignupBinding;

import java.util.HashMap;
import java.util.UUID;

public class SB_Signup extends AppCompatActivity {
    ActivitySbSignupBinding binding2;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDbRef;
    int requestcode=100;
    Uri Data;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding2= ActivitySbSignupBinding.inflate(getLayoutInflater());
        setContentView(binding2.getRoot());
        binding2.btnSign.setEnabled(true);
        storageReference=FirebaseStorage.getInstance().getReference("uploads");

        binding2.btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createUser();
                binding2.progressBar3.setVisibility(View.VISIBLE);

            }
        });
    }
    private void createUser()
    {
        if (binding2.CBStudent.isChecked()||binding2.CBTeacher.isChecked())
        {
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            assert currentFirebaseUser != null;
            String Number=currentFirebaseUser.getPhoneNumber();
            if(binding2.CBStudent.isChecked())
            {
                mDatabase = FirebaseDatabase.getInstance();
                String num= Number.substring(0,3);
                mDbRef = mDatabase.getReference("Student/"+num);
               /* User user = new User("Jahanzaib", false,Number);*/
                /*mDbRef.child(Number).setValue(user);*/

            }
            else {
                mDatabase = FirebaseDatabase.getInstance();
                String num= Number.substring(0,3);
                mDbRef = mDatabase.getReference("Teacher/"+num);
                UploadPdf_File(Data,Number);

            }
        }
        else
            {
                Toast.makeText(this, "  Please Select One Field", Toast.LENGTH_SHORT).show();
        }
    }

    public void Student(View view) {
        binding2.CBTeacher.setChecked(false);
    }

    public void Teacher(View view) {
        binding2.CBStudent.setChecked(false);
    }

    public void UploadPdf(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, requestcode);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100 && data != null)
        {

            binding2.filename.setText(data.getDataString());
            Data=data.getData();

        }
    }
    private void UploadPdf_File(Uri data,String Number)
    {
        StorageReference ref = storageReference.child(System.currentTimeMillis()+".PDF");
        ref.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {


                Toast.makeText(SB_Signup.this, "Hurrah"+Data, Toast.LENGTH_SHORT).show();
                Task<Uri> mImageUrl=taskSnapshot.getStorage().getDownloadUrl();
                while(!mImageUrl.isComplete());
                Uri uri=mImageUrl.getResult();
                User user = new User("Jahanzaib", false, Number,uri.toString() );
                mDbRef.child(Number).setValue(user);
                Toast.makeText(SB_Signup.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SB_Signup.this, "Failure:(", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
