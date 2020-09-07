package com.example.weather.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.adapter.HourAdapter;
import com.example.weather.model.Weather;
import com.example.weather.network.ApiManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

  private RecyclerView rvHour;
  private TextView tvTemp;
  private TextView tvStatus;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    tvTemp = (TextView) findViewById(R.id.tvTemp);
    tvStatus = (TextView) findViewById(R.id.tvStatus);

    getHours();

    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);


    rvHour = (RecyclerView) findViewById(R.id.rvHour);
    rvHour.setLayoutManager(layoutManager);
  }

  private void getHours() {
    Retrofit retrofit = new  Retrofit.Builder()
        .baseUrl(ApiManager.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    ApiManager service = retrofit.create(ApiManager.class);

    service.getHour().enqueue(new Callback<List<Weather>>() {
      @Override
      public void onResponse(Call<List<Weather>> call, Response<List<Weather>> response) {
        if (response.body() == null) return;

        List<Weather> weatherList = response.body();
        HourAdapter adapter = new HourAdapter(MainActivity.this, weatherList);
        rvHour.setAdapter(adapter);

        Weather weather = weatherList.get(0);
        tvTemp.setText(weather.getTemperature().getValue().intValue()+"");
        tvStatus.setText(weather.getIconPhrase());
      }

      @Override
      public void onFailure(Call<List<Weather>> call, Throwable t) {

      }
    });
  }
}
