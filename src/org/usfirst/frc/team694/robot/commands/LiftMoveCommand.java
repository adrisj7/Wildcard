package org.usfirst.frc.team694.robot.commands;

import org.usfirst.frc.team694.robot.Robot;
import org.usfirst.frc.team694.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
/**
 *
 */
public class LiftMoveCommand extends Command {

    public LiftMoveCommand() {
        requires(Robot.lift);
    }

    protected void execute() {
//        if (Robot.oi.operatorGamepad.getLeftY() > 0.9) {
//            Robot.lift.move(RobotMap.LIFT_MAX_SPEED);
//        } else if (Robot.oi.operatorGamepad.getLeftY() > 0.4) {
//            Robot.lift.move(RobotMap.LIFT_MAX_SPEED / 2);
//        } else if (Robot.oi.operatorGamepad.getLeftY() > -0.4) {
//            Robot.lift.stop();
//        } else if (Robot.oi.operatorGamepad.getLeftY() > -0.9) {
//            Robot.lift.move(RobotMap.LIFT_MAX_SPEED / 2 * -1);
//        } else {
//            Robot.lift.move(RobotMap.LIFT_MAX_SPEED * -1);
//        }
        double leftYAxis = Robot.oi.operatorGamepad.getRightY();
        double rightYAxis = Robot.oi.operatorGamepad.getLeftY();
        Robot.lift.move(Math.abs(Math.pow(leftYAxis, 2)) * Math.signum(leftYAxis));
        Robot.lift.move(Math.abs(Math.pow(rightYAxis, 3)) * Math.signum(rightYAxis));
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.lift.stop();
    }
}