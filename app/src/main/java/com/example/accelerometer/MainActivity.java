package com.example.accelerometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Button bttStart = null;
    Button bttStop = null;

    TextView tvX= null, tvY = null , tvZ = null;

    ArrayList<Float[]> myAccValues = null;


    SensorManager sensorManager=null;
    Sensor accelerometer = null;
    SensorEventListener sel = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bttStart = (Button) findViewById(R.id.bttStart);
        bttStop = (Button) findViewById((R.id.bttStop));

        myAccValues = new ArrayList<Float[]>();

        tvX = (TextView) findViewById(R.id.tvX);
        tvY= (TextView) findViewById(R.id.tvY);
        tvZ = (TextView) findViewById(R.id.tvZ);  //Simply initialized the two buttons and the text views


        sel = this;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); //initialized sensor manager

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null) { //in the if is checked if the phone has an accelerometer
            accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);  //in this line, after having verified that the accelerometer is available, the app gets the signal.
        }


        bttStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorManager.registerListener(sel, accelerometer,SensorManager.SENSOR_DELAY_NORMAL); //When new data is available, it is notified on onSensorChanged (delay normal is the frequency the device checks if data changed)
            }
        });
        bttStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorManager.unregisterListener(sel);
            }
        });
    }

    @Override //if there is some new data on the accelerometer, this function is called
    public void onSensorChanged(SensorEvent event) {
        float x,y,z;
        x=event.values[0];
        y=event.values[1];
        z=event.values[2];  //Takes data from the array "event" and passes it to variables.

        myAccValues.add(new Float[] {x,y,z});

        tvX.setText("X: "+ x);
        tvY.setText("Y: "+ y);
        tvZ.setText("Z "+ z);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
