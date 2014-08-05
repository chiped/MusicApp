import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;

public class Main {

	public static void main(String[] args) {

		File midi = new File("C:\\Users\\Matthew\\Desktop\\midiTest3.mid");

		try {
			ArrayList<Song> songs = Song.makeSongFromMidiFile(midi);
			for (Song song : songs){
				for(Note n : song.getNotes()){
					System.out.println(n);
				}
				
				ArrayList<NGram<Note>> oneGram = NGram.getNGrams(song.getNotes(), 1);
				try {//list based on one gram. each note is inside an NGram.
					ArrayList<HMMTrainingInstance<NGram<Note>, NGram<Note>>> trainingSet = HMMTrainingInstance.createList(oneGram.subList(0, oneGram.size()-1), oneGram.subList(1, oneGram.size()));
					
					HMM<NGram<Note>, NGram<Note>> model = new HMM<NGram<Note>, NGram<Note>>(trainingSet);
					System.out.println("Initial Probabilities");
					System.out.println(model.getInitialProbabilities());
					System.out.println("States");
					System.out.println(model.getStates());
					System.out.println("Observations");
					System.out.println(model.getObservations());
					System.out.println("transitions");
					System.out.println(model.getTransitionProbabilities());
					
					System.out.println("random path");
					System.out.println(model.getRandomPath(10));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				/*
				ArrayList<Note> notes = song.getNotes();
				try {//list based on only notes. No extra padding of 1Gram over the note
					HMMTrainingInstance.createList(notes.subList(0, notes.size()-1), notes.subList(1, notes.size()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				ArrayList<NGram<Note>> threeGram = NGram.getNGrams(song.getNotes(), 3);
				try {//list based on 3gram of notes. each 3gram has an observation of the 4th note
					HMMTrainingInstance.createList(threeGram.subList(0, threeGram.size()-1), notes.subList(3, notes.size()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				ArrayList<Double> durations = new ArrayList<Double>();
				for(Note note : notes) {
					durations.add(note.getDuration());
				}
				ArrayList<NGram<Double>> duration3Gram = NGram.getNGrams(durations, 3);
				try {//list based on 3gram of durations. each 3gram has an observation of the 4th note
					HMMTrainingInstance.createList(duration3Gram.subList(0, duration3Gram.size()-1), durations.subList(3, durations.size()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				//TODO append all these lists to corresponding lists outside of the forloop to accumulate results from all songs
				*/
			}
//			for(Song song : songs) {
//				final Song thisSong = song;
//				new Thread(new Runnable() {
//					public void run() {
//						thisSong.play();
//					}
//				}).start();
//			}
			songs.get(0).play();
		} catch (InvalidMidiDataException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
