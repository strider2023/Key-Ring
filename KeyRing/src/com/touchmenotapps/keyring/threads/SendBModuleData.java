package com.touchmenotapps.keyring.threads;

import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;

public class SendBModuleData implements Runnable {

	private OutputStream out;
	private byte[] packetData;
	private int packetLength = 0;
	
	public SendBModuleData(BluetoothSocket socket, byte[] packet, int length) {
		packetData = packet;
		packetLength = length;
		try {
			this.out = socket.getOutputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			out.write(formPacket(packetData, packetLength), 0 , packetLength);
			out.flush();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * 
	 * @param data
	 * @param len
	 * @return
	 */
	private byte[] formPacket(byte[] data, int len) {
		for(int count = 0; count < len-1; count++)
			data[len-1] += data[count];
		return data;
	}
}
