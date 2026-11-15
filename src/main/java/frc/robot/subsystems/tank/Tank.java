// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.tank;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotGearing;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotWheelSize;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Tank extends SubsystemBase {

  private final TankModule leftModule;
  private final TankModule rightModule;

  private final Field2d field = new Field2d();

    private final DifferentialDrivetrainSim driveSim = RobotBase.isSimulation()
      ? DifferentialDrivetrainSim.createKitbotSim(
          KitbotMotor.kDualCIMPerSide,
          KitbotGearing.k10p71,
          KitbotWheelSize.kSixInch,
          null)
      : null;

      private final Sendable driveWidget = new Sendable() {
    @Override
    public void initSendable(SendableBuilder builder) {
      builder.setSmartDashboardType("DifferentialDrive");
      builder.setActuator(false); 
      
      builder.addDoubleProperty("Left Motor Speed", () -> getWheelsSpeeds().left,null);
      builder.addDoubleProperty("Right Motor Speed", () -> getWheelsSpeeds().right, null);
    }
  };

  public Tank() {
    leftModule = new TankModule(TankConstants.LEFT_FRONT_ID, TankConstants.LEFT_BACK_ID, TankConstants.LEFT_INVERTED); 
    rightModule = new TankModule(TankConstants.RIGHT_FRONT_ID, TankConstants.RIGHT_BACK_ID, TankConstants.RIGHT_INVERTED); 

    SmartDashboard.putData("Tank/Field", field);
  }

  @Override
  public void periodic() {
    WheelSpeeds applied = getWheelsSpeeds();
    SmartDashboard.putNumber("Tank/Left output side", getWheelsSpeeds().left);
    SmartDashboard.putNumber("Tank/Right output side", getWheelsSpeeds().right);
    SmartDashboard.putNumber("Tank/Left applied speed", applied.left);
    SmartDashboard.putNumber("Tank/Right applied speed", applied.right);
    SmartDashboard.putNumber("Tank/Total output speed", (getWheelsSpeeds().left + getWheelsSpeeds().right) / 2.0 );

  
  }

  public void setWheelsSpeeds(double forward, double rotation) {
    WheelSpeeds speeds = DifferentialDrive.arcadeDriveIK(forward, rotation, TankConstants.SQUARE_INPUTS);

    leftModule.setSpeed(speeds.left);
    rightModule.setSpeed(speeds.right);

  }

  public WheelSpeeds getWheelsSpeeds() {
     return new WheelSpeeds(leftModule.getModuleSpeed(), rightModule.getModuleSpeed());
  }

  public void stop() {
    leftModule.stop();
    rightModule.stop();
  }

  @Override
  public void simulationPeriodic() {
    WheelSpeeds out = getWheelsSpeeds();
    double batteryVolts = RobotController.getBatteryVoltage();

    driveSim.setInputs(out.left * batteryVolts, out.right * batteryVolts);
    driveSim.update(0.02);

    field.setRobotPose(driveSim.getPose());

    SmartDashboard.putNumber("Tank/ Left Vel (m/s)", driveSim.getLeftVelocityMetersPerSecond());
    SmartDashboard.putNumber("Tank/ Right Vel (m/s)", driveSim.getRightVelocityMetersPerSecond());
    SmartDashboard.putData("Tank/Drive Widget", driveWidget);
  }
}
