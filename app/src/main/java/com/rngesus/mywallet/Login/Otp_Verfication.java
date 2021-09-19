package com.rngesus.mywallet.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rngesus.mywallet.MainActivity;
import com.rngesus.mywallet.R;
import com.rngesus.mywallet.databinding.ActivityOtpVerficationBinding;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Otp_Verfication extends AppCompatActivity {
    FirebaseAuth mauth;
    ActivityOtpVerficationBinding otpbind;
    String number;
    private  String verificationId;
    private static final long START_TIME_IN_MILLIS = 600000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    CountDownTimer mCountDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp__verfication);
        otpbind= ActivityOtpVerficationBinding.inflate(getLayoutInflater());
        setContentView(otpbind.getRoot());
        mauth=FirebaseAuth.getInstance();
        number=  getIntent().getStringExtra("phone");
        verificationId = getIntent().getStringExtra("verificationId");
        otpbind.tvMobile.setText(number);

        otpbind.Resend.setEnabled(false);
        otpbind.Resend.setClickable(false);
        otpbind.Resend.setTextColor(Color.GRAY);
        //timer:
        startTimer();
        //VERIFICATION OTP
        otpbind.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otpbind.pinView.getText().toString().trim().isEmpty()) {
                    otpbind.btnVerify.setVisibility(View.VISIBLE);
                    Toast.makeText(Otp_Verfication.this, "please fill the field", Toast.LENGTH_SHORT).show();
                } else {

                    if (verificationId != null) {
                        String code = otpbind.pinView.getText().toString().trim();
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                        otpbind.progressBarVerify.setVisibility(View.VISIBLE);
                        otpbind.btnVerify.setVisibility(View.INVISIBLE);
                        FirebaseAuth
                                .getInstance()
                                .signInWithCredential(credential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            otpbind.progressBarVerify.setVisibility(View.VISIBLE);
                                            otpbind.btnVerify.setVisibility(View.INVISIBLE);
                                            Toast.makeText(Otp_Verfication.this, "Welcome..."+code, Toast.LENGTH_SHORT).show();


                                        } else {
                                            otpbind.progressBarVerify.setVisibility(View.GONE);
                                            otpbind.btnVerify.setVisibility(View.VISIBLE);
                                            Toast.makeText(Otp_Verfication.this, "OTP is not Valid!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });
        //RESEND OTP
        otpbind.Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(Otp_Verfication.this, "WORKING ON RESEND", Toast.LENGTH_SHORT).show();
                resendVerificationCode();
                otpbind.Resend.setEnabled(false);
                otpbind.Resend.setClickable(false);
                otpbind.Resend.setTextColor(Color.GRAY);
                mCountDownTimer.start();

            }
        });


    }


    //TIMER
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {

            }
        };
        mCountDownTimer.start();
    }

    private void updateCountDownText() {
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d", seconds);
        if(timeLeftFormatted.equals("00"))
        {
            mCountDownTimer.cancel();
            otpbind.Resend.setEnabled(true);
            otpbind.Resend.setClickable(true);
            otpbind.Resend.setTextColor(Color.RED);
        }
        otpbind.countdown.setText(timeLeftFormatted);
    }
// RESEND VERIFICATION LOGIC
    public void resendVerificationCode()
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number,60, TimeUnit.SECONDS,Otp_Verfication.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                {


                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
                    {
                        Toast.makeText(Otp_Verfication.this, "OTP is successfully send.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(Otp_Verfication.this, "FAIL"+e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }
        );
    }


}