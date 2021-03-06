package org.usfirst.frc.team694.robot.commands;

import org.usfirst.frc.team694.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class QuisitorToggleCommand extends InstantCommand {

    public QuisitorToggleCommand() {
        requires(Robot.quisitor);
    }

    @Override
    protected void initialize() {
        Robot.quisitor.toggle();
    }
}