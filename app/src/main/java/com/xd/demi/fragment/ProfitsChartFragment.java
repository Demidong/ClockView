package com.xd.demi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xd.demi.R;
import com.xd.demi.UrlService;
import com.xd.demi.bean.DailyYeildsInfo;
import com.xd.demi.bean.Profits;
import com.xd.demi.bean.ResponseData;
import com.xd.demi.factory.ProfitsConverterFactory;
import com.xd.demi.view.ProfitsChartView;
import com.xd.demi.view.TabContainer;
import com.mic.etoast2.Toast;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by demi on 2017/5/16.
 */

public class ProfitsChartFragment extends Fragment {
    private TabContainer tab;
    private ProfitsChartView chart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profit_chart, container, false);
        tab =view.findViewById(R.id.tab);
        chart = view.findViewById(R.id.chart);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDatas();
    }


    public void getDatas() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fq-api.zixuan.com/stock-portfolios/")
                .addConverterFactory(new ProfitsConverterFactory())
                .client(okHttpClient)
                .build();
        UrlService service = retrofit.create(UrlService.class);
        Call<ResponseData<DailyYeildsInfo>> call = service.getDatas("520","LAST_ALL");
        call.enqueue(new Callback<ResponseData<DailyYeildsInfo>>() {
            @Override
            public void onResponse(Call<ResponseData<DailyYeildsInfo>> call, Response<ResponseData<DailyYeildsInfo>> response) {
                // JSONObject js =new JSONObject(json);
                if(response.body() == null){
                    chart.setData(createDatas());
                    Toast.makeText(getContext(),"服务器挂了！！！",2000).show();
                    return;
                }
                chart.setData(response.body().getInfo());
            }

            @Override
            public void onFailure(Call<ResponseData<DailyYeildsInfo>> call, Throwable t) {
                t.printStackTrace();
                chart.setData(createDatas());
            }
        });
    }

    private DailyYeildsInfo createDatas(){
        DailyYeildsInfo data = new DailyYeildsInfo();
        ArrayList<Profits> hs300 = new ArrayList<>();
        ArrayList<Profits> profits = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            Profits hs300d = new Profits(1529581311000L+i*100,Math.random() *30 +2.4);
            Profits profitsd = new Profits(1529581311000L+i*100,Math.random() *10 +3.2);
            hs300.add(hs300d);
            profits.add(profitsd);
        }
        data.setHS300(hs300);
        data.setPORTFOLIO(profits);
        return data;
    }
}
