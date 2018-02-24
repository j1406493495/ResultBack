package cn.com.woong.resultbackdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.com.woong.resultbacklib.ResultBack;
import cn.com.woong.resultbacklib.ResultInfo;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    Button btnFailed;
    Button btnSuccess;

    ResultBack mResultBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        init();
    }

    private void init() {
        btnFailed = findViewById(R.id.btn_failed);
        btnSuccess = findViewById(R.id.btn_success);

        mResultBack = new ResultBack(this);

        btnFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultBack.startForResult(ResultFailedActivity.class)
                        .subscribe(new Consumer<ResultInfo>() {
                            @Override
                            public void accept(ResultInfo resultInfo) throws Exception {
                                int resultCode = resultInfo.getResultCode();
                                Intent data = resultInfo.getData();

                                if (resultCode == RESULT_CANCELED) {
                                    Toast.makeText(MainActivity.this, "Result failed back", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResultSuccessActivity.class);
                intent.putExtra("success", false);

                mResultBack.startForResult(intent)
                        .subscribe(new Consumer<ResultInfo>() {
                            @Override
                            public void accept(ResultInfo resultInfo) throws Exception {
                                int resultCode = resultInfo.getResultCode();
                                Intent data = resultInfo.getData();

                                if (resultCode == RESULT_OK && data != null) {
                                    boolean result= data.getBooleanExtra("result", false);
                                    Toast.makeText(MainActivity.this, "result == " + result, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
