package soccer.slime;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class UserInput {

	JPanel panel;

	ArrayList<Integer> keys_pressed = new ArrayList<Integer>();

	public UserInput(JPanel panel, ArrayList<SlimeEntity> slimes) {

		this.panel = panel;
		
		
		panel.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent e) {
				keys_pressed.removeIf(s -> s.equals(e.getExtendedKeyCode()));
			}

			@Override
			public void keyPressed(KeyEvent e) {
				
				if (!(keys_pressed.contains(e.getExtendedKeyCode()))) {
					keys_pressed.add(e.getExtendedKeyCode());
				}

				System.out.println(keys_pressed.toString());
				for (SlimeEntity slime : slimes) {
					
					if (keys_pressed.contains(slime.key_set[0])) {
						//TODO
					} else if (keys_pressed.contains(slime.key_set[1])) {
						slime.updateLocation(0, 0);
					} else if (keys_pressed.contains(slime.key_set[2])) {
						slime.updateLocation(-15, 0);
					} else if (keys_pressed.contains(slime.key_set[3])) {
						slime.updateLocation(15, 0);
					}
					
				}
			}
		});

	}

}