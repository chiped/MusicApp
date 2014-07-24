public class Note{
	/**
	 * 
	 */
	private int arrivalTime;
	private int duration;
	private int pitch;
	public Note(int duration, int pitch, int arrivalTime){
		this.duration  = duration;
		this.pitch = pitch;
		this.arrivalTime = arrivalTime;
	}
	public int getArrivalTime() {
		return arrivalTime;
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
		return "Note {duration=" + duration + ":pitch=" + pitch + ":arrivalTime=" + arrivalTime + "}";
	}
	/**
	 * Takes string representation of a note {@link #toString()} and converts it to a Note
	 * @param aNoteString string representation of a note
	 * @return a Note
	 */
	public static Note makeNoteFromString(String aNoteString){	
		aNoteString = aNoteString.replace("Note {", "");
		aNoteString = aNoteString.replace("}", "");
		
		String[] noteVariables = aNoteString.split(":");	
		for(String n: noteVariables){
			System.out.println(n);
		}

		String durationString = noteVariables[0].replace("duration=","");
		String pitchString = noteVariables[1].replace("pitch=","");
		String arrivalTimeString = noteVariables[2].replace("arrivalTime=","");
		
		int duration = Integer.parseInt(durationString);
		int pitch = Integer.parseInt(pitchString);
		System.out.println(arrivalTimeString);
		int arrivalTime = Integer.parseInt(arrivalTimeString);
		return new Note(duration,pitch, arrivalTime);
	}
}
