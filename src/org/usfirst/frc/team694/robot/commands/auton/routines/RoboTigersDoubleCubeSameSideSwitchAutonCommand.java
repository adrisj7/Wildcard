package org.usfirst.frc.team694.robot.commands.auton.routines;

import org.usfirst.frc.team694.robot.commands.LiftMoveToBottomCommand;
import org.usfirst.frc.team694.robot.commands.LiftMoveToHeightCommand;
import org.usfirst.frc.team694.robot.commands.QuisitorAcquireCommand;
import org.usfirst.frc.team694.robot.commands.QuisitorCloseCommand;
import org.usfirst.frc.team694.robot.commands.QuisitorDeacquireCommand;
import org.usfirst.frc.team694.robot.commands.QuisitorOpenCommand;
import org.usfirst.frc.team694.robot.commands.QuisitorStopCommand;
import org.usfirst.frc.team694.robot.commands.auton.ConditionalDistanceEncodersCommand;
import org.usfirst.frc.team694.robot.commands.auton.DrivetrainMoveInchesEncoderCommand;
import org.usfirst.frc.team694.robot.commands.auton.DrivetrainRotateAbsoluteDegreesPIDCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
@Deprecated
public class RoboTigersDoubleCubeSameSideSwitchAutonCommand extends CommandGroup {

    public RoboTigersDoubleCubeSameSideSwitchAutonCommand(boolean isRobotRight) {

        // Initial score (sideways)
        addParallel(new ConditionalDistanceEncodersCommand(new LiftMoveToHeightCommand(15), 50));
        addSequential(new DrivetrainMoveInchesEncoderCommand(176, 0.8));
        addSequential(new DrivetrainRotateAbsoluteDegreesPIDCommand(isRobotRight? -90 : 90));
        addSequential(new DrivetrainMoveInchesEncoderCommand(15, 0.8)); //TODO: Check distance
        addSequential(new QuisitorDeacquireCommand(), 0.5);
        addSequential(new DrivetrainMoveInchesEncoderCommand(-5, 0.2));
        addSequential(new DrivetrainRotateAbsoluteDegreesPIDCommand(0));

        // Grab the second cube
        addSequential(new LiftMoveToBottomCommand());
        addSequential(new DrivetrainMoveInchesEncoderCommand(70, 0.4));
        addSequential(new DrivetrainRotateAbsoluteDegreesPIDCommand(isRobotRight? -90 : 90));
        addSequential(new DrivetrainMoveInchesEncoderCommand(15, 0.3));
        addSequential(new DrivetrainRotateAbsoluteDegreesPIDCommand(isRobotRight? -180 : 180));
        addSequential(new QuisitorOpenCommand());
        addParallel(new QuisitorAcquireCommand());
        addSequential(new DrivetrainMoveInchesEncoderCommand(6, 0.8));
        addSequential(new QuisitorCloseCommand());
        addSequential(new QuisitorStopCommand());

        // Score 2nd time
        addSequential(new LiftMoveToHeightCommand(30));
        addSequential(new QuisitorDeacquireCommand());

    }
}
