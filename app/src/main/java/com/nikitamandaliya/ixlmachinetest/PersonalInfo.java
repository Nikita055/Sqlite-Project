package com.nikitamandaliya.ixlmachinetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class PersonalInfo extends AppCompatActivity {
    EditText firstname_et,lastname_et,phone_et;
    RadioGroup personal_info_radio_group;
    DatePicker datePicker1;
    Button  submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        firstname_et = (EditText)findViewById(R.id.firstname_et);
        lastname_et = (EditText)findViewById(R.id.lastname_et);
        phone_et = (EditText)findViewById(R.id.phone_et);
        submit_btn = (Button)findViewById(R.id.personal_info_submit_btn);
        personal_info_radio_group = (RadioGroup)findViewById(R.id.personal_info_radio_group);
        datePicker1 = (DatePicker)findViewById(R.id.datePicker1);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validatefirstName() | !validateLastName() | !validatePhoneNumber() | !validateGender() | !validateAge()) {
                    return;
                }

                MyDatabaseHelper myDB= new MyDatabaseHelper(PersonalInfo.this);
                 myDB.addPersonalInfoData(firstname_et.getText().toString().trim(),
                         lastname_et.getText().toString().trim(),
                         phone_et.getText().toString().trim(),
                         personal_info_radio_group.getCheckedRadioButtonId()
                         );
                Intent intent = new Intent(PersonalInfo.this,EmpolyeeInfo.class);
                startActivity(intent);
            }
        });

    }

    private boolean validatefirstName()
    {
        String val = firstname_et.getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if(val.isEmpty()){
            firstname_et.setError("Enter First Name");
            return false;
        }else if(val.length()>20)
        {
            firstname_et.setError("Last name is too large");
            return false;
        }else if(val.matches(checkspaces)){
            firstname_et.setError("No white spaces are allowed!");
            return false;
        }else
        {
            firstname_et.setError(null);
            return true;
        }

    }

    private boolean validateLastName()
    {
        String val = lastname_et.getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if(val.isEmpty()){
            lastname_et.setError("Enter Last Name");
            return false;
        }else if(val.length()>20)
        {
            lastname_et.setError("Last name is too large");
            return false;
        }else if(val.matches(checkspaces)){
            lastname_et.setError("No white spaces are allowed!");
            return false;
        }else
        {
            lastname_et.setError(null);
            return true;
        }

    }


    private boolean validatePhoneNumber() {
        String val = phone_et.getText().toString().trim();

        if (val.isEmpty()) {
            phone_et.setError("Enter valid phone number");
            return false;
        }else if(val.length()>10)
        {
            phone_et.setError("phone number is too large");
            return false;
        }
        else if(val.length()<10)
        {
            phone_et.setError("phone number is too short");
            return false;
        }
        else {
            phone_et.setError(null);
            return true;
        }
    }

    private boolean validateGender() {
        if (personal_info_radio_group.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker1.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 14) {
            Toast.makeText(this, "You are not eligible to apply", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }
}