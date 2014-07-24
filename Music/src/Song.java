import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Song {
	private String songName;
	private String instrument;
	private ArrayList<Note> notes;
	private ArrayList<String> key;
	private int tempo;
	private String genre;

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

	public String getSongName(){
		return songName;
	}
	public void setSongName(String songName){
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
		return "Song (songName=" + songName +";instrument=" + instrument + ";notes=" + notes + ";key="
				+ key + ";tempo=" + tempo + ";genre=" + genre + ")";
	}

	public static Song makeSongFromMidiFile(File midi) {
		// TODO
		return null;
	}

	public void writeToFile(String fileDirectory) {
		try {
			File file = new File(fileDirectory + "\\" + "songs.txt");

			BufferedWriter output = new BufferedWriter(new FileWriter(file,
					true));
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

		return new Song(songName,instrument, notes, key, tempo, genre);
	}

	public static ArrayList<Song> makeSongsFromFile(String fileDirectory, String songName) {
		
		try {
			File file = new File(fileDirectory + "\\" + "song"+songName+".txt");
			ArrayList<Song> songs = new ArrayList<Song>();
			
			BufferedReader inputFile = new BufferedReader(new FileReader(file));
			String line;
			while ((line = inputFile.readLine()) != null) {
				songs.add(makeSongFromString(line.replace("\n", "")));
			}
			inputFile.close();
			return null;
		} catch (Exception e) {
			System.out.println("song failed to read from file");
			System.exit(1);
			return null;
		}

	}

	public void writeNGramsToFile(String fileDirectory, int n) {
		try {
			File file = new File(fileDirectory + "\\songName_" + songName + "_n_" + n
					+ "_instrument_" + this.instrument + "_genre_" + this.genre
					+ "_key_" + this.key + ".txt");

			BufferedWriter output = new BufferedWriter(new FileWriter(file,
					true));
			for (int i = n; i < notes.size(); i++) {
				Ngram ngram = new Ngram(songName,instrument,
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
