#FileSelector

##Android平台下的文件选择器

* 使用Android Studio，所以要引用该库也需时使用Android Studio开发
* 支持Activity、DialogFragment、AlertDialog三种模式进行文件选择
* 若不需要自定义可直接依赖aar包，需自定义可导入Module：FileSelector自行修改源码
* 无其他依赖


###DialogFragment模式使用方法
```java
    FileSelectorDialog fileDialog = new FileSelectorDialog();
    //设置文件选择完成后的回调事件
    fileDialog.setOnSelectFinish(new FileSelectorDialog.OnSelectFinish() {
        @Override
        public void onSelectFinish(ArrayList<String> paths) {
           Toast.makeText(getApplicationContext(), paths.toString(), Toast.LENGTH_SHORT).show();
        }
    });
    //传递配置信息
    Bundle bundle = new Bundle();
    bundle.putSerializable(FileConfig.FILE_CONFIG, fileConfig);
    fileDialog.setArguments(bundle);
    FragmentTransaction ft = getFragmentManager().beginTransaction();
    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    //在需要打开对话框时调用show函数
    fileDialog.show(ft, "fileDialog");
```

###Activity模式使用方法
```java
    //生成一个Intent指向FileSelectorActivity
    Intent intent = new Intent(getApplicationContext(), FileSelectorActivity.class);
    //传递配置文件
    intent.putExtra(FileConfig.FILE_CONFIG, fileConfig);
    //启动
    startActivityForResult(intent, 0);
```

接收返回的数据
```java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //通过FileSelector.RESULT键去取值，是个ArrayList<String>类型的值
                ArrayList<String> list = data.getStringArrayListExtra(FileSelector.RESULT);
                Toast.makeText(getApplicationContext(), list.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
```

在使用FileSelectorActivity时还需要在你的项目的AndroidManifest文件中加入：
```xml
    <activity android:name="zhou.tools.fileselector.FileSelectorActivity"/>
```

###AlertDialog模式使用方法
```java
    //通过参数实例化
    FileSelectorAlertDialog fileSelectorAlertDialog=new FileSelectorAlertDialog(this,fileConfig);
    //在需要显示的时候调用show方法
    fileSelectorAlertDialog.show();
```

效果图：

![Dialog模式](http://git.oschina.net/uploads/images/2015/0523/165250_cf1aed62_141009.png "Dialog模式")

![Activity模式](http://git.oschina.net/uploads/images/2015/0523/165322_8abc52be_141009.png "Activity模式")

##具体操作方式还请查看Demo

_by zzhoujay_
