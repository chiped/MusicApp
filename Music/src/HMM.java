import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Comparator;

public class HMM<State extends Comparable, Observation extends Comparable> {
	private HashSet<State> states;
	private HashSet<Observation> observations;

	private HashMap<State, Double> initialProbabilities;
	private HashMap<State, HashMap<State, Double>> transitionProbabilities;
	private HashMap<State, HashMap<Observation, Double>> observationProbabilities;

	public HMM(ArrayList<HMMTrainingInstance<State, Observation>> instances) {
		states = new HashSet<State>();
		observations = new HashSet<Observation>();
		initialProbabilities = new HashMap<State, Double>();
		transitionProbabilities = new HashMap<State, HashMap<State, Double>>();
		observationProbabilities = new HashMap<State, HashMap<Observation, Double>>();
		System.out.println("instances::" + instances);
		for (HMMTrainingInstance<State, Observation> instance : instances) {
			if (states.contains(instance.getState()) == false) {
				states.add(instance.getState());
			}
			if (observations.contains(instance.getObservation()) == false) {
				observations.add(instance.getObservation());
			}
			if (instance.getPrevInstance() == null) {
				double initialStateCount = initialProbabilities
						.containsKey(instance.getState()) ? initialProbabilities
						.get(instance.getState()) : 0.0;
				initialProbabilities.put(instance.getState(),
						initialStateCount + 1);
			}
		}
		setProbabilities(initialProbabilities);

		// initialize the mapping

		for (State state : states) {
			transitionProbabilities.put(state, new HashMap<State, Double>());
			observationProbabilities.put(state,
					new HashMap<Observation, Double>());
			System.out.println(transitionProbabilities);
			
		}
		// calculate the counts
		System.out.println("1");
		System.out.println(transitionProbabilities);
		for(HMMTrainingInstance<State, Observation> instance : instances){
			State state = instance.getState();
			
			System.out.println(state +"-3-"+transitionProbabilities
					.containsKey(state)+"-3-" +states.contains(state));
		}
		System.out.println("\n\n");
		for (HMMTrainingInstance<State, Observation> instance : instances) {

			State state = instance.getState();
			Observation observation = instance.getObservation();
			
			HashMap<State, Double> statesTransitionProbabilities = transitionProbabilities
					.get(state);
			//System.out.println(statesTransitionProbabilities);
			System.out.println(transitionProbabilities
					.containsKey(state));
			
			HashMap<Observation, Double> statesObservationProbabilities = observationProbabilities
					.get(state);
			
			if (instance.getNextInstance() != null) {
				
				//System.out.println(state);
				State nextState = instance.getNextInstance().getState();
				//System.out.println(state);
				//System.out.println(nextState);
				//System.out.println(statesTransitionProbabilities);//.containsKey(nextState))
				double nextStateTransitionCount = 0.0; 
				
				if(statesTransitionProbabilities.containsKey(nextState)){
					nextStateTransitionCount = statesTransitionProbabilities.get(nextState);
				}
						
				statesTransitionProbabilities.put(nextState,
						nextStateTransitionCount + 1);
			}

			double observationCount = statesObservationProbabilities
					.containsKey(observation) ? statesObservationProbabilities
					.get(observation) : 0.0;

			statesObservationProbabilities.put(observation,
					observationCount + 1);
		}

		// convert counts into probabilities
		for (State state : states) {
			HashMap<State, Double> stateTransitionProbabilities = transitionProbabilities
					.get(state);
			HashMap<Observation, Double> stateObservationProbabilities = observationProbabilities
					.get(state);
			setProbabilities(stateTransitionProbabilities);
			setProbabilities(stateObservationProbabilities);

		}

	}

	private <T extends Comparable> void setProbabilities(HashMap<T, Double> probabilities) {
		double sum = 0;
		for (Entry<T, Double> entry : probabilities.entrySet()) {
			sum += entry.getValue();

		}
		if (sum != 0) {
			for (Entry<T, Double> entry : probabilities.entrySet()) {
				T key = entry.getKey();
				double value = entry.getValue() / sum;
				probabilities.put(key, value);
			}
		}

	}

	private <T> T randomValue(HashMap<T, Double> probabilities) {
		double randomValue = new Random().nextDouble();

		double i = 0.0;
		T value = null;
		for (Map.Entry<T, Double> entry : probabilities.entrySet()) {
			value = entry.getKey();
			i += entry.getValue();
			if (i >= randomValue) {

				return value;
			}
		}

		return value;
	}

	public ArrayList<Pair<State, Observation>> getRandomPath(int length) {

		ArrayList<Pair<State, Observation>> path = new ArrayList<Pair<State, Observation>>(
				length);

		State currentState = randomValue(initialProbabilities);

		while (path.size() != length) {
			HashMap<State, Double> currentStateTransitions = transitionProbabilities
					.get(currentState);
			HashMap<Observation, Double> currentStateObservations = observationProbabilities
					.get(currentState);
			State next_state = randomValue(currentStateTransitions);
			Observation current_observation = randomValue(currentStateObservations);

			path.add(new Pair<State, Observation>(currentState,
					current_observation));
			currentState = next_state;

		}
		return path;
	}

	public HashSet<State> getStates() {
		return states;
	}

	public void setStates(HashSet<State> states) {
		this.states = states;
	}

	public HashSet<Observation> getObservations() {
		return observations;
	}

	public void setObservations(HashSet<Observation> observations) {
		this.observations = observations;
	}

	public HashMap<State, HashMap<State, Double>> getTransitionProbabilities() {
		return transitionProbabilities;
	}

	public void setTransitionProbabilities(
			HashMap<State, HashMap<State, Double>> transitionProbabilities) {
		this.transitionProbabilities = transitionProbabilities;
	}

	public HashMap<State, HashMap<Observation, Double>> getObservationProbabilities() {
		return observationProbabilities;
	}

	public void setObservationProbabilities(
			HashMap<State, HashMap<Observation, Double>> observationProbabilities) {
		this.observationProbabilities = observationProbabilities;
	}

	public HashMap<State, Double> getInitialProbabilities() {
		return initialProbabilities;
	}

}
