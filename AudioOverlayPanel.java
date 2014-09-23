package mainpackage;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;





@SuppressWarnings("serial")
public class AudioOverlayPanel extends JPanel implements ActionListener {
	private String overlayingMp3;
	private String overlayInputDir;
	private String directoryChosen;

	private JFileChooser fileChooser;
	//private JPanel middlePanel = new JPanel();

	private JButton addOverlay = new JButton("Set Overlay");

	private JButton overlayBrowseButton = new JButton("...");

	private JLabel overlayLabel = new JLabel("Overlaying");
	private JLabel overlayInputLabel = new JLabel("Choose an Input .mp3 File");

	private JTextField startTimeTextField = new JTextField(5);
	private JTextField endTimeTextField = new JTextField(5);
	private JTextField overlayInputTextField = new JTextField(12);

	private JButton dirChooserButton = new JButton("...");
	private JTextField dirChooserTextField = new JTextField(12);

	private String workingDirectory;
	private String file;

	private static final String dir = System.getProperty("user.home")
			+ "/.vamix1/";

	public AudioOverlayPanel(String directory, String inputFile) {
		this.workingDirectory = directory;
		this.file = inputFile;

		overlayBrowseButton.setPreferredSize(new Dimension(20, 19));
		overlayBrowseButton.addActionListener(this);
		dirChooserButton.addActionListener(this);
		addOverlay.addActionListener(this);

		setLayout(new GridBagLayout());
		GridBagConstraints eC = new GridBagConstraints();
		eC.gridx = 0;
		eC.gridy = 0;

		add(overlayLabel, eC);

		eC.gridy = 1;
		add(overlayInputLabel, eC);

		eC.gridy = 2;
		JPanel overlayPanel1 = new JPanel();
		overlayPanel1.add(overlayInputTextField);
		overlayPanel1.add(overlayBrowseButton);
		add(overlayPanel1, eC);

		eC.gridy = 3;
		add(new JLabel("Select Start/End time of Main Video"), eC);

		eC.gridy = 4;
		JPanel overlayPanel2 = new JPanel();
		overlayPanel2.add(new JLabel("Start "));
		overlayPanel2.add(startTimeTextField);
		overlayPanel2.add(new JLabel("End "));
		overlayPanel2.add(endTimeTextField);
		add(overlayPanel2, eC);

		eC.gridy = 5;
		JPanel stripPanel2 = new JPanel();
		stripPanel2.add(dirChooserTextField);
		stripPanel2.add(dirChooserButton);
		add(stripPanel2, eC);

		eC.gridy = 6;
		add(addOverlay, eC);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == overlayBrowseButton) {
			fileChooser = new JFileChooser();
			int returnVal = fileChooser.showOpenDialog(null);

			// fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				overlayInputTextField.setText(fileChooser.getSelectedFile()
						.toString());
				overlayingMp3 = fileChooser.getSelectedFile().getName();
				overlayInputDir = fileChooser.getSelectedFile().getParent()
						+ "/";
				// System.out.println(replace);

			} else if (fileChooser.getSelectedFile() == null) {

				JOptionPane.showMessageDialog(null,
						"Please select an mp3 file!");

			}

		} else if (e.getSource() == dirChooserButton) {
			fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = fileChooser.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				dirChooserTextField.setText(fileChooser.getSelectedFile()
						.toString());
				directoryChosen = fileChooser.getSelectedFile()
						.getAbsolutePath() + "/";
				// System.out.println(directoryChosen);

				// if(!replacingmp3.isEmpty() && !directoryChosen.isEmpty()){
				// replaceAudio.setEnabled(true);
				// }

			} else if (directoryChosen == null) {

				JOptionPane.showMessageDialog(null,
						"Please select a directory!");

			}
		} else if (e.getSource() == addOverlay) {
			addOverlay.setEnabled(false);
			// System.out.println("hi im here");
			// .toString());
			String start = this.startTimeTextField.getText();
			String end = this.endTimeTextField.getText();
			BackgroundOverlayAudio bg = new BackgroundOverlayAudio(
					directoryChosen, workingDirectory, overlayInputDir, file,
					overlayingMp3, start, end);
			bg.execute();

		}
	}

	class BackgroundOverlayAudio extends SwingWorker<Void, String> {
		String directoryChosen;
		String directory;
		String overlayDir;
		String overlayFile;
		String inputFile;
		String start;
		String end;
		String endTime;

		public BackgroundOverlayAudio(String directoryChosen, String directory,
				String overlayDir, String input, String overlayFile,
				String startTime, String endTime) {
			this.directoryChosen = directoryChosen;
			this.directory = directory;
			this.overlayDir = overlayDir;
			this.overlayFile = overlayFile;
			this.inputFile = input;
			this.start = startTime;
			int ending = Integer.parseInt(endTime)- Integer.parseInt(startTime);
			this.end = ""+ending;
			this.endTime=endTime;
		}

		@Override
		protected Void doInBackground() throws Exception {

			try {
				Directory.makeDir(dir);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			Process process;

			StringBuilder sb = new StringBuilder();

//			System.out.println("start is " + start);
//			System.out.println("end is " + end);
//			System.out.println("dir is " + dir);
//			System.out.println("directory is " + directory);
//			System.out.println("overlaydirectory is " + overlayDir);
//			System.out.println("directoryChosen is " + directoryChosen);
//			System.out.println("inputFile is " + inputFile);
//			System.out.println("overlayFile is " + overlayFile);

			// You entered
			// start is 5
			// end is 20
			// dir is /afs/ec.auckland.ac.nz/users/f/e/felz123/unixhome/.vamix1/
			// directory is /afs/ec.auckland.ac.nz/users/f/e/felz123/unixhome/
			// overlaydirectory is
			// /afs/ec.auckland.ac.nz/users/f/e/felz123/unixhome/
			// directoryChosen is
			// /afs/ec.auckland.ac.nz/users/f/e/felz123/unixhome/Documents/
			// inputFile is wild.mp4

			// String command =
			// "avconv -i "+mp4+".mp4 -i "+mp3+".mp3 -map 0:0 -map 1:0  -c:v copy -c:a copy -t "+timeToStop+" "+outputName+".mp4";

/*			String cmd1 = "avconv -ss "+start+" -i " + directory + inputFile + " -i "
					+ overlayDir + overlayFile
					+ " -map 0:0 -map 1:0 -c:v copy -c:a copy -t " + end+ " "
					+ directoryChosen + "out.mp4";
					
*/			
			
			System.out.println("end is "+end);
			System.out.println("endTime is "+endTime);
			sb.append("avconv -ss "+start+" -i " + directory + inputFile + " -i "
					+ overlayDir + overlayFile
					+ " -map 0:0 -map 1:0 -c:v copy -c:a copy -t " + end+ " "
					+ dir + "out.mp4");
			
//		String one=	"avconv -ss "+start+" -i " + directory + inputFile + " -i "
//				+ overlayDir + overlayFile
//				+ " -map 0:0 -map 1:0 -c:v copy -c:a copy -t " + end+ " "
//				+ dir + "out.mp4";
		
	//	int y = Integer.parseInt(start)-1;
		
			// generating 3 .st file
			sb.append("; avconv -ss 0 -i " + directory + inputFile
					+ " -vcodec libx264 -acodec aac   ");
			sb.append("  -bsf:v h264_mp4toannexb -f mpegts -strict experimental -t "+start+" -y "
					+ dir +"start.ts"); //
			
//			String two ="; avconv -ss 0 -i " + directory + inputFile
//					+ ".mp4 -vcodec libx264 -acodec aac   "+
//			"  -bsf:v h264_mp4toannexb -f mpegts -strict experimental -t "+start+" -y "
//					+ dir +"start.ts";
			
			sb.append("; avconv -ss 0 -i " + dir + "out.mp4 -vcodec libx264 -acodec aac   ");
			sb.append("  -bsf:v h264_mp4toannexb -f mpegts -strict experimental -t "+end+" -y "
					+ dir + "out.ts"); //
			
//			String three="; avconv -ss 0 -i " + dir + "out.mp4 -vcodec libx264 -acodec aac   "+"  -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
//					+ dir + "out.ts";

		//	int x = Integer.parseInt(endTime) +1;
			sb.append("; avconv -ss "+endTime+" -i " + directory + inputFile
					+ " -vcodec libx264 -acodec aac   ");
			sb.append("  -bsf:v h264_mp4toannexb -f mpegts -strict experimental "
					+ " -y " + dir +  "end.ts"); //
//
//			String four = "; avconv -ss "+endTime+" -i " + directory + inputFile
//					+ " -vcodec libx264 -acodec aac   "+"  -bsf:v h264_mp4toannexb -f mpegts -strict experimental "
//					+ " -y " + dir +  "end.ts";
			
			// concatenating to end
			sb.append("; avconv -i concat:\"" + dir
					
					+ "start.ts|" + dir +"out.ts|"+dir+"end"
					
					+ ".ts\" -c copy    -bsf:a aac_adtstoasc -y "
					+ directoryChosen + "final.mp4"); //
			
//			String five= "; avconv -i concat:\"" + dir
//					
//					+ "start.ts|" + dir +"out.ts|"+dir+"end"
//					
//					+ ".ts\" -c copy    -bsf:a aac_adtstoasc -y "
//					+ directoryChosen + "final.mp4";
			
/*
			System.out.println(one);
			System.out.println(two);
			System.out.println(three);
			System.out.println(four);
			System.out.println(five);*/
			
			 String command = sb.toString();
//			System.out.println(cmd1);

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
			JOptionPane.showMessageDialog(null,
					"Overlaying audio was successful!");
			addOverlay.setEnabled(true);
			overlayInputTextField.setText("");
			startTimeTextField.setText("");
			endTimeTextField.setText("");
			dirChooserTextField.setText("");

		}
	}

}
