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
	public void 

}
