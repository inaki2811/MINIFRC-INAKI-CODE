// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.tank;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Tank extends SubsystemBase {

  private final TankModule leftModule;
  private final TankModule rightModule;

  public Tank() {
    leftModule = new TankModule(TankConstants.LEFT_FRONT_ID, TankConstants.LEFT_BACK_ID, TankConstants.LEFT_INVERTED); 
    rightModule = new TankModule(TankConstants.RIGHT_FRONT_ID, TankConstants.RIGHT_BACK_ID, TankConstants.RIGHT_INVERTED); 

  }

  @Override
  public void periodic() {
    WheelSpeeds applied = getWheelsSpeeds();
    SmartDashboard.putNumber("Tank/Left applied output", applied.left);
    SmartDashboard.putNumber("Tank/Right applied output", applied.right);
    SmartDashboard.putNumber("Tank/Total output", (getWheelsSpeeds().left + getWheelsSpeeds().right) / 2.0 );

  
  }

  public void setWheelsSpeeds(double forward, double rotation) {
    WheelSpeeds speeds = DifferentialDrive.arcadeDriveIK(forward, rotation, TankConstants.SQUARE_INPUTS);

    leftModule.setSpeed(speeds.left);
    rightModule.setSpeed(speeds.right);

  }

  public WheelSpeeds getWheelsSpeeds() {
     return new WheelSpeeds(leftModule.getAppliedOutput(), rightModule.getAppliedOutput());
  }

  public void stop() {
    leftModule.stop();
    rightModule.stop();
  }


}
