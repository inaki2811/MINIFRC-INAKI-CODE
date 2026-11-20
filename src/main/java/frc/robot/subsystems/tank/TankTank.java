package frc.robot.subsystems.tank;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class TankTank {
    
    private final SparkMax sparkFront;
    private final SparkMax sparkBack;


    public TankTank(int sparkMaxFrontId , int sparkMaxBackId) {
        this.sparkFront = new SparkMax(sparkMaxFrontId, MotorType.kBrushed);
        this.sparkBack = new SparkMax(sparkMaxBackId, MotorType.kBrushed);


    }

    public void stopMotors() {
        sparkFront.set(0);
    }

    public SparkMax getSparkFront() {
        return sparkFront;
    }

    public SparkMax getSparkBack() {
        return sparkBack;
    }

    
    
}
