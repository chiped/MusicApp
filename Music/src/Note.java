public class Note{
	/**
	 * 
	 */
	private double arrivalTime;
	private double duration;
	private int pitch;
	public Note(double duration, int pitch, double arrivalTime){
		this.duration  = duration;
		this.pitch = pitch;
		this.arrivalTime = arrivalTime;
	}
	public double getArrivalTime() {
		return arrivalTime;
	}
	public double getDuration() {
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
	public boolean equals(Object other) {
		if(other instanceof Note && ((Note)other).getPitch() == pitch)
			return true;
		return false;
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
		String durationString = noteVariables[0].replace("duration=","");
		String pitchString = noteVariables[1].replace("pitch=","");
		String arrivalTimeString = noteVariables[2].replace("arrivalTime=","");
		
		double duration = Double.parseDouble(durationString);
		int pitch = Integer.parseInt(pitchString);
		double arrivalTime = Double.parseDouble(arrivalTimeString);
		return new Note(duration,pitch, arrivalTime);
	}
}
