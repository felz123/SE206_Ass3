package mainpackage;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimelineSidePanel extends JPanel {
	private JLabel videoLabel;
	private JLabel audioLabel;
	private JPanel videoPanel;
	private JPanel audioPanel;
	
	public TimelineSidePanel(){
		videoLabel = new JLabel("VIDEO");
		add(videoLabel);
		
	}

}
