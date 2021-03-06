package org.usfirst.frc.team694.robot.commands.auton.choosers;

import org.usfirst.frc.team694.robot.Robot;
import org.usfirst.frc.team694.robot.commands.auton.routines.RightAngleSameSideSwitchAutonCommand;

import edu.wpi.first.wpilibj.command.ConditionalCommand;

/**
 *
 */
public class RightAngleSameSideSwitchAutonCommandChooser extends ConditionalCommand {

    public RightAngleSameSideSwitchAutonCommandChooser() {
        super(new RightAngleSameSideSwitchAutonSameSideChooser());
    }

    @Override
    protected boolean condition() {
        return Robot.isRobotOnSameSideSwitch();
    }
    
    private static class RightAngleSameSideSwitchAutonSameSideChooser extends ConditionalCommand {

        public RightAngleSameSideSwitchAutonSameSideChooser() {
            super(new RightAngleSameSideSwitchAutonCommand(true), new RightAngleSameSideSwitchAutonCommand(false));
        }
        
        @Override
        public boolean condition() {
            return Robot.isRobotStartingOnRight();
        }
    }
}
  
