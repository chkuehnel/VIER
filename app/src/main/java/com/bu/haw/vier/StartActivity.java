package com.bu.haw.vier;

import com.bu.haw.vier.data.DataStorage;
import com.bu.haw.vier.data.ExStorageController;
import com.bu.haw.vier.data.dataObject;
import com.bu.haw.vier.util.SystemUiHider;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
public class StartActivity extends Activity implements SensorEventListener,
        AdministrationFragment.AccountChoseListener{

    private SensorManager sensorManager;
    private MediaPlayer mediaPlayer;
    private ArrayList beschleunigungsDaten;
    private boolean startRecording;
    private AdministrationFragment fragment;
    private String fileName;

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

        fragment = new AdministrationFragment(this);
        startFragment(R.id.fragment_administration_container, fragment, AdministrationFragment.class.getName());
    }

    private void startFragment(int resource, Fragment fragment, String TAG) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(resource, fragment, TAG);
        fragmentTransaction.commit();
    }

    private void initObjects() {
        max = 0;
        min = 100;
        startRecording = false;
        beschleunigungsDaten = new ArrayList();
        dataStoreBtn = (Button) findViewById(R.id.dummy_button);

        controller = new ExStorageController(this, false);

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
                    if (dataStorage.isFileValid()) {
                        dataStorage.writeToFile(beschleunigungsDaten);
                    }
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

    @Override
    public void accountChanged(String accountName) {
        this.fileName = accountName;
        dataStorage = new DataStorage(controller.getFile(accountName));
        // TODO: close Keyboard here
        getFragmentManager().beginTransaction().
                remove(getFragmentManager().findFragmentByTag(AdministrationFragment.class.getName())).commit();
    }
}
