package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClawSubsystem extends SubsystemBase {
    VictorSPX claw;
    Timer time = new Timer();
    boolean clawIsOpen = false;
  /** Creates a new ExampleSubsystem. */
  public ClawSubsystem() {
    initMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  private void initMotor(){

    claw = new VictorSPX(Constants.MOTOR_CLAW);

  }

  public void openClaw(){
    clawIsOpen = true;
    time.start();
    claw.set(ControlMode.PercentOutput, 0.5);
    if(time.get() > 0.5){
      time.stop();
      claw.set(ControlMode.PercentOutput, 0);
    }
  }
}

