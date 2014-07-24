import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class Song {
	private String songName;
	private String instrument;
	private ArrayList<Note> notes;
	private ArrayList<String> key;
	private int tempo;
	private String genre;

	public static final int NOTE_ON = 0x90;
	public static final int NOTE_OFF = 0x80;
	public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};


	public Song() {
		notes = new ArrayList<Note>();
		key = new ArrayList<String>();
	}

	protected Song(String songName, String instrument, ArrayList<Note> notes,
			ArrayList<String> key, int tempo, String genre) {
		this.songName = songName;
		this.instrument = instrument;
		this.notes = new ArrayList<Note>(notes);
		this.key = new ArrayList<String>(key);
		this.tempo = tempo;
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

	public ArrayList<String> getKey() {
		return key;
	}

	public void setKey(ArrayList<String> key) {
		this.key = key;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	@Override
	public String toString() {
		return "Song (songName=" + songName + ";instrument=" + instrument
				+ ";notes=" + notes + ";key=" + key + ";tempo=" + tempo
				+ ";genre=" + genre + ")";
	}

	public static ArrayList<Song> makeSongFromMidiFile(File midi) throws InvalidMidiDataException, IOException {
		ArrayList<Song> songs = new ArrayList<Song>();
		Sequence sequence = MidiSystem.getSequence(midi);
		int ppq = sequence.getResolution();
		System.out.println("PPQ: " + ppq );
		int trackNumber = 0;
		for (Track track :  sequence.getTracks()) {
			Song song = new Song();
			int tempo = 120; //in BPM. This is the default value
			double timeFactor = 60000.0/(tempo*ppq); //update this while updating tempo
			trackNumber++;
			System.out.println("Track " + trackNumber + ": size = " + track.size());
			System.out.println();
			HashMap<Integer, Long> map = new HashMap<Integer, Long>();
			for (int i=0; i < track.size(); i++) { 
				MidiEvent event = track.get(i);
				long tick = event.getTick();
				System.out.print("@" + tick + " ");
				MidiMessage message = event.getMessage();
				if (message instanceof ShortMessage) {
					ShortMessage sm = (ShortMessage) message;
					System.out.print("Channel: " + sm.getChannel() + " ");
					int key = sm.getData1();
					int octave = (key / 12)-1;
					int note = key % 12;
					String noteName = NOTE_NAMES[note];
					int velocity = sm.getData2();
					if (sm.getCommand() == NOTE_OFF || (sm.getCommand() == NOTE_ON && velocity == 0)) {
						long tickDuration = tick-map.get(key);
						Note newNote = new Note(tickDuration * timeFactor, key, map.get(key) );
						song.addNote(newNote);
						System.out.print("Lasted for: " + ( tickDuration * timeFactor ) + "ms ");
						System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
					} else if (sm.getCommand() == NOTE_ON) {
						System.out.print("Started at: " + ( tick * timeFactor ) + "ms ");
						System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
						map.put(key, tick);
					} else {
						System.out.println("Command:" + sm.getCommand());
					}
				} else {
					if(message instanceof MetaMessage && ((MetaMessage)message).getType() == 0x51 ) {
							System.out.println("Tempo changed");
							//TODO update tempo
					}
					else;
						System.out.println("Other message: " + message.getClass());
				}
			}
			song.setTempo(tempo);
			if(song.getNotes().size() > 0)
				songs.add(song);
			//System.out.println();
		}
		return songs;
	}

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

	}

	public static Song makeSongFromString(String aSongString) {
		aSongString = aSongString.replace("Song (", "");
		aSongString = aSongString.replace(")", "");
		String[] songVariables = aSongString.split(";");

		String songName = songVariables[0].replace("songName=", "");
		String instrument = songVariables[1].replace("instrument=", "");
		String notesString = songVariables[2].replace("notes=", "");
		String keyString = songVariables[3].replace("key=", "");
		String tempoString = songVariables[4].replace("tempo=", "");
		String genre = songVariables[5].replace("genre=", "");

		ArrayList<Note> notes = new ArrayList<Note>();
		notesString = notesString.replace("[", "");
		notesString = notesString.replace("]", "");
		String[] notesStringArray = notesString.split(", ");
		for (String noteString : notesStringArray) {
			notes.add(Note.makeNoteFromString(noteString));
		}

		ArrayList<String> key = new ArrayList<String>();
		keyString = keyString.replace("[", "");
		keyString = keyString.replace("]", "");
		String[] keyStringArray = keyString.split(", ");
		for (String note : keyStringArray) {
			key.add(note);
		}

		int tempo = Integer.parseInt(tempoString);

		return new Song(songName, instrument, notes, key, tempo, genre);
	}

	public static Song makeSongFromFile(String filePath){
		try {
			File file = new File(filePath);
			ArrayList<Song> songs = new ArrayList<Song>();

			BufferedReader inputFile = new BufferedReader(new FileReader(file));
			String line = inputFile.readLine();
			inputFile.close();
			if(line != null){
				return makeSongFromString(line.replace("\n", ""));
			}
			else{
				return null;
			}
		} catch (Exception e) {
			System.out.println("song failed to read from file");
			System.exit(1);
			return null;
		}

	}
	public static ArrayList<Song> makeSongsFromDirectory(String fileDirectory) {

		try {
			ArrayList<Song> songs = new ArrayList<Song>();

			File songDirectory = new File(fileDirectory);
			File[] songList =songDirectory.listFiles();
			for(File file: songList){
				String path = file.getAbsolutePath();
				if(path.replace(fileDirectory,"").startsWith("\\song_")){
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

	public void writeNGramsToFile(String fileDirectory, int n) {
		try {
			File file = new File(fileDirectory + "\\gram_" + n + "_songName_"
					+ this.songName + "_instrument_" + this.instrument + "_genre_"
					+ this.genre + "_key_" + this.key + ".txt");

			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			for (int i = n; i < notes.size(); i++) {
				Ngram ngram = new Ngram(songName, instrument,
						(ArrayList<Note>) notes.subList(i - n, i), key, tempo,
						genre);
				output.write(ngram.toString());
			}
			output.close();
		} catch (Exception e) {
			System.out.println("song failed to write to ngrams file ");
			System.exit(1);
		}

	}
}
