package mainpackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;



public class AudioPanel {
	
	//private StripAudioWorker myStripAudioWorker;
	private String workingDir;
	
	public AudioPanel(String dir){
		this.workingDir=dir;
	}
	
	//ADD DIRECTORIES TO FILE NAMES
	public void overlayAudio(String mp4, String mp3, String outputName, String timeToStop)  {
		
		String command = "avconv -i "+mp4+".mp4 -i "+mp3+".mp3 -map 0:0 -map 1:0  -c:v copy -c:a copy -t "+timeToStop+" "+outputName+".mp4";
		//String command ="avconv -i "+input1+" -i "+input2+" -filter_complex  amix=inputs=2:duration=first:dropout_transition=3 "+outputDirectory;

//		String command ="avconv -i "+input1+" -filter_complex  amix=inputs=1:duration=first:dropout_transition=3 "+outputDirectory;

	}
	
	//
	

	//takes in replacing mp3
	public void replaceAudio(String toBeReplaced, String replacingmp3, String outputFileName, String timeToStop){
		//making video audioless
		String commmand = "avconv -i "+toBeReplaced+".mp4 -vcodec copy -an silent.mp4";

		overlayAudio("silent.mp4", replacingmp3, outputFileName, timeToStop );
	}
	
	class StripAudioWorker extends SwingWorker<Void, String> {
		Process process;
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
	
}
