package org.usfirst.frc.team694.robot.commands;

import org.usfirst.frc.team694.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BITCOINCommand extends CommandGroup {
    public boolean startBITCOINAutomation; //records initial automation mode
    public double height = 2;
    public BITCOINCommand() {
        addSequential(new AcquirerTightenHoldCommand());
        addSequential(new AcquirerFlipUpCommand());
        addSequential(new LiftMaxDownCommand());
        //TODO when merge to branch with command, uncomment
        //addSequential(new LiftMoveUpHeightCommand(height));
        addSequential(new AcquirerLoosenHoldCommand());
        addSequential(new GrabberCloseCommand());
        }
    
    public void initialize() {
        startBITCOINAutomation = Robot.acquirer.isBITCOINAutomation;
    }
    public boolean isFinished() {
        //if started out in automation mode, 
        //will stop when reset button is pressed to make it not in automation mode
        if (startBITCOINAutomation && !(Robot.acquirer.isBITCOINAutomation)) {
            return true;
        // in all other cases, the command will run until completion
        } else {
            return super.isFinished();
        }
    }
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
}
