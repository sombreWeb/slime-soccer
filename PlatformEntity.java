package soccer.slime;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class PlatformEntity {
	
		
	private Color color;
	
	final int PLATFORM_HEIGHT;
	final int PLATFORM_WIDTH;
	final int[] location;
	
	private Rectangle collision_area;
	
	public PlatformEntity(int SCREEN_HEIGHT, int SCREEN_WIDTH, Color color) {
		
		this.PLATFORM_HEIGHT = (int) (SCREEN_HEIGHT/5.088);
		this.PLATFORM_WIDTH = SCREEN_WIDTH;
		this.location = new int[] {0, (int)(SCREEN_HEIGHT-PLATFORM_HEIGHT)};
		
		this.collision_area = new Rectangle(this.location[0], this.location[1], this.PLATFORM_WIDTH, this.PLATFORM_HEIGHT);
		
		this.color = color;
		
		update();
		
	}
	
	public void update() {
	}
	
	public void drawEntity(Graphics2D g) {
		
		g.setColor(this.color);
		g.fillRect(this.location[0], this.location[1], this.PLATFORM_WIDTH, this.PLATFORM_HEIGHT);
	}
	
	public Rectangle getCollisionArea() {
		this.collision_area.x = (int)(this.location[0]);
		this.collision_area.y = (int)(this.location[1]);
		return collision_area;
	}
}

