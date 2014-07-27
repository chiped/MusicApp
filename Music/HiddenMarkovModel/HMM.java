import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class HMM<T1, T2> {
	private TreeSet<T1> states;
	private TreeSet<T2> observations;
	private TreeMap<T1, TreeMap<T1, Double>> transitionProbabilities;
	private TreeMap<T1, TreeMap<T2, Double>> observationProbabilities;

	public HMM(ArrayList<HMMTrainingInstance<T1, T2>> instances) {
		states = new TreeSet<T1>();
		observations = new TreeSet<T2>();

		for (HMMTrainingInstance<T1, T2> instance : instances) {
			if (states.contains(instance.getState()) == false) {
				states.add(instance.getState());
			}
			if (observations.contains(instance.getObservation()) == false) {
				observations.add(instance.getObservation());
			}
		}

		// initialize the mapping
		transitionProbabilities = new TreeMap<T1, TreeMap<T1, Double>>();
		observationProbabilities = new TreeMap<T1, TreeMap<T2, Double>>();
		for (T1 state : states) {
			transitionProbabilities.put(state, new TreeMap<T1, Double>());
			observationProbabilities.put(state, new TreeMap<T2, Double>());
		}

		// calculate the counts
		for (HMMTrainingInstance<T1, T2> instance : instances) {

			T1 state = instance.getState();
			T2 observation = instance.getObservation();
			T1 nextState = null;
			if (instance.getNextInstance() != null) {
				nextState = instance.getNextInstance().getState();
			}

			TreeMap<T1, Double> statesTransitionProbabilities = transitionProbabilities
					.get(state);
			TreeMap<T2, Double> statesObservationProbabilities = observationProbabilities
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

		for (T1 state : states) {
			TreeMap<T1, Double> stateTransitionProbabilities = transitionProbabilities
					.get(state);
			TreeMap<T2, Double> stateObservationProbabilities = observationProbabilities
					.get(state);
			double sum = 0;

			for (Entry<T1, Double> entry : stateTransitionProbabilities
					.entrySet()) {
				sum += entry.getValue();

			}
			
			if (sum != 0) {
				for (Entry<T1, Double> entry : stateTransitionProbabilities
						.entrySet()) {
					T1 key = entry.getKey();
					double value = entry.getValue() / sum;
					stateTransitionProbabilities.put(key, value);
				}
			}
			
			
			sum = 0;
			for (Entry<T2, Double> entry : stateObservationProbabilities
					.entrySet()) {
				sum += entry.getValue();

			}
			if (sum != 0) {
				for (Entry<T2, Double> entry : stateObservationProbabilities
						.entrySet()) {
					T2 key = entry.getKey();
					double value = entry.getValue() / sum;
					stateObservationProbabilities.put(key, value);
				}
			}

		}

	}

	public TreeSet<T1> getStates() {
		return states;
	}

	public void setStates(TreeSet<T1> states) {
		this.states = states;
	}

	public TreeSet<T2> getObservations() {
		return observations;
	}

	public void setObservations(TreeSet<T2> observations) {
		this.observations = observations;
	}

	public TreeMap<T1, TreeMap<T1, Double>> getTransitionProbabilities() {
		return transitionProbabilities;
	}

	public void setTransitionProbabilities(
			TreeMap<T1, TreeMap<T1, Double>> transitionProbabilities) {
		this.transitionProbabilities = transitionProbabilities;
	}

	public TreeMap<T1, TreeMap<T2, Double>> getObservationProbabilities() {
		return observationProbabilities;
	}

	public void setObservationProbabilities(
			TreeMap<T1, TreeMap<T2, Double>> observationProbabilities) {
		this.observationProbabilities = observationProbabilities;
	}
}
