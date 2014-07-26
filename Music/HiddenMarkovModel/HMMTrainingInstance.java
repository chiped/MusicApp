
public abstract class HMMTrainingInstance<S,V> {
	private S state;
	private V observation;
	private HMMTrainingInstance<S,V> nextInstance;
	
	public HMMTrainingInstance(S aState, V anObservation){
		this.state = aState;
		this.observation = anObservation;
	}
	public S getState() {
		return state;
	}
	public void setState(S state) {
		this.state = state;
	}
	public V getObservation() {
		return observation;
	}
	public void setObservation(V observation) {
		this.observation = observation;
	}
	public HMMTrainingInstance<S, V> getNextInstance() {
		return nextInstance;
	}
	public void setNextInstance(HMMTrainingInstance<S,V> next){
		this.nextInstance = next;
	}
	
}
