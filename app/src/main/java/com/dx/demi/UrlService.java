package com.dx.demi;

import android.graphics.Bitmap;

import com.dx.demi.bean.ListInfo;
import com.dx.demi.bean.Profits;
import com.dx.demi.bean.ResponseData;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static android.R.attr.path;

/**
 * Created by demi on 2017/3/13.
 */

public interface UrlService {

    @GET("bd_logo1_31bdc765.png")
    Call<Bitmap> getImage();
    @GET("{id}/settlements")
    Call<ResponseData<ListInfo<Profits>>> getDatas(@Path("id") String id);
}
