package com.dx.demi.factory;

import com.dx.demi.bean.ListInfo;
import com.dx.demi.bean.Profits;
import com.dx.demi.bean.ResponseData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by demi on 2017/3/13.
 */

public class ProfitsResponseConverter<T>  implements Converter<ResponseBody,T>{
    Type type ;
    public ProfitsResponseConverter(Type type){
        this.type =type ;
    }
    @Override
    public T convert(ResponseBody value) throws IOException {
        String js = value.string();
        Gson gs = new Gson();
        ResponseData<ListInfo<Profits>> info = gs.fromJson(js, new TypeToken<ResponseData<ListInfo<Profits>>>() {
        }.getType());

        return (T) info;
    }
}
