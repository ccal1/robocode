package bbc;
import robocode.*;

public class EnemyRobot{
	double x, y, bearing, energy, velocity, heading, distance;
	String name;
	long time;
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getBearing() {
		return bearing;
	}
	public double getEnergy() {
		return energy;
	}
	public double getVelocity() {
		return velocity;
	}
	public double getHeading() {
		return heading;
	}
	public double getDistance() {
		return distance;
	}
	public String getName() {
		return name;
	}
	public void setDistance(double x, double y)
	{
		this.distance = Helper.distance(this.x,this.y,x,y);
	}
	public double getFirePower()
	{
		return Helper.firePower(this.distance);
	}
	public double getBulletSpeed()
	{
		return Helper.bulletSpeed(Helper.firePower(this.distance));
	}
	public long getTimeToTarget()
	{
		return Helper.time(this.distance, this.getBulletSpeed());
	}
	
	public EnemyRobot() {
		reset();
	}
	public EnemyRobot(ScannedRobotEvent event) {
		this.update(event);
	}
	public EnemyRobot(ScannedRobotEvent event, RobotState state) {
		this.update(event, state);
	}
	public void update(ScannedRobotEvent event, RobotState state) {
		this.update(event);

		//double absAngle = (state.getHeading() + event.getBearing());
		//if (absAngle < 0) absAngle += 360;
		this.x = Helper.enemyX(state.getX(), state.getHeading(), event.getBearing(), event.getDistance());
		this.y = Helper.enemyY(state.getY(), state.getHeading(), event.getBearing(), event.getDistance());
		//System.out.println("Enemy position:" + this.getX() + " " + this.getY());
	}
	
	public boolean noEnemy() {
		if (this.name.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public double getFutureX(long _time){
		return this.x + Math.sin(Math.toRadians(this.heading)) * this.velocity * _time;
	}
	public double getFutureX(){
		return Helper.enemyFutureX(this.getX(), this.getHeading(), this.getVelocity(), this.getTimeToTarget());
	}
	
	public double getFutureY(long _time){
		return this.y + Math.cos(Math.toRadians(this.heading)) * this.velocity * _time;
	}
	public double getFutureY(){
		return Helper.enemyFutureY(this.getY(), this.getHeading(), this.getVelocity(), this.getTimeToTarget());
	}
	public boolean isUpdated(long time){
		return time>(16 + this.time);
	}
	public void print()
	{
		System.out.println("Name : " + this.getName() + " time:"+ this.getTime());
	}
	public void update(ScannedRobotEvent event){
		this.name = event.getName();
		this.bearing = event.getBearing();
		this.distance = event.getDistance();
		this.energy = event.getEnergy();
		this.heading = event.getHeading();
		this.velocity = event.getVelocity();
		this.time=event.getTime();
	}
	
	public void update (ScannedRobotEvent event, Robot robot){
		update(event);
		double absAngle = (robot.getHeading() + event.getBearing());
		if (absAngle < 0) absAngle += 360;
		this.x = robot.getX() + Math.sin(Math.toRadians(absAngle)) * event.getDistance();
		this.y = robot.getY() + Math.cos(Math.toRadians(absAngle)) * event.getDistance();
	}
	
	public void reset() {
		this.name = "";
		this.bearing = 0;
		this.distance = 0;
		this.energy = 0;
		this.heading = 0;
		this.velocity = 0;
		this.x = 0;
		this.y = 0;
		this.time = 0;
	}
}
