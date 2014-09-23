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

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class AudioReplacePanel extends JPanel implements ActionListener{
	
	private String replace;
	private String replaceDir;
	//
	private String directoryChosen;
	private EmbeddedMediaPlayerComponent video;
	private String workingDirectory;
	private String file;
	private static final String dir = System.getProperty("user.home")
			+ "/.vamix1/";

	private JFileChooser fileChooser = new JFileChooser();
	private JPanel rightPanel = new JPanel();
	

	private JButton replaceAudio = new JButton("Replace Main Audio");
	

	private JButton replaceBrowseButton = new JButton("...");
	
	


	private JButton dirChooserButton = new JButton("...");
	private JTextField dirChooserTextField = new JTextField(12);

	
	private JLabel replaceLabel = new JLabel("Replacing");
	private JLabel replaceInputLabel = new JLabel("Choose an Input .mp3 File");
	
	private JTextField replaceInputTextField = new JTextField(12);
//	private String replacingmp3;
	
	public AudioReplacePanel(EmbeddedMediaPlayerComponent mediaComponent,String directory, String inputFile ){
		this.workingDirectory = directory;
		this.file = inputFile;
		this.video=mediaComponent;
	//	this.replacingmp3 = mp3;
		
		
		replaceBrowseButton.addActionListener(this);
		dirChooserButton.addActionListener(this);
		replaceAudio.addActionListener(this);
		replaceBrowseButton.setPreferredSize(new Dimension(20,19));
		//replaceAudio.setEnabled(false);

		setLayout(new GridBagLayout());

		GridBagConstraints eC = new GridBagConstraints();
		
		eC.gridx=0;
		eC.gridy=0;

		add(replaceLabel,eC);
		
		eC.gridy=1;

		add(replaceInputLabel,eC);
		
		eC.gridy=2;
		
		JPanel replacePanel1 = new JPanel();
		replacePanel1.add(replaceInputTextField);
		replacePanel1.add(replaceBrowseButton);
		add(replacePanel1,eC);
		
		
		
		eC.gridy=3;
		JPanel stripPanel2 = new JPanel();
		stripPanel2.add(dirChooserTextField);
		stripPanel2.add(dirChooserButton);
		add(stripPanel2, eC);
		
		
		eC.gridy=4;

		add(replaceAudio,eC);
	}
	public JTextField getDirChooserTextField() {
		return dirChooserTextField;
	}


	public void setDirChooserTextField(JTextField dirChooserTextField) {
		this.dirChooserTextField = dirChooserTextField;
	}


	public JTextField getReplaceInputTextField() {
		return replaceInputTextField;
	}


	public void setReplaceInputTextField(JTextField replaceInputTextField) {
		this.replaceInputTextField = replaceInputTextField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == replaceBrowseButton) {
			fileChooser = new JFileChooser();
			int returnVal = fileChooser.showOpenDialog(null);

		//	fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				replaceInputTextField.setText(fileChooser.getSelectedFile()
						.toString());
			replace=fileChooser.getSelectedFile().getName();
			replaceDir=fileChooser.getSelectedFile().getParent()+"/";
				System.out.println(replace);
				

			} else if (directoryChosen == null) {

				JOptionPane.showMessageDialog(null,
						"Please select a directory!");

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
				//System.out.println(directoryChosen);
				
				
//				if(!replacingmp3.isEmpty() && !directoryChosen.isEmpty()){
//					replaceAudio.setEnabled(true);
//					}

			} else if (directoryChosen == null) {

				JOptionPane.showMessageDialog(null,
						"Please select a directory!");

			}
		}
		

		else if (e.getSource() == replaceAudio) {
			replaceAudio.setEnabled(false);
			//System.out.println("hi im here");
			
			BackgroundReplaceAudio bg = new BackgroundReplaceAudio(directoryChosen, workingDirectory, file, replace);
			bg.execute();
		}

	}
	
	
	class BackgroundReplaceAudio extends SwingWorker<Void, String> {
		String directoryChosen;
		String directory;
		String inputFile;
		String replacingmp3;

		public BackgroundReplaceAudio(String directoryCh, String workDir,
				String input, String mp3) {
			this.directoryChosen = directoryCh;
			this.directory = workDir;
			this.inputFile = input;
			this.replacingmp3=mp3;
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
			System.out.println("directory is "+directory);
			System.out.println("directoryChosen is "+directoryChosen);
			System.out.println("dir is "+dir);
			System.out.println("replacingmp3 is "+replacingmp3);
			System.out.println("inputFile is "+inputFile);
			System.out.println("replaceDir is "+replaceDir);
			
			int seconds = (int)video.getMediaPlayer().getLength()/1000;
			
			
			sb.append("avconv -i "+directory+inputFile+" -vcodec copy -an "+dir+"silent.mp4");
			sb.append(" ; avconv -i "+dir+"silent.mp4 -i "+replaceDir+replacingmp3+" -map 0:0 -map 1:0  -c:v copy -c:a copy -t "+seconds+" -y "+directoryChosen+"out.mp4");
 
			System.out.println(seconds);
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
			JOptionPane.showMessageDialog(null,
					"Replacing audio was successful!");
			replaceAudio.setEnabled(true);
	//		replaceInputTextField.setText("");
		

		}
		}
	
	
	
	
}
