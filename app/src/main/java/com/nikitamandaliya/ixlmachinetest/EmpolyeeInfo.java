package com.nikitamandaliya.ixlmachinetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EmpolyeeInfo extends AppCompatActivity {
    EditText employee_no_et,employee_name_et,designation_et;
     Button employee_info_submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empolyee_info);

        employee_no_et = (EditText)findViewById(R.id.employee_no_et);
        employee_name_et = (EditText)findViewById(R.id.employee_name_et);
        designation_et = (EditText)findViewById(R.id.designation_et);

        employee_info_submit_btn = (Button)findViewById(R.id.employee_info_submit_btn);
        employee_info_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmployeeNo() | !validateEmployeeName() | !validateDesignation()) {
                    return;
                }
                Intent intent = new Intent(EmpolyeeInfo.this,BankInfo.class);
                startActivity(intent);
            }
        });

    }




    private boolean validateEmployeeNo()
    {
        String val = employee_no_et.getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if(val.isEmpty()){
            employee_no_et.setError("Enter Employee Number");
            return false;
        }else if(val.length()>20)
        {
            employee_no_et.setError("Employee no is too large");
            return false;
        }else if(val.matches(checkspaces)){
            employee_no_et.setError("No white spaces are allowed!");
            return false;
        }else
        {
            employee_no_et.setError(null);
            return true;
        }

    }

    private boolean validateEmployeeName()
    {
        String val = employee_name_et.getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if(val.isEmpty()){
            employee_name_et.setError("Enter Employee name");
            return false;
        }else if(val.length()>20)
        {
            employee_name_et.setError("Last name is too large");
            return false;
        }else if(val.matches(checkspaces)){
            employee_name_et.setError("No white spaces are allowed!");
            return false;
        }else
        {
            employee_name_et.setError(null);
            return true;
        }

    }


    private boolean validateDesignation()
    {
        String val = designation_et.getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if(val.isEmpty()){
            designation_et.setError("Enter Designation");
            return false;
        }else if(val.length()>20)
        {
            designation_et.setError("Designation is too large");
            return false;
        }else if(val.matches(checkspaces)){
            designation_et.setError("No white spaces are allowed!");
            return false;
        }else
        {
            designation_et.setError(null);
            return true;
        }

    }

}