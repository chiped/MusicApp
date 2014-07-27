import java.util.ArrayList;
import java.util.Collections;

public class Note{
	/**
	 * 
	 */
	private double arrivalTime;
	private double duration;
	private ArrayList<Integer> pitch;
	public Note(double duration, ArrayList<Integer> pitch, double arrivalTime){
		this.duration  = duration;
		Collections.sort(pitch);
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
	public ArrayList<Integer> getPitch() {
		return pitch;
	}
	public void setPitch(ArrayList<Integer> pitch) {
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

		String durationString = noteVariables[0].replace("duration=","");
		String pitchString = noteVariables[1].replace("pitch=","");
		String arrivalTimeString = noteVariables[2].replace("arrivalTime=","");

		
		ArrayList<Integer> pitch = new ArrayList<Integer>();
		pitchString = pitchString.substring(1, pitchString.length()-1);
		String[] pitchArray = pitchString.split(", ");
		for(String each:pitchArray) {
			pitch.add(Integer.parseInt(each));
		}
		
		double duration = Double.parseDouble(durationString);
		double arrivalTime = Double.parseDouble(arrivalTimeString);
		return new Note(duration,pitch, arrivalTime);
	}
}
