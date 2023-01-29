// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PneumaticSubsystem extends SubsystemBase {
  /** Creates a new Pneumatic. */
  private Compressor compressor = new Compressor(1, PneumaticsModuleType.REVPH);
  private Solenoid forward = new Solenoid(PneumaticsModuleType.REVPH,Constants.SOLENOID_FOWARD);
  private Solenoid reverse = new Solenoid(PneumaticsModuleType.REVPH,Constants.SOLENOID_REVERSE);
  public PneumaticSubsystem() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void defaultMode(boolean x, boolean b){
    if(x){
      reverse.set(false);
      forward.set(true);
    }else if(b){  
      forward.set(false);
      reverse.set(true);
    }else{
      forward.set(true);
      reverse.set(true);
    }
  }


}
