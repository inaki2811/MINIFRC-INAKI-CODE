package frc.robot.subsystems.tank;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;

public class TankModule {
    
    private final TankIO io;
    private final TankController controller;

    public TankModule(int frontId, int backId, boolean inverted) {
        this.io = new TankIO(frontId, backId);
        this.controller = new TankController(io, inverted);
    }


    public double getAppliedOutput() {
        double frontMotorSpeed = io.getSparkFront().getAppliedOutput();
        double backMotorSpeed = io.getSparkBack().getAppliedOutput();
        return (frontMotorSpeed + backMotorSpeed) / 2.0;

    }

    public void stop() {
        io.stopMotors();
    } 

    public void setSpeed(double speed) {
        controller.setOutput(speed);
    }



}

