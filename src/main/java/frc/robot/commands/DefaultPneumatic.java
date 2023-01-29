// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.PneumaticSubsystem;

public class DefaultPneumatic extends CommandBase {
  /** Creates a new DefaultPneumatic. */
  private final PneumaticSubsystem s_penumatic;
  private Joystick systemController;
  
  public DefaultPneumatic(PneumaticSubsystem subsystem, Joystick systemController) {
    s_penumatic = subsystem;
    addRequirements(s_penumatic);
    this.systemController = systemController;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean b = systemController.getRawButton(Constants.BUTTON_B);
    boolean x = systemController.getRawButton(Constants.BUTTON_X);

    s_penumatic.defaultMode(x, b);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
