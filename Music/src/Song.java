import java.util.ArrayList;

public class Song {
	private String instrument;
	private ArrayList<Note> notes;
	private ArrayList<String> key;
	private int tempo;
	
	public Song(){
		notes = new ArrayList<Note>();
		key = new ArrayList<String>();
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
	public void addNote(Note newNote){
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
	@Override
	public String toString() {
		return "Song [instrument=" + instrument + ", notes=" + notes + ", key="
				+ key + ", tempo=" + tempo + "]";
	}
	
	public static Song parseSongString(String aSongString){
		return null;
	}
	
}
