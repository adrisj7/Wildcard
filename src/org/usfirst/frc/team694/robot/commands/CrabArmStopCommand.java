package org.usfirst.frc.team694.robot.commands;

import org.usfirst.frc.team694.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class CrabArmStopCommand extends InstantCommand {
    public CrabArmStopCommand() {
        requires(Robot.crabArm);
    }
    
    protected void initialize() {
        Robot.crabArm.stop();
    }
}