package com.example.chioy.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Patient_Info_Card extends AppCompatActivity {
    private EditText edtId = null;
    private EditText edtName = null;
    private EditText edtSex = null;
    private EditText edtAge = null;
    private EditText edtHeight = null;
    private EditText edtWeight = null;
    private Button btnSubmit = null;
    private PersonInfo personInfo = null;
    private PersonInfo changeInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__info__card);
        changeInfo = (PersonInfo)getIntent().getSerializableExtra("update");
        init();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getText();
                //Intent intent = new Intent(Patient_Info_Card.this,MainActivity.class);
                Intent intent = new Intent();
                intent.putExtra("submit",personInfo);
                setResult(5,intent);
                finish();
            }
        });
    }
     public void init(){
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
         edtId = (EditText)findViewById(R.id.edtId2);
         edtName = (EditText)findViewById(R.id.edtName2);
         edtSex = (EditText)findViewById(R.id.edtSex2);
         edtAge = (EditText)findViewById(R.id.edtAge2);
         edtHeight = (EditText)findViewById(R.id.edtHeight2);
         edtWeight = (EditText)findViewById(R.id.edtWeight2);
         edtId.setEnabled(false);
         if(changeInfo != null) {
             edtId.setText(Integer.toString(changeInfo.getId()));
             edtName.setText(changeInfo.getName());
             edtSex.setText(changeInfo.getSex());
             edtAge.setText(changeInfo.getAge());
             edtHeight.setText(Double.toString(changeInfo.getHeight()));
             edtWeight.setText(Double.toString(changeInfo.getWeight()));
         }
     }
     public void getText(){
        if(edtId.getText().toString().trim().equals("")) {
            personInfo = new PersonInfo(0,edtName.getText().toString(),
                    edtSex.getText().toString(),
                    edtAge.getText().toString(),
                    Double.parseDouble(edtHeight.getText().toString()),
                    Double.parseDouble(edtWeight.getText().toString()),
                    1);

        }else {
            personInfo = new PersonInfo(Integer.parseInt(edtId.getText().toString()),
                    edtName.getText().toString(),
                    edtSex.getText().toString(),
                    edtAge.getText().toString(),
                    Double.parseDouble(edtHeight.getText().toString()),
                    Double.parseDouble(edtWeight.getText().toString()),
                    2);
        }
     }
}
