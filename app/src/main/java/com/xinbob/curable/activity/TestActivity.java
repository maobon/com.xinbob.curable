package com.xinbob.curable.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xinbob.curable.R;
import com.xinbob.curable.utils.AlipayZeroSdk;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button btn = (Button) findViewById(R.id.btn_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 捐赠开发者
                // HTTPS://QR.ALIPAY.COM/FKX07429ZNZLGTGEEIBX6F

                if (AlipayZeroSdk.hasInstalledAlipayClient(TestActivity.this)) {
                    AlipayZeroSdk.startAlipayClient(TestActivity.this, "FKX07429ZNZLGTGEEIBX6F");
                } else {
                    Toast.makeText(TestActivity.this, "谢谢，您没有安装支付宝客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
