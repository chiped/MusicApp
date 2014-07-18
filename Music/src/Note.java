public class Note{
	/**
	 * 
	 */
	private int duration;
	private int pitch;
	public Note(int duration, int pitch){
		this.duration  = duration;
		this.pitch = pitch;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getPitch() {
		return pitch;
	}
	public void setPitch(int pitch) {
		this.pitch = pitch;
	}
	@Override
	public String toString() {
		return "Note {duration=" + duration + ": pitch=" + pitch + "}";
	}
	/**
	 * Takes string representation of a note {@link #toString()} and converts it to a Note
	 * @param aNoteString string representation of a note
	 * @return a Note
	 */
	public static Note parseNoteString(String aNoteString){		
		aNoteString = aNoteString.replace("Note {", "");
		aNoteString = aNoteString.replace("}", "");
		
		String[] noteVariables = aNoteString.split(":");
		String durationString = noteVariables[0].replace("duration=","");
		String pitchString = noteVariables[1].replace("pitch=","");
		
		int duration = Integer.parseInt(durationString);
		int pitch = Integer.parseInt(pitchString);
		
		return new Note(duration,pitch);
	}
}
