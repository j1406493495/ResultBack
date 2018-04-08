package cn.com.woong.resultbacklib;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by woong on 2017/01/23.
 *
 * @author woong
 */
public class ResultBackFragment extends Fragment {
    private Map<Integer, ResultBack.Callback> mCallbacks = new HashMap<>();

    public ResultBackFragment() {
    }

    public void startForResult(Intent intent, ResultBack.Callback callback) {
        mCallbacks.put(callback.hashCode(), callback);
        startActivityForResult(intent, callback.hashCode());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //callback方式的处理
        ResultBack.Callback callback = mCallbacks.remove(requestCode);
        if (callback != null) {
            callback.onActivityResult(new ResultInfo(resultCode, data));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
