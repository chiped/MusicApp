import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
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
			observationProbabilities.put(state,
					new TreeMap<Observation, Double>());
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

	public ArrayList<Pair<State, Observation>> getRandomPath(int length) {

		ArrayList<Pair<State, Observation>> path = new ArrayList<Pair<State, Observation>>(
				length);

		int size = states.size();
		int randomIndex = new Random().nextInt(size); // In real life, the
														// Random object should
														// be rather more shared
														// than this
		int i = 0;
		State currentState = null;
		for (State state : states) {
			if (i == randomIndex) {
				currentState = state;
			}
			i = i + 1;
		}
		while (path.size() != length) {
			TreeMap<State, Double> currentStateTransitions = transitionProbabilities
					.get(currentState);

			int randomValue = new Random().nextInt() % 100;
			State next_state = null;
			for (Map.Entry<State, Double> entry : currentStateTransitions.entrySet()) {
				randomValue -= (entry.getValue()*100);
				
				if (randomValue <= 1) {
					next_state = entry.getKey();
					break;
				}

			}

			TreeMap<Observation, Double> currentStateObservations = observationProbabilities
					.get(currentState);

			randomValue = new Random().nextInt() % 100;
			Observation current_observation = null;
			for (Map.Entry<Observation, Double> entry : currentStateObservations.entrySet()) {
				randomValue -= (entry.getValue()*100);
				
				if (randomValue <= 1) {
					current_observation = entry.getKey();
					break;
				}

			}
			path.add(new Pair<State, Observation>(currentState, current_observation));
			currentState = next_state;
			if(next_state == null || current_observation == null){
				System.out.println("something really bad happened");
			}
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
