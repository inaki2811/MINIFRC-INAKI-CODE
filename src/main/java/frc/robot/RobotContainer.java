// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.tank.Tank;
import frc.robot.subsystems.tank.commands.DriveJoystick;

public class RobotContainer {

  private final Tank tank;

  private final XboxController driverController;



  public RobotContainer() {
    this.tank = new Tank();
    this.driverController = new XboxController(0);


    tank.setDefaultCommand(new DriveJoystick(
      tank,
      () -> -driverController.getLeftY(),
      () -> -driverController.getRightX()
    ));
    configureBindings();
  }



  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
