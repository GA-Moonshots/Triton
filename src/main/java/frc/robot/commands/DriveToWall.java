// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

/** An example command that uses an example subsystem. */
public class DriveToWall extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final DriveTrain m_driveTrain;
  boolean isDone;
  int theTargetDistance;

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
    m_driveTrain.ultrasonic.setAutomaticMode(true);
    isDone = false;
    System.out.println("Start");
  }

  public double power() {
    double MAX_POWER = 0.7; // cap the power 
    double MIN_POWER = 0.35; // lowest effective power

    // determine the error
    double error = theTargetDistance - m_driveTrain.ultrasonic.getRange();

    // determine the power output neutral of direction
    double output = Math.abs(error / theTargetDistance) * MAX_POWER;
    System.out.println("Getting output");
    if(output < MIN_POWER) output = MIN_POWER;
    if(output > MAX_POWER) output = MAX_POWER;

    // are we there yet? this is to avoid ping-ponging
    // plus we never stop the method unless our output is zero
    if(error <= 0) {
      isDone = true;
      return 0.0;
    } else {
      System.out.println("Done");
      return -output;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("Start of execute");
   if (m_driveTrain.ultrasonic.getRange() >= 20) {
    System.out.println("Trying to drive");
    m_driveTrain.m_driveTrain.arcadeDrive(0, power());
   }
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
