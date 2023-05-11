import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {
    public static void playAudio(String file){
        try{
            File audioPath = new File(file);
            if(audioPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioPath);
                Clip clip = AudioSystem.getClip();  
                clip.open(audioInput);  
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
