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
				ArrayList<Ngram> nGrams = s1.getNGrams(3);
				HMMTrainingInstance<Ngram, Double> previous = new HMMTrainingInstance<Ngram, Double>(nGrams.get(0), nGrams.get(0).getDuration());
				for(int i=1; i<nGrams.size(); i++) {
					Ngram nGram = nGrams.get(i);
					HMMTrainingInstance<Ngram, Double> instance = new HMMTrainingInstance<Ngram, Double>(nGram, nGram.getDuration());
					previous.setNextInstance(instance);
					previous = instance;
					System.out.println(instance);
				}

			}
//			songs.get(0).play();
		} catch (InvalidMidiDataException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
