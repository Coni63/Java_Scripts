package fr.coni.BtController;

import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.microedition.io.StreamConnection;

public class Listener implements Runnable {

    private StreamConnection mConnection;
    private Main parent;
    
    public Listener(StreamConnection connection, Main parent_)
    {
        mConnection = connection;
        parent = parent_;
    }

    @Override
    public void run() {
        try {
            // prepare to receive data
            InputStream inputStream = mConnection.openInputStream();

            System.out.println("waiting for input");

            while (true) {
            	byte[] b = new byte[24];
                inputStream.read(b);
                float[] result = ByteArray2FloatArray(b);
                parent.setSensors(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public float[] ByteArray2FloatArray(byte[] buffer){
    	float[] values = new float[6];
    	ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
    	
    	for (int i =0 ; i<6 ; i++) {
    		values[i] = byteBuffer.getFloat(4*i);
    	}
    	
        return values;
    }
}