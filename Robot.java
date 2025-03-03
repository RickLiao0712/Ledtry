package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.LEDPattern;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;


public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer;
  private Joystick stick = new Joystick(0);

  private AddressableLED m_led;
  private AddressableLEDBuffer m_ledBuffer;
  private AddressableLEDBufferView mView;
  private final LEDPattern m_rainbow = LEDPattern.rainbow(255, 128);
  private static final Distance kLedSpacing = Meters.of(1 / 120.0);
  private final LEDPattern m_scrollingRainbow = m_rainbow.scrollAtAbsoluteSpeed(MetersPerSecond.of(1), kLedSpacing);
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    m_led = new AddressableLED(0);
    m_ledBuffer = new AddressableLEDBuffer(60);
    m_led.setLength(m_ledBuffer.getLength());// Set the data
    m_led.setData(m_ledBuffer);
    m_led.start();

    AddressableLEDBufferView m_left = m_ledBuffer.createView(0, 59);
    AddressableLEDBufferView m_right = m_ledBuffer.createView(60, 119).reversed();


    m_robotContainer = new RobotContainer();
  }

  private void Run(){
    LEDPattern gradient = LEDPattern.gradient(LEDPattern.GradientType.kDiscontinuous, Color.kRed, Color.kBlue);
    gradient.applyTo(m_ledBuffer);
    m_led.setData(m_ledBuffer);
  }

  // private void Breathe(){
  //   LEDPattern base = LEDPattern.discontinuousGradient(Color.kRed, Color.kBlue);
  //   LEDPattern pattern = base.breathe(Seconds.of(1));
  //   pattern.applyTo(m_ledBuffer);
  //   m_led.setData(m_ledBuffer);
  // }

  @Override
  public void robotPeriodic() {
    m_scrollingRainbow.applyTo(m_ledBuffer);
    m_led.setData(m_ledBuffer);
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    m_scrollingRainbow.applyTo(m_ledBuffer);
    m_led.setData(m_ledBuffer);
  }

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    m_scrollingRainbow.applyTo(m_ledBuffer);
    m_led.setData(m_ledBuffer);

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    
  if(stick.getRawButtonPressed(1)){
    LEDPattern red = LEDPattern.solid(Color.kRed);
    red.applyTo(m_ledBuffer);
    m_led.setData(m_ledBuffer);
    // Breathe();
  }else if (stick.getRawButtonPressed(2)){
    LEDPattern Blue = LEDPattern.solid(Color.kBlue);
    Blue.applyTo(m_ledBuffer);
    m_led.setData(m_ledBuffer);
  }else{
    Run();
  }
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

}