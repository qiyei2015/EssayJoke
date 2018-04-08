package com.qiyei.appdemo.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qiyei.appdemo.R;
import com.qiyei.sdk.log.LogManager;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class RxjavaTestActivity extends AppCompatActivity {

    private static final String TAG = "RxjavaTestActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_test);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testCreate();
            }
        });

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testMap();
            }
        });

        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testZip();
            }
        });

        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testConcat();
            }
        });

        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testFlatMap();
            }
        });

        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testConcatMap();
            }
        });

    }

    private void testCreate(){

        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                LogManager.i(TAG, "ObservableEmitter onNext" + 1);
                emitter.onNext(1);

                LogManager.i(TAG, "ObservableEmitter onNext" + 2);
                emitter.onNext(2);

                LogManager.i(TAG, "ObservableEmitter onNext" + 3);
                emitter.onNext(3);


                LogManager.i(TAG, "ObservableEmitter onNext" + 4);
                emitter.onNext(4);

                //发射错误 必须在onComplete之前调用，并且observer不能截断。否则接受者不接受，就会抛出异常
                LogManager.i(TAG, "ObservableEmitter onError");
                emitter.onError(new Exception("hello error !"));

                //发生完成
                LogManager.i(TAG, "ObservableEmitter onComplete");
                emitter.onComplete();
            }

        }).subscribe(new Observer<Integer>() {

            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                LogManager.i(TAG, "Observer onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
//                if (integer == 2){
//                    disposable.dispose();
//                }
                LogManager.i(TAG, "Observer onNext disposable.isDisposed():" + disposable.isDisposed());
                LogManager.i(TAG, "Observer onNext" + integer);
            }

            @Override
            public void onError(Throwable e) {
                LogManager.i(TAG, "Observer onError " + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogManager.i(TAG, "Observer onComplete");
            }
        });

    }


    private void testMap(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                LogManager.i(TAG, "ObservableEmitter onNext" + 1);
                emitter.onNext(1);

                LogManager.i(TAG, "ObservableEmitter onNext" + 2);
                emitter.onNext(2);

                LogManager.i(TAG, "ObservableEmitter onNext" + 3);
                emitter.onNext(3);


                LogManager.i(TAG, "ObservableEmitter onNext" + 4);
                emitter.onNext(4);
            }
        }).map(new Function<Integer, String>() {


            @Override
            public String apply(Integer integer) throws Exception {
                LogManager.i(TAG, "Function apply for:" + integer);
                return "apply " + integer;
            }
        }).subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                LogManager.i(TAG, "Observer onNext " + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }


    private void testZip(){

    }

    private void testConcat(){


    }

    private void testFlatMap(){

    }

    private void testConcatMap(){

    }
}
