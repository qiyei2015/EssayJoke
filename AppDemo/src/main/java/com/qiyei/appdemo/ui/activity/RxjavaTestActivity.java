package com.qiyei.appdemo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qiyei.appdemo.R;
import com.qiyei.sdk.log.LogManager;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxjavaTestActivity extends AppCompatActivity {

    private static final String TAG = "RxjavaTestActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_test);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testCreate();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testMap();
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testZip();
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testConcat();
            }
        });

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testFlowable();
            }
        });

        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testConcatMap();
            }
        });

        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        testThreadChange();
                    }
                }).start();
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

        }).observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Observer<Integer>() {

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

        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) {

            }
        },BackpressureStrategy.DROP).subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });

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

        //测试FlatMap
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                ArrayList<String> list = new ArrayList<>();

                //将一个发射器变为5个发射器，并应用函数
                for (int i = 0 ;i < 5;i++){
                    list.add(integer + "_" + i);
                }
                return Observable.fromIterable(list).delay(3, TimeUnit.SECONDS);

            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogManager.i(TAG, "Consumer accept " + s);
            }
        });

        AndroidSchedulers.mainThread()
    }


    private void testZip(){
        //两个在不同的线程中
        Observable observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).subscribeOn(Schedulers.io());

        Observable observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                emitter.onNext("a");
                emitter.onNext("b");
                emitter.onNext("c");
                emitter.onNext("d");
                emitter.onNext("e");
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {

            @Override
            public String apply(Integer integer, String s) {

                return s + "_" + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String o) throws Exception {
                LogManager.i(TAG, "Consumer accept " + o);
            }
        });

    }

    private void testConcat(){

    }

    private void testFlowable(){

        //背压适合数据量比较大的场景
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) {
                //一直发送事件
                for (int i = 0 ;i< 100;i++){
                    LogManager.i(TAG, "FlowableOnSubscribe subscribe " + i);
                    emitter.onNext(i);
                }

            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    Subscription mSubscription;
                    @Override
                    public void onSubscribe(Subscription s) {
                        mSubscription = s;
                        //去掉下面这一行会抛异常
                        s.request(100);
                        LogManager.i(TAG, "1 Subscriber onSubscribe " + s);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogManager.i(TAG, "1 Subscriber onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        //取消发送，要不然的话，会卡死系统
                        mSubscription.cancel();
                        LogManager.i(TAG, "1 Subscriber onError " + t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        //异步订阅
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) {
                //一直发送事件
                for (int i = 0 ;i < 100;i++){
                    emitter.onNext(i);
                    LogManager.i(TAG, "FlowableOnSubscribe subscribe " + i + " " + Thread.currentThread());
                }

            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        //不加这一句代码，onNext不会执行
                        s.request(1000);
                        LogManager.i(TAG, "2 Subscriber onSubscribe " + s);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogManager.i(TAG, "2 Subscriber onNext " + integer + " " + Thread.currentThread());
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogManager.i(TAG, "2 Subscriber onError " + t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void testConcatMap(){

    }

    private void testThreadChange(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                emitter.onNext("111");
                emitter.onNext("222");
                emitter.onNext("333");
                //Schedulers.newThread()线程
                LogManager.i(TAG,"subscribe " + Thread.currentThread());
//                emitter.onComplete();
//                emitter.onComplete();
//                emitter.onNext("444");
                emitter.onError(new UnknownError("ahhh"));
                emitter.onComplete();
            }
        })
         .subscribeOn(Schedulers.newThread())
         .observeOn(AndroidSchedulers.mainThread())
         .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                //当前线程，指调用该方法线程
                LogManager.i(TAG,"onSubscribe " + Thread.currentThread());
            }

            @Override
            public void onNext(String s) {
                //主线程
                LogManager.i(TAG,"onNext " + s + Thread.currentThread());
            }

            @Override
            public void onError(Throwable e) {
                LogManager.i(TAG,"onError " + Thread.currentThread());
            }

            @Override
            public void onComplete() {
                LogManager.i(TAG,"onComplete " + Thread.currentThread());
            }
        });
    }
}
