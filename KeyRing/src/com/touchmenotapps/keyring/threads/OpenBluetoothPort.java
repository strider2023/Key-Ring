package com.touchmenotapps.keyring.threads;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * 
 * @author Arindam Nath
 *
 */
public class OpenBluetoothPort 
	extends AsyncTask<String, Void, Integer> {

	private final int SUCCESS = 0;
	private final int DEVICE_NOT_FOUND = 1;
	private final int EXCEPTION = 2;
	private final int BLUETOOTH_OFF = 3;
	
	//A standard SSP UUID
	private final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private BluetoothAdapter mBluetoothAdapter;
	private OnBluetoothPortOpened mCallback;
	private BluetoothSocket mBSocket;
	private byte[] mPacketData;
	private Context mContext;
	
	public interface OnBluetoothPortOpened {
		public void onBluetoothCommandSent();
	}
	
	public OpenBluetoothPort(Context context, OnBluetoothPortOpened callback, byte[] packet) {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		mCallback = callback;
		mPacketData = packet;
		mContext = context;
	}
	
	@Override
	protected Integer doInBackground(String... params) {
		if(mBluetoothAdapter.isEnabled()) {
			try {
			      for(BluetoothDevice bt : mBluetoothAdapter.getBondedDevices()) {
			         if(bt.getName().equalsIgnoreCase(params[0])) {
			        	 BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(bt.getAddress());
						mBluetoothAdapter.cancelDiscovery(); //We have our device so cancel search
						mBSocket = device.createRfcommSocketToServiceRecord(SPP_UUID);
						mBSocket.connect();
						mBSocket.getOutputStream().write(
								mPacketData, 0 , mPacketData.length);
						mBSocket.getOutputStream().flush();
						return SUCCESS; 
			         }
			      }
				return DEVICE_NOT_FOUND;
			} catch (IOException e) {
				return EXCEPTION;
			}
		} else
			return BLUETOOTH_OFF;
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		switch (result) {
		case SUCCESS:
			mCallback.onBluetoothCommandSent();
			break;
		case DEVICE_NOT_FOUND:
			Toast.makeText(mContext, "Device not found.", Toast.LENGTH_LONG).show();
			break;
		case EXCEPTION:
			Toast.makeText(mContext, "An exception occured while sending data.", Toast.LENGTH_LONG).show();
			break;
		case BLUETOOTH_OFF:
			Toast.makeText(mContext, "Bluetooth has been turned off", Toast.LENGTH_LONG).show();
			break;
		}
	}
}
