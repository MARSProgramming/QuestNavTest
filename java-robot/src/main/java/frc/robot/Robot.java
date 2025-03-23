// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;

public class Robot extends LoggedRobot {

  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot finishes booting and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Set up the main robot container.
    // This will perform all our button bindings, and put our autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    Logger.recordMetadata("ProjectName", "Quest3S-Swerve");
    if (isReal()) {
      Logger.addDataReceiver(new WPILOGWriter());
      Logger.addDataReceiver(new NT4Publisher());
    } else {
      setUseTiming(false);
      String logPath = LogFileUtil.findReplayLog();
      Logger.setReplaySource(new WPILOGReader(logPath));
      Logger.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim")));
    }
    Logger.start();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    Pose2d PoseA = m_robotContainer.questNav.getPose();
    Logger.recordOutput("MyPose", PoseA);

    // Get the DriveSubsystem from the RobotContainer
    // Periodically clean up the control messages processed on the Quest headset
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  /** This function is periodically called while the robot is in Disabled mode. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when the robot enters Autonomous mode. */
  @Override
  public void autonomousInit() {

  }

  /** This function is periodically called while the robot is in Autonomous mode. */
  @Override
  public void autonomousPeriodic() {}

  /** This function is called once when the robot enters Teleop mode. */
  @Override
  public void teleopInit() {
  }

  /** This function is periodically called while the robot is in Teleop mode. */
  @Override
  public void teleopPeriodic() {}

  /** This function is called once when the robot enters Test mode. */
  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is periodically called while the robot is in Test mode. */
  @Override
  public void testPeriodic() {}
}
