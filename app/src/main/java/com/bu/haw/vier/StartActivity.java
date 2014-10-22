package com.bu.haw.vier;

import com.bu.haw.vier.data.DataStorage;
import com.bu.haw.vier.data.ExStorageController;
import com.bu.haw.vier.data.dataObject;
import com.bu.haw.vier.util.SystemUiHider;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 * Todo delete content of folder util!
 */
public class StartActivity extends Activity implements SensorEventListener{

    private SensorManager sensorManager;
    private MediaPlayer mediaPlayer;
    private ArrayList beschleunigungsDaten;
    private boolean startRecording;

    private double ax,ay,az;   // these are the acceleration in x,y and z axis
    private double max,min;

    private Button dataStoreBtn;
    TextView textView;

    DataStorage dataStorage;
    ExStorageController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);

        initObjects();
        addListeners();
    }

    private void initObjects() {
        max = 0;
        min = 100;
        startRecording = false;
        beschleunigungsDaten = new ArrayList();
        textView = (TextView) findViewById(R.id.example);
        dataStoreBtn = (Button) findViewById(R.id.dummy_button);

        controller = new ExStorageController(this, false);
        dataStorage = new DataStorage(controller.getFile("save.txt"));

    }

    private void addListeners() {
        dataStoreBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(!startRecording) {
                    startRecording = true;
                    dataStoreBtn.setText("stop");
                } else {
                    startRecording = false;
                    dataStoreBtn.setText("start");
                    dataStorage.writeToFile(beschleunigungsDaten);
                }

            }
        });
    }


    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            ax=event.values[0];
            ay=event.values[1];
            az=event.values[2];
            textView.setText(String.format("x = %.2f \n" +
                    "y = %.2f\n" +
                    "z = %.2f\n" +
                    "a = %.2f\n" +
                    "min = %.2f\n" +
                    "max = %.2f",event.values[0],event.values[1],event.values[2],Math.sqrt(ax*ax+ay*ay+az*az),min,max));

            if(Math.sqrt(ax*ax+ay*ay+az*az)>max) max = Math.sqrt(ax*ax+ay*ay+az*az);
            if(Math.sqrt(ax*ax+ay*ay+az*az)<min) min = Math.sqrt(ax*ax+ay*ay+az*az);


            if(Math.sqrt(ax*ax+ay*ay+az*az)<2) {
                mediaPlayer = MediaPlayer.create(this, R.raw.wilhelmscream_64kb);
                mediaPlayer.start();
            }
            if(Math.sqrt(ax*ax+ay*ay+az*az)>20){
                mediaPlayer = MediaPlayer.create(this, R.raw.goofy_holler);
                mediaPlayer.start();
            }


            if(startRecording) beschleunigungsDaten.add(new dataObject(ax, ay, az));

        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
