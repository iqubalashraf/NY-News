package com.app.nytimes.apis;

import com.app.nytimes.data.ArticleDataModel;
import com.app.nytimes.utils.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * This is a wrapper class for Retrofit, Do write all API declaration here
 */

public class RetrofitRestClient {

    private static RetrofitRestClient instance;
    private Retrofit retrofit;
    private APIService apiService;

    private RetrofitRestClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        apiService = getAPIServiceEndPoint();

    }

    public static RetrofitRestClient getInstance() {
        if (instance == null) {
            instance = new RetrofitRestClient();
        }
        return instance;
    }

    public APIService getAPIServiceEndPoint() {
        if (apiService == null) {
            apiService = retrofit.create(APIService.class);
        }
        return apiService;
    }

    public interface APIService {

        @GET("svc/topstories/v2/{section}.json?api-key=eek2SSIFAR4ZY10em0egzcoWw5RVTGtQ")
        Observable<ArticleDataModel> listNews(@Path("section") String section);

    }
}
