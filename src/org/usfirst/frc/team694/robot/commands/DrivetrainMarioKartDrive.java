package org.usfirst.frc.team694.robot.commands;

import org.usfirst.frc.team694.robot.Robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotController;

/**
 *
 */
public class DrivetrainMarioKartDrive extends DefaultCommand {

    NetworkTableInstance instance;
    NetworkTable table;

    NetworkTableEntry gyroX;
    NetworkTableEntry gyroY;
    NetworkTableEntry accelX;
    NetworkTableEntry accelY;
    NetworkTableEntry accelZ;

    public DrivetrainMarioKartDrive() {
        requires(Robot.drivetrain);
        instance = NetworkTableInstance.getDefault();
        table = instance.getTable("PS4");
        
        gyroX = table.getEntry("PS4-GYRO-X");
        gyroY = table.getEntry("PS4-GYRO-Y");
        accelX = table.getEntry("PS4-ACCEL-X");
        accelY = table.getEntry("PS4-ACCEL-Y");
        accelZ = table.getEntry("PS4-ACCEL-Z");
    }

    @Override
    protected void initialize() {
        super.initialize();
    }

    @Override
    protected void defaultExecute() {
        //Preliminary Values from the triggers used for Curvature Drive
        double rightTrigger = Robot.oi.driverGamepad.getRawRightTriggerAxis();
        double leftTrigger = Robot.oi.driverGamepad.getRawLeftTriggerAxis();

        //Values Used for Curvature Drive
        double rightTriggerSquared = rightTrigger * Math.abs(rightTrigger);
        double leftTriggerSquared = leftTrigger * Math.abs(leftTrigger);

        double side = gyroX.getDouble(0) / 80.0;
        System.out.println("[DrivetrainMarioKartDrive] " + gyroX.getDouble(0));
        if (Math.abs(rightTrigger + leftTrigger) > 0.05) {
            Robot.drivetrain.curvatureDrive(rightTriggerSquared - leftTriggerSquared, side, false);
        } else {
            Robot.drivetrain.curvatureDrive(rightTriggerSquared - leftTriggerSquared, side, true);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        
    }
}
