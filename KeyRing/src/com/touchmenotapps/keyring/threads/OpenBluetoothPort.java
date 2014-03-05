package com.touchmenotapps.keyring.threads;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

/**
 * 
 * @author Arindam Nath
 *
 */
public class OpenBluetoothPort 
	extends AsyncTask<String, Void, BluetoothSocket> {

	//A standard SSP UUID
	private final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private BluetoothAdapter mBluetoothAdapter;
	private OnBluetoothPortOpened mCallback;
	private BluetoothSocket mBSocket;
	
	public interface OnBluetoothPortOpened {
		public void onBluetoothConnectionSuccess(BluetoothSocket socket);
	}
	
	public OpenBluetoothPort(OnBluetoothPortOpened callback) {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		mCallback = callback;
	}
	
	@Override
	protected BluetoothSocket doInBackground(String... params) {
		if(mBluetoothAdapter.isEnabled()) {
			try {
			      for(BluetoothDevice bt : mBluetoothAdapter.getBondedDevices()) {
			         if(bt.getName().equalsIgnoreCase(params[0])) {
			        	 BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(bt.getAddress());
						mBluetoothAdapter.cancelDiscovery(); //We have our device so cancel search
						mBSocket = device.createRfcommSocketToServiceRecord(SPP_UUID);
						mBSocket.connect();
						return mBSocket; 
			         }
			      }
				return null;
			} catch (IOException e) {
				return null;
			}
		} else
			return null;
	}

	@Override
	protected void onPostExecute(BluetoothSocket result) {
		super.onPostExecute(result);
		mCallback.onBluetoothConnectionSuccess(result);
	}
}
