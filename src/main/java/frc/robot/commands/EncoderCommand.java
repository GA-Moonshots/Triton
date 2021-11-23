// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.lang.Math.*;

/** An example command that uses an example subsystem. */
public class EncoderCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveTrain m_driveTrain;
  private final double targetRotations;

  private boolean isDone;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public EncoderCommand(DriveTrain p_driveTrain, double requestedDistance) {
    m_driveTrain = p_driveTrain;
    targetRotations = ((requestedDistance / (6 * Math.PI)));
    //* Constants.ENCODER_COUNTS_PER_REV
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(p_driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isDone = false;
    m_driveTrain.m_encoder.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    while (-m_driveTrain.m_encoder.get() < targetRotations) {
      System.out.println(-m_driveTrain.m_encoder.get());
      m_driveTrain.m_driveTrain.arcadeDrive(0, 0.3);
    }
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
