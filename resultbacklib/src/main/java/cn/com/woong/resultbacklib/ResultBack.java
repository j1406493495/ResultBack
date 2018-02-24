package cn.com.woong.resultbacklib;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;

import io.reactivex.Observable;

/**
 * Created by woong on 2017/01/23.
 * @author woong
 */
public class ResultBack {
    private static final String TAG = "ResultBack";
    private ResultBackFragment mResultBackFragment;

    public ResultBack(Activity activity) {
        mResultBackFragment = getResultBackFragment(activity);
    }

    public ResultBack(Fragment fragment){
        this(fragment.getActivity());
    }

    private ResultBackFragment getResultBackFragment(Activity activity) {
        ResultBackFragment resultBackFragment = findResultBackFragment(activity);
        if (resultBackFragment == null) {
            resultBackFragment = new ResultBackFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(resultBackFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return resultBackFragment;
    }

    private ResultBackFragment findResultBackFragment(Activity activity) {
        return (ResultBackFragment) activity.getFragmentManager().findFragmentByTag(TAG);
    }

    public Observable<ResultInfo> startForResult(Intent intent) {
        return mResultBackFragment.startForResult(intent);
    }

    public Observable<ResultInfo> startForResult(Class<?> clazz) {
        Intent intent = new Intent(mResultBackFragment.getActivity(), clazz);
        return startForResult(intent);
    }
}
