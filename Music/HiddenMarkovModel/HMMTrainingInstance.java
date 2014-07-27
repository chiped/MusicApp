
public class HMMTrainingInstance<S,O> {
	private S state;
	private O observation;
	private HMMTrainingInstance<S,O> nextInstance;
	
	public HMMTrainingInstance(S aState, O anObservation){
		this.state = aState;
		this.observation = anObservation;
	}
	public S getState() {
		return state;
	}
	public void setState(S state) {
		this.state = state;
	}
	public O getObservation() {
		return observation;
	}
	public void setObservation(O observation) {
		this.observation = observation;
	}
	public HMMTrainingInstance<S, O> getNextInstance() {
		return nextInstance;
	}
	public void setNextInstance(HMMTrainingInstance<S,O> next){
		this.nextInstance = next;
	}

	
}
