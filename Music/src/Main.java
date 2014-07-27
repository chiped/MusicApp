import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;

public class Main {

	public static void main(String[] args) {

		File midi = new File("C:\\Users\\ChiP\\Desktop\\midiTest3.mid");

		try {
			ArrayList<Song> songs = Song.makeSongFromMidiFile(midi);
			for (Song song : songs){
				Song s1 = song;
				for(Note n : s1.getNotes()){
					System.out.println(n);
				}
				
			}

		} catch (InvalidMidiDataException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
