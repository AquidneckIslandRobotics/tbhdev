package org.usfirst.frc.team78.robot;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
//	final String defaultAuto = "Default";
//	final String customAuto = "My Auto";
//	String autoSelected;
//	SendableChooser<String> chooser = new SendableChooser<>();
	
	VictorSP leftShooter = new VictorSP(0);
	VictorSP rightShooter = new VictorSP(1);
	
	double currentMotorValue = 0;
	double previousMotorValue = 0;
	double previousError = 0;
	
	double tbh = 0;
	double tbhGain = 1.5E-5;
	
	Encoder shooterEnc = new Encoder(0, 1, true, EncodingType.k4X);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		leftShooter.stopMotor();
		rightShooter.stopMotor();
		
		leftShooter.setInverted(true);
		rightShooter.setInverted(true);
		
		shooterEnc.reset();
		shooterEnc.setDistancePerPulse(9.78);
		shooterEnc.setSamplesToAverage(10);
		
//		chooser.addDefault("Default Auto", defaultAuto);
//		chooser.addObject("My Auto", customAuto);
//		SmartDashboard.putData("Auto choices", chooser);
		

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
//		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
//		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
//		switch (autoSelected) {
//		case customAuto:
			// Put custom auto code here
//			break;
//		case defaultAuto:
//		default:
			// Put default auto code here
//			break;
//		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopInit() {
		
		leftShooter.stopMotor();
		rightShooter.stopMotor();
		
		
    	SmartDashboard.putNumber("Target RPM", 0.0);
	}
	
	@Override
	public void disabledInit() {
		
		leftShooter.stopMotor();
		rightShooter.stopMotor();
	}
	
	@Override
	public void teleopPeriodic() {
		
		
    	SmartDashboard.putNumber("Left Shooter Motor PWM Value", leftShooter.get());
    	SmartDashboard.putNumber("Right Shooter Motor PWM Value", rightShooter.get());

    	
    	SmartDashboard.putBoolean("Apply", false);
    	
    	double targetRPM = SmartDashboard.getNumber("Target RPM", 0.0);
    	
    	double currentRPM = shooterEnc.getRate();
    	
    	double error = targetRPM - currentRPM;
    	
    	currentMotorValue += tbhGain*error;
    	
    	if ((previousError > 0) != (error > 0)) {
    		currentMotorValue = 0.5 * (currentMotorValue + tbh);
            tbh = currentMotorValue;

            previousError = error;
        }
    	
    	if (targetRPM == 0){
    		currentMotorValue = 0;
    	}
    	
    	leftShooter.set(currentMotorValue);
    	rightShooter.set(currentMotorValue);
    	
    	
//    	currentMotorValue = (currentMotorValue - previousMotorValue)/2;
//    	currentMotorValue = currentMotorValue + tbhGain *(targetRPM-currentRPM);
    	
//    	if (currentRPM < targetRPM){
//    		leftShooter.set(1);
//        	rightShooter.set(1);
//    	} else  if (currentRPM == targetRPM){
//    		leftShooter.set(leftShooter.get());
//        	rightShooter.set(rightShooter.get());
//    	} else {
//    		leftShooter.set(0);
//        	rightShooter.set(0);
//    		
//    	}
    	
    	
    	
    	SmartDashboard.putNumber("Shooter Encoder", shooterEnc.getRate()); 	

    	
 //   	previousMotorValue = currentMotorValue;
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

