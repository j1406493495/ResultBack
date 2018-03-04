package cn.com.woong.resultbackdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;

import cn.com.woong.resultbacklib.ResultBack;
import cn.com.woong.resultbacklib.ResultInfo;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {
    Button btnFailed;
    Button btnSuccess;

    ResultBack mResultBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        initView();
        initData();
    }

    private void initView() {
        btnFailed = findViewById(R.id.btn_failed);
        btnSuccess = findViewById(R.id.btn_success);
    }

    private void initData() {
        mResultBack = new ResultBack(this);

        RxView.clicks(btnFailed)
                .flatMap(new Function<Object, Observable<ResultInfo>>() {
                    @Override
                    public Observable<ResultInfo> apply(Object o) throws Exception {
                        return new ResultBack(MainActivity.this).startForResult(ResultFailedActivity.class);
                    }
                })
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

        RxView.clicks(btnSuccess)
                .map(new Function<Object, Intent>() {
                    @Override
                    public Intent apply(Object o) throws Exception {
                        Intent intent = new Intent(MainActivity.this, ResultSuccessActivity.class);
                        intent.putExtra("success", false);
                        return intent;
                    }
                })
                .flatMap(new Function<Intent, Observable<ResultInfo>>() {
                    @Override
                    public Observable<ResultInfo> apply(Intent intent) throws Exception {
                        return mResultBack.startForResult(intent);
                    }
                })
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
}
