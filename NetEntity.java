package soccer.slime;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class NetEntity {
	
	final private int SCREEN_WIDTH;
	
	final int NET_HORIZONTAL_LINES;
	final int NET_VERTICAL_LINES;
	final int NET_HEIGHT;
	final int NET_WIDTH;
	final int[] NET_LOCATION;
	private Color color;
	
	int net_line_width;
	int penalty_line_height;
	int square_size;
	
	final private int[] PLATFORM_LOCATION;
	
	final private boolean RIGHT_SIDE;
	
	private Rectangle net_top_collision_area;

	
	public NetEntity(int SCREEN_HEIGHT, int SCREEN_WIDTH, int[] PLATFORM_LOCATION, Color color, boolean right_side) {
		
		this.SCREEN_WIDTH = SCREEN_WIDTH;
		
		this.NET_HORIZONTAL_LINES = 13; 
		this.NET_VERTICAL_LINES = 6;
		this.NET_HEIGHT = (int) (SCREEN_HEIGHT/4.93);
		this.NET_WIDTH = (int) (SCREEN_HEIGHT/10.177);
		
		this.RIGHT_SIDE = right_side;
		this.square_size = (int) (this.NET_WIDTH/this.NET_VERTICAL_LINES);
		this.net_line_width = (int) (this.NET_WIDTH/30);
		this.penalty_line_height = (int) (this.net_line_width*4);
		
		if(this.RIGHT_SIDE) {
			this.NET_LOCATION = new int[] {(this.SCREEN_WIDTH-this.NET_WIDTH-this.square_size), (int)(PLATFORM_LOCATION[1]-this.NET_HEIGHT)};
		} else {
			this.NET_LOCATION = new int[] {0, (int)(PLATFORM_LOCATION[1]-this.NET_HEIGHT)};
		}

		this.PLATFORM_LOCATION = PLATFORM_LOCATION;
		
		this.color = color;
		
		this.net_top_collision_area = new Rectangle((int)(NET_LOCATION[0]), (int)(NET_LOCATION[1]), NET_WIDTH+square_size, net_line_width);
		
		update();
		
	}
	
	public void update() {
	}
	
	public void drawEntity(Graphics2D g) {
		
		g.setColor(this.color);
		
		if (this.RIGHT_SIDE) {
			// Main post
			g.fillRect(this.NET_LOCATION[0], this.NET_LOCATION[1], this.square_size, this.NET_HEIGHT);
			// Line under net
			g.fillRect((this.SCREEN_WIDTH -this.NET_HEIGHT), (this.PLATFORM_LOCATION[1]+penalty_line_height), this.NET_HEIGHT, penalty_line_height);
			// Nets
			for (int line = 0; line<NET_VERTICAL_LINES; line++) {
				g.fillRect((NET_LOCATION[0]+(line * square_size)+(square_size*2)), NET_LOCATION[1], net_line_width, (int) (this.NET_HEIGHT*1.02));
			}
			for (int line = 0; line<NET_HORIZONTAL_LINES; line++) {
				g.fillRect((this.SCREEN_WIDTH-this.NET_WIDTH-this.square_size)+square_size,(NET_LOCATION[1] + (line * (square_size))), (NET_WIDTH+net_line_width), net_line_width);
			}
		} else {
			// Main post
			g.fillRect((this.NET_LOCATION[0]+NET_WIDTH), this.NET_LOCATION[1], this.square_size, this.NET_HEIGHT);
			// Line under net
			g.fillRect(0, (this.PLATFORM_LOCATION[1]+penalty_line_height), NET_HEIGHT, penalty_line_height);
			// Nets
			for (int line = 0; line<NET_VERTICAL_LINES; line++) {
				g.fillRect((line * (square_size)), NET_LOCATION[1], net_line_width, (int) (NET_HEIGHT*1.02));
			}
			for (int line = 0; line<NET_HORIZONTAL_LINES; line++) {
				g.fillRect(0,(NET_LOCATION[1] + (line * (square_size))), (NET_WIDTH+net_line_width), net_line_width);
			}
		}
		
		g.setColor(Color.red);
		g.fillRect((int)(NET_LOCATION[0]), (int)(NET_LOCATION[1]), NET_WIDTH+square_size, 2);
		
	}

	// Getters\
	public Rectangle getCollisionAreaTop() {
		return net_top_collision_area;
	}
	
}

