import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

import org.junit.Test;

public class SongTests {
	
	private static ArrayList<Integer> zero;
	private static ArrayList<Integer> one;
	private static ArrayList<Integer> two;
	
	static {
		zero = new ArrayList<Integer>();
		zero.add(0);
		one = new ArrayList<Integer>();
		one.add(1);
		two = new ArrayList<Integer>();
		two.add(2);		
	}

	@Test
	public void noteToStringTest() {
		Note n1 = new Note(0, zero, 0);
		Note n2 = new Note(1, one, 1);
		Note n3 = new Note(2, two, 2);

		String n1String = n1.toString();
		String n2String = n2.toString();
		String n3String = n3.toString();

		assertEquals("Note {duration=0.0:pitch=[0]:arrivalTime=0.0}", n1String);
		assertEquals("Note {duration=1.0:pitch=[1]:arrivalTime=1.0}", n2String);
		assertEquals("Note {duration=2.0:pitch=[2]:arrivalTime=2.0}", n3String);	
	}
	@Test
	public void noteFromStringTest(){
		Note n1 = new Note(0, zero, 0);
		Note n2 = new Note(1, one, 1);
		Note n3 = new Note(2, two, 2);

		String n1String = n1.toString();
		String n2String = n2.toString();
		String n3String = n3.toString();
		assertEquals("Note {duration=0.0:pitch=[0]:arrivalTime=0.0}", Note.makeNoteFromString(n1String).toString());
		assertEquals("Note {duration=1.0:pitch=[1]:arrivalTime=1.0}", Note.makeNoteFromString(n2String).toString());
		assertEquals("Note {duration=2.0:pitch=[2]:arrivalTime=2.0}", Note.makeNoteFromString(n3String).toString());
	}
	@Test
	public void multiNoteToStringTest() {
		ArrayList<Integer> zeroAndOne = new ArrayList<Integer>();
		zeroAndOne.addAll(zero);
		zeroAndOne.addAll(one);
		ArrayList<Integer> zeroAndOneAndTwo = new ArrayList<Integer>();
		zeroAndOneAndTwo.addAll(zeroAndOne);
		zeroAndOneAndTwo.addAll(two);
		Note n1 = new Note(0, zero, 0);
		Note n2 = new Note(1, zeroAndOne, 1);
		Note n3 = new Note(2, zeroAndOneAndTwo, 2);

		String n1String = n1.toString();
		String n2String = n2.toString();
		String n3String = n3.toString();

		assertEquals("Note {duration=0.0:pitch=[0]:arrivalTime=0.0}", n1String);
		assertEquals("Note {duration=1.0:pitch=[0, 1]:arrivalTime=1.0}", n2String);
		assertEquals("Note {duration=2.0:pitch=[0, 1, 2]:arrivalTime=2.0}", n3String);	
	}
	@Test
	public void multiNoteFromStringTest() {
		ArrayList<Integer> zeroAndOne = new ArrayList<Integer>();
		zeroAndOne.addAll(zero);
		zeroAndOne.addAll(one);
		ArrayList<Integer> zeroAndOneAndTwo = new ArrayList<Integer>();
		zeroAndOneAndTwo.addAll(zeroAndOne);
		zeroAndOneAndTwo.addAll(two);
		Note n1 = new Note(0, zero, 0);
		Note n2 = new Note(1, zeroAndOne, 1);
		Note n3 = new Note(2, zeroAndOneAndTwo, 2);

		String n1String = n1.toString();
		String n2String = n2.toString();
		String n3String = n3.toString();

		assertEquals("Note {duration=0.0:pitch=[0]:arrivalTime=0.0}", Note.makeNoteFromString(n1String).toString());
		assertEquals("Note {duration=1.0:pitch=[0, 1]:arrivalTime=1.0}", Note.makeNoteFromString(n2String).toString());
		assertEquals("Note {duration=2.0:pitch=[0, 1, 2]:arrivalTime=2.0}", Note.makeNoteFromString(n3String).toString());
	}
	@Test
	public void  songToStringTest(){
		Note n1 = new Note(0, zero, 0);
		Note n2 = new Note(1, one, 1);
		Note n3 = new Note(2, two, 2);
		
		Song s1 = new Song();
		
		LinkedHashSet<String> key = new LinkedHashSet<String>();
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
		
		String notesString = "[Note {duration=0.0:pitch=[0]:arrivalTime=0.0}, Note {duration=1.0:pitch=[1]:arrivalTime=1.0}, Note {duration=2.0:pitch=[2]:arrivalTime=2.0}]";
		
		String s1StringTest = "Song (songName=Worst Of Lullabies;instrument=Guitar;notes=" + notesString + ";key=[A, B, C#];genre=Rock and Roll)";
		String s1String = s1.toString();
		assertEquals(s1StringTest, s1String);	
	}
	@Test
	public void songFromStringTest(){
		Note n1 = new Note(0, zero, 0);
		Note n2 = new Note(1, one, 1);
		Note n3 = new Note(2, two, 2);
		
		Song s1 = new Song();
		
		HashSet<String> key = new HashSet<String>();
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
		
		String notesString = "[Note {duration=0:pitch=[0]:arrivalTime=0.0}, Note {duration=1:pitch=[1]:arrivalTime=1.0}, Note {duration=2:pitch=[2]:arrivalTime=2.0}]";
		
		String s1StringTest = "Song (songName=Worst of Lullabies;instrument=Guitar;notes=" + notesString + ";key=[A, B, C#];genre=Rock and Roll)";
		String s1String = s1.toString();
	
		assertEquals(s1String, Song.makeSongFromString(s1StringTest).toString());
	}
	@Test
	public void multiNoteSongToStringTest() {
		ArrayList<Integer> zeroAndOne = new ArrayList<Integer>();
		zeroAndOne.addAll(zero);
		zeroAndOne.addAll(one);
		ArrayList<Integer> zeroAndOneAndTwo = new ArrayList<Integer>();
		zeroAndOneAndTwo.addAll(zeroAndOne);
		zeroAndOneAndTwo.addAll(two);
		Note n1 = new Note(0, zero, 0);
		Note n2 = new Note(1, zeroAndOne, 1);
		Note n3 = new Note(2, zeroAndOneAndTwo, 2);
		
		Song s1 = new Song();
		
		LinkedHashSet<String> key = new LinkedHashSet<String>();
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
		
		String notesString = "[Note {duration=0.0:pitch=[0]:arrivalTime=0.0}, Note {duration=1.0:pitch=[0, 1]:arrivalTime=1.0}, Note {duration=2.0:pitch=[0, 1, 2]:arrivalTime=2.0}]";
		
		String s1StringTest = "Song (songName=Worst Of Lullabies;instrument=Guitar;notes=" + notesString + ";key=[A, B, C#];genre=Rock and Roll)";
		String s1String = s1.toString();
		assertEquals(s1StringTest, s1String);	
	}
	@Test
	public void multiNoteSongFromStringTest() {
		ArrayList<Integer> zeroAndOne = new ArrayList<Integer>();
		zeroAndOne.addAll(zero);
		zeroAndOne.addAll(one);
		ArrayList<Integer> zeroAndOneAndTwo = new ArrayList<Integer>();
		zeroAndOneAndTwo.addAll(zeroAndOne);
		zeroAndOneAndTwo.addAll(two);
		Note n1 = new Note(0, zero, 0);
		Note n2 = new Note(1, zeroAndOne, 1);
		Note n3 = new Note(2, zeroAndOneAndTwo, 2);
		
		Song s1 = new Song();
		
		HashSet<String> key = new HashSet<String>();
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
		
		String notesString = "[Note {duration=0:pitch=[0]:arrivalTime=0.0}, Note {duration=1:pitch=[0, 1]:arrivalTime=1.0}, Note {duration=2:pitch=[0, 1, 2]:arrivalTime=2.0}]";
		
		String s1StringTest = "Song (songName=Worst of Lullabies;instrument=Guitar;notes=" + notesString + ";key=[A, B, C#];genre=Rock and Roll)";
		String s1String = s1.toString();
		
		assertEquals(s1String, Song.makeSongFromString(s1StringTest).toString());
	}
	//@Test
	public void readAndWriteSongToFileTest(){
		String directory = "C:\\Users\\Matthew\\Desktop";
		Note n1 = new Note(0, zero, 0);
		Note n2 = new Note(1, one, 1);
		Note n3 = new Note(2, two, 2);
		
		Song s1 = new Song();
		
		HashSet<String> key = new HashSet<String>();
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
		
		s1.writeToFile(directory);
		Song s2 = Song.makeSongFromFile(directory +"\\song_Worst Of Lullabies.txt");
		
		assertEquals(s1.toString(), s2.toString());
		
		ArrayList<Song> testSongs = Song.makeSongsFromDirectory(directory);
		Song s3 = testSongs.get(0);
		assertEquals(s1.toString(), s3.toString());
		
		
		
		
	}

}
