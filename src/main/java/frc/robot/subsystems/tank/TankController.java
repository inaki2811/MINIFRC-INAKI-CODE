package frc.robot.subsystems.tank;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;

public class TankController {

    private final SparkMax sparkLeader;
    private final SparkMax sparkFollower;

    public TankController(TankIo io, boolean isInverted) {
        this.sparkLeader = io.getSparkFront();
        this.sparkFollower = io.getSparkBack();

        configureMotors(isInverted);
    }

    private void configureMotors(boolean isInverted) {
        SparkMaxConfig baseConfig = new SparkMaxConfig();

        baseConfig.idleMode(IdleMode.kBrake);
        baseConfig.voltageCompensation(TankConstants.VOLTAGE_COMPENSATION);
        baseConfig.smartCurrentLimit(TankConstants.CURRENT_LIMIT);

        SparkMaxConfig leaderConfig = new SparkMaxConfig();
        leaderConfig.apply(baseConfig).inverted(isInverted);
        
        SparkMaxConfig followerConfig = new SparkMaxConfig();
        followerConfig.apply(baseConfig).follow(sparkLeader);

        sparkLeader.configure(leaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        sparkFollower.configure(followerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void setVelocity(double leftVelocity) {
        sparkLeader.set(leftVelocity);
    }

}
