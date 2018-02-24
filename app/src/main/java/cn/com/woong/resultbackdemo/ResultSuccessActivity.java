package cn.com.woong.resultbackdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by wong on 2018/2/24.
 */

public class ResultSuccessActivity extends AppCompatActivity {
    Button btnResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_result_sucess);
        init();
    }

    private void init() {
        btnResult = findViewById(R.id.btn_result);

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result", true);
                setResult(RESULT_OK, intent);
                Toast.makeText(ResultSuccessActivity.this, "setResult result is true", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
