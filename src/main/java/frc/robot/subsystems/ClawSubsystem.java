package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClawSubsystem extends SubsystemBase {
    VictorSPX trilho;
    VictorSPX claw;
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

    trilho = new VictorSPX(Constants.MOTOR_TRILHO);
    claw = new VictorSPX(Constants.MOTOR_CLAW);

  }
}

