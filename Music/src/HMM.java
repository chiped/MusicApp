import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;

public class HMM<State, Observation> {
	private TreeSet<State> states;
	private TreeSet<Observation> observations;

	private TreeMap<State, Double> initialProbabilities;
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
		transitionProbabilities = new TreeMap<State, TreeMap<State, Double>>();
		observationProbabilities = new TreeMap<State, TreeMap<Observation, Double>>();
		for (State state : states) {
			transitionProbabilities.put(state, new TreeMap<State, Double>());
			observationProbabilities.put(state,
					new TreeMap<Observation, Double>());
		}

		// calculate the counts
		for (HMMTrainingInstance<State, Observation> instance : instances) {

			State state = instance.getState();
			Observation observation = instance.getObservation();

			TreeMap<State, Double> statesTransitionProbabilities = transitionProbabilities
					.get(state);
			TreeMap<Observation, Double> statesObservationProbabilities = observationProbabilities
					.get(state);

			if (instance.getNextInstance() != null) {
				State nextState = instance.getNextInstance().getState();
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
			setProbabilities(stateTransitionProbabilities);
			setProbabilities(stateObservationProbabilities);

		}

	}

	private <T> void setProbabilities(TreeMap<T, Double> probabilities) {
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

	private <T> T randomValue(TreeMap<T, Double> probabilities) {
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
			TreeMap<State, Double> currentStateTransitions = transitionProbabilities
					.get(currentState);
			TreeMap<Observation, Double> currentStateObservations = observationProbabilities
					.get(currentState);
			State next_state = randomValue(currentStateTransitions);
			Observation current_observation = randomValue(currentStateObservations);

			path.add(new Pair<State, Observation>(currentState,
					current_observation));
			currentState = next_state;

		}
		return path;
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
