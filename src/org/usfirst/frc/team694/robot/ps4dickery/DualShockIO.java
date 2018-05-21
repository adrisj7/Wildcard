package org.usfirst.frc.team694.robot.ps4dickery;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.zip.CRC32;

import purejavahidapi.HidDeviceInfo;
import purejavahidapi.InputReportListener;
import purejavahidapi.PureJavaHidApi;
import purejavahidapi.windows.HidDevice;

public class DualShockIO {
	// for Dualshock PS4 4


	//http://eleccelerator.com/wiki/index.php?title=DualShock_4#Report_Structure
	
	private static final short VENDOR_ID = 0x054C;
	private static final short PRODUCT_ID = 0x09CC;

	private float gyroX, gyroY;
	private float accelX, accelY, accelZ;

	HidDevice myDevice = null;

	private static short toInt16(byte b0, byte b1) {
		return (short) (((b0 & 0xff) << 8) | (b1 & 0xff));
	}

	// public static void main(String[] args) throws IOException {
	public DualShockIO() {
		HidDeviceInfo myInfo = null;

		// Find the proper info tag for our device
		List<HidDeviceInfo> devices = PureJavaHidApi.enumerateDevices();
		for (HidDeviceInfo info : devices) {
			if (info.getVendorId() == VENDOR_ID && info.getProductId() == PRODUCT_ID) {
				myInfo = info;
				break;
			}
		}
		if (myInfo == null) {
			System.err.println("Cannot locate PS4 controller! Make sure it's plugged in and, you know, not broken!");
			return;
		} else {
			System.out.println("PS4 Device found!");
		}

		// Connect to the device
		try {
			myDevice = (HidDevice) PureJavaHidApi.openDevice(myInfo);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		myDevice.setInputReportListener(new InputReportListener() {
			@Override
			public void onInputReport(purejavahidapi.HidDevice source, byte reportID, byte[] data, int length) {
				// System.out.printf("onInputReport: id %d len %d data ", reportID, length);

				// Gyro Y and Gyro Z look to be the same thing :(
				gyroX = -90 * toInt16(data[19], data[18]) / 8192f;
				gyroY = -90 * toInt16(data[21], data[20]) / 8192f;

				// TODO: Figure this out
				accelX = -toInt16(data[17], data[16]) / 8192f;
				accelY = -toInt16(data[15], data[14]) / 8192f;
				accelZ = -toInt16(data[13], data[12]) / 8192f;

				// float gyroZ = -90 * toInt16(data[23], data[22]) / 8192f;
				// for (int i = 0; i < length; i++)
				// System.out.printf("%02X ", data[i]);
				// System.out.println();
			}
		});

	}

	public float getGyroX() {
		return gyroX;
	}

	public float getGyroY() {
		return gyroY;
	}

	public float getAccelX() {
		return accelX;
	}

	public float getAccelY() {
		return accelY;
	}

	public float getAccelZ() {
		return accelZ;
	}

	public void sendReport(boolean enableRumble, byte rumbleWeak, byte rumbleStrong, byte red, byte green, byte blue) {
		byte report[] = new byte[79];
		report[0] = (byte) 0xa2;
		report[1] = (byte) 0x11;

		report[4] = enableRumble? (byte)0xf3 : (byte)0xf0;

		report[7] =  rumbleWeak;
		report[8] =  rumbleStrong;
		report[9] =  red;
		report[10] = green;
		report[11] = blue;

		// Copied, unknown
		report[2] = (byte) 0xc0;
		report[3] = (byte) 0x20;
		report[5] = (byte) 0xc0;
		report[6] = (byte) 0x20;

		// CRC32

		CRC32 crc32 = new CRC32();
		crc32.update(report, 0, 75);
		ByteBuffer crcbuff = ByteBuffer.allocate(4);
		crcbuff.putInt((int)crc32.getValue()); // Um, should this be cast to int?
		report[75] = crcbuff.get(0);
		report[76] = crcbuff.get(1);
		report[77] = crcbuff.get(2);
		report[78] = crcbuff.get(3);

		myDevice.setOutputReport((byte) 0, report, 31);
		myDevice.setOutputReport((byte) 1, Arrays.copyOfRange(report, 32, 32+31), 31);
		myDevice.setOutputReport((byte) 2, Arrays.copyOfRange(report, 32+31, 79), 78-(32+31));
	}

	public static void main(String[] args) {
		DualShockIO test = new DualShockIO();
		test.sendReport(false, (byte)0, (byte)0, (byte)255, (byte)0, (byte)0);
	}
}
 