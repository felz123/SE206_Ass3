package mainpackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

import mainpackage.AudioOverlayPanel.BackgroundOverlayAudio;
import mainpackage.OverlayPanel.BackgroundOverlayText;

public class TitleCreditsPanel extends JPanel implements ActionListener{
	private JPanel leftPanel = new JPanel();
	private JPanel rightPanel = new JPanel();
	
	private JLabel textLabel = new JLabel("Enter text");
	private JTextArea textChooser = new JTextArea();
	private JLabel fontLabel = new JLabel("Font specifications");
	private JComboBox<String> sizeChooser = new JComboBox<String>(new String[]{"7","8","9","10","11","12","13","14","15"});
	private JComboBox<String> fontChooser = new JComboBox<String>(new String[]{"Arial","Comic Sans","Lexia","Ultra Violet"});
	private JLabel backgroundLabel = new JLabel("Background Color");
	private JComboBox<String> fontColorChooser = new JComboBox<String>(new String[]{"Black","White","Blue","Red","Green"});
	private JComboBox<String> backgroundColorChooser = new JComboBox<String>(new String[]{"Black","White","Blue","Red","Green"});
	private JComboBox<String> textSideChooser = new JComboBox<String>(new String[]{"Beginning","Ending"});
	private JLabel positionLabel = new JLabel("Choose a position");
	private JButton positionChooser = new JButton("...");
	private JTextField positionX = new JTextField(4);
	private JTextField positionY = new JTextField(4);
	private JLabel timeLabel = new JLabel("Insert Time (Seconds)");
	private JTextField timeTextField = new JTextField(10);
	private JButton addTextButton = new JButton("Add to Project");
	
	private String chosenText;
	private int chosenSize;
	private String chosenFont;
	private String chosenX;
	private String chosenY;
	private String chosenColor;
	private String chosenBackgroundColor;
	private String chosenTime;
	private String chosenEnding;
	private String directoryChosen;
	
	private EmbeddedMediaPlayerComponent video;
	private String workingDirectory;
	private String file;
	
	private static final String dir = System.getProperty("user.home")
			+ "/.vamix1/";
	// we have to know directory of where src folder is
	String fontDir = "/media/felz123/HOLDEN/HaseebsWorkplace/SE206_Assignment3_hsye185_felz123/";

	
	public TitleCreditsPanel(EmbeddedMediaPlayerComponent mediaComponent,
			String directory, String inputFile) {
		this.workingDirectory = directory;
		this.file = inputFile;
		this.video = mediaComponent;
		
		
			setLayout(new BorderLayout());
			add(leftPanel,BorderLayout.LINE_START);
//			rightPanel.setBackground(Color.red);
			add(rightPanel,BorderLayout.CENTER);
			
			textChooser.setPreferredSize(new Dimension(300,100));
			textChooser.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			textChooser.setLineWrap(true);
			
			leftPanel.setLayout(new GridBagLayout());
			leftPanel.setPreferredSize(new Dimension(350,1));
			rightPanel.setLayout(new GridBagLayout());
			
		    GridBagConstraints eC = new GridBagConstraints();
//		    eC.weighty = 1;
		    eC.weightx = 1;
		    eC.gridx = 0;
		    eC.gridy = 0;
		    leftPanel.add(textLabel,eC);
		    eC.gridy = 1;
		    leftPanel.add(textChooser,eC);
		    eC.gridy = 0;
		    rightPanel.add(fontLabel,eC);
		    eC.gridy = 1;
		    
		    JPanel fontPanel = new JPanel();
		    sizeChooser.addActionListener(this);
		    fontPanel.add(sizeChooser);
		    fontColorChooser.addActionListener(this);
		    fontPanel.add(fontColorChooser);
		    fontChooser.addActionListener(this);
		    fontPanel.add(fontChooser);
		    rightPanel.add(fontPanel,eC);
		    eC.gridy = 2;
		    rightPanel.add(backgroundLabel,eC);
		    eC.gridy = 3;
		    rightPanel.add(backgroundColorChooser,eC);
		    eC.gridy = 4;
		    rightPanel.add(positionLabel,eC);
		    eC.gridy = 5;
		    JPanel positionPanel = new JPanel();
		    positionChooser.setPreferredSize(new Dimension(30,22));
		    positionPanel.add(positionChooser);
			positionX.setPreferredSize(new Dimension(50,22));
			positionPanel.add(positionX);
			positionY.setPreferredSize(new Dimension(50,22));
			positionPanel.add(positionY);
		    textSideChooser.setPreferredSize(new Dimension(80,22));
		    positionPanel.add(textSideChooser);
		    rightPanel.add(positionPanel,eC);
		    eC.gridy = 6;
		    rightPanel.add(timeLabel,eC);
		    eC.gridy = 7;
		    rightPanel.add(timeTextField,eC);
		    eC.gridy = 8;
		    addTextButton.addActionListener(this);
		    rightPanel.add(addTextButton,eC);
	}
	public JButton getPositionButton(){
		return this.positionChooser;
	}
	public JTextField getPositionTextFieldX(){
		return this.positionX;
	}
	public JTextField getPositionTextFieldY(){
		return this.positionY;
	}
	public void overlayText(){
		System.out.println(chosenText);
		System.out.println(chosenFont);
		System.out.println(chosenSize);
		System.out.println(chosenX);
		System.out.println(chosenY);
		System.out.println(chosenColor);
		System.out.println(chosenTime);
		System.out.println(chosenEnding);
		System.out.println(chosenBackgroundColor);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addTextButton) {
			chosenText = this.textChooser.getText();
			chosenFont = this.fontChooser.getSelectedItem().toString();
			chosenSize = Integer.parseInt(this.sizeChooser.getSelectedItem()
					.toString());
			chosenX = this.positionX.getText();
			chosenY = this.positionY.getText();
			chosenColor = this.fontColorChooser.getSelectedItem().toString();
			chosenTime = this.timeTextField.getText();
			chosenEnding = this.textSideChooser.getSelectedItem().toString();

			// if one of the fields is empty
			if (chosenX.isEmpty() || chosenY.isEmpty() || chosenTime.isEmpty()
					|| chosenText.isEmpty()) {
				JOptionPane.showMessageDialog(null,
						"please fill all required fields!");
				addTextButton.setEnabled(true);
			} else {

				final JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Choose a Directory");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);

				// if directory is chosen, we assign it to directoryChosen
				// variable
				// directory for output file
				if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

					directoryChosen = chooser.getSelectedFile()
							.getAbsolutePath() + "/";

					overlayText(directoryChosen, workingDirectory, file);

					// if directory remains null and wasn't specified by user,
					// error
					// message displays
				} else if (directoryChosen == null) {

					JOptionPane.showMessageDialog(null,
							"Please select a directory!");
					addTextButton.setEnabled(true);

				}
			}

			// overlayText();
		} else if (e.getSource() == fontChooser || e.getSource() == sizeChooser) {
			chosenFont = this.fontChooser.getSelectedItem().toString();
			chosenSize = Integer.parseInt(this.sizeChooser.getSelectedItem()
					.toString());
			try {
				FileInputStream instanceStream = new FileInputStream(new File(
						"./" + chosenFont + ".ttf"));
				textChooser
						.setFont(Font.createFont(Font.TRUETYPE_FONT,
								instanceStream).deriveFont(Font.PLAIN,
								chosenSize + 10));
				instanceStream.close();
			} catch (FontFormatException | IOException exp) {
				exp.printStackTrace();
			}
		}

	}
		
	// method that overlays text over video
	private void overlayText(String directoryChosen, String directory,
			String inputFile) {

		BackgroundOverlayText bg = new BackgroundOverlayText(directoryChosen,
				directory, inputFile);
		// BackgroundOverlayText bg = new BackgroundOverlayText();
		bg.execute();

	}
	class BackgroundOverlayText extends SwingWorker<Void, String> {
		String directoryChosen;
		String directory;
		String inputFile;

		public BackgroundOverlayText(String directoryCh, String workDir,
				String input) {
			this.directoryChosen = directoryCh;
			this.directory = workDir;
			this.inputFile = input;
		}

		@Override
		protected Void doInBackground() throws Exception {
			try {
				Directory.makeDir(dir);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			Process process;
			String outputFile = "texted";
			String x = "temp";
			// String inputFile = "wild";

			StringBuilder sb = new StringBuilder();
			/**
			 * avconv -i wild.mp4 -vf
			 * "drawtext=fontfile='/media/felz123/HOLDEN/HaseebsWorkplace/SE206_Assignment3_hsye185_felz123/Arial.ttf
			 * ' :text='hi i hope this works':fontcolor=black:draw='lt(t,5)'"
			 * -c:a copy trial.mp4 that works with no cutting or anything
			 * 
			 * **/
			
//			String command ="avconv -i /media/felz123/HOLDEN/HaseebsWorkplace/wild.mp4 " +
//					"-ss 00:00:00.001 -f image2 -vframes 1 /media/felz123/HOLDEN/HaseebsWorkplace/out.png";
			
			
			// adding to beginning
			if (chosenEnding.equals("Beginning")) {
				
				sb.append("avconv -i "+ directory + inputFile +
					" -ss 00:00:00.001 -f image2 -vframes 1 "+dir+"out.png");
				sb.append(" ; avconv -loop 1 -shortest -y -i " + dir
						+ "out.png");
				sb.append(" -t " + chosenTime + " -y " + dir + "result.mp4");
				
				
				
				// adding text
				sb.append(" ; avconv -ss 0 -i " + dir + "result.mp4"
						+ " -strict experimental -vf \"drawtext=fontfile="
						+ fontDir + chosenFont + ".ttf'");
				sb.append(":fontcolor= " + chosenColor + ":fontsize="
						+ chosenSize);
				sb.append(": x='" + chosenX + "': y='" + chosenY + "': text='"
						+ chosenText);
				sb.append("'\" -t " + chosenTime + " -y ");
				sb.append(dir + outputFile + ".mp4 "); //

				// took out -an and added strict -ss 0

				// generating ending .st file
				sb.append("; avconv -ss 0 -i " + dir + outputFile //
						+ ".mp4 -vcodec libx264 -acodec aac   ");
				sb.append("  -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
						+ dir + outputFile + ".ts"); //

				sb.append("; avconv -ss 0 -i " + directory
						+ inputFile + " -vcodec libx264 -acodec aac   ");
				sb.append("  -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
						+ dir + x + ".ts"); //

				// concatenating to end
				sb.append("; avconv -i concat:\"" + dir
						+ outputFile//
						+ ".ts|" + dir
						+ x//
						+ ".ts\" -c copy    -bsf:a aac_adtstoasc -an -y "
						+ dir + "final.mp4");//

				
				sb.append("; avconv -i " + directory + inputFile + " " + dir  +
						    "soundtrack.mp3");
						  
						 sb.append("; avconv -i " + dir + "final.mp4 -itsoffset "+chosenTime+" -i " + dir 
						  + "soundtrack.mp3 -c copy -map 0:0 -map 1:0 " + directoryChosen +
						  "output.mp4");
						 

						 

				// adding to ending
			} else {

				int a= (int) video.getMediaPlayer().getLength() / 1000;
				int seconds = a- Integer.parseInt(chosenTime);
				// int seconds = (30000 / 1000) - Integer.parseInt(chosenTime);
				System.out.println(video.getMediaPlayer().getLength());
				// System.out.println("timechosen" + seconds);
				// adding text
				
				sb.append("avconv -i "+ directory + inputFile +
						" -ss "+a+" -f image2 -vframes 1 "+dir+"out.png");
					sb.append(" ; avconv -loop 1 -shortest -y -i " + dir
							+ "out.png");
					sb.append(" -t " + chosenTime + " -y " + dir + "result.mp4");
				
				sb.append(" ; avconv -ss " + seconds + " -i " + dir
						+ "result.mp4"
						+ " -strict experimental -vf \"drawtext=fontfile="
						+ fontDir + chosenFont + ".ttf'");
				sb.append(":fontcolor= " + chosenColor + ":fontsize="
						+ chosenSize);
				sb.append(": x='" + chosenX + "': y='" + chosenY + "': text='"
						+ chosenText);
				sb.append("'\" -t " + chosenTime + " -y ");
				sb.append(dir + outputFile + ".mp4 "); //

				// took out -an and added strict

				// generating ending .st file
				sb.append("; avconv -ss 0 -i " + dir + outputFile //
						+ ".mp4 -vcodec libx264 -acodec aac   ");
				sb.append("  -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
						+ dir + outputFile + ".ts"); //

				sb.append("; avconv -i " + directory + inputFile
						+ " -vcodec libx264 -acodec aac   ");
				sb.append("  -bsf:v h264_mp4toannexb -f mpegts -strict experimental "
						 + " -y " + dir + x + ".ts"); //

				// concatenating to end
				sb.append("; avconv -i concat:\"" + dir
						+ x //
						+ ".ts|" + dir
						+ outputFile //
						+ ".ts\" -c copy    -bsf:a aac_adtstoasc -y "
						+ directoryChosen + "final.mp4"); //

			}

			// getting audio out of video
			/*
			 * sb.append("; avconv -i " + directory + inputFile + " " + dir // +
			 * x + ".mp3");
			 * 
			 * // adding audio back, i am actually adding audio over audio, see
			 * if // u can add it on only on the overlaid part
			 * sb.append("; avconv -i " + dir + "final.mp4 -i " + dir // // + x
			 * + ".mp3 -c copy -map 0:0 -map 1:0 " + directoryChosen +
			 * "output.mp4");
			 */

			String command = sb.toString();
			System.out.println(command);
			ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c",
					command);

			try {
				process = builder.start();
				process.waitFor();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			return null;
		}

		// Override done() to perform specific functionalities when
		// doInBackground has finished
		@Override
		protected void done() {
			try {
				Directory.removeDir(dir);
			} catch (Exception e) {
				e.printStackTrace();
			}
			addTextButton.setEnabled(true);
			JOptionPane.showMessageDialog(null,
					"Overlaying text on video was successful!");
			positionX.setText("");
			positionY.setText("");
			timeTextField.setText("");
			textChooser.setText("");

		}

	}

}
