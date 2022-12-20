package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  private Joystick j1 = new Joystick(0);
  private Joystick j2 = new Joystick(1);

  private VictorSPX mD = new VictorSPX(Constants.mD);
  private VictorSPX mDI = new VictorSPX(Constants.mDI);
  private VictorSPX mE = new VictorSPX(Constants.mE);
  private VictorSPX mEI = new VictorSPX(Constants.mEI);
  private VictorSPX mLan = new VictorSPX(Constants.mLan);
  private VictorSPX mlan2 = new VictorSPX(Constants.mlan2);

  Encoder enzo = new Encoder(Constants.enconderEnzo,Constants.enconderEnzo2);

  private boolean a, b, x, y, lb, rb;
  
  private double speed = 1;

  private double pX, pY, pX2, pY2;
  private double mR, mL;
  private int ang;
  private double tR, tL;
  private double lm;

  private double mag, mag2;
  private double seno, seno2;
  private int qd;
  private boolean on = false;
  private boolean onC = false;

  private int ang2;
  private boolean a2,b2,x2;
  private double speed2;

  AHRS ahrs;

  private double sensorRaw,sensorCm;
  AnalogInput analog = new AnalogInput(3);
  

  @Override
  public void robotInit() {
    mEI.follow(mE);
    mDI.follow(mD);

    mE.configNeutralDeadband(0.04);
    mD.configNeutralDeadband(0.04);    

    mE.setNeutralMode(NeutralMode.Brake);
    mD.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void teleopPeriodic() {
    a = j1.getRawButton(Constants.a);
    b = j1.getRawButton(Constants.b);
    x = j1.getRawButton(Constants.x);
    y = j1.getRawButton(Constants.y);
    lb = j1.getRawButton(Constants.lb);
    rb = j1.getRawButton(Constants.rb);
    ang = j1.getPOV();
    ang2 = j2.getPOV();
    tL = j1.getRawAxis(Constants.eixotL);
    tR = j1.getRawAxis(Constants.eixotR);
    a2 = j2.getRawButton(Constants.a);
    b2 = j2.getRawButton(Constants.b);
    x2 = j2.getRawButton(Constants.x);
    
    speed = velocity(a, b, x);
    speed2 = velocity(a2, b2, x2);
    setPoint();
    
    mag = Math.sqrt(Math.pow(pX, 2) + Math.pow(pY, 2));
    mag2 = Math.sqrt(Math.pow(pX2, 2) + Math.pow(pY2, 2));

    mR = minMotor(mR);
    mL = minMotor(mL);
    mag = minMotor(mag);
    mag2 = minMotor(mag2);
    tR = minMotor(tR);
    tL = minMotor(tL);

    if(mag > 0.1)
      analogic();
    else if(mag2 > 0.1)
      analogicReverse(pX2, pY2);

    if(ang != -1) pov();
    if(tR != 0 || tL != 0) trigger();
    
    if (y && !onC) {
      on = !on;  
      onC = true;
    } else if(!y) 
    onC = false;

    minMotor(lm);
    //lock(y);
    
    sensorRaw = analog.getValue();
    sensorCm = (6762/(sensorRaw-9))-4;
    //Distancia Sensor

    SmartDashboard.putNumber("Velocidade", speed);
    SmartDashboard.putNumber("Forca motor direito", mR);
    SmartDashboard.putNumber("Trigger Direito", tR);
    SmartDashboard.putNumber("Forca motor esquerdo", mL);
    SmartDashboard.putNumber("Trigger Esquerdo", -tL);
    SmartDashboard.putNumber("POV", ang);
    SmartDashboard.putNumber("Sensor Distancia", sensorCm);
    SmartDashboard.putNumber("Enconder distancia", enzo.getDistance());
    SmartDashboard.putBoolean("Enconder direcao", enzo.getDirection());
    SmartDashboard.putNumber("Velocidade 2",speed2);
    SmartDashboard.putNumber("POV 2", ang2);

    //mE.set(ControlMode.PercentOutput, mL);
    //mD.set(ControlMode.PercentOutput, -mR);

    if(lb)
      mLan.set(ControlMode.PercentOutput, - speed);
    else
       mLan.set(ControlMode.PercentOutput, 0);

    if(rb)
      mlan2.set(ControlMode.PercentOutput, - speed);
    else
      mlan2.set(ControlMode.PercentOutput, 0);

    mR = 0;
    mL = 0;
    pX = 0;
    pY = 0;
  }

  public void lock(boolean y){
    if (y) {
      mR = 0; 
      mL = 0;
    } 
  } 

  public double minMotor(double lm){
    
    if(Math.abs(lm) < 0.04) 
    return 0;

    else 
    return lm;
  }

  public double velocity(boolean a, boolean b, boolean x) {

    if (a)
      speed = Constants.med;

    else if (b)
      speed = Constants.min;

    else if (x)
      speed = Constants.max;

    return speed;

  }

  public void setPoint() {
    pX = j1.getRawAxis(Constants.eixopX);
    pY = -j1.getRawAxis(Constants.eixopY);
    pX2 = j1.getRawAxis(Constants.eixopX2);
    pY2 = -j1.getRawAxis(Constants.eixopY2);
  }

  public int detectQuad(double pX, double pY) {

    if (pX >= 0 && pY >= 0)
      return 1;

    else if (pX < 0 && pY >= 0)
      return 2;

    else if (pX < 0 && pY < 0)
      return 3;

    else if (pX >= 0 && pY < 0)
      return 4;

    else
      return 0;
  }

  public void analogic() {
    
    qd = detectQuad(pX, pY);
    seno = pY/mag;

    if( qd == 1){
      mR = (2 * seno - 1) * mag;
      mL = mag;
    }
    else if( qd == 2){
      mR = mag;
      mL = (2 * seno - 1) * mag;
    }
    else if( qd == 3 ){
      mR = -mag;
      mL = (2 * seno + 1) * mag;
    }
    else if( qd == 4 ){
      mR = (2 * seno + 1) * mag;
      mL = -mag;
    } 
    mR *= speed;
    mL *= speed;
  }

  public void  analogicReverse(double pX2, double pY2){

    qd = detectQuad(pX2, pY2);  
    seno2 = pY2/mag2;

    if(qd == 1){
      mR = (-2 * seno2 + 1) * mag2;
      mL = -mag2;
    }
    else if( qd == 2){
      mR = -mag2;
      mL = (-2 * seno2 + 1) * mag2;
    }
    else if( qd == 3 ){
      mR = (-2 * seno2 -1) * mag2;
      mL = mag2;
    }
    else if( qd == 4 ){
      mR = mag2;
      mL = (-2 * seno2 - 1) * mag2;
    }
    mR *= speed;
    mL *= speed;
  }

  public void pov(){
    switch(ang){
      
      case 0:
      mR = 0.25;
      mL = 0.25;
      break;
    
      case 45:
      mR = 0;
      mL = 0.25;
      break;

      case 90:
      mR = -0.25;
      mL = 0.25;
      break;
      
      case 135:
      mR = -0.25;
      mL = 0;
      break;
      
      case 180:
      mR = -0.25;
      mL = -0.25;
      break;
    
      case 225:
      mR = 0;
      mL = -0.25;
      break;
     
      case 270:
      mR = 0.25;
      mL = -0.25;
      break;
     
      case 315:
      mR = 0.25;
      mL = 0; 
      break;

      default:
      mR = 0;
      mL = 0;
    }
  }

  public void trigger(){

  if(tR != 0){
    if (pX >= 0){
    mR = tR * (1 - pX) * speed;
    mL = tR * speed;
    }  
    else if (pX < 0){
    mR = tR * speed;
    mL = tR * (1 + pX) * speed; 
    }
  }
  else if(tL != 0){
    if (pX >= 0){
    mR = tL * speed;
    mL = tL * (1 - pX) * speed;
    }  
    else if (pX < 0){
    mR = tL * (1 + pX) * speed;
    mL = tL * speed;
    }
  
  }

}

}