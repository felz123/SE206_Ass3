package mainpackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class WorkplacePopup extends JFrame implements ActionListener{
	private JTextField directoryTextField;
	

	public WorkplacePopup(){
		setTitle("VAMIX");
		setLayout(new BorderLayout());
		setSize(new Dimension(400,110));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
//		setUndecorated(true);
//		getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK));
		directoryTextField = new JTextField();
		directoryTextField.setPreferredSize(new Dimension(250,22));
		
		JPanel textPanel = new JPanel();
		JPanel mainPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(this);
		okButton.setPreferredSize(new Dimension(80,19));
		buttonPanel.add(okButton);
		
		JButton browseDirectory = new JButton("Browse...");
		
		browseDirectory.setPreferredSize(new Dimension(25,22));
		final JFileChooser directoryChooser = new JFileChooser();
		directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		browseDirectory.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = directoryChooser.showOpenDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	directoryTextField.setText(directoryChooser.getSelectedFile().toString());
			    }
				
			}
			
		});JPasswordField password = new JPasswordField();
		textPanel.add(new JLabel("Please select a working directory"));
		mainPanel.add(directoryTextField);
		mainPanel.add(browseDirectory);
		final JComponent[] inputs = new JComponent[] {
				textPanel,
				mainPanel,
		};
		//JOptionPane.showMessageDialog(null, inputs, "VAMIX", JOptionPane.QUESTION_MESSAGE);
		add(textPanel,BorderLayout.PAGE_START);
		add(mainPanel,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.PAGE_END);
		System.out.println("You entered " +	directoryTextField.getText());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public JTextField getDirectoryTextField() {
		return directoryTextField;
	}

	public void setDirectoryTextField(JTextField directoryTextField) {
		this.directoryTextField = directoryTextField;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if((new File(directoryTextField.getText())).exists()){
			dispose();
		}
		
	}
}
