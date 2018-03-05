# ResultBack-RxJava
仿照RxPermissions中封装Fragment发起请求的方式，对startActivityForResult和onActivityResult进行封装，即为今天我要介绍的项目[ResultCallBack](https://github.com/j1406493495/ResultBack)，

RxPermissions项目源码的解析可见我的个人网站：http://woong.com.cn/

其中master分支为非RxJava版本，rxjava分支为RxJava版本。

##### 非RxJava版本使用

添加依赖：

```
dependencies {
    compile "com.woong:ResultBack:1.0.0"
}
```

使用方式：

```
btnSuccess.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, ResultSuccessActivity.class);
        intent.putExtra("success", false);

        mResultBack.startForResult(intent, new ResultBack.Callback() {
            @Override
            public void onActivityResult(ResultInfo resultInfo) {
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
```

##### RxJava版本使用

添加依赖：

```
dependencies {
    compile "com.woong:ResultBack-RxJava:1.0.0"
}
```

使用方式：

```
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
```

##### Fragment请求源码分析

```
private Map<Integer, PublishSubject<ResultInfo>> mSubjects = new HashMap<>();

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
```

首先创建一个Map维护请求列表，每次startActivityForResult和onActivityResult时分别存入和移除subject。

第20～21行发送onNext和onComplete到请求下游。



---

本文由 [Woong](http://woong.com.cn/) 创作，采用 [知识共享署名4.0](https://creativecommons.org/licenses/by/4.0/) 国际许可协议进行许可

本站文章除注明转载/出处外，均为本站原创或翻译，转载前请务必署名

最后编辑时间为:2018-03-04 00:00:00