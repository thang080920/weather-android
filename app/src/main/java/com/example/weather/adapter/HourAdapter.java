package com.example.weather.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weather.R;
import com.example.weather.model.Weather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HourAdapter extends RecyclerView.Adapter {

  private Activity activity;
  private List<Weather> weatherList;

  public HourAdapter(Activity activity, List<Weather> weatherList) {
    this.activity = activity;
    this.weatherList = weatherList;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

    LayoutInflater inflater = activity.getLayoutInflater();
    View itemView = inflater.inflate(R.layout.item_hour,viewGroup,false);

    HourHolder holder = new HourHolder(itemView);

    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
    HourHolder vh = (HourHolder) viewHolder;
    Weather weather = weatherList.get(i);

    vh.tvTime.setText(convertTime(weather.getDateTime()));
    vh.tvTemp.setText(weather.getTemperature().getValue()+"");
    String url = "";
    if (weather.getWeatherIcon() <10) {
      url = "https://developer.accuweather.com/sites/default/files/0"+ weather.getWeatherIcon() +"-s.png";
    }
    else {
      url = "https://developer.accuweather.com/sites/default/files/"+ weather.getWeatherIcon() +"-s.png";
    }

    Glide.with(activity).load(url).into(vh.icon);
  }



  @Override
  public int getItemCount() {
    return weatherList.size();
  }


  public static class HourHolder extends RecyclerView.ViewHolder {

    private TextView tvTime;
    private ImageView icon;
    private  TextView tvTemp;

    public HourHolder(@NonNull View itemView) {
      super(itemView);

      tvTime = (TextView) itemView.findViewById(R.id.tvTime);
      icon = (ImageView) itemView.findViewById(R.id.icon);
      tvTemp = (TextView) itemView.findViewById(R.id.tvTemp);
    }

  }
    public String convertTime(String inputTime) {
      SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd 'T' HH:MM:ss");

      Date date = null;

      try {
        date = inFormat.parse(inputTime);
      } catch (ParseException e) {
        e.printStackTrace();
      }

      SimpleDateFormat outFormat = new SimpleDateFormat("ha");
      String goal = outFormat.format(date);

      return goal;
  }
}
