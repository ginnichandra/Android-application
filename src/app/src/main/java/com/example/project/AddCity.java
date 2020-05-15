package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddCity extends AppCompatActivity {

    EditText cityName;
    EditText lon;
    EditText lat;
    Button add;
    CityDataSource cityDataSource;
    City city;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

            cityName = findViewById(R.id.city);
            lon = findViewById(R.id.longitude);
            lat = findViewById(R.id.latitude);
            add = findViewById(R.id.add);
            cityDataSource = new CityDataSource(this);
            if (!cityDataSource.isOpen()) cityDataSource.open();
                city = new City();
        }

        @Override
        protected void onResume() {
            super.onResume();
            if (!cityDataSource.isOpen()) cityDataSource.open();
        }

        @Override
        protected void onPause() {
            super.onPause();
        }

        public void add(View view)
        {
            if(cityName.getText().toString().equals(""))
            {
                Toast toast=Toast. makeText(getApplicationContext(),"Enter City Name",Toast. LENGTH_SHORT);
                toast.show();
            }
            else if(lon.getText().toString().equals(""))
            {
                Toast toast=Toast. makeText(getApplicationContext(),"Enter Longitude",Toast. LENGTH_SHORT);
                toast.show();
            }
            else if(lat.getText().toString().equals(""))
            {
                Toast toast=Toast. makeText(getApplicationContext(),"Enter Latitude",Toast. LENGTH_SHORT);
                toast.show();
            }
            else {
                city.setCity(cityName.getText().toString());
                city.setLongitude(Float.parseFloat(lon.getText().toString()));
                city.setLatitude(Float.parseFloat(lat.getText().toString()));
                city.setId(cityDataSource.getCityCount() + 1);
                cityDataSource.addCity(city);
                Intent intent = new Intent(getApplicationContext(), CityList.class);
                startActivity(intent);
            }
        }
    }