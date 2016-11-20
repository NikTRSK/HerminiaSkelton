package client;
import java.io.File;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import utilities.Constants;

public class BackgroundMusic{
	private Media battlemusic, casualmusic, startmusic, finalmusic, waitmusic;
	private boolean musicFound;
	private static MediaPlayer myBGM;
	private JFXPanel fxPanel;
	
	public BackgroundMusic(){
		musicFound = true;
		fxPanel = new JFXPanel();
		findLocalFiles();
	}
	
	private void findLocalFiles(){
		File battleMusicFilePath = new File(Constants.resourceFolderbgm + Constants.battlemusic);
		battlemusic = new Media(battleMusicFilePath.toURI().toString());
		File casualMusicFilePath = new File(Constants.resourceFolderbgm + Constants.casualmusic);
		casualmusic = new Media(casualMusicFilePath.toURI().toString());
		File startMusicFilePath = new File(Constants.resourceFolderbgm + Constants.startmusic);
		startmusic = new Media(startMusicFilePath.toURI().toString());
		File finalMusicFilePath = new File(Constants.resourceFolderbgm + Constants.finalmusic);
		finalmusic = new Media(finalMusicFilePath.toURI().toString());
		File waitMusicFilePath = new File(Constants.resourceFolderbgm + Constants.waitmusic);
		waitmusic = new Media(waitMusicFilePath.toURI().toString());
	}
	
	protected void gamestart(){
		if(musicFound){
			myBGM = new MediaPlayer(startmusic);
			myBGM.setOnEndOfMedia(new Runnable() {
			       public void run() {
			    	   myBGM.seek(Duration.ZERO);
			       }
			   });
			myBGM.play();
		}
	}
	
	protected void battlestart(){
		if(musicFound){
			myBGM = new MediaPlayer(battlemusic);
			myBGM.setOnEndOfMedia(new Runnable() {
			       public void run() {
			    	   myBGM.seek(Duration.ZERO);
			       }
			   });
			myBGM.play();
		}
	}
	
	protected void casualstart(){
		if(musicFound){
			myBGM = new MediaPlayer(casualmusic);
			myBGM.setOnEndOfMedia(new Runnable() {
			       public void run() {
			    	   myBGM.seek(Duration.ZERO);
			       }
			   });
			myBGM.play();
		}	
	}
	
	protected void waitstart(){
		if(musicFound){
			myBGM = new MediaPlayer(waitmusic);
			myBGM.setOnEndOfMedia(new Runnable() {
			       public void run() {
			    	   myBGM.seek(Duration.ZERO);
			       }
			   });
			myBGM.play();
		}	
	}
	
	protected void finalstart(){
		if(musicFound){
			myBGM = new MediaPlayer(finalmusic);
			myBGM.setOnEndOfMedia(new Runnable() {
			       public void run() {
			    	   myBGM.seek(Duration.ZERO);
			       }
			   });
			myBGM.play();
		}	
	}
	
	protected void endMusic(){
		if(musicFound){
			myBGM.stop();
		}	
	}
	
	protected void pauseMusic(){
		if(musicFound){
			myBGM.pause();
		}
	}
	
	protected void unpauseMusic(){
		if(musicFound){
			myBGM.play();
		}
	}

}

