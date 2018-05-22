package com.example.chioy.test;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class TableActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private TableRow row;
    DisplayMetrics dm = new DisplayMetrics();
    ArrayList<PersonInfo> personInfos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        tableLayout = (TableLayout)findViewById(R.id.tableLayout);
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        View v = new View(this);
        personInfos = (ArrayList<PersonInfo>)getIntent().getSerializableExtra("tableInfo");
        for (PersonInfo personInfo : personInfos) {
            row = new TableRow(this);
            row.setMinimumWidth(dm.widthPixels);
            setRow(row,personInfo);
            tableLayout.addView(row);
        }
    }

    public void setRow(TableRow row,PersonInfo personInfo) {
        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        TextView tv3 = new TextView(this);
        TextView tv4 = new TextView(this);
        TextView tv5 = new TextView(this);
        TextView tv6 = new TextView(this);
        tv1.setTextColor(Color.BLACK);
        tv1.setWidth(dm.widthPixels/6);
        tv1.setHeight(30);
        tv1.setText("    "+Integer.toString(personInfo.getId()));
        tv2.setTextColor(Color.BLACK);
        tv2.setWidth(dm.widthPixels/6);
        tv2.setHeight(30);
        tv2.setText(personInfo.getName());
        tv3.setTextColor(Color.BLACK);
        tv3.setWidth(dm.widthPixels/6);
        tv3.setHeight(30);
        tv3.setText(personInfo.getSex());
        tv4.setTextColor(Color.BLACK);
        tv4.setWidth(dm.widthPixels/6);
        tv4.setHeight(30);
        tv4.setText(personInfo.getAge());
        tv5.setTextColor(Color.BLACK);
        tv5.setWidth(dm.widthPixels/6);
        tv5.setHeight(30);
        tv5.setText(Double.toString(personInfo.getHeight()));
        tv6.setTextColor(Color.BLACK);
        tv6.setWidth(dm.widthPixels/6);
        tv6.setHeight(30);
        tv6.setText(Double.toString(personInfo.getWeight()));
        row.addView(tv1);
        row.addView(tv2);
        row.addView(tv3);
        row.addView(tv4);
        row.addView(tv5);
        row.addView(tv6);
    }
}
