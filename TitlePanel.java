package mainpackage;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitlePanel extends JPanel{
	private final JLabel titleLabel;
	
	public TitlePanel(String chosenTitle){
		titleLabel = new JLabel(chosenTitle);
		add(titleLabel);
	}
	
	
}
