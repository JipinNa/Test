package com.example.chioy.test;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.*;
import android.widget.Toast;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private InfoThread infoThread = null;   //新线程
    private Socket socket = null;           //socket
    private ArrayList<PersonInfo>  personInfos = new ArrayList<>();//接收的序列化数组
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DoubleAdapter mAdapter;
    private PersonInfo submitInfo = null;   //上传到服务器的信息
    int k = 1;
    int p = 1;
    /**
     * 主线程Handler
     */
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    init();
                    Log.d(TAG, "handleMessage: 456");
                    break;
                case 2:
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.requestLayout();
                    break;
            }
        }
    };

    /**
     * 主界面启动
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoThread = new InfoThread();
        infoThread.start();
    }

    /**
     * 主界面重启
     */
    @Override
    protected void onRestart() {
        if (getP() == 0){
            Log.d(TAG, "onResume: 0000000000000");
            setK(2);
            if (submitInfo != null){
                setP(3);
            }else {
                setP(5);
            }
        }
        Log.d(TAG, "onResume: "+getP());
        super.onRestart();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public  int getP() {
        return p;
    }

    public void setP(int p) {this.p = p;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    /**
     * 主界面退出当前界面
     */

    /**
     * 重启接收传来的结果
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null){
            Toast.makeText(this,"添加失败！",Toast.LENGTH_SHORT).show();
            return;
        }
        if(resultCode == 5){
            Toast.makeText(this,"添加成功！",Toast.LENGTH_SHORT).show();
            submitInfo = (PersonInfo) data.getSerializableExtra("submit");
            Log.d(TAG, "onActivityResult: "+submitInfo.getName());
        }
    }

    /**
     * 填充Menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    /**
     * 给Menu的Item设置点击
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(MainActivity.this, Patient_Info_Card.class);
                startActivityForResult(intent,6);
                break;
            case R.id.action_change:
                if (mAdapter.getTag() == 1) {
                    mAdapter.setTag(2);
                }else {
                    mAdapter.setTag(1);
                }
                mRecyclerView.requestLayout();
                Log.d(TAG, "onOptionsItemSelected: "+mAdapter.getTag());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化主界面
     */
    public void init(){

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        //设置布局管理器为2列，纵向
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new DoubleAdapter(this,personInfos);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new DoubleAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                PersonInfo changeInfo = personInfos.get(position);
                Intent intent = new Intent(MainActivity.this,Patient_Info_Card.class);
                intent.putExtra("update",changeInfo);
                startActivityForResult(intent,6);
            }
        });
        for (PersonInfo personInfo : personInfos){
            Log.d(TAG, "init: "+personInfo.getName());
        }
    }

    /**
     * 发送数据到服务端
     * @param outputStream
     * @param submitInfo
     */
    public void sendData(OutputStream outputStream, PersonInfo submitInfo){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(submitInfo);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 网络通信处理线程
     */
    class InfoThread extends Thread{
        OutputStream outputStream;
        InputStream inputStream;
        ObjectInputStream objectInputStream;
        Object objs = null;
        int k1,p1;
        @SuppressLint("HandlerLeak")
        public void run() {
            try {
                socket = new Socket("192.168.0.225", 3234);
                outputStream = socket.getOutputStream();
                inputStream = socket.getInputStream();
                objectInputStream = new ObjectInputStream(new BufferedInputStream(inputStream));
                sendData(outputStream, new PersonInfo(3));
                while (true){
                    p1 = getP();
                    k1 = getK();
                    if (p1 == 1){
                        objs = objectInputStream.readObject();
                        personInfos = (ArrayList<PersonInfo>) objs;
                        setP(0);
                    }
                        if (k1 == 1) {
                            Message msg1 = new Message();
                            msg1.what = 1;
                            mHandler.sendMessage(msg1);
                            setK(0);
                        }
                        switch (p1){
                        case 3:
                                sendData(outputStream,submitInfo);
                                sleep(1000);
                                if (submitInfo.getId() == 0){
                                    personInfos.add(submitInfo);
                                    submitInfo = null;
                                    ArrayList<PersonInfo> objs5 = (ArrayList<PersonInfo>) objectInputStream.readObject();
                                    int s = 0;
                                    for (PersonInfo personInfo : objs5) {
                                        personInfos.set(s,personInfo);
                                        s++;
                                        }
                                        setP(0);
                                    Message msg2 = new Message();
                                    msg2.what = 2;
                                    mHandler.sendMessage(msg2);
                                    }else {
                                    submitInfo = null;
                                    ArrayList<PersonInfo> objs5 = (ArrayList<PersonInfo>) objectInputStream.readObject();
                                    int s = 0;
                                    for (PersonInfo personInfo : objs5) {
                                        personInfos.set(s, personInfo);
                                        s++;
                                        Log.d(TAG, "run: " + personInfo.getAge());
                                    }
                                    setP(0);
                                    Message msg2 = new Message();
                                    msg2.what = 2;
                                    mHandler.sendMessage(msg2);
                                }
                                break;
                            case 5:
                                    sendData(outputStream, new PersonInfo(3));
                                    sleep(1000);
                                    personInfos = (ArrayList<PersonInfo>)objectInputStream.readObject();
                                    setP(0);
                                    break;
                                default:
                                    break;
                                }
                            }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
