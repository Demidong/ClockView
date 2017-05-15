package com.dx.demi;

import android.graphics.Bitmap;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by demi on 2017/3/13.
 */

public interface UrlService {

    @GET("bd_logo1_31bdc765.png")
    Call<Bitmap> getImage();
}
