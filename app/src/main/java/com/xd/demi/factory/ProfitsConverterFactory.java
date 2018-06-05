package com.xd.demi.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by demi on 2017/3/13.
 */

public class ProfitsConverterFactory extends Converter.Factory{

    public static ProfitsConverterFactory create() {
        return new ProfitsConverterFactory();
    }


    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new ProfitsResponseConverter<>(type);
    }


}
