package com.nikitamandaliya.ixlmachinetest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class BankInfo extends AppCompatActivity {
    EditText bank_name_et,account_no_et,ifsc_code_et;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    Button bank_info_submit_btn;
    Button mCaptureBtn;
    ImageView mImageView;
    private static final int PERMISSION_CODE = 1000;
    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_info);

        bank_name_et = (EditText)findViewById(R.id.bank_name_et);
        account_no_et = (EditText)findViewById(R.id.account_no_et);
        ifsc_code_et = (EditText)findViewById(R.id.ifsc_code_et);
        bank_info_submit_btn = (Button)findViewById(R.id.bank_info_submit_btn) ;


        mImageView = (ImageView)findViewById(R.id.bank_image_view);
        mCaptureBtn = (Button)findViewById(R.id.capture_image_btn);

        //button click
        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //https://www.youtube.com/watch?v=Z8Cc3QTjrUA
                //if system os is >= mashmallow, request runtime permission
               Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               startActivityForResult(intent,0);
            }
        });

        bank_info_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validatebankName() | !validateAccountNo() | !validateIFSCcode() ) {
                    return;
                }
                    Intent intent = new Intent(BankInfo.this,MainActivity.class);
                    startActivity(intent);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        mImageView.setImageBitmap(bitmap);
    }

    private boolean validatebankName()
    {
        String val = bank_name_et.getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if(val.isEmpty()){
            bank_name_et.setError("Enter Bank Name");
            return false;
        }else if(val.length()>20)
        {
            bank_name_et.setError("Last name is too large");
            return false;
        }else if(val.matches(checkspaces)){
            bank_name_et.setError("No white spaces are allowed!");
            return false;
        }else
        {
            bank_name_et.setError(null);
            return true;
        }

    }


    private boolean validateAccountNo()
    {
        String val = account_no_et.getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if(val.isEmpty()){
            account_no_et.setError("Enter Account Number");
            return false;
        }else if(val.length()>12)
        {
            account_no_et.setError("Account Number is too large");
            return false;
        }
        else if(val.length()<12)
        {
            account_no_et.setError("Account Number is too short");
            return false;
        }
        else if(val.matches(checkspaces)){
            account_no_et.setError("No white spaces are allowed!");
            return false;
        }else
        {
            account_no_et.setError(null);
            return true;
        }

    }

    private boolean validateIFSCcode()
    {
        String val = ifsc_code_et.getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if(val.isEmpty()){
            ifsc_code_et.setError("Enter ifsc code");
            return false;
        }else if(val.length()>11)
        {
            ifsc_code_et.setError("ifsc code is too large");
            return false;
        }
        else if(val.length()<11)
        {
            ifsc_code_et.setError("ifsc code is too short");
            return false;
        }
        else if(val.matches(checkspaces)){
            ifsc_code_et.setError("No white spaces are allowed!");
            return false;
        }else
        {
            ifsc_code_et.setError(null);
            return true;
        }

    }


}