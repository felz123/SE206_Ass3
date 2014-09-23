package mainpackage;

/**
 * Overlay text over video panel
 * Authors: Farida, Haseeb
 * */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

@SuppressWarnings("serial")
public class OverlayPanel extends JPanel implements ActionListener {

	private JPanel leftPanel = new JPanel();
	private JPanel rightPanel = new JPanel();

	private JLabel textLabel = new JLabel("Enter text");
	private JTextArea textChooser = new JTextArea();
	private JLabel fontLabel = new JLabel("Font specifications");
	private JComboBox<String> sizeChooser = new JComboBox<String>(new String[] {
			"10", "15", "20", "25", "30", "35", "40", "45", "50" });
	private JComboBox<String> fontChooser = new JComboBox<String>(new String[] {
			"Arial", "Comic Sans", "Lexia", "Ultra Violet" });
	private JComboBox<String> fontColorChooser = new JComboBox<String>(
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

	private JButton generateButton = new JButton("Generate Preview");

	private String chosenText;
	private int chosenSize;
	private String chosenFont;
	private String chosenX;
	private String chosenY;
	private String chosenColor;
	private String chosenTime;
	private String chosenEnding;
	private String directoryChosen;

	private EmbeddedMediaPlayerComponent video;
	private String workingDirectory;
	private String file;

	private static final String dir = System.getProperty("user.home")
			+ "/.vamix1/";

	String fontDir = "/media/felz123/HOLDEN/HaseebsWorkplace/SE206_Assignment3_hsye185_felz123/";

	public OverlayPanel(EmbeddedMediaPlayerComponent mediaComponent,
			String directory, String inputFile) {
		this.workingDirectory = directory;
		this.file = inputFile;
		this.video = mediaComponent;

		addTextButton.addActionListener(this);
		
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
		fontPanel.add(fontColorChooser);
		fontChooser.addActionListener(this);
		fontPanel.add(fontChooser);
		rightPanel.add(fontPanel, eC);
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
		textSideChooser.setPreferredSize(new Dimension(100, 22));
		positionPanel.add(textSideChooser);
		rightPanel.add(positionPanel, eC);
		eC.gridy = 6;
		rightPanel.add(timeLabel, eC);
		eC.gridy = 7;
		rightPanel.add(timeTextField, eC);
		eC.insets = new Insets(10, 0, 0, 0);
		eC.gridy = 8;
		rightPanel.add(generateButton, eC);
		eC.gridy = 9;
		rightPanel.add(addTextButton, eC);

	}

	public JButton getGenerateButton() {
		return this.generateButton;
	}

	public JLabel getTextLabel() {
		return textLabel;
	}

	public void setTextLabel(JLabel textLabel) {
		this.textLabel = textLabel;
	}

	public JTextArea getTextChooser() {
		return textChooser;
	}

	public void setTextChooser(JTextArea textChooser) {
		this.textChooser = textChooser;
	}

	public JComboBox<String> getSizeChooser() {
		return sizeChooser;
	}

	public void setSizeChooser(JComboBox<String> sizeChooser) {
		this.sizeChooser = sizeChooser;
	}

	public JComboBox<String> getFontChooser() {
		return fontChooser;
	}

	public void setFontChooser(JComboBox<String> fontChooser) {
		this.fontChooser = fontChooser;
	}

	public JComboBox<String> getFontColorChooser() {
		return fontColorChooser;
	}

	public void setFontColorChooser(JComboBox<String> fontColorChooser) {
		this.fontColorChooser = fontColorChooser;
	}

	public JComboBox<String> getTextSideChooser() {
		return textSideChooser;
	}

	public void setTextSideChooser(JComboBox<String> textSideChooser) {
		this.textSideChooser = textSideChooser;
	}

	public JTextField getTimeTextField() {
		return timeTextField;
	}

	public void setTimeTextField(JTextField timeTextField) {
		this.timeTextField = timeTextField;
	}

	public EmbeddedMediaPlayerComponent getVideo() {
		return video;
	}

	public void setVideo(EmbeddedMediaPlayerComponent video) {
		this.video = video;
	}

	public String getWorkingDirectory() {
		return workingDirectory;
	}

	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
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
			} else {

				final JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Choose a Directory");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);

				//if directory is chosen, we assign it to directoryChosen
				// variable directory for output file
				if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

					directoryChosen = chooser.getSelectedFile()
							.getAbsolutePath() + "/";

					overlayText(directoryChosen, workingDirectory, file);

					
					//if directory remains null and wasn't specified by user,
					//error message displays			 
				} else if (directoryChosen == null) {

					JOptionPane.showMessageDialog(null,
							"Please select a directory!");
					addTextButton.setEnabled(true);

				}
			}

			//making sure no fields were left blank
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

	// method that overlays text over video, calling background thread
	private void overlayText(String directoryChosen, String directory,
			String inputFile) {

		BackgroundOverlayText bg = new BackgroundOverlayText(directoryChosen,
				directory, inputFile);
		bg.execute();

	}

	class BackgroundOverlayText extends SwingWorker<Void, String> {
		String directoryChosen;
		String directory;
		String inputFile;
		Process process;

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

			
			String outputFile = "texted";
			String x = "temp";

			StringBuilder sb = new StringBuilder();

			// adding to beginning
			if (chosenEnding.equals("Beginning")) {
				
				// Adding text to beginning
				sb.append("avconv -ss 0 -i " + directory + inputFile
						+ " -strict experimental -vf \"drawtext=fontfile="
						+ fontDir + chosenFont + ".ttf'");
				sb.append(":fontcolor= " + chosenColor + ":fontsize="
						+ chosenSize);
				sb.append(": x='" + chosenX + "': y='" + chosenY + "': text='"
						+ chosenText);
				sb.append("'\" -t " + chosenTime + " -y ");
				sb.append(dir + outputFile + ".mp4 "); 

				// re-encoding files
				sb.append("; avconv -ss 0 -i " + dir + outputFile //
						+ ".mp4 -vcodec libx264 -acodec aac   ");
				sb.append("  -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
						+ dir + outputFile + ".ts"); //

				sb.append("; avconv -ss " + chosenTime + " -i " + directory
						+ inputFile + " -vcodec libx264 -acodec aac   ");
				sb.append("  -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
						+ dir + x + ".ts"); //

				// Concatenating files
				sb.append("; avconv -i concat:\"" + dir
						+ outputFile//
						+ ".ts|" + dir
						+ x//
						+ ".ts\" -c copy    -bsf:a aac_adtstoasc -y "
						+ directoryChosen + "final.mp4");//

				// Adding to ending
			} else {

				int videoLength = ViewPanel.getVideoLength(directory, inputFile);
				int seconds = videoLength - Integer.parseInt(chosenTime);

				// Adding text to ending
				sb.append("avconv -ss " + seconds + " -i " + directory
						+ inputFile
						+ " -strict experimental -vf \"drawtext=fontfile="
						+ fontDir + chosenFont + ".ttf'");
				sb.append(":fontcolor= " + chosenColor + ":fontsize="
						+ chosenSize);
				sb.append(": x='" + chosenX + "': y='" + chosenY + "': text='"
						+ chosenText);
				sb.append("'\" -t " + chosenTime + " -y ");
				sb.append(dir + outputFile + ".mp4 "); //

				// re-encoding files
				sb.append("; avconv -ss 0 -i " + dir + outputFile //
						+ ".mp4 -vcodec libx264 -acodec aac   ");
				sb.append("  -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
						+ dir + outputFile + ".ts"); //

				sb.append("; avconv -i " + directory + inputFile
						+ " -vcodec libx264 -acodec aac   ");
				sb.append("  -bsf:v h264_mp4toannexb -f mpegts -strict experimental -t "
						+ seconds + " -y " + dir + x + ".ts"); //

				// Concatenating 
				sb.append("; avconv -i concat:\"" + dir
						+ x //
						+ ".ts|" + dir
						+ outputFile //
						+ ".ts\" -c copy    -bsf:a aac_adtstoasc -y "
						+ directoryChosen + "final.mp4"); //

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
					"Overlaying text on video was successful =)");
			}else{
				JOptionPane.showMessageDialog(null,
						"Error encountered, Overlaying text was not successful =(");
			}
			
			positionX.setText("");
			positionY.setText("");
			timeTextField.setText("");
			textChooser.setText("");

		}

	}

}
