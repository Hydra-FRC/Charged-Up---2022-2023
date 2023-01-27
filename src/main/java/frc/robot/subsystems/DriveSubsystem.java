package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {

  private final VictorSPX frontalLeft;
  private final VictorSPX posteriorLeft;
  private final VictorSPX frontalRight;
  private final VictorSPX posteriorRight;

  public DriveSubsystem() {
    //Victor Attribuitions
    frontalLeft = new VictorSPX(Constants.kVictorLeftFrontal);
    posteriorLeft = new VictorSPX(Constants.kVictorLeftPosterior);
    frontalRight = new VictorSPX(Constants.kVictorRightFrontal);
    posteriorRight = new VictorSPX(Constants.kVictorRightPosterior);

    //Motors Alignment
    posteriorLeft.follow(frontalLeft);
    posteriorRight.follow(frontalRight);

    //Inverted Motors
    frontalLeft.setInverted(false);
    posteriorLeft.setInverted(false);
    frontalRight.setInverted(true);
    posteriorRight.setInverted(false); //False because the polars are reversed | Falso pois os polos estao invertidos

    //Motors DeadZone
    frontalLeft.setNeutralMode(NeutralMode.Brake);
    frontalRight.setNeutralMode(NeutralMode.Brake);
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


