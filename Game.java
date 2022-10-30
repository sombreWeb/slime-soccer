/**
 * 
 */
package soccer.slime;


import javax.swing.JFrame; 

/**
 * This class will contain the main game loop.
 * @author Conor
 *
 */
public class Game {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		// Setup the initial window
		JFrame window = new JFrame();  
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		window.setTitle("Slime Soccer");
		window.setResizable(false);
		window.setVisible(true);
		
		//Add the JPanel
		OneVOneScreen oneVOneGamePanel = new OneVOneScreen();
		window.add(oneVOneGamePanel);
		
		window.pack();
		window.setLocationRelativeTo(null);
		
		// Focus on the JPanel for user input
		oneVOneGamePanel.setFocusable(true);
		oneVOneGamePanel.requestFocus();
		
		// Run the one v one game panel
		oneVOneGamePanel.startGameThread();	

	}
}
