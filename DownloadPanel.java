package mainpackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 * Haseeb Syed, hsye185, 6842858
 */
@SuppressWarnings("serial")
/**
 * This Class allows the user to download a file when provided the URL, to a specified directory
 */
public class DownloadPanel extends JPanel implements ActionListener, FocusListener{
	
	private Process process;
	
	private JLabel label1;
	
	private JTextField urlTextField;
	private JTextField directoryTextField;
	
	private JProgressBar progressBar;
	
	private JPanel buttonsPanel;
	
	private JButton directoryButton;
	private JButton downloadButton;
	private JButton cancelButton;
	
	private DownloadWorker myDownloadWorker;
//	JLabel background = new JLabel(new ImageIcon("openSource.jpg"));
//	Image bgimage;
//	
	public DownloadPanel(){
//		MediaTracker mt = new MediaTracker(this);
//	    bgimage = Toolkit.getDefaultToolkit().getImage("openSource.jpg");
//	    mt.addImage(bgimage, 0);
//	    try {
//	      mt.waitForAll();
//	    } catch (InterruptedException e) {
//	      e.printStackTrace();
//	    }
		
		/*
		 * Most people will be using other simpler border layouts that require less code
		 * HOWEVER, by using GridBagLayout i can add a lot of the more finer details that 
		 * will make the GUI look nicer, The extra GUI code here is just for a more defined
		 * look.
		 */
		
		//Applies the Layout to the current JPanel
		GridBagLayout g = new GridBagLayout();
		setLayout(g);
		
		//Creates a seperate constraint object for each, it is possible with only using one but
		//doing it this way is better is more clean and supports OO design.
		GridBagConstraints cLabel = new GridBagConstraints();
		GridBagConstraints cURLTextField = new GridBagConstraints();
		GridBagConstraints cDirectoryTextField = new GridBagConstraints();
		GridBagConstraints cDirectoryButton = new GridBagConstraints();
		GridBagConstraints cButtonsPanel = new GridBagConstraints();
		GridBagConstraints cProgress = new GridBagConstraints();
		
		
		//Specifying placement within the grid
		cLabel.gridx = 1;
		cLabel.gridy = 1;
		//Specifiying how many grid spaces it takes up
		cLabel.gridwidth = 2;
		//Specifies the padding on all 4 sides
		cLabel.insets = new Insets(0,0,10,0);
		//Creating object, then adding the constraint to it
		label1 = new JLabel("Download");
		g.addLayoutComponent(label1, cLabel);
		
		//Specifying placement within the grid
		cURLTextField.gridx = 1;
		cURLTextField.gridy = 2;
		//Specifiying how many grid spaces it takes up
		cURLTextField.gridwidth = 2;
		//Specifies the padding on all 4 sides
		cURLTextField.insets = new Insets(10,0,10,0);
		//Creating TextField, then adding the constraint to it
		urlTextField = new JTextField("Please enter URL here",25);
		urlTextField.addFocusListener(this);
		g.addLayoutComponent(urlTextField, cURLTextField);
		
		//Specifying placement within the grid
		cDirectoryTextField.gridx = 1;
		cDirectoryTextField.gridy = 3;
		//Specifiying how many grid spaces it takes up
		cDirectoryTextField.gridwidth = 2;
		//Specifies the padding on all 4 sides
		cDirectoryTextField.insets = new Insets(0,0,10,0);
		//Creating TextField, then adding the constraint to it
		directoryTextField = new JTextField("    Please select a directory for the output",25);
		//Changing the TextField to uneditable, thus only allowing the file chooser
		//to provide a legitimate directory change. ie less room for error.
		directoryTextField.setEditable(false);
		g.addLayoutComponent(directoryTextField, cDirectoryTextField);		
		
		//Specifying placement within the grid
		cDirectoryButton.gridx = 2;
		cDirectoryButton.gridy = 3;
		//Specifying the padding from the default location
		cDirectoryButton.insets = new Insets(0,310,10,0);
		//Locks it EAST of its current grid spacer
		cDirectoryButton.anchor = GridBagConstraints.EAST;
		directoryButton = new JButton("...");
		//Setting the size of the button (To match the height of the textfield).
		directoryButton.setPreferredSize(new Dimension(30,19));
		g.addLayoutComponent(directoryButton, cDirectoryButton);
		//Adding the listener to detect button clicks
		directoryButton.addActionListener(this);
		
		//downloadButton & cancelButton do not need constraints as, i will put them
		//into a panel soon. Below, creates both buttons and adds listeners.
		downloadButton = new JButton("Download");
		downloadButton.addActionListener(this);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		
		
		//Creating the panel to hold both the downloadButton and the cancelButton
		//Settings the relative positions
		cButtonsPanel.gridx = 2;
		cButtonsPanel.gridy = 4;
		buttonsPanel = new JPanel();
		buttonsPanel.add(downloadButton);
		buttonsPanel.add(cancelButton);
		//Adding the Constraints
		g.addLayoutComponent(buttonsPanel, cButtonsPanel);

		//Specifying relative position for the progressbar
		cProgress.gridx = 1;
		cProgress.gridy = 5;
		//Setting the amount of grid spaces it takes up
		cProgress.gridwidth = 2;
		//Sets the padding of the top to be 20 units.
		cProgress.insets = new Insets(20,0,0,0);
		//Creates a progress bar object starting from 0.
		progressBar = new JProgressBar(0, 100);
		//Setting the size and the option to show the number.
		progressBar.setPreferredSize(new Dimension(300,20));
		progressBar.setStringPainted(true);
		//Adding the constraints
		g.addLayoutComponent(progressBar, cProgress);
		
		//Adding the components to the Panel
		add(label1);
		add(urlTextField);
		add(directoryTextField);
		add(directoryButton);
		add(buttonsPanel);
		add(progressBar);

	}
	
//	protected void paintComponent(Graphics g) {
//	    super.paintComponent(g);
//	    g.drawImage(bgimage, 0, 0, null);
//	  }
//	
	/**
	 *This class is a custom SwingWorker to process the actual download using the wget Bash Function.
	 */
	class DownloadWorker extends SwingWorker<Void,String>{
		
		@Override
		protected Void doInBackground() throws Exception {
			//Checks if the current worker is actually cancelled
			if(!isCancelled()){
				//Gets the url from the corresponding text field
				String cmdURL = urlTextField.getText();
				//Gets the directory from the corresponding text field
				String targetDirectory = directoryTextField.getText();
				//Creates the process to be send to bash
				ProcessBuilder builder = new ProcessBuilder("wget","-P",targetDirectory,"-c","--progress=bar:force",cmdURL);
				//redirect the error stream for evaluation
				builder.redirectErrorStream(true);
				//Starts the process
				try {
					process = builder.start();
				} catch (IOException e) {
					//This will never happen for our functionality, but if it ever does this error message will come up.
					JOptionPane.showMessageDialog(null,"Bash Process could not start due to an I/O error");
				}
				
				BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line;
				try {
					
				
						while ((line = stdout.readLine()) != null) {
							//sends each line from the input/error stream to publish, (for GUI updates)
							if(!isCancelled()){
								publish(line+System.getProperty("line.separator"));
							}else{
								process.destroy();
								return null;
							}
						}
					
				} catch (IOException e) {
					System.out.println("hi");
					JOptionPane.showMessageDialog(null,"Process could not read line from input/error stream due to I/O error");
				}

			}
			return null;
		}
		protected void process(List<String> chunks){
			for(String s : chunks){
				//Checks if the current worker is actually cancelled
				if(!isCancelled()){
					//Sets the pattern to extract the percentage number from the output of the bash
					Pattern varPattern = Pattern.compile("(\\d*)\\%");
					Matcher matcher = varPattern.matcher(s);
					//Updates the progress bar by parsing the string input from the matcher
					while (matcher.find()) {
						String var = matcher.group(1);
						try{
						progressBar.setValue(Integer.parseInt(var));
						}catch(NumberFormatException e){
							JOptionPane.showMessageDialog(null,"Error during the reading of the output of Swing Worker bash process");
						}
						
					}	
				}
				
			}
		}
		protected void done(){
			//Checks if the current worker is actually cancelled
			if(!isCancelled()){
				/*
				 * Evaluates the exit value from the bash process, if its 0 its sucessful and hence the appropriate message
				 * will be displays, and the log file will be updated. If its any other number, all the wget errors are
				 * accounted for and the appropriate message will be displayed.
				 */
				int exitValue = process.exitValue();
				if(exitValue==0){
					JOptionPane.showMessageDialog(null,"Download Complete!");
//					LogPanel.updateFile("DOWNLOAD");
					urlTextField.setText("");
					directoryTextField.setText("    Please select a directory for the output");
					
				}else if(exitValue==1){
					JOptionPane.showMessageDialog(null,"There was a Generic error");
				}else if(exitValue==2){
					JOptionPane.showMessageDialog(null,"There was an error during Parsing");
				}else if(exitValue==3){
					JOptionPane.showMessageDialog(null,"There was an error with File I/O");
				}else if(exitValue==4){
					JOptionPane.showMessageDialog(null,"There was an error while searching for the file");
				}else if(exitValue==5){
					JOptionPane.showMessageDialog(null,"There was an SSL verification failure");
				}else if(exitValue==6){
					JOptionPane.showMessageDialog(null,"There was a Username/password authentication failure");
				}else if(exitValue==7){
					JOptionPane.showMessageDialog(null,"There was Protocol Erorrs");
				}else if(exitValue==8){
					JOptionPane.showMessageDialog(null,"There was a Server issue");
				}
				
			}else{
				//If the process is cancelled, then the process will be displayed and the relevant message is shown
				process.destroy();
				JOptionPane.showMessageDialog(null,"Download Cancelled");
			}
			
		}
		
	}

	/**
	 * This a custom actionPerformed implementation that manages 3 different buttons being clicked and 
	 * outputting the appropriate actions
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==downloadButton){
			//Makes sure the user enters a URL
			if(urlTextField.getText().equals("Please enter URL here")){
				JOptionPane.showMessageDialog(null,"Please enter a URL!");
			}else if(directoryTextField.getText().equals("    Please select a directory for the output")){
				//Checks if the default text is still there, indicating browse was not used (as it is uneditable).
				//Displays the appropriate message
				JOptionPane.showMessageDialog(null,"Please select a directory!");
			}else{
				//Assigns the current urlTextField content to url
				String url = urlTextField.getText();
				//Assigns the current Directory specified to fileDirectory
				String fileDirectory = directoryTextField.getText();
				//Finds the file name from the URL
				String fileName = url.substring(url.lastIndexOf('/') + 1);  
				//Checks if the file exists in the target directory
				File f = new File(fileDirectory+fileName);
				if(f.exists()){
					//If it exists then give the option to resume/override
				    Object[] options = {"Resume Download", "Override it"};
					int returnOption = JOptionPane.showOptionDialog(null,
					    "The file already exists!, what would you like to do?",
					    "Download Options",
					    JOptionPane.DEFAULT_OPTION,
					    JOptionPane.QUESTION_MESSAGE,
					    null,
					    options,
					    options[0]);
					if(returnOption==0){
						//If they chose to resume, they execute the DownloadWorker
						//If the file is already completed, it will just present a "done" message instantly
						myDownloadWorker = new DownloadWorker();
						myDownloadWorker.execute();
					}else if(returnOption==1){
						//If they chose to override, the file is deleted, then DownloadWorker is executed again;
						try {
							Files.delete(FileSystems.getDefault().getPath(fileDirectory+fileName));
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null,"An error occured, the file could not be deleted");
						}
						myDownloadWorker = new DownloadWorker();
						myDownloadWorker.execute();
					}
				}
				else{
					//If the file doesn't exist, it asks if the user if the target download is open source
				    Object[] options2 = {"Yes, the file is Open Source", "Cancel"};
					int returnOption = JOptionPane.showOptionDialog(null,
					    "Is the file Open Source?",
					    "Download Options",
					    JOptionPane.DEFAULT_OPTION,
					    JOptionPane.QUESTION_MESSAGE,
					    null,
					    options2,
					    options2[0]);

					if(returnOption==0){
						//If they chose the option indicating it is open source, the download will commence via DownloadWorker
						myDownloadWorker = new DownloadWorker();
						myDownloadWorker.execute();
					}else if(returnOption==1){
						//If they chose to cancel, nothing will happen. It will just return to the main screen.
					}
				}
			}
			
		}else if(e.getSource()==cancelButton){
			//If the cancel button is pressed, it cancelled the current DownloadWorker
			try{
				myDownloadWorker.cancel(false);
			}catch(NullPointerException eNull){
				//This is meant to throw an exception when it interrupts the Swing Worker
				//No need to show message
			}
			
		}else if(e.getSource()==directoryButton){
			//If the directory button is pressed, then the JFileChooser opens up allowing the user to pick
			//a directory
			final JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			File file;
			int returnVal = fc.showOpenDialog(this);
			  if (returnVal == JFileChooser.APPROVE_OPTION) {
		            file = fc.getSelectedFile();
		           	directoryTextField.setText(file.toString()+System.getProperty("file.separator"));
		        }
		}
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource()==urlTextField){
			if(urlTextField.getText().equals("Please enter URL here")){
				urlTextField.setText("");
			}
		}
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		if(e.getSource()==urlTextField){
			if(urlTextField.getText().equals("")){
				urlTextField.setText("Please enter URL here");
			}
		}
		
	}

}
