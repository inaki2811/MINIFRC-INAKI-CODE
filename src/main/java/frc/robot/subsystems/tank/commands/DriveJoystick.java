// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.tank.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.tank.Tank;
import frc.robot.subsystems.tank.TankConstants;  

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class DriveJoystick extends Command {

    private final Tank tank;
    private final Supplier<Double> forwardSupplier;
    private final Supplier<Double> turnSupplier;

  public DriveJoystick(Tank tank, Supplier<Double> forwardSupplier, Supplier<Double> turnSupplier) {
    this.tank = tank;
    this.forwardSupplier = forwardSupplier;
    this.turnSupplier = turnSupplier;
    
    addRequirements(tank);

  }

  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double forward = processAxis(forwardSupplier, TankConstants.FORWARD_SCALE);
    double turn = processAxis(turnSupplier, TankConstants.TURN_SCALE);

    tank.setWheelsSpeeds(forward, turn);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    tank.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  private static double processAxis(Supplier<Double> axisSupplier, double scale) {
    double value = MathUtil.applyDeadband(axisSupplier.get(), TankConstants.DEADBAND);

    return value * scale;
  }
}
