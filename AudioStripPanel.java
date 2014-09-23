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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import mainpackage.OverlayPanel.BackgroundOverlayText;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class AudioStripPanel extends JPanel implements ActionListener {
	//
	private String directoryChosen;
//	private EmbeddedMediaPlayerComponent video;
	private String workingDirectory;
	private String file;


	//

	private JFileChooser fileChooser = new JFileChooser();
	private JPanel leftPanel = new JPanel();

	private JButton stripButton = new JButton("Strip Audio");

	private JButton stripBrowseButton = new JButton("...");

	private JLabel stripLabel = new JLabel("Stripping");
	private JLabel stripOutputLabel = new JLabel("Choose an Output Directory");

	private JTextField stripOutputTextField = new JTextField(12);
	
	

	

	public AudioStripPanel(String directory, String inputFile) {
		//
		this.workingDirectory = directory;
		this.file = inputFile;
		//this.video = mediaComponent;

		stripBrowseButton.addActionListener(this);
		stripButton.addActionListener(this);
		stripBrowseButton.setPreferredSize(new Dimension(20, 19));

		setPreferredSize(new Dimension(200, 1));

		setLayout(new GridBagLayout());

		GridBagConstraints eC = new GridBagConstraints();

		eC.gridx = 0;
		eC.gridy = 0;

		add(stripLabel, eC);

		eC.gridy = 1;

		add(stripOutputLabel, eC);

		eC.gridy = 2;

		JPanel stripPanel1 = new JPanel();
		stripPanel1.add(stripOutputTextField);
		stripPanel1.add(stripBrowseButton);
		add(stripPanel1, eC);

		eC.gridy = 3;

		add(stripButton, eC);
		//
		stripButton.setEnabled(false);
	}
	public JTextField getStripOutputTextField() {
		return stripOutputTextField;
	}

	public void setStripOutputTextField(JTextField stripOutputTextField) {
		this.stripOutputTextField = stripOutputTextField;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (e.getSource() == stripBrowseButton) {
			int returnVal = fileChooser.showOpenDialog(null);

			

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				stripOutputTextField.setText(fileChooser.getSelectedFile()
						.toString());
				directoryChosen = fileChooser.getSelectedFile()
						.getAbsolutePath() + "/";
				stripButton.setEnabled(true);
				// actual stripping

			} else if (directoryChosen == null) {

				JOptionPane.showMessageDialog(null,
						"Please select a directory!");

			}
		} else if (e.getSource() == stripButton) {
			stripButton.setEnabled(false);
			System.out.println("hi im here");
			BackgroundStripAudio bg = new BackgroundStripAudio(directoryChosen, workingDirectory, file);
			// BackgroundOverlayText bg = new BackgroundOverlayText();
			bg.execute();
		}

	}

	
	class BackgroundStripAudio extends SwingWorker<Void, String> {
		String directoryChosen;
		String directory;
		String inputFile;

		public BackgroundStripAudio(String directoryCh, String workDir,
				String input) {
			this.directoryChosen = directoryCh;
			this.directory = workDir;
			this.inputFile = input;
		}

		@Override
		protected Void doInBackground() throws Exception {
		

			Process process;
			String outputFile = "texted";
			String x = "temp";
			// String inputFile = "wild";

			StringBuilder sb = new StringBuilder();
			sb.append("avconv -i " + inputFile + " " +directoryChosen+ inputFile + ".mp3");

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
			JOptionPane.showMessageDialog(null,
					"Stripping audio was successful!");
			stripButton.setEnabled(true);
		

		}
		}
	
	

}
