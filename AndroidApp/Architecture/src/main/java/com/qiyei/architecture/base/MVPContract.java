package com.qiyei.architecture.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

public class MVPContract {

    public interface IView {

    }

    
    public interface IPresenter extends LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        void onCreate(@NonNull LifecycleOwner owner);

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        void onStart(@NonNull LifecycleOwner owner);

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        void onResume(@NonNull LifecycleOwner owner);


        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        void onPause(@NonNull LifecycleOwner owner);

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        void onStop(@NonNull LifecycleOwner owner);


        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        void onDestroy(@NonNull LifecycleOwner owner);

        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        void onLifeCycleChange(@NonNull LifecycleOwner owner);
    }

    public static class AbsPresenter implements IPresenter {

        private LifecycleOwner mLifecycleOwner;

        public AbsPresenter(@NonNull LifecycleOwner lifecycleOwner) {
            mLifecycleOwner = lifecycleOwner;
            if(this.mLifecycleOwner != null){
                this.mLifecycleOwner.getLifecycle().addObserver(this);
            }
        }

        @Override
        public void onCreate(@NonNull LifecycleOwner owner) {

        }

        @Override
        public void onStart(@NonNull LifecycleOwner owner) {

        }

        @Override
        public void onResume(@NonNull LifecycleOwner owner) {

        }

        @Override
        public void onPause(@NonNull LifecycleOwner owner) {

        }

        @Override
        public void onStop(@NonNull LifecycleOwner owner) {

        }

        @Override
        public void onDestroy(@NonNull LifecycleOwner owner) {
            if(this.mLifecycleOwner != null){
                this.mLifecycleOwner.getLifecycle().removeObserver(this);
            }
        }

        @Override
        public void onLifeCycleChange(@NonNull LifecycleOwner owner) {

        }
    }
}
