
public class HMMTrainingInstance<State,Observation> {
	private State state;
	private Observation observation;
	private HMMTrainingInstance<State,Observation> nextInstance;
	
	public HMMTrainingInstance(State aState, Observation anObservation){
		this.state = aState;
		this.observation = anObservation;
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

	public String toString(){
		return "State: " + this.state + ", Observation: " + this.observation;
	}
	
}
