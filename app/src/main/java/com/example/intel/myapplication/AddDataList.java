package com.example.intel.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.intel.myapplication.DB.DBHandler;
import com.example.intel.myapplication.R;

import java.util.ArrayList;

public class AddDataList extends AppCompatActivity {

    RecyclerView mobilerecycler;

    EditText nameEdt, phoneEdt, emailEdt;

    ImageView plusBtn;
    MobileAdapter adapter;
    ArrayList<String> mobileList = new ArrayList<>();
    ArrayList<Product> mList = new ArrayList<>();
    Button addBtn;
    Product itemdata;

    DBHandler mDbHandler;
    String empId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data_list);
        intizlise();
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("empId")) {
                empId = intent.getStringExtra("empId");
            }
        }

        if (!empId.isEmpty()) {
            getEmpDetails();
        }

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchMobileDatatoRecycler();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (empId.isEmpty()) { // New
                    if (!Validate()) {
                        //passDataToPreviousClass(0);
                        //String id=mDbHandler.getEmployeeCount()==0?"1":String.valueOf(mDbHandler.getEmployeeCount());

                        String  id="1";
                        if (mDbHandler.getEmployeeCount() == 0) {
                            id = "1";
                        } else {
                            id=String.valueOf(mDbHandler.getEmployeeCount()+1);
                        }

                        mDbHandler.addEmployeeData(id,nameEdt.getText().toString(),emailEdt.getText().toString());
                        for(int i=0;i<mobileList.size();i++) {
                            mDbHandler.addMobileData(id,mobileList.get(i));
                        }

                        Toast.makeText(AddDataList.this,"Details Saved",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();

                    }
                } else { // Update
                    if (!Validate()) {

                        mDbHandler.updateEmployeeData(empId,nameEdt.getText().toString(),emailEdt.getText().toString());
                        mDbHandler.deleteMobileData(empId);
                        for(int i=0;i<mobileList.size();i++) {
                            mDbHandler.addMobileData(empId,mobileList.get(i));
                        }
                        Toast.makeText(AddDataList.this,"Details Updated",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();

                    }
                }

            }
        });

    }

    boolean Validate() {
        boolean failflag = false;

        if (nameEdt.getText().toString().trim().isEmpty()) {
            nameEdt.setError("Please Enter Name");
            failflag = true;
            return failflag;
        }
        if (emailEdt.getText().toString().trim().isEmpty()) {
            emailEdt.setError("please Enter email");
            failflag = true;
            return failflag;
        }
        if (mobileList.size()<1) {
            Toast.makeText(this,"Invaild Mobile Number",Toast.LENGTH_SHORT).show();
            failflag = true;
            return failflag;
        }
        return failflag;
    }

    void getEmpDetails() {

        Cursor cursor = mDbHandler.getEmployeeDetail(empId);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    nameEdt.setText(cursor.getString(1));
                    emailEdt.setText(cursor.getString(2));
                    mobileList=getMobileList(empId);
                } while (cursor.moveToNext());
            }
        }
        prepareData();
    }

    ArrayList<String> getMobileList(String id) {

        ArrayList<String> mobileList = new ArrayList<>();
        Cursor cursor = mDbHandler.getMobileData(id);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    mobileList.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            return mobileList;
        }
        return mobileList;
    }

    void passDataToPreviousClass(int type) {

        if (type == 0) {

            Product item = new Product();
            item.setName(nameEdt.getText().toString());
            item.setPhoneno(mobileList);
            item.setEmail(emailEdt.getText().toString());
            //mList.add(item);

            Intent intent = new Intent();
            intent.putExtra("sentdata", item);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            final Product item1 = new Product();
            item1.setName(nameEdt.getText().toString());
            item1.setPhoneno(mobileList);
            item1.setEmail(emailEdt.getText().toString());

            Intent intent1 = new Intent();
            intent1.putExtra("updatedata", item1);
            setResult(RESULT_OK, intent1);
            finish();


        }

    }

    void fetchMobileDatatoRecycler() {
        mobileList.add(phoneEdt.getText().toString());
        phoneEdt.setText("");
        prepareData();
    }

    void prepareData() {

        if (!mobileList.isEmpty()) {
            if (mobileList.size() > 0) {
                adapter = new MobileAdapter(AddDataList.this, mobileList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AddDataList.this, LinearLayoutManager.VERTICAL, false);
                mobilerecycler.setLayoutManager(mLayoutManager);
                mobilerecycler.setItemAnimator(new DefaultItemAnimator());
                mobilerecycler.addItemDecoration(new DividerItemDecoration(AddDataList.this, LinearLayoutManager.VERTICAL));
                mobilerecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }


    }

    void intizlise() {

        mDbHandler=new DBHandler(this);
        mobilerecycler = (RecyclerView) findViewById(R.id.mobilerecycelerview);
        nameEdt = (EditText) findViewById(R.id.name_edt);
        emailEdt = (EditText) findViewById(R.id.email_edt);
        phoneEdt = (EditText) findViewById(R.id.phone_edt);
        plusBtn = (ImageView) findViewById(R.id.addimage_btn);
        addBtn = (Button) findViewById(R.id.data_btn1);
    }
}
