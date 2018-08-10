package fr.coni.myapplication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity";

    private TextView AccX, AccY, AccZ, GyroX, GyroY, GyroZ;
    private float[] sensors = new float[6]; // 24 bytes

    private SensorManager sensorManager;
    Sensor accelerometer;
    Sensor gyroscope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate : Initializing Sensor Services");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        Log.d(TAG, "onCreate : Register sensors listener");
        sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(MainActivity.this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);

        Log.d(TAG, "onCreate : Create Link to TextViews");
        this.AccX = (TextView) findViewById(R.id.AccX);
        this.AccY = (TextView) findViewById(R.id.AccY);
        this.AccZ = (TextView) findViewById(R.id.AccZ);
        this.GyroX = (TextView) findViewById(R.id.GyroX);
        this.GyroY = (TextView) findViewById(R.id.GyroY);
        this.GyroZ = (TextView) findViewById(R.id.GyroZ);

        Log.d(TAG, "onCreate : Check the Bluetooth");
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null){
            Toast.makeText(MainActivity.this, R.string.NoBT, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, R.string.OkBT, Toast.LENGTH_SHORT).show();
            tryToLogOnComputer(bluetoothAdapter);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //Log.d(TAG, "onSensorChanged : X: " + sensorEvent.values[0] + " Y: " + sensorEvent.values[1] + " Z: " + sensorEvent.values[2]);

        //Log.d(TAG, "onSensorChanged : Display values");
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            this.AccX.setText(String.valueOf(sensorEvent.values[0]));
            this.AccY.setText(String.valueOf(sensorEvent.values[1]));
            this.AccZ.setText(String.valueOf(sensorEvent.values[2]));
            sensors[0] = sensorEvent.values[0];
            sensors[1] = sensorEvent.values[1];
            sensors[2] = sensorEvent.values[2];
        } else {
            this.GyroX.setText(String.valueOf(sensorEvent.values[0]));
            this.GyroY.setText(String.valueOf(sensorEvent.values[1]));
            this.GyroZ.setText(String.valueOf(sensorEvent.values[2]));
            sensors[3] = sensorEvent.values[0];
            sensors[4] = sensorEvent.values[1];
            sensors[5] = sensorEvent.values[2];
        }
    }

    public void tryToLogOnComputer(BluetoothAdapter bluetoothAdapter){
        Set<BluetoothDevice> devices;

        devices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice blueDevice : devices) {
            //Toast.makeText(MainActivity.this, "Device = " + blueDevice.getName(), Toast.LENGTH_SHORT).show();
            if (blueDevice.getName().equals("DESKTOP-H1R82CL")){
                Thread connector = new ConnectThread(blueDevice);
                connector.start();
                break;
            }
        }
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            UUID MY_UUID = UUID.fromString("DEADBEEF-0000-0000-0000-000000000000");
            mmDevice = device;
            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) { }
            mmSocket = tmp;
        }

        public void run() {

            try {
                mmSocket.connect();
                Thread sender = new Sender(mmSocket);
                       sender.start();
            } catch (IOException connectException) {
                try {
                    mmSocket.close();
                } catch (IOException closeException) {}
                //return;
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }

    }

    private class Sender extends Thread {

        private final BluetoothSocket mmSocket;
        private OutputStream outputStream;

        public Sender(BluetoothSocket device) {
            mmSocket = device;
            try {
                outputStream = device.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            while (true){
                byte[] sensorsAsByte = FloatArray2ByteArray(sensors);
                try {
                    outputStream.write(sensorsAsByte);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void cancel() {

        }

        public byte[] FloatArray2ByteArray(float[] values){
            ByteBuffer buffer = ByteBuffer.allocate(4 * values.length);

            for (float value : values){
                buffer.putFloat(value);
            }

            return buffer.array();
        }
    }

}
