package mainpackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.NativeLibrary;

//fix error when both fwd/rwd pressed
//add to play video from current JSLIDER
//Fix play button, after stopped and scroller moved
public class ViewPanel extends JPanel implements ActionListener,
ChangeListener, MouseListener {
	// Create a new media player instance for the run-time platform

	private String workingDirectory = "/afs/ec.auckland.ac.nz/users/h/s/hsye185/unixhome/Documents/";

	// Tabbed Pane containinee Panels
	TitleCreditsPanel titleCreditsPanel;
	OverlayPanel overlayPanel;
	//AudioPanel audioPanel;

	private PreviewWorker previewWorker;
	private FileTree workspaceFileTree;
	private boolean isPressing;
	//
	private boolean isSelectingPositionTab1 = false;
	private boolean isSelectingPositionTab2 = false;

	private JMenuBar menuBar;
	private JMenu exampleMenu1;
	private JMenu exampleMenu2;
	private JMenu exampleMenu3;
	private JMenu exampleMenu4;
	private JMenu exampleMenu5;
	private JMenu exampleMenu6;
	private JMenuItem openProjectItem;
	private JMenuItem saveProjectItem;
	private JMenuItem copyMenuItem;
	private JMenuItem pasteMenuItem;

	private JPanel videoViewPanel = new JPanel();
	// ============================================================================================
	private JPanel bottomPanel = new JPanel();
	private JPanel previewPanel = new JPanel();
	private JPanel editPanel = new JPanel();

	private JPanel timeLinePanel = new JPanel();
	private TimelinePanel timeLineContent = new TimelinePanel();

	private JPanel previewContent;
	// ============================================================================================

	private JPanel fileTreePanel = new JPanel();

	private JPanel thumbnailViewPanel = new JPanel();
	private JButton drawMain = new JButton("Add To Project");
	private JButton drawTitle = new JButton("Add Title");
	private JButton drawCredits = new JButton("Add Credits");
	private JButton drawOverlay1 = new JButton("Add Overlay to beginning");
	private JButton drawOverlay2 = new JButton("Add Overlay to ending");
	private JButton drawOverlayAudio = new JButton("Add Audio Overlay");
	private JButton drawOverlayAudio2 = new JButton("Add 2nd Audio Overlay");

	private EmbeddedMediaPlayerComponent mediaComponent = new EmbeddedMediaPlayerComponent();
	private EmbeddedMediaPlayer videoPlayer = mediaComponent.getMediaPlayer();
	private EmbeddedMediaPlayerComponent previewMediaComponent = new EmbeddedMediaPlayerComponent();
	private EmbeddedMediaPlayer previewPlayer = previewMediaComponent
			.getMediaPlayer();
	private JSlider timeSlider = new JSlider();
	private JSlider volumeSlider = new JSlider();
	private JPanel buttonsPanel = new JPanel();
	private JButton playButton = new JButton("Play/Pause");
	private JButton stopButton = new JButton("Stop");
	private JButton restartButton = new JButton("Restart");
	private JButton forwardButton = new JButton(">>");
	private JButton backwardsButton = new JButton("<<");
	private JButton muteButton = new JButton("Mute");
	private JButton stripButton = new JButton("Strip Audio");
	private boolean isPaused = false;
	private boolean isSeeking = false;
	private SeekWorker mySeeker;
	private StripAudioWorker myStripAudioWorker;
	// private final String fileLocation =
	// "/media/felz123/HOLDEN/HaseebsWorkplace/wild.mp4";
	private Process process;

	private String fileLocation;
	// so of there is no parent, its only a slash, does that make sense?
	private String directory = "/";
	private String inputFile;

	public ViewPanel() {

		// displaying a file chooser

		while (fileLocation == null) {
			final JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Please select a media file =)");
			fc.showOpenDialog(this);
			// setting file variable to the selected file
			try {
				fileLocation = fc.getSelectedFile().getAbsolutePath();
				directory = fc.getSelectedFile().getParent() + "/";
				inputFile = fc.getSelectedFile().getName();
				//				System.out.println(fileLocation);
				//				 System.out.println(inputFile);
				//				 System.out.println(directory);
				// final String directory =
				// "/media/felz123/HOLDEN/HaseebsWorkplace/";
			} catch (NullPointerException n) {

			}

		}

		//		videoPlayer.playMedia(inputFile);
		//		videoPlayer.stop();

		timeSlider.setValue(0);

		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
				"/usr/lib/vlc");
		setLayout(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		buttonsPanel.add(playButton);
		buttonsPanel.add(stopButton);
		buttonsPanel.add(restartButton);
		buttonsPanel.add(backwardsButton);
		buttonsPanel.add(forwardButton);
		// buttonsPanel.add(stripButton);
		buttonsPanel.add(muteButton);
		buttonsPanel.add(volumeSlider);

		playButton.addActionListener(this);
		stopButton.addActionListener(this);
		restartButton.addActionListener(this);
		forwardButton.addActionListener(this);
		backwardsButton.addActionListener(this);
		stripButton.addActionListener(this);
		volumeSlider.addChangeListener(this);
		timeSlider.addMouseListener(this);

		videoPlayer.addMediaPlayerEventListener(new VideoListener(timeSlider));
		mediaComponent.setPreferredSize(new Dimension(900, 600));
		// add(mediaComponent,BorderLayout.LINE_END);
		videoViewPanel.setLayout(new GridBagLayout());
		videoViewPanel.setBackground(Color.LIGHT_GRAY);
		videoViewPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		videoViewPanel.add(mediaComponent, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.NORTH;
		timeSlider.setBackground(Color.LIGHT_GRAY);
		videoViewPanel.add(timeSlider, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		buttonsPanel.setBackground(Color.LIGHT_GRAY);
		videoViewPanel.add(buttonsPanel, gbc);

		add(videoViewPanel, BorderLayout.LINE_END);

		timeSlider.setPreferredSize(new Dimension(600, 20));

		menuBar = new JMenuBar();
		add(menuBar, BorderLayout.PAGE_START);

		bottomPanel.setPreferredSize(new Dimension(1200, 300));
		bottomPanel.setBackground(Color.LIGHT_GRAY);
		add(bottomPanel, BorderLayout.PAGE_END);
		bottomPanel.setLayout(new BorderLayout());
		previewPanel.setBackground(Color.LIGHT_GRAY);
		previewPanel.setPreferredSize(new Dimension(300, 1));
		bottomPanel.add(previewPanel, BorderLayout.LINE_START);
		editPanel.setBackground(Color.LIGHT_GRAY);
		bottomPanel.add(editPanel, BorderLayout.CENTER);
		timeLinePanel.setBackground(Color.LIGHT_GRAY);
		timeLinePanel.setPreferredSize(new Dimension(650, 1));
		bottomPanel.add(timeLinePanel, BorderLayout.LINE_END);

		// ======
		// bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		// bottomPanel.setBorder(BorderFactory.createTitledBorder("title"));
		// videoViewPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		// bottomPanel.setBorder(BorderFactory.createLineBorder(Color.cyan));
		// ======

		thumbnailViewPanel.setPreferredSize(new Dimension(200, 200));
		thumbnailViewPanel.setBackground(Color.LIGHT_GRAY);
		thumbnailViewPanel.setBorder(BorderFactory
				.createLineBorder(Color.BLACK));
		drawMain.addActionListener(this);
		thumbnailViewPanel.add(drawMain);
		drawTitle.addActionListener(this);
		thumbnailViewPanel.add(drawTitle);
		drawCredits.addActionListener(this);
		thumbnailViewPanel.add(drawCredits);
		drawOverlay1.addActionListener(this);
		thumbnailViewPanel.add(drawOverlay1);
		drawOverlay2.addActionListener(this);
		thumbnailViewPanel.add(drawOverlay2);
		drawOverlayAudio.addActionListener(this);
		thumbnailViewPanel.add(drawOverlayAudio);
		drawOverlayAudio2.addActionListener(this);
		thumbnailViewPanel.add(drawOverlayAudio2);

		add(thumbnailViewPanel, BorderLayout.CENTER);

		fileTreePanel.setLayout(new BorderLayout());
		fileTreePanel.setPreferredSize(new Dimension(250, 200));
		fileTreePanel.setBackground(Color.BLACK);
		fileTreePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				// System.out.println(fileTreePanel.getY());
	//	workspaceFileTree = new FileTree(new File(directory));
	//	workspaceFileTree.getTree().addMouseListener(this);
		//fileTreePanel.add(workspaceFileTree,BorderLayout.CENTER);
		
		add(fileTreePanel, BorderLayout.LINE_START);

		// build the File menu
		exampleMenu1 = new JMenu("exampleMenu1");
		exampleMenu2 = new JMenu("exampleMenu2");
		exampleMenu3 = new JMenu("exampleMenu3");
		exampleMenu4 = new JMenu("exampleMenu4");
		exampleMenu5 = new JMenu("exampleMenu5");
		exampleMenu6 = new JMenu("exampleMenu6");

		openProjectItem = new JMenuItem("OppreviewWorkeren Project");
		saveProjectItem = new JMenuItem("Save Project");

		openProjectItem.addActionListener(this);
		exampleMenu1.add(openProjectItem);
		exampleMenu1.add(new JMenuItem("Export"));
		exampleMenu1.add(new JMenuItem("Switch Workplace"));

		exampleMenu2.add(new JMenuItem("example item"));
		exampleMenu3.add(new JMenuItem("example item"));
		exampleMenu4.add(new JMenuItem("example item"));
		exampleMenu5.add(new JMenuItem("example item"));
		exampleMenu6.add(new JMenuItem("example item"));

		exampleMenu1.add(saveProjectItem);
		exampleMenu2.add(new JMenuItem("example item"));
		exampleMenu3.add(new JMenuItem("example item"));
		exampleMenu4.add(new JMenuItem("example item"));
		exampleMenu5.add(new JMenuItem("example item"));
		exampleMenu6.add(new JMenuItem("example item"));

		menuBar.add(exampleMenu1);
		menuBar.add(exampleMenu2);
		menuBar.add(exampleMenu3);
		menuBar.add(exampleMenu4);
		menuBar.add(exampleMenu5);
		menuBar.add(exampleMenu6);
		// ============================================================================

		TitlePanel editTitle = new TitlePanel("Text Settings");
		editTitle.setBackground(Color.orange);
		editTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JTabbedPane editContent = new JTabbedPane();
		editContent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		titleCreditsPanel = new TitleCreditsPanel(mediaComponent, directory, inputFile);
		//overlayPanel = new OverlayPanel();
		overlayPanel = new OverlayPanel(mediaComponent, directory, inputFile);
		// audioPanel = new AudioPanel();
		AudioReplacePanel audioReplacePanel = new AudioReplacePanel(mediaComponent, directory, inputFile);
		AudioStripPanel audioStripPanel = new AudioStripPanel( directory, inputFile);
		AudioOverlayPanel audioOverlayPanel = new AudioOverlayPanel(directory, inputFile);
		overlayPanel.getGenerateButton().addActionListener(this);
		editContent.add("Title/Credits", titleCreditsPanel);
		editContent.add("Overlay Text", overlayPanel);
		editContent.add("Strip Audio", audioStripPanel);
		editContent.add("Overlay Audio", audioOverlayPanel);
		editContent.add("Replace Audio", audioReplacePanel);
		editContent.add("Merge", new JPanel());

		// Listeners for add buttons
		titleCreditsPanel.getPositionButton().addActionListener(this);
		overlayPanel.getPositionButton().addActionListener(this);
		//
		editPanel.setLayout(new BorderLayout());

		editPanel.add(editTitle, BorderLayout.PAGE_START);
		editPanel.add(editContent, BorderLayout.CENTER);

		// ==================================================================================
		TitlePanel previewTitle = new TitlePanel("Preview");
		previewTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		previewTitle.setBackground(Color.orange);

		// previewContent = new JPanel();
		// previewContent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		previewPanel.setLayout(new BorderLayout());
		// previewContent.setBackground(Color.LIGHT_GRAY);
		// previewContent.addMouseListener(this);
		// previewContent.setLayout(new BorderLayout());
		// previewContent.add(previewMediaComponent,BorderLayout.CENTER);
		previewMediaComponent.addMouseListener(this);
		previewPanel.add(previewTitle, BorderLayout.PAGE_START);
		previewPanel.add(previewMediaComponent, BorderLayout.CENTER);
		// ==================================================================================
		TitlePanel timeLineTitle = new TitlePanel(
				"Visual Representation of Current Project");
		timeLineTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		timeLineTitle.setBackground(Color.orange);

		timeLineContent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		timeLineContent.setBackground(Color.LIGHT_GRAY);
		timeLinePanel.setLayout(new BorderLayout());
		timeLinePanel.add(timeLineTitle, BorderLayout.PAGE_START);
		timeLinePanel.add(timeLineContent, BorderLayout.CENTER);
		// ==================================================================================
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == playButton) {
			// previewPlayer.playMedia(fileLocation);
			if (isSeeking) {
				mySeeker.cancel(true);
				isSeeking = false;
				videoPlayer.play();
			} else if (videoPlayer.getRate() == 7.5f) {
				videoPlayer.setRate(1f);
			} else if (videoPlayer.isPlaying()) {
				videoPlayer.pause();
				isPaused = true;
			} else {
				if (isPaused) {
					videoPlayer.play();
					isPaused = false;
				} else {
					videoPlayer.playMedia(fileLocation);
				}

			}

		} else if (e.getSource() == stopButton) {
			timeSlider.setValue(0);
			videoPlayer.stop();
		} else if (e.getSource() == restartButton) {
			timeSlider.setValue(0);
			System.out.println(videoPlayer.getLength());
			videoPlayer.playMedia(fileLocation);
		} else if (e.getSource() == forwardButton) {
			if (videoPlayer.isPlaying()) {
				mySeeker = new SeekWorker(true);
				mySeeker.execute();
				isSeeking = true;
			}
		} else if (e.getSource() == backwardsButton) {
			if (videoPlayer.isPlaying()) {
				mySeeker = new SeekWorker(false);
				mySeeker.execute();
				isSeeking = true;
			}
		} else if (e.getSource() == stripButton) {
			if (videoPlayer.isPlaying()) {
				myStripAudioWorker = new StripAudioWorker();
				myStripAudioWorker.execute();
			}
		} else if (e.getSource() == titleCreditsPanel.getPositionButton()) {
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			isSelectingPositionTab1 = true;
		} else if (e.getSource() == overlayPanel.getPositionButton()) {
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			isSelectingPositionTab2 = true;
		} else if (e.getSource() == drawMain) {
			timeLineContent.addToProject(new int[] { 130, 50, 400, 20 },
					new Color(84, 52, 10));
			// audio
			timeLineContent.addToProject(new int[] { 130, 180, 400, 20 },
					new Color(84, 52, 10));
			System.out.println(timeLineContent.getPreferredSize());
			// timeLineContent.addToProject(new int[]{(int)
			// (Math.random()*200),(int) (Math.random()*200),(int)
			// (Math.random()*500),(int) (Math.random()*100)},new
			// Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)));
		} else if (e.getSource() == drawTitle) {
			timeLineContent.addToProject(new int[] { 80, 50, 50, 20 },
					new Color(191, 119, 25));
		} else if (e.getSource() == drawCredits) {
			timeLineContent.addToProject(new int[] { 530, 50, 50, 20 },
					new Color(191, 119, 25));
		} else if (e.getSource() == drawOverlay1) {
			timeLineContent.addToProject(new int[] { 130, 75, 80, 20 },
					new Color(145, 123, 93));
		} else if (e.getSource() == drawOverlay2) {
			timeLineContent.addToProject(new int[] { 480, 75, 50, 20 },
					new Color(145, 123, 93));
		} else if (e.getSource() == drawOverlayAudio) {
			timeLineContent.addToProject(new int[] { 160, 205, 250, 20 },
					new Color(145, 123, 93));
		} else if (e.getSource() == drawOverlayAudio2) {
			timeLineContent.addToProject(new int[] { 250, 230, 250, 20 },
					new Color(145, 123, 93));
		}else if(e.getSource()==overlayPanel.getGenerateButton()){
			previewWorker = new PreviewWorker();
			previewWorker.execute();
		}
	}

	class SeekWorker extends SwingWorker<Void, Integer> {
		private Boolean isForward;

		public SeekWorker(boolean testForward) {
			isForward = testForward;
		}

		@Override
		protected Void doInBackground() throws Exception {
			if (isForward) {
				while (!isCancelled()) {
					if (videoPlayer.getTime() == videoPlayer.getLength()
							|| videoPlayer.getTime() == 0) {
						this.cancel(true);
					} else {
						Thread.sleep(10);
						publish(+100);
					}

				}
			} else {
				while (!isCancelled()) {
					if (videoPlayer.getTime() == videoPlayer.getLength()
							|| videoPlayer.getTime() == 0) {
						this.cancel(true);
						videoPlayer.stop();
					} else {
						Thread.sleep(10);
						publish(-100);
					}
				}
			}
			return null;
		}

		protected void process(List<Integer> chunks) {
			for (Integer s : chunks) {
				videoPlayer.skip(s);
			}
		}

		protected void done() {
			if (!isCancelled()) {

			}

		}

	}

	class StripAudioWorker extends SwingWorker<Void, String> {

		@Override
		protected Void doInBackground() throws Exception {
			ProcessBuilder builder = new ProcessBuilder("avconv", "-i",
					"wild.mp4", "wild2.mp3", "-y");
			builder.redirectErrorStream(true);

			try {
				process = builder.start();
			} catch (IOException e) {
				// This will never happen for our functionality, but if it ever
				// does this error message will come up.
				JOptionPane.showMessageDialog(null,
						"Bash Process could not start due to an I/O error");
			}

			BufferedReader stdout = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line;
			try {

				while ((line = stdout.readLine()) != null) {
					System.out.println(line);
					// sends each line from the input/error stream to publish,
					// (for GUI updates)
					if (!isCancelled()) {
						publish(line + System.getProperty("line.separator"));
					} else {
						process.destroy();
						return null;
					}
				}

			} catch (IOException e) {
				JOptionPane
				.showMessageDialog(null,
						"Process could not read line from input/error stream due to I/O error");
			}

			return null;
		}

		protected void process(List<String> chunks) {
			for (String s : chunks) {
				System.out.println(s);

			}
		}

		protected void done() {
			int exitValue = process.exitValue();
			if (exitValue == 0) {
				JOptionPane.showMessageDialog(null, "Audio Strip Complete!");
			} else {
				JOptionPane.showMessageDialog(null,
						"Could not complete Audio Strip, an Error occured");
			}

		}

	}
	class PreviewWorker extends SwingWorker<Void,String>{

		@Override
		protected Void doInBackground() throws Exception {
			/*
			 * avconv -ss 0 -i wild.mp4 -strict experimental -vf "drawtext=fontfile=Arial.ttf:fontsize=12:fontcolor=black:x=5:y=5:text='hi'"
			 *  -t 5 out.mp4
			 */

			//ProcessBuilder builder = new ProcessBuilder("avconv","-ss","0","-i","wild.mp4","-strict","experimental","-vf" +
			//		"","","","","","","","");

			Process process;
			String cmd="";
			//S//ystem.out.println(overlayPanel.getSizeChooser().getSelectedItem().toString());
			if(overlayPanel.getTextSideChooser().getSelectedItem().toString().equals("Beginning")){
				System.out.println(1);
				cmd="avconv -ss 0 -i /media/hsye185/HSYE185/workspace/SE206_Assignment3_hsye185_felz123/wild.mp4 -strict experimental -vf " +
						"\"drawtext=fontfile=/media/hsye185/HSYE185/workspace/SE206_Assignment3_hsye185_felz123/"+overlayPanel.getFontChooser().getSelectedItem().toString()+".ttf:fontsize="+overlayPanel.getSizeChooser().getSelectedItem().toString()+":fontcolor="+overlayPanel.getFontColorChooser().getSelectedItem().toString()+":x="+overlayPanel.getPositionTextFieldX().getText()+":y="+overlayPanel.getPositionTextFieldY().getText()+":text='"+overlayPanel.getTextChooser().getText()+"'\" -t "+overlayPanel.getTimeTextField().getText()+" -y  /media/hsye185/HSYE185/workspace/SE206_Assignment3_hsye185_felz123/preview.mp4";
			}else{
				int lengthOfVideo = (int)(videoPlayer.getLength()/1000);
				int startTime = lengthOfVideo - Integer.parseInt(overlayPanel.getTimeTextField().getText()); 
//				System.out.println(lengthOfVideo);
				cmd="avconv -ss "+startTime+" -i /media/hsye185/HSYE185/workspace/SE206_Assignment3_hsye185_felz123/wild.mp4 -strict experimental -vf " +
						"\"drawtext=fontfile=/media/hsye185/HSYE185/workspace/SE206_Assignment3_hsye185_felz123/"+overlayPanel.getFontChooser().getSelectedItem().toString()+".ttf:fontsize="+overlayPanel.getSizeChooser().getSelectedItem().toString()+":fontcolor="+overlayPanel.getFontColorChooser().getSelectedItem().toString()+":x="+overlayPanel.getPositionTextFieldX().getText()+":y="+overlayPanel.getPositionTextFieldY().getText()+":text='"+overlayPanel.getTextChooser().getText()+"'\" -t "+overlayPanel.getTimeTextField().getText()+" -y  /media/hsye185/HSYE185/workspace/SE206_Assignment3_hsye185_felz123/preview.mp4";
			}


			ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);


			try {
				process = builder.start();
				process.waitFor();
			} catch (Exception e) {
				e.printStackTrace();
			}


			return null;
		}

		protected void done(){
			JOptionPane.showMessageDialog(null,"Generating preview was successful");
			previewPlayer.playMedia("/media/hsye185/HSYE185/workspace/SE206_Assignment3_hsye185_felz123/preview.mp4");


		}

	}

	@Override
	public void stateChanged(ChangeEvent e) {

		if (e.getSource() == volumeSlider) {
			videoPlayer.setVolume(volumeSlider.getValue());
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == timeSlider) {
			if (videoPlayer.isPlaying()) {
				videoPlayer.setTime((int) ((e.getX() / 600.0) * videoPlayer
						.getLength()));
			}
		}
		if (e.getSource() == previewMediaComponent) {
			if (isSelectingPositionTab1) {
				titleCreditsPanel.getPositionTextFieldX().setText(
						String.valueOf(e.getPoint().getX()));
				titleCreditsPanel.getPositionTextFieldY().setText(
						String.valueOf(e.getPoint().getY()));
				isSelectingPositionTab1 = false;
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			} else if (isSelectingPositionTab2) {
				overlayPanel.getPositionTextFieldX().setText(
						String.valueOf(e.getPoint().getX()));
				overlayPanel.getPositionTextFieldY().setText(
						String.valueOf(e.getPoint().getY()));
				isSelectingPositionTab2 = false;
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() == timeSlider) {
			if (videoPlayer.isPlaying()) {
				videoPlayer.setTime((int) ((e.getX() / 600.0) * videoPlayer
						.getLength()));
			}
		}else if(e.getSource()==workspaceFileTree.getTree()){
			System.out.println("@@@ "+e.getPoint());
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getSource() == timeSlider) {
			if (videoPlayer.isPlaying()) {
				videoPlayer.setTime((int) ((e.getX() / 600.0) * videoPlayer
						.getLength()));
			}else if(e.getSource()==workspaceFileTree.getTree()){
				
				System.out.println(e.getPoint());
			} 
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
