// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class DefaultDrive extends CommandBase {
  private final DriveSubsystem s_drive;
  private Joystick driverController;
  double left_stickX, left_stickY,right_stickX,right_stickY, lt, rt, spd=1;

  public DefaultDrive(DriveSubsystem subsystem,Joystick driverController) {
    // Use addRequirements() here to declare subsystem dependencies.
    s_drive = subsystem;
    addRequirements(s_drive);
    this.driverController = driverController;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean a = driverController.getRawButton(Constants.BUTTON_A);
    boolean b = driverController.getRawButton(Constants.BUTTON_B);
    boolean x = driverController.getRawButton(Constants.BUTTON_X);
    buttonSe(a, b, x);

    this.left_stickX = driverController.getRawAxis(Constants.STICK_X);
    this.left_stickY = driverController.getRawAxis(Constants.STICK_Y);
    this.lt = driverController.getRawAxis(Constants.LT);
    this.rt = driverController.getRawAxis(Constants.RT);
    this.right_stickX = driverController.getRawAxis(Constants.RIGHT_STICK_X);
    this.right_stickY = driverController.getRawAxis(Constants.RIGHT_STICK_y);

    s_drive.defaultDrive(left_stickX, left_stickY,right_stickX,right_stickY, lt, rt,spd);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false; 
  }

  private void buttonSe(boolean a, boolean b, boolean x){
    this.spd = x ? 1 : a ? 0.5 : b ? 0.25 : spd;
  }
}
