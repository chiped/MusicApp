import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class HMM<State, Observation> {
	private TreeSet<State> states;
	private TreeSet<Observation> observations;
	private TreeMap<State, TreeMap<State, Double>> transitionProbabilities;
	private TreeMap<State, TreeMap<Observation, Double>> observationProbabilities;

	public HMM(ArrayList<HMMTrainingInstance<State, Observation>> instances) {
		states = new TreeSet<State>();
		observations = new TreeSet<Observation>();

		for (HMMTrainingInstance<State, Observation> instance : instances) {
			if (states.contains(instance.getState()) == false) {
				states.add(instance.getState());
			}
			if (observations.contains(instance.getObservation()) == false) {
				observations.add(instance.getObservation());
			}
		}

		// initialize the mapping
		transitionProbabilities = new TreeMap<State, TreeMap<State, Double>>();
		observationProbabilities = new TreeMap<State, TreeMap<Observation, Double>>();
		for (State state : states) {
			transitionProbabilities.put(state, new TreeMap<State, Double>());
			observationProbabilities.put(state, new TreeMap<Observation, Double>());
		}

		// calculate the counts
		for (HMMTrainingInstance<State, Observation> instance : instances) {

			State state = instance.getState();
			Observation observation = instance.getObservation();
			State nextState = null;
			if (instance.getNextInstance() != null) {
				nextState = instance.getNextInstance().getState();
			}

			TreeMap<State, Double> statesTransitionProbabilities = transitionProbabilities
					.get(state);
			TreeMap<Observation, Double> statesObservationProbabilities = observationProbabilities
					.get(state);

			if (nextState != null) {
				double nextStateTransitionCount = statesTransitionProbabilities
						.containsKey(nextState) ? statesTransitionProbabilities
						.get(nextState) : 0.0;
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
			TreeMap<State, Double> stateTransitionProbabilities = transitionProbabilities
					.get(state);
			TreeMap<Observation, Double> stateObservationProbabilities = observationProbabilities
					.get(state);
			double sum = 0;

			for (Entry<State, Double> entry : stateTransitionProbabilities
					.entrySet()) {
				sum += entry.getValue();

			}
			
			if (sum != 0) {
				for (Entry<State, Double> entry : stateTransitionProbabilities
						.entrySet()) {
					State key = entry.getKey();
					double value = entry.getValue() / sum;
					stateTransitionProbabilities.put(key, value);
				}
			}
			
			
			sum = 0;
			for (Entry<Observation, Double> entry : stateObservationProbabilities
					.entrySet()) {
				sum += entry.getValue();

			}
			if (sum != 0) {
				for (Entry<Observation, Double> entry : stateObservationProbabilities
						.entrySet()) {
					Observation key = entry.getKey();
					double value = entry.getValue() / sum;
					stateObservationProbabilities.put(key, value);
				}
			}

		}

	}

	public TreeSet<State> getStates() {
		return states;
	}

	public void setStates(TreeSet<State> states) {
		this.states = states;
	}

	public TreeSet<Observation> getObservations() {
		return observations;
	}

	public void setObservations(TreeSet<Observation> observations) {
		this.observations = observations;
	}

	public TreeMap<State, TreeMap<State, Double>> getTransitionProbabilities() {
		return transitionProbabilities;
	}

	public void setTransitionProbabilities(
			TreeMap<State, TreeMap<State, Double>> transitionProbabilities) {
		this.transitionProbabilities = transitionProbabilities;
	}

	public TreeMap<State, TreeMap<Observation, Double>> getObservationProbabilities() {
		return observationProbabilities;
	}

	public void setObservationProbabilities(
			TreeMap<State, TreeMap<Observation, Double>> observationProbabilities) {
		this.observationProbabilities = observationProbabilities;
	}
}
