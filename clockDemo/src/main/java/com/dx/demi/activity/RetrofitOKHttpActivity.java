package com.dx.demi.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dx.demi.R;
import com.dx.demi.UrlService;
import com.dx.demi.factory.BitmapConverterFactory;
import com.dx.demi.utils.Platform;
import com.dx.demi.view.NineSquareView;


import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by demi on 2017/3/13.
 */

public class RetrofitOKHttpActivity extends Activity implements View.OnClickListener {
    ImageView image;
    Button button;
    private Platform mPlatform;
    NineSquareView nineSquareView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_retrofit);
//        image = (ImageView) findViewById(R.id.image);
//        button = (Button) findViewById(R.id.button);
        nineSquareView = (NineSquareView) findViewById(R.id.nsv);
//        button.setOnClickListener(this);
        mPlatform = Platform.get();

    }

    @Override
    public void onClick(View v) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("bd_logo1_31bdc765.png")
//                .build();
//       Call call= okHttpClient.newCall(request);
//        call.enqueue(new Callback()
//        {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
//                mPlatform.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        InputStream inputStream = response.body().byteStream();
//                        Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
//                        image.setImageBitmap(bitmap);
//                    }
//                });
//
//            }
//
//        });
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/")
                .addConverterFactory(BitmapConverterFactory.create())
                .client(okHttpClient)
                .build();
        UrlService service = retrofit.create(UrlService.class);
        Call<Bitmap> call = service.getImage();
        call.enqueue(new retrofit2.Callback<Bitmap>() {
            @Override
            public void onResponse(Call<Bitmap> call, final retrofit2.Response<Bitmap> response) {
                mPlatform.execute(new Runnable() {
                    @Override
                    public void run() {
//                        InputStream inputStream = response.body().byteStream();
//                        Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                        image.setImageBitmap(response.body());
                    }
                });
            }

            @Override
            public void onFailure(Call<Bitmap> call, Throwable t) {

            }
        });
    }
}
