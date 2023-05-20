package model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class Music {
    static Clip clip;
    public static void playMusic(){
        try{
            File musicPath = new File("C:\\Users\\陈彦妤\\Music\\小英雄大肚腩+-+动画片+猪猪侠+主题曲+童话英雄_爱给网_aigei_com (1).wav");
            if(musicPath.exists()){
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-20.0f);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
