/**
 * 
 */
package soccer.slime;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;


/**
 * @author Conor
 *
 */
public class BallEntity {
	
	final int SIZE;
	final double[] START_POSITION;
	public double[] location;
	public double[] center_position; 
	
	private Rectangle collision_area;
	public double current_speed;
	private double current_direction;
	
	public double roll_speed;
	
	public int time_since_last_collision;
	
	//public double[] current_tick_movement;

	private Color color;
	
	
	public BallEntity(int SCREEN_HEIGHT, int SCREEN_WIDTH, Color color, Rectangle platform_collision_area) {
		
		this.SIZE = (int) (SCREEN_HEIGHT/18.3);
		this.START_POSITION = new double[] {((SCREEN_WIDTH/2)-(SIZE/2)), ((SCREEN_HEIGHT/2)-(SIZE/2))};
	//	this.START_POSITION = new double[] {SCREEN_WIDTH-40,-200};
		this.location = this.START_POSITION;
		
		//temp
		this.location[0] = 300;
		
		this.center_position = new double[] {(this.location[0]+(SIZE/2)), (this.location[1]+(SIZE/2))};
		
		this.color = color;
		
		this.collision_area = new Rectangle((int)(location[0]), (int)(location[1]), SIZE, SIZE);
		
		this.current_speed = 0.1;
		this.current_direction = 180;
		
		time_since_last_collision = 0;
		
		roll_speed = 1;
		//this.current_tick_movement = new double[] {0.0,0.0};
		
		update();
	}
	
	public void update() {
		
		double[] movement= getMoveVectorFromSpeedAndAngle(current_speed, current_direction);
		location[0] += movement[0];
		location[1] += movement[1];
		
	}
	
	public void drawEntity(Graphics2D g) {
		
		g.setColor(color);
		g.fillOval((int)(this.location[0]), (int)(this.location[1]), this.SIZE, this.SIZE);
		
		//temp below
		g.setColor(Color.BLACK);
		int x = (int) ((SIZE/2) * Math.cos(Math.toRadians(getAngle()-90)));
		int y = (int) ((SIZE/2) * Math.sin(Math.toRadians(getAngle()-90)));
		x += (int)(getCenter()[0]);
		y += (int)(getCenter()[1]);
		
		g.drawLine((int)(getCenter()[0]),(int)(getCenter()[1]), x, y);
	}

	
	public void updateLocationByValue(double valueToAdd, int x_or_y) {
		location[x_or_y] += valueToAdd;
	}
	
	
	// Utility
	public double[] getMoveVectorFromSpeedAndAngle(double speed, double angle) {
		
		int reverse = -1;
		double angle_radians = 0;
		if (angle != 180) {		
			angle_radians = Math.toRadians(angle);
			reverse = 1;
		}
		
	    double x = speed * (double)Math.sin(angle_radians) * reverse;
	    double y = speed * (double)Math.cos(angle_radians) * reverse * -1;
	    
	    return new double[] {x,y};
	}
	
	// Updates
	public void updateSpeed(double speed) {
		current_speed += speed;
		
		if(current_speed<0) {
			current_speed = 0;
		}
	}
	
	public void updateAngle(double angle) {
		current_direction += angle;
	}
	
	public void update_time_since_last_collision(int time_since_last_collision) {
		this.time_since_last_collision += time_since_last_collision;
		if (this.time_since_last_collision<0) {
			set_time_since_last_collision(0);
		}
	}
	
	// Getters
	public double[] getCenter() {
		this.center_position[0] = (this.location[0]+(SIZE/2)); 
		this.center_position[1] = (this.location[1]+(SIZE/2));
		return center_position;
	}
	
	public int getSize() {
		return SIZE;
	}
	
	public double getSpeed() {
		return current_speed;
	}
	
	public double getAngle() {
		return current_direction;
	}
	
	public Rectangle getCollisionArea() {
		collision_area.x = (int)(location[0]);
		collision_area.y = (int)(location[1]);
		return collision_area;
	}
	
	public int get_time_since_last_collision() {
		return time_since_last_collision;
	}
	
	// Setters
	public void setAngle(double angle) {
		current_direction = angle;
	}
	
	public void setSpeed(double speed) {
		current_speed = speed;
		if(current_speed<0) {
			current_speed = 0;
		}
	}
	
	public void set_time_since_last_collision(int time_since_last_collision) {
		this.time_since_last_collision = time_since_last_collision;
	}
	
}
