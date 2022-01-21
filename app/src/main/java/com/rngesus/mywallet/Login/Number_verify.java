package com.rngesus.mywallet.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rngesus.mywallet.Adapter.SpinnerAdapter;
import com.rngesus.mywallet.MainActivity;
import com.rngesus.mywallet.ModelClass.countryitem;
import com.rngesus.mywallet.R;
import com.rngesus.mywallet.StudyBuddy.Login.SB_Login;
import com.rngesus.mywallet.StudyBuddy.Login.SB_Signup;
import com.rngesus.mywallet.databinding.ActivityNumberVerifyBinding;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Number_verify extends AppCompatActivity {
ActivityNumberVerifyBinding binding;
private FirebaseAuth mauth;
String countrycode;
String lancode;
private ArrayList<countryitem> mCountryList;
private SpinnerAdapter  mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNumberVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        //INSTANCE
        mauth=FirebaseAuth.getInstance();
        //ONCLICK FOR OTP_SEND
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(binding.etPhone.getText()))
                {
                    binding.etPhone.setError("Please Fill This Field");
                }
                else if(binding.etPhone.getText().toString().trim().length()>20)
                {
                    Toast.makeText(Number_verify.this, "INVALID PHONE NUMBER", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    otpSend();
                }

            }
        });
        //Spinner
        mAdapter = new SpinnerAdapter(this, mCountryList);
        binding.spinner.setAdapter(mAdapter);;
        //ITEM_SELECTED
       binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryitem clickedItem = (countryitem) parent.getItemAtPosition(position);
                countrycode = clickedItem.getCountryName();
                lancode= clickedItem.getLanguage();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void otpSend()
    {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnSend.setVisibility(View.INVISIBLE);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mauth)
                        .setPhoneNumber(countrycode+binding.etPhone.getText().toString().trim())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(Number_verify.this)// Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {



                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                binding.progressBar.setVisibility(View.GONE);
                                binding.btnSend.setVisibility(View.VISIBLE);
                                Toast.makeText(Number_verify.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationId, forceResendingToken);
                                binding.progressBar.setVisibility(View.GONE);
                                binding.btnSend.setVisibility(View.VISIBLE);
                                Toast.makeText(Number_verify.this, "OTP is successfully send.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Number_verify.this, Otp_Verfication.class);
                                String number=countrycode+binding.etPhone.getText().toString().trim();
                                Toast.makeText(Number_verify.this, ""+number, Toast.LENGTH_SHORT).show();
                                intent.putExtra("phone", number);
                                intent.putExtra("verificationId", verificationId);
                                binding.etPhone.setText(" ");
                                startActivity(intent);
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        if(lancode==null)
        {
            mauth.setLanguageCode("en_gb");
        }
        else
        {
            mauth.setLanguageCode(lancode);

        }
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Toast.makeText(this, "welcome", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Number_verify.this, SB_Login.class);
            startActivity(intent);

        }//No User is Logged in

    }
    private void init() {
        mCountryList = new ArrayList<>();
        mCountryList.add(new countryitem("+92", R.drawable.pak,"ur"));
        mCountryList.add(new countryitem("+33", R.drawable.france,"fr"));
        mCountryList.add(new countryitem("+49", R.drawable.download,"de"));
        mCountryList.add(new countryitem("+44", R.drawable.england,"en-gb"));
    }
}