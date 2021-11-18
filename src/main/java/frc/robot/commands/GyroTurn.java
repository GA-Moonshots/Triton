// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

/** An example command that uses an example subsystem. */
public class GyroTurn extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final DriveTrain m_driveTrain;

  private int check;
  private double target;
  double requestedRotation;
  public boolean isDone;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public GyroTurn(DriveTrain p_driveTrain, double requestedRotation) {
    this.requestedRotation = requestedRotation;
    m_driveTrain = p_driveTrain;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    target = m_driveTrain.m_gyro.getAngle() + requestedRotation;
    check = 0;
    isDone = false;
  }

  public double power() {
    double MAX_POWER = 0.7; // cap the power 
    double MIN_POWER = 0.35; // lowest effective power
    int ENOUGH_CHECKS = 15; // how many times do we pass our target until we're satisfied?
    int MIN_ERROR = 1;

    // determine the error
    double error = target - m_driveTrain.m_gyro.getAngle();

    // determine the power output neutral of direction
    double output = Math.abs(error / requestedRotation) * MAX_POWER;
    if(output < MIN_POWER) output = MIN_POWER;
    if(output > MAX_POWER) output = MAX_POWER;

    // are we there yet? this is to avoid ping-ponging
    // plus we never stop the method unless our output is zero
    if(Math.abs(error) < MIN_ERROR) check++;
    if(check > ENOUGH_CHECKS) {
      isDone = true;
      return 0.0;
    }
    // determine the direction
    // if I was trying to go a positive angle change from the start
    if(requestedRotation > 0){
      if(error > 0) return output; // move in a positive direction
        else return -output; // compensate for over-turning by going a negative direction
    }
     //if I was trying to go a negative angle from the start
    else {
      if(error < 0) return -output; // move in a negative direction as intended
      else return output; // compensate for over-turning by moving a positive direction
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_driveTrain.m_driveTrain.arcadeDrive(power(), 0);
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
