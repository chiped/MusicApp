import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class readAndWriteStringTests {

	@Test
	public void noteToStringTest() {
		Note n1 = new Note(0, 0, 0);
		Note n2 = new Note(1, 1, 1);
		Note n3 = new Note(2, 2, 2);

		String n1String = n1.toString();
		String n2String = n2.toString();
		String n3String = n3.toString();

		assertEquals("Note {duration=0:pitch=0:arrivalTime=0}", n1String);
		assertEquals("Note {duration=1:pitch=1:arrivalTime=1}", n2String);
		assertEquals("Note {duration=2:pitch=2:arrivalTime=2}", n3String);	
	}
	@Test
	public void noteFromStringTest(){
		Note n1 = new Note(0, 0, 0);
		Note n2 = new Note(1, 1, 1);
		Note n3 = new Note(2, 2, 2);

		String n1String = n1.toString();
		String n2String = n2.toString();
		String n3String = n3.toString();

		assertEquals("Note {duration=0:pitch=0:arrivalTime=0}", Note.makeNoteFromString(n1String).toString());
		assertEquals("Note {duration=1:pitch=1:arrivalTime=1}", Note.makeNoteFromString(n2String).toString());
		assertEquals("Note {duration=2:pitch=2:arrivalTime=2}", Note.makeNoteFromString(n3String).toString());
	}
	@Test
	public void  songToStringTest(){
		Note n1 = new Note(0, 0, 0);
		Note n2 = new Note(1, 1, 1);
		Note n3 = new Note(2, 2, 2);
		
		Song s1 = new Song();
		
		ArrayList<String> key = new ArrayList<String>();
		key.add("A");
		key.add("B");
		key.add("C#");
		
		s1.addNote(n1);
		s1.addNote(n2);
		s1.addNote(n3);
		s1.setSongName("Worst Of Lullabies");
		s1.setGenre("Rock and Roll");
		s1.setInstrument("Guitar");
		s1.setKey(key);
		
		s1.setTempo(30);
		
		String notesString = "[Note {duration=0:pitch=0:arrivalTime=0}, Note {duration=1:pitch=1:arrivalTime=1}, Note {duration=2:pitch=2:arrivalTime=2}]";
		
		String s1StringTest = "Song (songName=Worst Of Lullabies;instrument=Guitar;notes=" + notesString + ";key=[A, B, C#];tempo=30;genre=Rock and Roll)";
		String s1String = s1.toString();
		System.out.println(s1String);
		assertEquals(s1StringTest, s1String);	
	}
	@Test
	public void songFromStringTest(){
		Note n1 = new Note(0, 0, 0);
		Note n2 = new Note(1, 1, 1);
		Note n3 = new Note(2, 2, 2);
		
		Song s1 = new Song();
		
		ArrayList<String> key = new ArrayList<String>();
		key.add("A");
		key.add("B");
		key.add("C#");
		
		s1.addNote(n1);
		s1.addNote(n2);
		s1.addNote(n3);
		s1.setSongName("Worst of Lullabies");
		s1.setGenre("Rock and Roll");
		s1.setInstrument("Guitar");
		s1.setKey(key);
		
		s1.setTempo(30);
		
		String notesString = "[Note {duration=0:pitch=0:arrivalTime=0}, Note {duration=1:pitch=1:arrivalTime=1}, Note {duration=2:pitch=2:arrivalTime=2}]";
		
		String s1StringTest = "Song (songName=Worst of Lullabies;instrument=Guitar;notes=" + notesString + ";key=[A, B, C#];tempo=30;genre=Rock and Roll)";
		String s1String = s1.toString();
	
		assertEquals(s1String, Song.makeSongFromString(s1StringTest).toString());
	}

}