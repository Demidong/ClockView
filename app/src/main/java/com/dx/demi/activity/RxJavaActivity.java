package com.dx.demi.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dx.demi.R;
import com.dx.demi.UrlService;
import com.dx.demi.bean.Student;
import com.dx.demi.factory.BitmapConverterFactory;
import com.dx.demi.utils.Platform;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;


/**
 * Created by demi on 2017/3/13.
 */

public class RxJavaActivity extends Activity implements View.OnClickListener{

static List<Student> stuList = new ArrayList<Student>(){
    {
        add(new Student("小明",15));
        add(new Student("小花",10));
        add(new Student("小红",5));
    }
};

    TextView text_content;
    TextView text_title;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_rx);
        text_content = (TextView) findViewById(R.id.text_content);
        text_title = (TextView) findViewById(R.id.text_title);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        getFIlesInSDk();
    }


    public void testRXJAVA(){

        // 创建被观察者对象
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello world");
                subscriber.onCompleted();
            }

        });

        //创建观察者对象
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Toast.makeText(RxJavaActivity.this,s,Toast.LENGTH_SHORT).show();
                text_content.setText(s);
            }
        };

        //被观察者要订阅观察者
        observable.map(new Func1<String,String>() {
            @Override
            public String call(String o) {
                return o+", the early bird cathes the worm !";
            }
        }).subscribe(observer);
    }

    public void testRXJAVAAdvance(){
        query("demi").flatMap(new Func1<Student, Observable<Student>>() {
            @Override
            public Observable<Student> call(Student student) {
                return getAllStudents(student);
            }
        }).subscribe(new Observer<Student>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Student student) {
                Log.i("RxJavaActivity", "onNext Value: "+student);
            }
        });
    }
    public Observable<Student> query(String name){
        return  Observable.just(new Student(name));
    }

    public Observable<Student> getAllStudents(Student student){
        return  Observable.from(stuList);
    }

    public void getFIlesInSDk(){
        String basePath = Environment.getExternalStorageDirectory().getPath();
        File rootFile = new File(basePath);
        Observable.just(rootFile).flatMap(new Func1<File, Observable<File>>() {
            @Override
            public Observable<File> call(File file) {
                return listFile(file);
            }
        }).filter(new Func1<File, Boolean>() {
            @Override
            public Boolean call(File file) {
                return file.getName().endsWith(".txt");
            }
        }).subscribe(new Observer<File>() {
            @Override
            public void onCompleted() {
                Log.i("RxJavaActivity", "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(File files) {
                Log.i("RxJavaActivity", "onNext: "+files.getName());
            }
        });
    }
    public Observable<File> listFile(File file){
        if(file.isDirectory()){
          return  Observable.from(file.listFiles()).flatMap(new Func1<File, Observable<File>>() {
                @Override
                public Observable<File> call(File file) {
                    return listFile(file);
                }
            });
        }else{
            return Observable.just(file);
        }
    }
}
