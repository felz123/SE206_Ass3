package mainpackage;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BrowsePanel extends JPanel{
	private JTextField browseTextField;
	private JButton browseButton;
	public BrowsePanel(int textLength){
		setPreferredSize(new Dimension(200,22));
		browseTextField = new JTextField(textLength);
		browseButton = new JButton("...");
		add(browseTextField);
		add(browseButton);
	}

}
