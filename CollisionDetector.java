package soccer.slime;

import java.awt.Rectangle;
import java.util.ArrayList;

public class CollisionDetector {
	
	private ArrayList<SlimeEntity> slimes;
	private ArrayList<BallEntity> balls;
	private PlatformEntity platform;
	
	private double gravity;

	public CollisionDetector(ArrayList<SlimeEntity> slimes, ArrayList<BallEntity> balls, ArrayList<NetEntity> nets, PlatformEntity platform) {
		
		this.slimes = slimes;
		this.balls = balls;
		this.platform = platform;
		
		gravity = 1;
	}
	
	public void checkAllCollisions() {
		
		// Gravity to ball
		double gravity_speed_reduction = 0.01;
		for (BallEntity ball: balls) {
			
			// apply gravity change to angle
			if(ball.getAngle()<180 && ball.getAngle()>0) {
				ball.setAngle(ball.getAngle()+gravity);
				if(ball.getAngle()>180) {
					ball.setAngle(180);
				}
			} else if (ball.getAngle()>180) {
				ball.setAngle(ball.getAngle()-gravity);
				if(ball.getAngle()<180) {
					ball.setAngle(180);
				}
			}
			
			// apply gravity change to speed
			if(ball.getAngle()>90 && ball.getAngle()<270) {
				if(ball.getSpeed()!=0) {
					ball.updateSpeed(gravity_speed_reduction);
				}
			} else if (ball.getAngle()<=90 || ball.getAngle()>=270) {
				if(ball.getSpeed()!=0) {
					ball.updateSpeed(-gravity_speed_reduction);
				}
			}
			
			//change direction at peak altitude
			if (ball.getAngle()==0 && ball.getSpeed()==0) {
				ball.setAngle(180);
				if(!(checkCollisionTwoRectangles(ball.getCollisionArea(), platform.getCollisionArea()))) {
					ball.setSpeed(0.01);
				}
			}
			
		}

		// Ball and platform
		double bounce_speed_reduction = -0.8;
		for (BallEntity ball: balls) {
			if(checkCollisionTwoRectangles(ball.getCollisionArea(), platform.getCollisionArea())){
				if (ball.get_time_since_last_collision() == 0) {
					if(ball.getAngle()<=180) {
						double bounce_angle = 180 - ball.getAngle();
						ball.setAngle(bounce_angle);
					} else {
						double bounce_angle = 360 - ball.getAngle();
						ball.updateAngle(bounce_angle);
					}
					
					if((ball.getSpeed()-bounce_speed_reduction)<0) {
						ball.setSpeed(0);
					} else {
						ball.updateSpeed(bounce_speed_reduction);
					}
					
					//Update collision timer
					ball.set_time_since_last_collision(5);
					
				}
			}
		}
		
		// Ball and slime
		for (BallEntity ball: balls) {
			for (SlimeEntity slime: slimes) {
				if(checkCollisionBallAndSlime(ball, slime)) {
					
					double distance_between_centers = slime.findDistanceBetweenTwoPoints(slime.getCenter(), ball.getCenter());
					double angle_slime_center_ball_center = slime.findAnglePoint1ToPoint2(slime.getCenter(), ball.getCenter(), distance_between_centers);
					
					ball.setAngle(angle_slime_center_ball_center);
					ball.updateSpeed(1);
				}
			}
		}
		
		//Reduce collision counters
		for (BallEntity ball: balls) {
			ball.update_time_since_last_collision(-1);
		}
	}
	
	// Collision detection
	public boolean checkCollisionTwoRectangles(Rectangle rect_a, Rectangle rect_b) {
		
		if (rect_a.intersects(rect_b)) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean checkCollisionBallAndSlime(BallEntity ball, SlimeEntity slime) {
		
		int distance_between_slime_center_and_ball_center = (int)(slime.findDistanceBetweenTwoPoints(slime.getCenter(), ball.getCenter()));
		int collision_distance = (int)((ball.getSize()/2) + (slime.getSize()/2)); 
		
		if ((collision_distance-1) >= distance_between_slime_center_and_ball_center && distance_between_slime_center_and_ball_center <= (collision_distance+1)) {
			return true;
		} else {
			return false;
		}
	}

}