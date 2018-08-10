package fr.coni.BtController;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class WaitThread implements Runnable{

	private Main parent;
	
	/** Constructor */
	public WaitThread(Main parent_) {
		this.parent = parent_;
	}

	@Override
	public void run() {
		waitForConnection();
	}

	/** Waiting for connection from devices */
	private void waitForConnection() {
		// retrieve the local Bluetooth device object
		LocalDevice local = null;

		StreamConnectionNotifier notifier;
		StreamConnection connection = null;

		// setup the server to listen for connection
		try {
			local = LocalDevice.getLocalDevice();
			local.setDiscoverable(DiscoveryAgent.GIAC);
			
			System.out.println(local.getFriendlyName());

			//UUID uuid = new UUID("d0c722b07e1511e1b0c40800200c9a66", false);
            //String url = "btspp://localhost:" + uuid.toString() + ";name=RemoteBluetooth";
            
			String uuid = "deadbeef000000000000000000000000";
            String url = "btspp://localhost:" + uuid + ";name=RemoteBluetooth";
		
            notifier = (StreamConnectionNotifier)Connector.open(url);
		} catch (Exception e) {
            e.printStackTrace();
            return;
        }

		// waiting for connection
		while(true) {
			try {
				System.out.println("waiting for connection...");
	            connection = notifier.acceptAndOpen();
	            System.out.println("After AcceptAndOpen...");

	            Thread processThread = new Thread(new Listener(connection, this.parent));
	            	   processThread.start();
	            break;

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}
}