// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Ultrasonic;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.revrobotics.*;
import com.revrobotics.Rev2mDistanceSensor.Port;

import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
  /** Creates a new Drive Subsystem. */

  // Declare + instantiate gyro
    public final ADXRS450_Gyro m_gyro = new ADXRS450_Gyro();

  // Declare + instantiate encoder
    public final Encoder m_encoder = new Encoder(Constants.ENCODER_PORT_1, Constants.ENCODER_PORT_2);

  // Declare ultrasonic
    public Rev2mDistanceSensor ultrasonic = new Rev2mDistanceSensor(Port.kMXP);

  // TODO: instantiate and declare at same time https://github.com/wpilibsuite/allwpilib/blob/main/wpilibjExamples/src/main/java/edu/wpi/first/wpilibj/examples/gearsbot/subsystems/DriveTrain.java

    private final WPI_TalonSRX m_leftMotor1;
    private final WPI_TalonSRX m_leftMotor2;
    private final WPI_TalonSRX m_rightMotor1;
    private final WPI_TalonSRX m_rightMotor2;
    public final DifferentialDrive m_driveTrain;

  // Group speed controllers
    SpeedControllerGroup m_left;
    SpeedControllerGroup m_right;

  public DriveTrain() {
    // Instantiate differential drive
    m_left = new SpeedControllerGroup(m_leftMotor1 = new WPI_TalonSRX(Constants.MOTOR1), m_leftMotor2 = new WPI_TalonSRX(Constants.MOTOR2));
    m_right = new SpeedControllerGroup(m_rightMotor1 = new WPI_TalonSRX(Constants.MOTOR3), m_rightMotor2 = new WPI_TalonSRX(Constants.MOTOR4));

    m_driveTrain = new DifferentialDrive(m_right, m_left);

    // TODO: name the sensors on the LiveWindow

    addChild("Drive", m_driveTrain);
    addChild("Gyro", m_gyro);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
