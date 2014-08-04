import java.util.ArrayList;
import java.util.List;


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
	public static <S, O> ArrayList<HMMTrainingInstance<S, O>> createList(List<S> states, List<O> observations) throws Exception {
		ArrayList<HMMTrainingInstance<S, O>> returnList = new ArrayList<HMMTrainingInstance<S, O>>();
		if(states.size() == 0 || observations.size() ==0 || states.size() != observations.size()) {
			throw new Exception("Size mismatch error");
		}
		HMMTrainingInstance<S, O> previous = new HMMTrainingInstance<S, O>(states.get(0), observations.get(0));
		returnList.add(previous);
		for(int i=1; i<states.size(); i++) {
			HMMTrainingInstance<S, O> instance = new HMMTrainingInstance<S, O>(states.get(i), observations.get(i));
			returnList.add(instance);
			instance.setPrevInstance(previous);
			previous.setNextInstance(instance);
			previous = instance;
		}
		return returnList;
	}
	public String toString(){
		return "State: " + this.state + ", Observation: " + this.observation;
	}
	
}
