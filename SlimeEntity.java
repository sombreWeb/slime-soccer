/**
 * 
 */
package soccer.slime;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
/**
 * @author Conor
 *
 */
public class SlimeEntity {
	
	final private int SCREEN_HEIGHT, STROKE_SIZE;
	
	final public int SIZE; 
	final private int[] START_POSITION;
	public int[] location;
	private double[] center_position;
	
	final private int EYE_SIZE;
	final private double PUPIL_SIZE;
	public int[] eye_position;//TODO make private
	
	public double eye_to_ball_angle;
	
	public double[] center_eye_position;//TODO make private
	private double distance_between_eye_and_ball;
	final double DISTANCE_FROM_CENTER_EYE_TO_CENTER_PUPIL;
	public double[] pupil_location; //TODO make private
	private int distance_between_slime_start_and_edge;
	
	private int OFFSET;
	final private int EYE_OFFSET;
	
	// Keyboard input keys
	final int[] key_set;
	
	private Color color;
	
	
	public SlimeEntity(int SCREEN_HEIGHT, int SCREEN_WIDTH, int STROKE_SIZE, int[] PLATFORM_LOCATION, boolean right_side, BallEntity ball, Color color, int[] key_set) {
		
		this.SCREEN_HEIGHT = SCREEN_HEIGHT;
		this.STROKE_SIZE = STROKE_SIZE;
		this.SIZE = (int) (SCREEN_HEIGHT/4.867);
		
		this.EYE_SIZE = (int) (SCREEN_HEIGHT/23.224);
		this.PUPIL_SIZE = EYE_SIZE/2.1;
		this.DISTANCE_FROM_CENTER_EYE_TO_CENTER_PUPIL = this.EYE_SIZE/3.2;
		
		this.distance_between_slime_start_and_edge = (int) ((SCREEN_HEIGHT/1.974)-this.SIZE);
		
		this.key_set = key_set;
		
		if(right_side) {
			this.OFFSET = 0;
		} else {
			this.OFFSET = (int) (SCREEN_WIDTH-(this.distance_between_slime_start_and_edge*2)-this.SIZE);
			
			// temp
			this.OFFSET =1000;
		}
		
		this.START_POSITION = new int[] {(int) (this.distance_between_slime_start_and_edge+this.OFFSET), (int)(PLATFORM_LOCATION[1]-(this.SIZE/2)-(this.STROKE_SIZE/2))};
		this.location = this.START_POSITION;
		
		//temp
		this.location[0] = 400;
		
		if(right_side) {
			this.EYE_OFFSET = 0;
		} else {
			this.EYE_OFFSET = (int) (((this.SCREEN_HEIGHT/7.2))-(this.EYE_SIZE/2));
		}
		
		this.color = color;
		
		update(ball);
		
	}
	
	public void update(BallEntity ball) {
		
		// Update the direction the pupil is looking
		eye_position = new int[] {(int) ((location[0]+(SCREEN_HEIGHT/7.2))-EYE_OFFSET), (int) (location[1]+(SCREEN_HEIGHT/46))};
		center_eye_position = new double[] {(eye_position[0]+(EYE_SIZE/2)), (eye_position[1]+(EYE_SIZE/2))};
		
		distance_between_eye_and_ball = findDistanceBetweenTwoPoints(center_eye_position, (ball.getCenter()));
		eye_to_ball_angle = findAnglePoint1ToPoint2(center_eye_position, ball.getCenter(), distance_between_eye_and_ball);
		pupil_location = findCenterPupil(eye_to_ball_angle, DISTANCE_FROM_CENTER_EYE_TO_CENTER_PUPIL, center_eye_position, ball);
		
		// Update the center position
		center_position = new double[]{(location[0]+(SIZE/2)), (location[1]+(SIZE/2))};
		
	}
	
	public void drawEntity(Graphics2D g) {
		
		Arc2D arc1 = new Arc2D.Double(location[0],location[1], SIZE, SIZE, 0, 180, Arc2D.PIE);
		g.setColor(color);
		g.draw(arc1);
		g.fill(arc1);
		g.setColor(Color.white);
		g.fillOval(eye_position[0], eye_position[1], EYE_SIZE, EYE_SIZE);
		g.setColor(Color.black);
		g.fillOval((int)(pupil_location[0]-(int)(PUPIL_SIZE/2)), (int)(pupil_location[1]-(int)(PUPIL_SIZE/2)), (int)(PUPIL_SIZE), (int)(PUPIL_SIZE));
		
	}
	
	public double[] findCenterPupil(double angle, double length_from_eye, double[] center_eye_location, BallEntity ball) {
		
		double startX = center_eye_location[0];
		double startY = center_eye_location[1];
	    double length = length_from_eye;
	    
	    int endX = 0, endY = 0;
		if(ball.center_position[0] >= center_eye_location[0] && ball.center_position[1] <= center_eye_location[1]) {
			endX = (int) (startX + (double)Math.cos(Math.toRadians(angle-90)) * length);
		    endY = (int) (startY + (double)Math.sin(Math.toRadians(angle-90)) * length);
		} else if (ball.center_position[0] > center_eye_location[0] && ball.center_position[1] > center_eye_location[1]) {
			endX = (int) (startX + (double)Math.sin(Math.toRadians(angle-90)) * length);
		    endY = (int) (startY + (double)Math.cos(Math.toRadians(angle-90)) * length);
		} else if (ball.center_position[0] <= center_eye_location[0] && ball.center_position[1] >= center_eye_location[1]) {
			endX = (int) (startX + (double)Math.cos(Math.toRadians(angle-90)) * length);
		    endY = (int) (startY + (double)Math.sin(Math.toRadians(angle-90)) * length);
		} else if (ball.center_position[0] < center_eye_location[0] && ball.center_position[1] < center_eye_location[1]) {
			endX = (int) (startX + (double)Math.sin(Math.toRadians(angle-90)) * length);
		    endY = (int) (startY + (double)Math.cos(Math.toRadians(angle-90)) * length);
		}
		
	    double[] center_of_pupil = new double[] {endX, endY};
	    
	    return center_of_pupil;
	}

	
	// Utility
	public double findDistanceBetweenTwoPoints(double[] point_1_xy, double[] point_2_xy) {
		
		double distance_between_points = (double) Math.sqrt( (Math.pow(point_2_xy[0]-point_1_xy[0], 2)) + (Math.pow(point_2_xy[1]-point_1_xy[1], 2)));
		return distance_between_points;
	}
	
	public double findAnglePoint1ToPoint2(double[] point_1_xy, double[] point_2_xy, double distance_between_points) {
		
		double angle_radians = Math.acos((point_1_xy[0]-point_2_xy[0]) / distance_between_points);
		
		double angle_degrees = Math.toDegrees(angle_radians);
		
		double angle_degrees_north_0 = angle_degrees - 90;

		int direction_correction = 0;
		if(point_2_xy[0] >= point_1_xy[0] && point_2_xy[1] <= point_1_xy[1]) {
			direction_correction = 0;
		} else if (point_2_xy[0] > point_1_xy[0] && point_2_xy[1] > point_1_xy[1]) {
			direction_correction = 90;
		} else if (point_2_xy[0] <= point_1_xy[0] && point_2_xy[1] >= point_1_xy[1]) {
			direction_correction = 180;
		} else if (point_2_xy[0] < point_1_xy[0] && point_2_xy[1] < point_1_xy[1]) {
			direction_correction = 270;
		}
		
		double angle_degrees_north_0_corrected = Math.abs(angle_degrees_north_0) + direction_correction;
		
		return angle_degrees_north_0_corrected;
	}
	
	// Update
	public void updateLocation(double x, double y) {
		location[0] += x;
		location[1] += y;
	}
	
	// Getters
	public double[] getCenter() {
		return center_position;
	}
	
	public int getSize() {
		return SIZE;
	}
	
}
