package com.dx.demi;

import android.graphics.Bitmap;

import com.dx.demi.bean.DailyYeildsInfo;
import com.dx.demi.bean.ListInfo;
import com.dx.demi.bean.Profits;
import com.dx.demi.bean.ResponseData;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.R.attr.path;

/**
 * Created by demi on 2017/3/13.
 */

public interface UrlService {

    @GET("bd_logo1_31bdc765.png")
    Call<Bitmap> getImage();
    @GET("{id}/daily-yields?")
    Call<ResponseData<DailyYeildsInfo>> getDatas(@Path("id") String id , @Query("type") String type);
}
