package cn.com.woong.resultbacklib;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;


/**
 * Created by woong on 2017/01/23.
 *
 * @author woong
 */
public class ResultBack {
    private static final String TAG = "ResultBack";
    private ResultBackFragment mResultBackFragment;

    public ResultBack(Fragment fragment) {
        this(fragment.getActivity());
    }

    public ResultBack(Activity activity) {
        mResultBackFragment = getResultBackFragment(activity);
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

    public void startForResult(Class<?> clazz, Callback callback) {
        Intent intent = new Intent(mResultBackFragment.getActivity(), clazz);
        startForResult(intent, callback);
    }

    public void startForResult(Intent intent, Callback callback) {
        mResultBackFragment.startForResult(intent, callback);
    }

    public interface Callback {
        void onActivityResult(ResultInfo resultInfo);
    }
}
