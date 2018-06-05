package com.xd.demi.factory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by demi on 2017/3/13.
 */

public class BitmapResponseConverter<T>  implements Converter<ResponseBody,T>{
    Type type ;
    public BitmapResponseConverter(Type type){
        this.type =type ;
    }
    @Override
    public T convert(ResponseBody value) throws IOException {
        InputStream inputStream = value.byteStream();
        Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
        return (T) bitmap;
    }
}
