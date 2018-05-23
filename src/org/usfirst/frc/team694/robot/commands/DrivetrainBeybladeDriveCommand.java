package org.usfirst.frc.team694.robot.commands;

import org.usfirst.frc.team694.robot.Robot;

/**
 *
 */
public class DrivetrainBeybladeDriveCommand extends DefaultCommand {


    public DrivetrainBeybladeDriveCommand() {
        requires(Robot.drivetrain);
    }

    @Override
    protected void initialize() {
        super.initialize();

    }

    @Override
    protected void defaultExecute() {
        double joyX = Robot.oi.driverGamepad.getLeftX();
        double joyY = Robot.oi.driverGamepad.getLeftY();
        double triggerR = Robot.oi.driverGamepad.getRawRightTriggerAxis();
        double triggerL = Robot.oi.driverGamepad.getRawLeftTriggerAxis();

        double joyMagSqr = joyX*joyX + joyY*joyY;
        double joyAngle = Math.atan2(joyY, joyX);

        double deltaAngle = Math.toRadians(Robot.drivetrain.getAbsoluteGyroAngle()) - joyAngle;

        double spinStrength = triggerR - triggerL;

        double forwardBackMagnitude = spinStrength * joyMagSqr * Math.cos(deltaAngle);

        Robot.drivetrain.tankDrive(spinStrength + forwardBackMagnitude, -spinStrength + forwardBackMagnitude);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        
    }
}
