package com.app.nytimes.apis;

import android.support.annotation.NonNull;
import android.util.Log;

import com.app.nytimes.data.ArticleDataModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * This class will contain all the Observable
 * */

public class MyObservable {

    public static Observable<ArticleDataModel> getArticleObservable(String section){
        return RetrofitRestClient
                .getInstance()
                .getAPIServiceEndPoint()
                .listNews(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }




    public static DisposableObserver<Object> getObserver(final NetworkCallback callback){
        return new DisposableObserver<Object>() {

            Object object;

            @Override
            public void onNext(@NonNull Object object) {
                this.object = object;
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                if (callback != null){
                    callback.onFailure(e.getMessage());
                }else {
                    throw new RuntimeException("Observer callback is null");
                }
            }

            @Override
            public void onComplete() {
                Log.d("Ashraf","Completed");
                if (callback != null){
                    if(object != null){
                        callback.onSuccess(object);
                    }else {
                        callback.onFailure("Something went wrong...");
                    }
                }else {
                    throw new RuntimeException("Observer callback is null");
                }
            }
        };
    }

    public interface NetworkCallback{
        void onSuccess(Object response);
        void onFailure(String errorMessage);
    }

}
