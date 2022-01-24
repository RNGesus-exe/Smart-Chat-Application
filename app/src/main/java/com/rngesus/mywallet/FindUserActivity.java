package com.rngesus.mywallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rngesus.mywallet.User.UserListAdapter;
import com.rngesus.mywallet.User.UserObject;
import com.rngesus.mywallet.Utils.CountryToPhonePrefix;

import java.util.ArrayList;

public class FindUserActivity extends AppCompatActivity {

    private RecyclerView mUserList;
    private RecyclerView.Adapter mUserListAdapter;
    private RecyclerView.LayoutManager mUserListLayoutManager;

    ArrayList<UserObject> userList, contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);

        contactList= new ArrayList<>();
        userList= new ArrayList<>();

        initializeRecyclerView();
        getContactList();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getContactList(){
        String ISOPrefix = getCountryISO();
        @SuppressLint("Recycle") Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null,null);
        while(phones.moveToNext()){
            @SuppressLint("Range") String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            @SuppressLint("Range") String phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            phone = phone.replace(" ", "");
            phone = phone.replace("-", "");
            phone = phone.replace("(", "");
            phone = phone.replace(")", "");

            if(!String.valueOf(phone.charAt(0)).equals("+")){
                phone = ISOPrefix + phone;
            }

            UserObject mContact = new UserObject("",name,phone);
            contactList.add(mContact);
            getUserDetails(mContact);
        }
    }

    private void getUserDetails(UserObject mContact) {
        DatabaseReference mUserDB = FirebaseDatabase.getInstance().getReference().child("user");
        Query query = mUserDB.orderByChild("phone").equalTo(mContact.getPhone());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String phone = "",
                            name = "";
                    for(DataSnapshot childSnapShot : snapshot.getChildren()){
                        if(childSnapShot.child("phone").getValue() != null){
                            phone = childSnapShot.child("phone").getValue().toString();
                        }
                        if(childSnapShot.child("name").getValue() != null){
                            name = childSnapShot.child("name").getValue().toString();
                        }

                        UserObject mUser = new UserObject(childSnapShot.getKey(),name,phone);
                        if(name.equals(phone)){
                            for(UserObject mContactIterator : contactList){
                                if(mContactIterator.getPhone().equals(mUser.getPhone())){
                                    mUser.setName(mContactIterator.getName());
                                }
                            }
                        }

                        userList.add(mUser);
                        mUserListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String getCountryISO(){
        String iso = null;

        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        if(telephonyManager.getNetworkCountryIso() != null){
            if(!telephonyManager.getNetworkCountryIso().toString().equals("")){
                iso = telephonyManager.getNetworkCountryIso().toString();
            }
        }
        return CountryToPhonePrefix.getPhone(iso);
    }

    @SuppressLint("WrongConstant")
    private void initializeRecyclerView() {
        mUserList = findViewById(R.id.userList);
        mUserList.setNestedScrollingEnabled(false);
        mUserList.setHasFixedSize(false);
        mUserListLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL,false);
        mUserList.setLayoutManager(mUserListLayoutManager);
        mUserListAdapter = new UserListAdapter(userList);
        mUserList.setAdapter(mUserListAdapter);
    }
}