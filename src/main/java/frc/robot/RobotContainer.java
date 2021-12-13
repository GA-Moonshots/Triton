// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.AutoCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.GyroTurn;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.RelayCommand;
import frc.robot.commands.DriveToWall;
import frc.robot.commands.EncoderCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // these are private member variables (hence the "m_" and are not to be made static or public)
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final DriveTrain m_drivetrain = new DriveTrain();
  private final AutoCommand m_autoCommand = new AutoCommand(m_drivetrain);
  private final Joystick m_xboxController = new Joystick(Constants.XBOX);

  // Static stuff hosted here for easy access 
  public final static Joystick bigJoystick = new Joystick(Constants.BIGBOI);
  

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // SETTING DEFAULT COMMANDS
    m_drivetrain.setDefaultCommand(new DriveCommand(m_drivetrain, m_xboxController));

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //define + instantiate buttons
    final JoystickButton a = new JoystickButton(m_xboxController, 1);
    final JoystickButton b = new JoystickButton(m_xboxController, 2);
    final JoystickButton x = new JoystickButton(m_xboxController, 3);
    final JoystickButton y = new JoystickButton(m_xboxController, 4);
    final JoystickButton lBump = new JoystickButton(m_xboxController, 5);
    final JoystickButton rBump = new JoystickButton(m_xboxController, 6);

    //assign buttons to commands
    a.whenPressed(new RelayCommand());
    b.whenPressed(new GyroTurn(m_drivetrain, 90));
    x.whenPressed(new GyroTurn(m_drivetrain, -90));
    y.whenPressed(new GyroTurn(m_drivetrain, 45));
    lBump.whenPressed(new DriveToWall(m_drivetrain, 20));
    rBump.whenPressed(new EncoderCommand(m_drivetrain, 12));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}