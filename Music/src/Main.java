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
					instance.setPrevInstance(previous);
					previous.setNextInstance(instance);
					previous = instance;
					System.out.println(instance);
				}

			}
//			for(Song song : songs) {
//				final Song thisSong = song;
//				new Thread(new Runnable() {
//					public void run() {
//						thisSong.play();
//					}
//				}).start();
//			}
			songs.get(1).play();
		} catch (InvalidMidiDataException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
