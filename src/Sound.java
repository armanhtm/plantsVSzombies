import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * @author Arman Hatami
 * @version 1.0
 * sound
 */
public class Sound {
    //address of sound file
    private String soundPath;
    //state of play
    private int playLevel;
    //music thread
    Thread music;

    /**
     * constructor method
     * @param file_path
     */
    public Sound(String file_path)
    {
        this.soundPath =file_path;
        this.playLevel = 1;
    }

    /**
     * play sound until we call stop()
     */
    public void play() {
        if (playLevel != 1)
            return;
        playLevel = 2;
        music =new Thread(new Runnable()
        {
            public void run()
            {

                while(playLevel == 2) {
                    try
                    {
                        File file = new File(soundPath);
                        AudioInputStream stream = AudioSystem.getAudioInputStream(file);
                        AudioFormat format = stream.getFormat();
                        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                        SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
                        byte[] buf = new byte[512 * 1024];
                        line.open();
                        line.start();
                        int bytes = 0;
                        while (bytes != -1)
                        {
                            if(playLevel == 3)
                                break;
                            bytes = stream.read(buf, 0, buf.length);
                            if (bytes >= 0)
                                line.write(buf, 0, bytes);
                        }
                        line.drain();
                        line.close();
                    }
                    catch (UnsupportedAudioFileException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    catch (LineUnavailableException e)
                    {
                        e.printStackTrace();
                    }

                    if (playLevel == 3)
                        break;}}
        });
        music.start();
    }

    /**
     * stop the sound from play() method
     */
    public void Stop() {
        this.playLevel = 3;
        music.stop();
    }

    /**
     * play sound just once
     */
    public void playSound() {
        new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    File file = new File(soundPath);
                    AudioInputStream stream = AudioSystem.getAudioInputStream(file);
                    AudioFormat format = stream.getFormat();
                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                    SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
                    byte[] buf = new byte[512 * 1024];
                    line.open();
                    line.start();
                    int bytes = 0;
                    while (bytes != -1)
                    {
                        if(playLevel == 3)
                            break;
                        bytes = stream.read(buf, 0, buf.length);
                        if (bytes >= 0)
                            line.write(buf, 0, bytes);
                    }
                    line.drain();
                    line.close();
                }
                catch (UnsupportedAudioFileException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                catch (LineUnavailableException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}

