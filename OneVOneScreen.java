/**
 * 
 */
package soccer.slime;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;


import javax.swing.JPanel;

/**
 * This will be the panel which has the one v one active match. 
 * @author Conor
 *
 */
public class OneVOneScreen extends JPanel implements Runnable{
	
	private static final long serialVersionUID = 3439678235556672539L;
	
	// SCREEN SETTINGS
	final int DEFAULT_SCREEN_HEIGHT = 569;
	final int DEFAULT_SCREEN_WIDTH = (int) (DEFAULT_SCREEN_HEIGHT/0.494);
	final int SCALE = 1;
	
	final int SCREEN_WIDTH = DEFAULT_SCREEN_WIDTH * SCALE;
	final int SCREEN_HEIGHT = DEFAULT_SCREEN_HEIGHT * SCALE;
	
	//External entities
	private BallEntity ball;
	private SlimeEntity slime_1;
	private SlimeEntity slime_2;
	private PlatformEntity platform;
	private NetEntity net_1;
	private NetEntity net_2;
	private CollisionDetector collision_detector;
	final int[] slime_1_keyset = new int[] {87,83,65,68}; 
	final int[] slime_2_keyset = new int[] {38,40,37,39}; 
	
	//STROKE SIZE
	final int STROKE_SIZE = 2;
	
	// Game clock thread
	Thread gameThread;
	
	public OneVOneScreen() {
		
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.blue);
		this.setDoubleBuffered(true);
		
		//Initialize entities
		platform = new PlatformEntity(this.SCREEN_HEIGHT, this.SCREEN_WIDTH, Color.GRAY);
		ball = new BallEntity(this.SCREEN_HEIGHT, this.SCREEN_WIDTH, Color.white, this.platform.getCollisionArea());
		slime_1 = new SlimeEntity(this.SCREEN_HEIGHT, this.SCREEN_WIDTH, this.STROKE_SIZE, this.platform.location, true, ball, Color.green, slime_1_keyset); 
		slime_2 = new SlimeEntity(this.SCREEN_HEIGHT, this.SCREEN_WIDTH, this.STROKE_SIZE, this.platform.location, false, ball, Color.magenta, slime_2_keyset); 
		net_1 = new NetEntity(this.SCREEN_HEIGHT, this.SCREEN_WIDTH, this.platform.location, Color.white, true);
		net_2 = new NetEntity(this.SCREEN_HEIGHT, this.SCREEN_WIDTH, this.platform.location, Color.white, false);
		
		ArrayList<SlimeEntity> slimes = new ArrayList<SlimeEntity>();
		ArrayList<BallEntity> balls = new ArrayList<BallEntity>();
		ArrayList<NetEntity> nets = new ArrayList<NetEntity>();
		
		slimes.add(slime_1);
		slimes.add(slime_2);
		balls.add(ball);
		nets.add(net_1);
		nets.add(net_2);
		
		collision_detector = new CollisionDetector(slimes, balls, nets, platform);
		
		new UserInput(this, slimes);
	}

	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	

	@Override
	public void run() {
		
		while (gameThread != null) {
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			update();
			repaint();
		}
	}
	
	public void update() {
		
		collision_detector.checkAllCollisions();
		
		this.ball.update();
		this.slime_1.update(ball);
		this.slime_2.update(ball);
		this.platform.update();
		this.net_1.update();
		this.net_2.update();
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Change to graphics 2D
		Graphics2D g2 = (Graphics2D)g;
		
		// Set stroke size
		BasicStroke sline = new BasicStroke(this.STROKE_SIZE);
		g2.setStroke(sline);
		
		//draw platform
		this.platform.drawEntity(g2);
		
		// Draw slimes
		this.slime_1.drawEntity(g2);
		this.slime_2.drawEntity(g2);
		
		// Draw the ball
		this.ball.drawEntity(g2);
		
		// Draw the nets
		this.net_1.drawEntity(g2);
		this.net_2.drawEntity(g2);
		
		// Dispose of the graphics object
		g2.dispose();
	}
}

