package mainpackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

/**
 * Text editing
 * 
 * @author farida el zanaty felz123 9092212
 * 
 */

public class Text {

	// final String directory = "/media/felz123/HOLDEN/HaseebsWorkplace";
	final public String dir = "/media/felz123/HOLDEN/HaseebsWorkplace/.vamix";

	public void text(Boolean ending) {
		getFrame();
		// background task for making a video
		// true is ending
		MakeVideo makeVideo;
		makeVideo = new MakeVideo(ending);
		makeVideo.execute();
		//overlayAudio("/media/felz123/HOLDEN/HaseebsWorkplace/wild.mp3","/media/felz123/HOLDEN/sintel.mp3","/media/felz123/HOLDEN/HaseebsWorkplace/output.mp3");
		
	
	}

	public void overlayAudio(String in1, String in2, String outputDirectory)  {
		Process process;
		
		String command ="avconv -i "+in1+" -i "+in2+" -filter_complex  amix=inputs=2:duration=first:dropout_transition=3 "+outputDirectory;
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", command);

		try {
			process = builder.start();
			process.waitFor();
		} catch (Exception e) {

			e.printStackTrace();
		}
		System.out.println("overlaying audio was successful!");

	}
	
	public void getThumbnail(){
Process process;
		
		String command ="";
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", command);

		try {
			process = builder.start();
			process.waitFor();
		} catch (Exception e) {

			e.printStackTrace();
		}
		System.out.println("thumbnail!");
	}
	
	public void getFrame(){
		Process process;
		
		String command ="avconv -i /media/felz123/HOLDEN/HaseebsWorkplace/wild.mp4 " +
				"-ss 00:00:00.001 -f image2 -vframes 1 /media/felz123/HOLDEN/HaseebsWorkplace/out.png";
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", command);

		try {
			process = builder.start();
			process.waitFor();
		} catch (Exception e) {

			e.printStackTrace();
		}
		System.out.println("frame!");
	}

	class MakeVideo extends SwingWorker<Void, String> {

		Process process;
		boolean ending;

		public MakeVideo(boolean ending) {
			this.ending = ending;
		}

		// Override doInBackgrount() to execute longTask in the background
		@Override
		protected Void doInBackground() throws Exception {
			Directory.makeDir(dir);

			final String directory = "/media/felz123/HOLDEN/HaseebsWorkplace/";
			final String dir = "/media/felz123/HOLDEN/HaseebsWorkplace/.vamix/";

			String imageFile = "out";
			String inputFile = "wild";
			int time = 5;
			String text = "farida is the best";
			String fontDir = "'" + directory + "/take_and_give/1.ttf'";
			int font = 48;
			String fontString = "" + font;
			String timeString = "" + time;
			String outputFile = "texted";

			try {

				// you can add file handling and where to put text exactly
				// you can rm the output file from first command

				StringBuilder sb = new StringBuilder();

				// making video out of image

				sb.append("avconv -loop 1 -shortest -y -i " + directory
						+ imageFile);
				sb.append(".png -t " + timeString + " -y " + dir + "result.avi");

				// making silent audio
				// sb.append("; avconv -ar 48000 -f s16le -acodec pcm_s16le -i /dev/zero  -ab 64K -f mp2 -t 10 -acodec mp2 -y "+directory+"silence.mp2");

				// adding it to our video
				// sb.append("; avconv -i "+directory+"resultwithoutaudio.avi -i "+directory+"silence.mp2 -c copy -map 0:0 -map 1:0 "+directory+"resultwithaudio.mp4");

				// adding text
				sb.append("; avconv -i " + dir
						+ "result.avi -vf \"drawtext=fontfile=" + fontDir);
				sb.append(":fontsize=" + fontString);
				sb.append(": x='main_w/2-text_w/2': y='main_h/2-text_h/2': text='"
						+ text);
				sb.append("'\" -an -y ");
				sb.append(dir + outputFile + ".mp4 ");

				// generating video .st file
				sb.append("; avconv -ss 0 -i " + dir + outputFile
						+ ".mp4  -vcodec libx264 -acodec aac   ");
				sb.append("  -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
						+ dir + outputFile + ".ts");

				// generating ending .st file
				sb.append("; avconv -ss 0 -i " + directory + inputFile
						+ ".mp4 -vcodec libx264 -acodec aac   ");
				sb.append("  -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
						+ dir + inputFile + ".ts");

				// concatenating to end
				if (ending) {
					sb.append("; avconv -i concat:\"" + dir + inputFile
							+ ".ts|" + dir + outputFile
							+ ".ts\" -c copy    -bsf:a aac_adtstoasc -y "
							+ directory + "final.mp4");

					// concatenating to beginning
				} else {
					sb.append("; avconv -i concat:\"" + dir + outputFile
							+ ".ts|" + dir + inputFile
							+ ".ts\" -c copy    -bsf:a aac_adtstoasc -y " + dir
							+ "full.mp4");

					sb.append("; avconv -i " + directory + "wild.mp4 " + dir
							+ "wild.mp3");
					sb.append("; avconv -i " + dir + "full.mp4 -itsoffset "
							+ timeString + " -i " + dir
							+ "wild.mp3 -c copy -map 0:0 -map 1:0 " + directory
							+ "final.mp4");

				}

				String command = sb.toString();

				ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c",
						command);

				process = builder.start();
				process.waitFor();

			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				Directory.removeDir(dir);
			}
			return null;

		}

		// Override done() to perform specific functionalities when
		// doInBackground has finished
		@Override
		protected void done() {
			try {
				System.out.println("done!");
				// Directory.removeDir();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
