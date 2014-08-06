import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

public class Song {
	private String songName;
	private String instrument;
	private ArrayList<Note> notes;
	private HashSet<String> key;
	private String genre;
	private float tempo;
	
	public Song() {
		notes = new ArrayList<Note>();
		key = new HashSet<String>();
		tempo = 120;
	}

	protected Song(String songName, String instrument, ArrayList<Note> notes,
			HashSet<String> key, String genre) {
		this.songName = songName;
		this.instrument = instrument;
		this.notes = new ArrayList<Note>(notes);
		this.key = new HashSet<String>(key);
		this.genre = genre;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public ArrayList<Note> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<Note> notes) {
		this.notes = notes;
	}

	public void addNote(Note newNote) {
		this.notes.add(newNote);
	}

	public HashSet<String> getKey() {
		return key;
	}

	public void setKey(HashSet<String> key) {
		this.key = key;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public float getTempo() {
		return tempo;
	}

	public void setTempo(float tempo) {
		this.tempo = tempo;
	}

	@Override
	public String toString() {
		return "Song (songName=" + songName + ";instrument=" + instrument
				+ ";notes=" + notes + ";key=" + key
				+ ";genre=" + genre + ")";
	}

	/**
	 * 
	 * @param midi midi file
	 * @return an array of songs
	 * @throws InvalidMidiDataException
	 * @throws IOException
	 */
	public static ArrayList<Song> makeSongFromMidiFile(File midi)
			throws InvalidMidiDataException, IOException {
		int NOTE_ON = 0x90;
		int NOTE_OFF = 0x80;
		String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};



		ArrayList<Song> songs = new ArrayList<Song>();
		Sequence sequence = MidiSystem.getSequence(midi);
		
		int ppq = sequence.getResolution();
		int trackNumber = 0;
		float tempo = 120;
		for (Track track : sequence.getTracks()) {
			Song song = new Song();
															// updating tempo
			trackNumber++;
			System.out.println("Track " + trackNumber + ": size = "
					+ track.size());
			System.out.println();
			
			HashSet<Integer> currentPitches = new HashSet<Integer>();
			long arrivalTime = 0;
			for (int i = 0; i < track.size(); i++) {
				MidiEvent event = track.get(i);
				long tick = event.getTick();
				
				MidiMessage message = event.getMessage();

				if (message instanceof ShortMessage) {
					ShortMessage sm = (ShortMessage) message;
					int pitch = sm.getData1();
					
					int velocity = sm.getData2();
					if (sm.getCommand() == NOTE_OFF	|| (sm.getCommand() == NOTE_ON && velocity == 0)) {
						double duration = tick - arrivalTime;
						if(duration > 0) {
							double tickDuration = duration / ppq;
							ArrayList<Integer> pitches = new ArrayList<Integer>(currentPitches);
							song.addNote(new Note(tickDuration, pitches, (double)arrivalTime/ppq));
						}
						arrivalTime = tick;
						currentPitches.remove(pitch);
						song.getKey().add(NOTE_NAMES[pitch%12]);
					} else if (sm.getCommand() == NOTE_ON) {
						if(!currentPitches.isEmpty()) {
							double duration = tick - arrivalTime;
							if(duration > 0) {
								double tickDuration = duration / ppq;
								ArrayList<Integer> pitches = new ArrayList<Integer>(currentPitches);
								song.addNote(new Note(tickDuration, pitches, (double)arrivalTime/ppq));
							}
						}
						arrivalTime = tick;
						currentPitches.add(pitch);
					} 
				}
				else {
					if(message instanceof MetaMessage) {
						MetaMessage mm = (MetaMessage) message;
						byte[] abData = mm.getData();
						if(mm.getType() == 0x51) {
							System.out.println("Tempo changed");int	nTempo = ((abData[0] & 0xFF) << 16)
									| ((abData[1] & 0xFF) << 8)
									| (abData[2] & 0xFF);           // tempo in microseconds per beat
							float bpm = convertTempo(nTempo);
							// truncate it to 2 digits after dot
							bpm = (float) (Math.round(bpm*100.0f)/100.0f);
							tempo = bpm;
							System.out.println("Set Tempo: "+bpm+" bpm");
						}
						else if(mm.getType() == 0x03) {
							song.setSongName(new String(abData));
						}
						else if(mm.getType() == 0x04) {
							song.setInstrument(new String(mm.getMessage()));
							System.out.println("Instrument: " + new String(mm.getMessage()));
						}
					}
				}
			}
			
			if (song.getNotes().size() > 0){
				song.setTempo(tempo);
				songs.add(song);
			}
		}
		return songs;
	}

	private static float convertTempo(float value) {
		if (value <= 0) {
			value = 0.1f;
		}
		return 60000000.0f / value;
	}


	public static Song makeSongFromString(String aSongString) {
		aSongString = aSongString.replace("Song (", "");
		aSongString = aSongString.replace(")", "");
		String[] songVariables = aSongString.split(";");

		String songName = songVariables[0].replace("songName=", "");
		String instrument = songVariables[1].replace("instrument=", "");
		String notesString = songVariables[2].replace("notes=", "");
		String keyString = songVariables[3].replace("key=", "");
		String genre = songVariables[4].replace("genre=", "");

		ArrayList<Note> notes = new ArrayList<Note>();
		notesString = notesString.substring(1, notesString.length()-1);
		String[] notesStringArray = notesString.split(",\\s+(?![^\\[]*\\])");
		for (String noteString : notesStringArray) {
			notes.add(Note.makeNoteFromString(noteString));
		}

		HashSet<String> key = new HashSet<String>();
		keyString = keyString.replace("[", "");
		keyString = keyString.replace("]", "");
		String[] keyStringArray = keyString.split(", ");
		for (String note : keyStringArray) {
			key.add(note);
		}


		return new Song(songName, instrument, notes, key, genre);
	}

		
	public double getDuration() {
		double duration = 0;
		for(Note note : getNotes()) {
			duration += note.getDuration();
		}
		return duration;
	}
	
	public void play() {
		try {
			Synthesizer synth = MidiSystem.getSynthesizer();
			synth.open();
			final MidiChannel[] mc = synth.getChannels();
			Instrument[] instr = synth.getDefaultSoundbank().getInstruments();
			synth.loadInstrument(instr[80]);
			long initial = System.currentTimeMillis();
			float tempoFactor = 60000/getTempo();
			for(Note n:getNotes()) {
				long elapsed = System.currentTimeMillis() - initial;
				if(Math.round(n.getArrivalTime()*tempoFactor) > elapsed) {
					try {
						Thread.sleep(Math.round(n.getArrivalTime()*tempoFactor) - elapsed);
					} catch (InterruptedException e) {}
				}
				for(int pitch : n.getPitch()) {
					mc[0].noteOn(pitch,75);
				}
				try {
					Thread.sleep(Math.round(n.getDuration()*tempoFactor));
				} catch (InterruptedException e) {}
				for(int pitch : n.getPitch()) {
					mc[0].noteOff(pitch);
				}
			}
		} catch (MidiUnavailableException e) {}
	}
	
	public static Song OneGramPathToSong(ArrayList<Pair<NGram<Note>, NGram<Note>>> oneGramPath){
		Song newSong = new Song();
		double time = 0.0;
		for(Pair<NGram<Note>, NGram<Note>> p:oneGramPath){
			double newNoteDuration = p.getL().getItems().get(0).getDuration();
			Note newNote = new Note(newNoteDuration,p.getL().getItems().get(0).getPitch(), time);
			time += newNoteDuration;
			newSong.addNote(newNote);
		}
		Note oldNote = oneGramPath.get(oneGramPath.size() - 1).getR().getItems().get(0);
		Note newNote = new Note(oldNote.getDuration(), oldNote.getPitch(), time);
		newSong.addNote(newNote);
		return newSong;
	}
	/*
	public static Song makeSongFromFile(String filePath) {
		try {
			File file = new File(filePath);
			ArrayList<Song> songs = new ArrayList<Song>();

			BufferedReader inputFile = new BufferedReader(new FileReader(file));
			String line = inputFile.readLine();
			inputFile.close();
			if (line != null) {
				return makeSongFromString(line.replace("\n", ""));
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("song failed to read from file");
			System.exit(1);
			return null;
		}

	}
	*/
/*
	public static ArrayList<Song> makeSongsFromDirectory(String fileDirectory) {

		try {
			ArrayList<Song> songs = new ArrayList<Song>();

			File songDirectory = new File(fileDirectory);
			File[] songList = songDirectory.listFiles();
			for (File file : songList) {
				String path = file.getAbsolutePath();
				if (path.replace(fileDirectory, "").startsWith("\\song_")) {
					Song song = Song.makeSongFromFile(path);
					songs.add(song);
				}
			}
			return songs;

		} catch (Exception e) {
			System.out.println("song failed to read from file");
			System.exit(1);
			return null;
		}

	}
*/
//	public void writeNGramsToFile(String fileDirectory, int n) {
//		try {
//			File file = new File(fileDirectory + "\\gram_" + n + "_songName_"
//					+ this.songName + "_instrument_" + this.instrument
//					+ "_genre_" + this.genre + "_key_" + this.key + ".txt");
//
//			BufferedWriter output = new BufferedWriter(new FileWriter(file));
//			for (int i = n; i < notes.size(); i++) {
//				Ngram ngram = new Ngram(songName, instrument,
//						new ArrayList<Note>( notes.subList(i - n, i) ), key,
//						genre);
//				output.write(ngram.toString());
//			}
//			output.close();
//		} catch (Exception e) {
//			System.out.println("song failed to write to ngrams file ");
//			System.exit(1);
//		}
//
//	}
//	
//	public ArrayList<Ngram> getNGrams(int n) {
//		ArrayList<Ngram> nGrams = new ArrayList<Ngram>();
//		for (int i = n; i < notes.size(); i++) {
//			Ngram ngram = new Ngram(songName, instrument,
//					new ArrayList<Note>( notes.subList(i - n, i)), key,
//					genre);
//			nGrams.add(ngram);
//		}
//		return nGrams;
//	}
	

	/*
	public void writeToFile(String fileDirectory) {
		try {
			File file = new File(fileDirectory + "\\" + "song_" + songName
					+ ".txt");

			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(this.toString());
			output.close();
		} catch (Exception e) {
			System.out.println("song failed to write to file ");
			System.exit(1);
		}

	}*/
}
