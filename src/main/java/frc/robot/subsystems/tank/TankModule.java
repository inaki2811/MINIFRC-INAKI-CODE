package frc.robot.subsystems.tank;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;

public class TankModule {
    
    private final TankIo io;
    private final TankController controller;

    public TankModule(int frontId, int backId, boolean inverted) {
        this.io = new TankIo(frontId, backId);
        this.controller = new TankController(io, inverted);
    }

    public DifferentialDrive.WheelSpeeds getWheelSpeed(TankModule leftModule, TankModule rightModule) {
        double leftSpeed = leftModule.getModuleSpeed();
        double rightSpeed = rightModule.getModuleSpeed();

        return new DifferentialDrive.WheelSpeeds(leftSpeed, rightSpeed);
    }

    public double getModuleSpeed() {
        double frontMotorSpeed = io.getSparkFront().getAppliedOutput();
        double backMotorSpeed = io.getSparkBack().getAppliedOutput();
        return (frontMotorSpeed + backMotorSpeed) / 2.0;

    }

    public void stop() {
        io.stopMotors();
    }

    public void setSpeed(double Speed) {
        controller.setVelocity(Speed);
    }



}

