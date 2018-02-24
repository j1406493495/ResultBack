package cn.com.woong.resultbacklib;

import android.content.Intent;

/**
 * Created by woong on 2018/01/23.
 * @author woong
 */
public class ResultInfo {
    private int resultCode;
    private Intent data;

    public ResultInfo(int resultCode, Intent data) {
        this.resultCode = resultCode;
        this.data = data;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Intent getData() {
        return data;
    }

    public void setData(Intent data) {
        this.data = data;
    }
}
