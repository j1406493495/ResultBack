# ResultBack-RxJava
仿照RxPermissions中封装Fragment发起请求的方式，对startActivityForResult和onActivityResult进行封装，即为今天我要介绍的项目[ResultCallBack](https://github.com/j1406493495/ResultBack)，

RxPermissions项目源码的解析可见我的个人网站文章：

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

		new ResultBack(MainActivity.this).startForResult(intent, new ResultBack.Callback() {
			@Override
			public void onActivityResult(ResultInfo resultInfo) {
				int resultCode = resultInfo.getResultCode();
				Intent data = resultInfo.getData();

				if (resultCode == RESULT_OK && data != null) {
					boolean result= data.getBooleanExtra("result", false);
					Toast.makeText(MainActivity.this, "result == " + result, 		
						Toast.LENGTH_SHORT).show();
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
				return new ResultBack(MainActivity.this).startForResult(intent);
			}
		})
		.subscribe(new Consumer<ResultInfo>() {
			@Override
			public void accept(ResultInfo resultInfo) throws Exception {
				int resultCode = resultInfo.getResultCode();
				Intent data = resultInfo.getData();

				if (resultCode == RESULT_OK && data != null) {
					boolean result= data.getBooleanExtra("result", false);
					Toast.makeText(MainActivity.this, "result == " + result, 
						Toast.LENGTH_SHORT).show();
				}
			}
		});
```

