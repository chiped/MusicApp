import java.util.ArrayList;


public class ReadingSongStringsTest {
	public static void test(){
		Note n1 = new Note(0, 0, 0);
		Note n2 = new Note(1, 1, 1);
		Note n3 = new Note(2, 2, 2);
		Note n4 = new Note(3, 3, 3);
		Note n5 = new Note(4, 4, 3);
		
		ArrayList<Note> notes = new ArrayList<Note>();
		notes.add(n1);
		notes.add(n2);
		notes.add(n3);
		notes.add(n4);
		notes.add(n5);
		
		System.out.println(notes);
		String notesString = notes.toString();
		
		notes.removeAll(notes);
		
		//NEED TO COMPLETE
		notesString =notesString.replace("[", "");
		notesString =notesString.replace("]", "");
		String[] notesStringArray = notesString.split(", ");
		for(String noteString:notesStringArray){
			notes.add(Note.makeNoteFromString(noteString));
		}
		System.out.println(notes);
		
		ArrayList<String> key = new ArrayList<String>();
		key.add("A");
		key.add("B");
		key.add("C");
		key.add("D#");
		
		Song s1 = new Song();
		s1.setNotes(notes);
		s1.setGenre("rock and roll");
		s1.setInstrument("guitar");
		s1.setKey(key);
		s1.setTempo(32);
		
		System.out.println(s1);
		String aSongString = s1.toString();
		Song s2 = Song.makeSongFromString(aSongString);
		
		System.out.println(s2);
		
	}
}
