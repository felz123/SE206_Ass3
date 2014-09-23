package mainpackage;

import javax.swing.JSlider;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;

public class VideoListener implements MediaPlayerEventListener{
	private JSlider mainTimeSlider;
	
	public VideoListener(JSlider timeSlider){
		mainTimeSlider = timeSlider;
	}
	@Override
	public void backward(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buffering(MediaPlayer arg0, float arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endOfSubItems(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finished(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forward(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lengthChanged(MediaPlayer arg0, long arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mediaChanged(MediaPlayer arg0, libvlc_media_t arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mediaDurationChanged(MediaPlayer arg0, long arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mediaFreed(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mediaMetaChanged(MediaPlayer arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mediaParsedChanged(MediaPlayer arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mediaStateChanged(MediaPlayer arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mediaSubItemAdded(MediaPlayer arg0, libvlc_media_t arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newMedia(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void opening(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pausableChanged(MediaPlayer arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paused(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playing(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void positionChanged(MediaPlayer arg0, float arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void seekableChanged(MediaPlayer arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void snapshotTaken(MediaPlayer arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopped(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subItemFinished(MediaPlayer arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subItemPlayed(MediaPlayer arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void timeChanged(MediaPlayer mainMediaPlayer, long currentTime) {
		mainTimeSlider.setValue((int)(((double)currentTime/(double)mainMediaPlayer.getLength())*100));
	}

	@Override
	public void titleChanged(MediaPlayer arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void videoOutput(MediaPlayer arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

}
