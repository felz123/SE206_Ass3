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


@SuppressWarnings("serial")
public class TitleCreditsPanel extends JPanel implements ActionListener {
	private JPanel leftPanel = new JPanel();
	private JPanel rightPanel = new JPanel();

	private JLabel textLabel = new JLabel("Enter text");
	private JTextArea textChooser = new JTextArea();
	private JLabel fontLabel = new JLabel("Font specifications");
	private JComboBox<String> sizeChooser = new JComboBox<String>(new String[] {
			"10", "14", "18", "22", "28", "36", "48", "72" });
	private JComboBox<String> fontChooser = new JComboBox<String>(new String[] {
			"Arial", "Comic Sans", "Lexia", "Ultra Violet" });
	private JLabel backgroundLabel = new JLabel("Background Color");
	private JComboBox<String> fontColorChooser = new JComboBox<String>(
			new String[] { "Black", "White", "Blue", "Red", "Green" });
	private JComboBox<String> backgroundColorChooser = new JComboBox<String>(
			new String[] { "Black", "White", "Blue", "Red", "Green" });
	private JComboBox<String> textSideChooser = new JComboBox<String>(
			new String[] { "Beginning", "Ending" });
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
	private String chosenTime;
	private String chosenEnding;
	private String directoryChosen;

	private String workingDirectory;
	private String file;

	private static final String dir = System.getProperty("user.home")
			+ "/.vamix1/";

	String fontDir = "/media/felz123/HOLDEN/HaseebsWorkplace/SE206_Assignment3_hsye185_felz123/";

	public TitleCreditsPanel(
			String directory, String inputFile) {
		
		this.workingDirectory = directory;
		this.file = inputFile;

		setLayout(new BorderLayout());
		add(leftPanel, BorderLayout.LINE_START);
		add(rightPanel, BorderLayout.CENTER);

		textChooser.setPreferredSize(new Dimension(300, 100));
		textChooser.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textChooser.setLineWrap(true);

		leftPanel.setLayout(new GridBagLayout());
		leftPanel.setPreferredSize(new Dimension(350, 1));
		rightPanel.setLayout(new GridBagLayout());

		GridBagConstraints eC = new GridBagConstraints();
		eC.weightx = 1;
		eC.gridx = 0;
		eC.gridy = 0;
		leftPanel.add(textLabel, eC);
		eC.gridy = 1;
		leftPanel.add(textChooser, eC);
		eC.gridy = 0;
		rightPanel.add(fontLabel, eC);
		eC.gridy = 1;

		JPanel fontPanel = new JPanel();
		sizeChooser.addActionListener(this);
		fontPanel.add(sizeChooser);
		fontColorChooser.addActionListener(this);
		fontPanel.add(fontColorChooser);
		fontChooser.addActionListener(this);
		fontPanel.add(fontChooser);
		rightPanel.add(fontPanel, eC);
		eC.gridy = 2;
		rightPanel.add(backgroundLabel, eC);
		eC.gridy = 3;
		rightPanel.add(backgroundColorChooser, eC);
		eC.gridy = 4;
		rightPanel.add(positionLabel, eC);
		eC.gridy = 5;
		JPanel positionPanel = new JPanel();
		positionChooser.setPreferredSize(new Dimension(30, 22));
		positionPanel.add(positionChooser);
		positionX.setPreferredSize(new Dimension(50, 22));
		positionPanel.add(positionX);
		positionY.setPreferredSize(new Dimension(50, 22));
		positionPanel.add(positionY);
		textSideChooser.setPreferredSize(new Dimension(80, 22));
		positionPanel.add(textSideChooser);
		rightPanel.add(positionPanel, eC);
		eC.gridy = 6;
		rightPanel.add(timeLabel, eC);
		eC.gridy = 7;
		rightPanel.add(timeTextField, eC);
		eC.gridy = 8;
		addTextButton.addActionListener(this);
		rightPanel.add(addTextButton, eC);
	}

	public JButton getPositionButton() {
		return this.positionChooser;
	}

	public JTextField getPositionTextFieldX() {
		return this.positionX;
	}

	public JTextField getPositionTextFieldY() {
		return this.positionY;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addTextButton) {
			addTextButton.setEnabled(false);
			chosenText = this.textChooser.getText();
			chosenFont = this.fontChooser.getSelectedItem().toString();
			chosenSize = Integer.parseInt(this.sizeChooser.getSelectedItem()
					.toString());
			chosenX = this.positionX.getText();
			chosenY = this.positionY.getText();
			chosenColor = this.fontColorChooser.getSelectedItem().toString();
			chosenTime = this.timeTextField.getText();
			chosenEnding = this.textSideChooser.getSelectedItem().toString();

			// check no field is left blank
			if (chosenX.isEmpty() || chosenY.isEmpty() || chosenTime.isEmpty()
					|| chosenText.isEmpty()) {
				JOptionPane.showMessageDialog(null,
						"please fill all required fields!");
				addTextButton.setEnabled(true);
				
				//no fields are left empty, choose output directory
			} else {

				final JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Choose a Directory");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);

				// if directory is chosen, we assign it to directoryChosen
				// variable directory for output file
				if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

					directoryChosen = chooser.getSelectedFile()
							.getAbsolutePath() + "/";

					overlayText(directoryChosen, workingDirectory, file);

					// if directory remains null and wasn't specified by user,
					// error message displays
				} else if (directoryChosen == null) {

					JOptionPane.showMessageDialog(null,
							"Please select a directory!");
					addTextButton.setEnabled(true);

				}
			}

			//changes fonts of already written text, updates
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

	// calls background task to be executed on a swingworker
	private void overlayText(String directoryChosen, String directory,
			String inputFile) {

		BackgroundtitlesAndCredits bg = new BackgroundtitlesAndCredits(
				directoryChosen, directory, inputFile);
		bg.execute();

	}

	class BackgroundtitlesAndCredits extends SwingWorker<Void, String> {
		String directoryChosen;
		String directory;
		String inputFile;
		Process process;

		public BackgroundtitlesAndCredits(String directoryCh, String workDir,
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

			String outputFile = "texted";
			String x = "temp";

			StringBuilder sb = new StringBuilder();

			//TITLES
			if (chosenEnding.equals("Beginning")) {

				// Making video out of an image
				sb.append("avconv -i " + directory + inputFile
						+ " -ss 00:00:00.001 -f image2 -vframes 1 " + dir
						+ "out.png");
				sb.append(" ; avconv -loop 1 -shortest -y -i " + dir
						+ "out.png");
				sb.append(" -t " + chosenTime + " -y " + dir + "result.mp4");

				// Adding text to video made above
				sb.append(" ; avconv -ss 0 -i " + dir + "result.mp4"
						+ " -strict experimental -vf \"drawtext=fontfile="
						+ fontDir + chosenFont + ".ttf'");
				sb.append(":fontcolor= " + chosenColor + ":fontsize="
						+ chosenSize);
				sb.append(": x='" + chosenX + "': y='" + chosenY + "': text='"
						+ chosenText);
				sb.append("'\" -t " + chosenTime + " -y ");
				sb.append(dir + outputFile + ".mp4 "); 

				// Re-encoding files
				sb.append("; avconv -ss 0 -i " + dir + outputFile 
						+ ".mp4 -vcodec libx264 -acodec aac   ");
				sb.append("  -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
						+ dir + outputFile + ".ts"); 

				sb.append("; avconv -ss 0 -i " + directory + inputFile
						+ " -vcodec libx264 -acodec aac   ");
				sb.append("  -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
						+ dir + x + ".ts"); 

				// Concatenating files
				sb.append("; avconv -i concat:\"" + dir
						+ outputFile
						+ ".ts|" + dir
						+ x//
						+ ".ts\" -c copy    -bsf:a aac_adtstoasc -an -y " + dir
						+ "final.mp4");

				//Extracting mp3 and re-adding to output video
				sb.append("; avconv -i " + directory + inputFile + " " + dir
						+ "soundtrack.mp3");

				sb.append("; avconv -i " + dir + "final.mp4 -itsoffset "
						+ chosenTime + " -i " + dir
						+ "soundtrack.mp3 -c copy -map 0:0 -map 1:0 "
						+ directoryChosen + "output.mp4");

				// CREDITS
			} else {
			
				//getting video length
				int videoLength = ViewPanel.getVideoLength(directory, inputFile);
				int seconds = videoLength - Integer.parseInt(chosenTime);
				
				// Making video out of an image
				sb.append("avconv -i " + directory + inputFile + " -ss " + videoLength
						+ " -f image2 -vframes 1 " + dir + "out.png");
				sb.append(" ; avconv -loop 1 -shortest -y -i " + dir
						+ "out.png");
				sb.append(" -t " + chosenTime + " -y " + dir + "result.mp4");

				//Adding text to previous video
				sb.append(" ; avconv -ss " + seconds + " -i " + dir
						+ "result.mp4"
						+ " -strict experimental -vf \"drawtext=fontfile="
						+ fontDir + chosenFont + ".ttf'");
				sb.append(":fontcolor= " + chosenColor + ":fontsize="
						+ chosenSize);
				sb.append(": x='" + chosenX + "': y='" + chosenY + "': text='"
						+ chosenText);
				sb.append("'\" -t " + chosenTime + " -y ");
				sb.append(dir + outputFile + ".mp4 "); 

				// Re-encoding files
				sb.append("; avconv -ss 0 -i " + dir + outputFile 
						+ ".mp4 -vcodec libx264 -acodec aac   ");
				sb.append("  -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
						+ dir + outputFile + ".ts"); 

				sb.append("; avconv -i " + directory + inputFile
						+ " -vcodec libx264 -acodec aac   ");
				sb.append("  -bsf:v h264_mp4toannexb -f mpegts -strict experimental "
						+ " -y " + dir + x + ".ts"); 

				// Concatenating files
				sb.append("; avconv -i concat:\"" + dir
						+ x //
						+ ".ts|" + dir
						+ outputFile //
						+ ".ts\" -c copy    -bsf:a aac_adtstoasc -y "
						+ directoryChosen + "final.mp4"); 

			}

			String command = sb.toString();
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
			int exitvalue = process.exitValue();
			if(exitvalue==0){
				JOptionPane.showMessageDialog(null,
						"Adding titles/credits was successful=)");
			}else{
				JOptionPane.showMessageDialog(null,
						"Error encountered, adding titles/credits was not successful =(");
			}

			positionX.setText("");
			positionY.setText("");
			timeTextField.setText("");
			textChooser.setText("");

		}

	}

}
