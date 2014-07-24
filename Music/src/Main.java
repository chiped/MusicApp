import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;

public class Main {
	
	public static void main(String[] args) {

		
		File midiDirectory = new File("Music\\src");
		
		
		File[] midiList = midiDirectory.listFiles();
		for(File file: midiList){
			if(file.getAbsolutePath().endsWith(".mid")){
				try {
					ArrayList<Song> songs = Song.makeSongFromMidiFile(file);
					for(Song song: songs)
						System.out.println(song);
					System.out.println(songs.size() + " songs in file " + file);
				} catch (InvalidMidiDataException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
	}
}
