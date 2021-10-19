// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;

/** An example command that uses an example subsystem. */
public class RelayCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  public boolean isOn;
  private static DigitalOutput relay = new DigitalOutput(1);
  private boolean runOnce;
  private double currentTime;
  private Timer timer;
  private boolean keepRunning;
  private double endTime;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public RelayCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isOn  = false;
    timer = new Timer();
    keepRunning = true;
    runOnce = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!isOn) {
      currentTime = timer.getFPGATimestamp();
      endTime = currentTime + 0.5;
      isOn = true;
      relay.set(true);
      while(keepRunning) {
        if (timer.getFPGATimestamp() >= endTime) {
          isOn = false;
          relay.set(false);
          keepRunning = false;
          runOnce = true;
        }
      } 
    } else {
      isOn = false;
      relay.set(false);
      }
    // Set the toggle so this command only runs once
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return runOnce;
  }
}
