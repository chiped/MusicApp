
public class Note {
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
		return "Note [duration=" + duration + ", pitch=" + pitch + "]";
	}
	
}
