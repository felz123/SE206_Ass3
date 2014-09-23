package mainpackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AudioOverlayPanel extends JPanel implements ActionListener{

	private JFileChooser fileChooser = new JFileChooser();
	private JPanel middlePanel = new JPanel();
	
	private JButton addOverlay = new JButton("Set Overlay");
	
	private JButton overlayBrowseButton = new JButton("...");

	private JLabel overlayLabel = new JLabel("Overlaying");
	private JLabel overlayInputLabel = new JLabel("Choose an Input .mp3 File");
	
	private JTextField startTimeTextField = new JTextField(5);
	private JTextField endTimeTextField = new JTextField(5);
	private JTextField overlayInputTextField = new JTextField(12);
	
	
	public AudioOverlayPanel(){
		
		
		overlayBrowseButton.setPreferredSize(new Dimension(20,19));
		overlayBrowseButton.addActionListener(this);
		
		setLayout(new GridBagLayout());
		GridBagConstraints eC = new GridBagConstraints();
		eC.gridx=0;
		eC.gridy=0;
		
		add(overlayLabel,eC);
		
		eC.gridy=1;
		add(overlayInputLabel,eC);
		
		eC.gridy=2;
		JPanel overlayPanel1 = new JPanel();
		overlayPanel1.add(overlayInputTextField);
		overlayPanel1.add(overlayBrowseButton);
		add(overlayPanel1,eC);

		
		eC.gridy=3;
		add(new JLabel("Select Start/End time of Main Video"),eC);
		

		eC.gridy=4;
		JPanel overlayPanel2 = new JPanel();
		overlayPanel2.add(new JLabel("Start "));
		overlayPanel2.add(startTimeTextField);
		overlayPanel2.add(new JLabel("End "));
		overlayPanel2.add(endTimeTextField);
		add(overlayPanel2,eC);
		eC.gridy=5;
		add(addOverlay,eC);
	}
	public JButton getOverlayBrowseButton() {
		return overlayBrowseButton;
	}


	public void setOverlayBrowseButton(JButton overlayBrowseButton) {
		this.overlayBrowseButton = overlayBrowseButton;
	}


	public JTextField getStartTimeTextField() {
		return startTimeTextField;
	}


	public void setStartTimeTextField(JTextField startTimeTextField) {
		this.startTimeTextField = startTimeTextField;
	}


	public JTextField getEndTimeTextField() {
		return endTimeTextField;
	}


	public void setEndTimeTextField(JTextField endTimeTextField) {
		this.endTimeTextField = endTimeTextField;
	}


	public JTextField getOverlayInputTextField() {
		return overlayInputTextField;
	}


	public void setOverlayInputTextField(JTextField overlayInputTextField) {
		this.overlayInputTextField = overlayInputTextField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int returnVal = fileChooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	overlayInputTextField.setText(fileChooser.getSelectedFile().toString());
	    }
		
	}
}
