package com.example.intel.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.intel.myapplication.DB.DBHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView alertTxt;
    RecyclerView mRecyclerView;

    FloatingActionButton addfloatingbtn;
    ArrayList<Product> mList = new ArrayList<>();

    Product item;

    ProductData mAdapter;

    int Requestcode = 20;
    int requestCodeForResult = 21;
    int selectPosition = 0;

    int reqForRefresh = 345;
    DBHandler mDbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intilize();

        addfloatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddDataList.class);
                startActivityForResult(intent, reqForRefresh);
            }
        });

        getEmployeeDetails();

    }

    void intilize() {

        mDbHandler = new DBHandler(this);
        alertTxt = (TextView) findViewById(R.id.data_txt);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyler_list);
        addfloatingbtn = (FloatingActionButton) findViewById(R.id.float_btn);

    }

    void getEmployeeDetails() {

        mList = new ArrayList<>();
        Cursor cursor = mDbHandler.getEmployeeData();

        if (cursor != null) {

            if (cursor.moveToFirst()) {
                do {

                    Product item = new Product();
                    item.setId(cursor.getString(0));
                    item.setName(cursor.getString(1));
                    item.setEmail(cursor.getString(2));
                    item.setPhoneno(getMobileList(item.getId()));
                    mList.add(item);

                } while (cursor.moveToNext());
            }

        }

        fetchDataToRecycelerView();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == reqForRefresh) {
                getEmployeeDetails();
            }
        }

    }

    void fetchDataToRecycelerView() {

        if (mList.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            alertTxt.setVisibility(View.GONE);

            mAdapter = new ProductData(MainActivity.this, mList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            mAdapter.setOnClickListener(new ProductData.OnClickListener() {
                @Override
                public void onLayoutClick(int position) {
                    selectPosition = position;
                    Intent intent = new Intent(MainActivity.this, AddDataList.class);
                    intent.putExtra("empId", mList.get(position).getId());
                    startActivityForResult(intent, reqForRefresh);
                }
            });
        } else {

            mRecyclerView.setVisibility(View.GONE);
            alertTxt.setVisibility(View.VISIBLE);
        }


    }
}
