package cn.com.woong.resultbacklib;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by woong on 2017/01/23.
 * @author woong
 */
public class ResultBackFragment extends Fragment {
    private Map<Integer, PublishSubject<ResultInfo>> mSubjects = new HashMap<>();

    public ResultBackFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public Observable<ResultInfo> startForResult(final Intent intent) {
        final PublishSubject<ResultInfo> subject = PublishSubject.create();
        return subject.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                mSubjects.put(subject.hashCode(), subject);
                startActivityForResult(intent, subject.hashCode());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //rxjava方式的处理
        PublishSubject<ResultInfo> subject = mSubjects.remove(requestCode);
        if (subject != null) {
            subject.onNext(new ResultInfo(resultCode, data));
            subject.onComplete();
        }
    }
}
