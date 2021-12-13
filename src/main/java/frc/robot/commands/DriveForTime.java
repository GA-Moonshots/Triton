// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class DriveForTime extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveTrain m_driveTrain;
  private final double requestedTime;
  private double currentTime;
  private Timer timer;
  private boolean isDone;
  private double targetTime;


  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveForTime(DriveTrain p_driveTrain, double requestedTime) {
    m_driveTrain = p_driveTrain;
    this.requestedTime = requestedTime;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer = new Timer();
    isDone = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Get the current time
    currentTime = timer.getFPGATimestamp();
    // Add the requested time to our current time so we can figure out when the robot should stop driving
    targetTime = currentTime + requestedTime;
    // Drive forward until the robot stops
    while (timer.getFPGATimestamp() < targetTime) {
      m_driveTrain.m_driveTrain.arcadeDrive(0, Constants.DRIVE_FOR_TIME_SPEED);
    }
    // Turn off the motors
    m_driveTrain.m_driveTrain.arcadeDrive(0, 0);
    isDone = true;
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
