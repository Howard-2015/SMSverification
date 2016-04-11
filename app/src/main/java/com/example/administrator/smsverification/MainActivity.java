package com.example.administrator.smsverification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class MainActivity extends AppCompatActivity {
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化sdk
        SMSSDK.initSDK(this, "1179786a85432", "ed130c3324d53f171c15785d2474b655");
        setContentView(R.layout.activity_main);

        mBtn= (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开SMSSDK的注册界面
                RegisterPage registerPage=new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler(){
                    @Override
                    public void afterEvent(int event, int result, Object data) {
                       //解析注册的结果
                        if (result==SMSSDK.RESULT_COMPLETE){
                            @SuppressWarnings("unchecked")
                            HashMap<String,Object> phoneMap=new HashMap<String, Object>();
                            String country= (String) phoneMap.get("country");
                            String phone= (String) phoneMap.get("phone");
                            //提交用户信息
                            submitUserInfo(country,phone);
                        }

                    }
                });
                //在mainactivity中显示
                registerPage.show(MainActivity.this);

            }
        });
    }

    private void submitUserInfo(String country, String phone) {
        Random random=new Random();

        String uid=Math.abs(random.nextInt())+"";
        String nickName="PHONE";
        SMSSDK.submitUserInfo(uid,nickName,null,country,phone);
    }
}
