package nl.rug.oop.rts.util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Class to play sounds.
 */
public class SoundPlayer {
    /**
     * Play a sound from the 'sounds' folder.
     *
     * @param soundFileName Name of the sound file
     */
    public void playSound(String soundFileName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    new File("src/main/resources/sounds/" + soundFileName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

            // Add a LineListener to the Clip
            clip.addLineListener(evt -> {
                if (evt.getType() == LineEvent.Type.STOP) {
                    evt.getLine().close();
                }
            });

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error with playing sound: " + e.getMessage());
        }
    }
}
