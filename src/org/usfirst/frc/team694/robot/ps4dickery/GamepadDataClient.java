package org.usfirst.frc.team694.robot.ps4dickery;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class GamepadDataClient {

	public static void main(String[] args) {

		DualShockIO input = new DualShockIO();

		NetworkTableInstance instance = NetworkTableInstance.getDefault();
		NetworkTable table = instance.getTable("PS4");
		instance.startClientTeam(694);
		instance.startDSClient();

		System.out.println("Started NetworkTables Client!");

		NetworkTableEntry gyroX = table.getEntry("PS4-GYRO-X");
		NetworkTableEntry gyroY = table.getEntry("PS4-GYRO-Y");
		NetworkTableEntry accelX = table.getEntry("PS4-ACCEL-X");
		NetworkTableEntry accelY = table.getEntry("PS4-ACCEL-Y");
		NetworkTableEntry accelZ = table.getEntry("PS4-ACCEL-Z");

		while (true) {
			System.out.printf(" Gyro X: %.02f\n", input.getGyroX());
			gyroX.setDouble(input.getGyroX());
			gyroY.setDouble(input.getGyroY());
			accelX.setDouble(input.getAccelX());
			accelY.setDouble(input.getAccelY());
			accelZ.setDouble(input.getAccelZ());

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
