package mainpackage;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Main{

	/**
	 * @param args
	 */
	//check if directory exists first.
	public static void main(String[] args){

//		final JTextField directoryTextField = new JTextField();
//		directoryTextField.setPreferredSize(new Dimension(250,22));
//		JPanel textPanel = new JPanel();
//		JPanel mainPanel = new JPanel();
//		JButton browseDirectory = new JButton("Browse...");
//		
//		browseDirectory.setPreferredSize(new Dimension(25,22));
//		final JFileChooser directoryChooser = new JFileChooser();
//		directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//		browseDirectory.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//			   
//			    while(!(new File(directoryTextField.getText()).exists())){
//			    	int returnVal = directoryChooser.showOpenDialog(null);
//			    	 if(returnVal == JFileChooser.APPROVE_OPTION) {
//			    		 directoryTextField.setText(directoryChooser.getSelectedFile().toString());
//			    	 }
//			    }
//				
//			}
//			
//		});JPasswordField password = new JPasswordField();
//		textPanel.add(new JLabel("Please select a working directory"));
//		mainPanel.add(directoryTextField);
//		mainPanel.add(browseDirectory);
//		final JComponent[] inputs = new JComponent[] {
//				textPanel,
//				mainPanel,
//		};
//		JOptionPane.showMessageDialog(null, inputs, "VAMIX", JOptionPane.QUESTION_MESSAGE);
//		System.out.println("You entered " +	directoryTextField.getText());
		//==============================================================
		final WorkplacePopup popup = new WorkplacePopup();
		popup.setSize(new Dimension(400,90));
		
		popup.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				
				JFrame myFrame = new JFrame("VAMIX");
				myFrame.setExtendedState(myFrame.MAXIMIZED_BOTH); 
				int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
				int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//				myFrame.setMaximumSize(new Dimension(width,height));
				myFrame.setMinimumSize(new Dimension(width-100,height-50));
				ViewPanel myVideoPanel = new ViewPanel(popup.getDirectoryTextField().getText());
				myFrame.setContentPane(myVideoPanel);
				myFrame.setVisible(true);
				myFrame.setDefaultCloseOperation(myFrame.EXIT_ON_CLOSE);
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				System.out.println("shit closed");
			
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		popup.setVisible(true);
		
//		myFrame.setExtendedState(myFrame.MAXIMIZED_BOTH);l 
//		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
////		myFrame.setMaximumSize(new Dimension(width,height));
//		myFrame.setMinimumSize(new Dimension(width-100,height-50));
//		ViewPanel myVideoPanel = new ViewPanel();
//		myFrame.setContentPane(myVideoPanel);
//		myFrame.setVisible(true);
//		myFrame.setDefaultCloseOperation(myFrame.EXIT_ON_CLOSE);
//		myFrame.setUndecorated(true);
//		myFrame.setResizable(false);
		
	}


}
