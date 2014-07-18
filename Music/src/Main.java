import java.io.File;
import java.util.ArrayList;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class Main {
	
	public static void main(String[] args) {

		
		File midiDirectory = new File(".\\src");
		
		
		File[] midiList = midiDirectory.listFiles();
		for(File file: midiList){
			if(file.getAbsolutePath().endsWith(".mid")){
				Song song = Song.makeSongFromMidiFile(file);
				
			}
		}
		
	}
}
