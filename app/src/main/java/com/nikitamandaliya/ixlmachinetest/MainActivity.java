package com.nikitamandaliya.ixlmachinetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

//https://www.geeksforgeeks.org/floating-action-button-fab-in-android-with-example/
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    // Make sure to use the FloatingActionButton
    // for all the FABs
    ListView listView;
    LayoutInflater inflater;
    FloatingActionButton mAddFab;
    View dialogView;
    MyDatabaseHelper helper;
    TextView Tv_No, Tv_Name, Tv_phone_no, Tv_gender, Tv_date, Tv_empl_no, Tv_emply_name,Tv_designation, Tv_acc_type, Tv_work_exp, Tv_bank_name ,bank_branch_name, Tv_Acc_no, Tv_Ifsc_code,img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Register all the FABs with their IDs
        // This FAB button is the Parent
        mAddFab = findViewById(R.id.add_fab);
        // FAB button

        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PersonalInfo.class);
                startActivity(intent);
            }
        });

        helper = new MyDatabaseHelper(this);
        listView = (ListView)findViewById(R.id.list_data);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setListView(){
        Cursor cursor = helper.allData();
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this, cursor, 1);
        listView.setAdapter(customCursorAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setListView();
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView getId = (TextView)view.findViewById(R.id.listID);
        final long id = Long.parseLong(getId.getText().toString());
        final Cursor cur = helper.oneData(id);
        cur.moveToFirst();

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Pilih Opsi");

        //Add a list
        String[] options = {"Lihat Data", "Edit Data", "Hapus Data"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        final AlertDialog.Builder viewData = new AlertDialog.Builder(MainActivity.this);
                        inflater = getLayoutInflater();
                        dialogView = inflater.inflate(R.layout.view_data, null);
                        viewData.setView(dialogView);
                        viewData.setTitle("Lihat Data");

                        Tv_No = (TextView)dialogView.findViewById(R.id.tv_No);
                        Tv_Name = (TextView)dialogView.findViewById(R.id.tv_Name);
                        Tv_phone_no = (TextView)dialogView.findViewById(R.id.tv_TempatLahir);
                        Tv_gender = (TextView)dialogView.findViewById(R.id.tv_Tanggal);
                        Tv_date = (TextView)dialogView.findViewById(R.id.tv_JK);
                        Tv_empl_no = (TextView)dialogView.findViewById(R.id.tv_Alamat);

                        Tv_No.setText("Nomor: " + cur.getString(cur.getColumnIndex(MyDatabaseHelper.row_nomor)));
                        Tv_Name.setText("Nama: " + cur.getString(cur.getColumnIndex(MyDatabaseHelper.row_nama)));
                        Tv_phone_no.setText("Tempat Lahir: " + cur.getString(cur.getColumnIndex(MyDatabaseHelper.row_tempatLahir)));
                        Tv_gender.setText("Jenis Kelamin: " + cur.getString(cur.getColumnIndex(MyDatabaseHelper.row_jk)));
                        Tv_date.setText("Tanggal Lahir: " + cur.getString(cur.getColumnIndex(MyDatabaseHelper.row_tglLahir)));
                        Tv_empl_no.setText("Alamat: " + cur.getString(cur.getColumnIndex(MyDatabaseHelper.row_alamat)));

                        viewData.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        viewData.show();
                }
                switch (which){
                    case 1:
                        Intent iddata = new Intent(MainActivity.this, EditActivity.class);
                      //  iddata.putExtra(MyDatabaseHelper.row_id, id);
                        startActivity(iddata);
                }
                switch (which){
                    case 2:
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        builder1.setMessage("Data ini akan dihapus.");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                helper.deleteData(id);
                                Toast.makeText(MainActivity.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                                setListView();
                            }
                        });
                        builder1.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = builder1.create();
                        alertDialog.show();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}