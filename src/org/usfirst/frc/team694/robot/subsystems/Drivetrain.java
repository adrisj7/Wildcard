/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.            */
/* Open Source Software - may be modified and shared by FRC teams. The code  */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team694.robot.subsystems;

import org.usfirst.frc.team694.robot.RobotMap;
import org.usfirst.frc.team694.robot.commands.DrivetrainPiotrDriveCommand;
import org.usfirst.frc.team694.util.LineSensor;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * An example subsystem. You can replace me with your own Subsystem.
 */
public class Drivetrain extends Subsystem {
    private WPI_TalonSRX leftFrontMotor;
    private WPI_TalonSRX leftMiddleMotor;
    private WPI_TalonSRX leftRearMotor;
    private WPI_TalonSRX rightFrontMotor;
    private WPI_TalonSRX rightMiddleMotor;
    private WPI_TalonSRX rightRearMotor;

    private SpeedControllerGroup leftDrivetrainMotorGroup;
    private SpeedControllerGroup rightDrivetrainMotorGroup;

    private DifferentialDrive differentialDrive;
    
    private LineSensor leftLineSensor;
    private LineSensor rightLineSensor;

    private Solenoid gearShift;
    
    private ADXRS450_Gyro gyro;
    

    public Drivetrain() {
        //TODO: Remove magic numbers: Add in RobotMap
        leftFrontMotor = new WPI_TalonSRX(RobotMap.LEFT_FRONT_MOTOR_PORT);
        leftMiddleMotor = new WPI_TalonSRX(RobotMap.LEFT_MIDDLE_MOTOR_PORT);
        leftRearMotor = new WPI_TalonSRX(RobotMap.LEFT_REAR_MOTOR_PORT);
        leftMiddleMotor.follow(leftFrontMotor);
        leftRearMotor.follow(leftFrontMotor);

        rightFrontMotor = new WPI_TalonSRX(RobotMap.RIGHT_FRONT_MOTOR_PORT);
        rightMiddleMotor = new WPI_TalonSRX(RobotMap.RIGHT_MIDDLE_MOTOR_PORT);
        rightRearMotor = new WPI_TalonSRX(RobotMap.RIGHT_REAR_MOTOR_PORT);
        rightMiddleMotor.follow(rightFrontMotor);
        rightMiddleMotor.follow(rightFrontMotor);

        leftDrivetrainMotorGroup.setInverted(true);

        leftFrontMotor.setNeutralMode(NeutralMode.Coast);
        leftMiddleMotor.setNeutralMode(NeutralMode.Coast);
        leftRearMotor.setNeutralMode(NeutralMode.Coast);
        rightFrontMotor.setNeutralMode(NeutralMode.Coast);
        rightMiddleMotor.setNeutralMode(NeutralMode.Coast);
        rightRearMotor.setNeutralMode(NeutralMode.Coast);
        
        leftRearMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        rightRearMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

        leftLineSensor = new LineSensor(RobotMap.DRVETRAIN_LINE_SENSOR_LEFT_PORT);
        rightLineSensor = new LineSensor(RobotMap.DRVETRAIN_LINE_SENSOR_RIGHT_PORT);
        
        gearShift = new Solenoid(RobotMap.GEAR_SHIFT_CHANNEL);
        //head
        //leftEncoder.setDistancePerPulse(RobotMap.DRIVETRAIN_ENCODER_INCHES_PER_PULSE);
        //rightEncoder.setDistancePerPulse(RobotMap.DRIVETRAIN_ENCODER_INCHES_PER_PULSE);

        differentialDrive = new DifferentialDrive(leftFrontMotor, rightFrontMotor);
        
        gyro = new ADXRS450_Gyro();
        
        differentialDrive = new DifferentialDrive(leftDrivetrainMotorGroup, rightDrivetrainMotorGroup);

    }

    public void resetEncoders() {
        leftRearMotor.setSelectedSensorPosition(0, 0, 0);
        rightRearMotor.setSelectedSensorPosition(0, 0, 0);
    }

    public double getLeftSpeed() {
        return leftRearMotor.getSelectedSensorVelocity(0);
    }

    public double getRightSpeed() {
        return rightRearMotor.getSelectedSensorVelocity(0);
    }

    public double getSpeed() {
        return Math.max(Math.abs(getLeftSpeed()), Math.abs(getRightSpeed()));
    }

    public double getEncoderDistance() {
        return Math.abs(Math.max(getLeftEncoderDistance(), getRightEncoderDistance()));
    }

    public double getLeftEncoderDistance() {
        return leftRearMotor.getSelectedSensorPosition(0)  * RobotMap.DRIVETRAIN_RAW_MULTIPLIER;
    }

    public double getRightEncoderDistance() {
        return rightRearMotor.getSelectedSensorPosition(0) * RobotMap.DRIVETRAIN_RAW_MULTIPLIER;
    }
    
    public double getLeftRawEncoderDistance() {
        return leftRearMotor.getSelectedSensorPosition(0);
    }
    
    public double getRightRawEncoderDistance() {
        return rightRearMotor.getSelectedSensorPosition(0);
    }

    public void tankDrive(double left, double right) {
        differentialDrive.tankDrive(left, right);
    }

    public void arcadeDrive(double speed, double rotation) {
        differentialDrive.arcadeDrive(speed, rotation);
    }

    public void stop() {
        tankDrive(0, 0);
    }

    public void highGearShift() {
        gearShift.set(false);
    }

    public void lowGearShift() {
        gearShift.set(true);
    }

    public void toggleGearShift() {
        boolean m = !(gearShift.get());
        gearShift.set(m);
    }
    public void resetLineSensors(){
        leftLineSensor.resetAmbient();
        rightLineSensor.resetAmbient();
    }
    public double getGyroAngle(){
        return gyro.getAngle();
    }
    public boolean isOnLine(int mode){
        return leftLineSensor.basicFind(mode) || rightLineSensor.basicFind(mode);
    }
    public boolean rightIsOnLine(int mode){
        return rightLineSensor.basicFind(mode);
    }
    public boolean leftIsOnLine(int mode){
        return leftLineSensor.basicFind(mode);
    }
    /*TODO: Should we remove?
    public int leftEncoderRaw() {
    return leftEncoder.getRaw();
    }

    public int rightEncoderRaw() {
    return rightEncoder.getRaw();
    }

    public int encoderRaw() {
    return Math.abs(Math.max(rightEncoderRaw(), leftEncoderRaw()));
    }*/

    public void initDefaultCommand() {
        //setDefaultCommand(new DriveCommand());
        setDefaultCommand(new DrivetrainPiotrDriveCommand());
    }

    public void resetGyro() {
        // TODO Auto-generated method stub
        gyro.reset();
    }

}
