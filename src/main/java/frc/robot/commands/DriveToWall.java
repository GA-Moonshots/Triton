// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

/** An example command that uses an example subsystem. */
public class DriveToWall extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final DriveTrain m_driveTrain;
  boolean isDone;
  float theTargetDistance;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveToWall(DriveTrain p_driveTrain, int targetDistance) {
    m_driveTrain = p_driveTrain;
    theTargetDistance = targetDistance;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Tell the ultrasonic sensor to start reading distance
    // THE ULTRASONIC DOESNT HAVE UNLIMITED RANGE, IT WILL NOT SEE SOMETHING IF IT IS TOO FAR AWAY
    m_driveTrain.ultrasonic.setAutomaticMode(true);
    isDone = false;
  }

  public double power() {
    // Cap the power 
    double MAX_POWER = Constants.DRIVE_TO_WALL_MAX; 
    // Lowest effective power
    double MIN_POWER = Constants.DRIVE_TO_WALL_MIN; 

    // Determine the error by seeing the difference between where we are and where we want to be
    double error = theTargetDistance - m_driveTrain.ultrasonic.getRange();

    // Determine the power output neutral of direction
    double output = Math.abs(error / theTargetDistance);
    // Make sure the power doesn't go lower than the minimum
    if(output < MIN_POWER) output = MIN_POWER;
    // Make sure the power doesn't go higher than the maximum
    if(output > MAX_POWER) output = MAX_POWER;

    // Check to see if we're there yet. This is to avoid ping-ponging infinitely
    if (error >= 0) {
      isDone = true;
      return 0.0;
    } else {
      return -output;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // If we are farther away from the wall than we want to be
   if (m_driveTrain.ultrasonic.getRange() >= theTargetDistance) {
     // Go forward with the speed given from power()
    m_driveTrain.m_driveTrain.arcadeDrive(0, power());
   }
   else isDone = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}
