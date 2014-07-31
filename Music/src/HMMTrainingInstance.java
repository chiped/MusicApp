
public class HMMTrainingInstance<State,Observation> {
	private State state;
	private Observation observation;
	private HMMTrainingInstance<State,Observation> nextInstance;
	private HMMTrainingInstance<State,Observation> prevInstance;
	
	public HMMTrainingInstance(State aState, Observation anObservation){
		this.state = aState;
		this.observation = anObservation;
		this.prevInstance = null;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public Observation getObservation() {
		return observation;
	}
	public void setObservation(Observation observation) {
		this.observation = observation;
	}
	public HMMTrainingInstance<State, Observation> getNextInstance() {
		return nextInstance;
	}
	public void setNextInstance(HMMTrainingInstance<State,Observation> next){
		this.nextInstance = next;
	}
	public HMMTrainingInstance<State, Observation> getPrevInstance() {
		return prevInstance;
	}
	public void setPrevInstance(HMMTrainingInstance<State,Observation> prev){
		this.prevInstance = prev;
	}
	public String toString(){
		return "State: " + this.state + ", Observation: " + this.observation;
	}
	
}
