import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class HMM<T1, T2> {
	private TreeSet<T1> states;
	private TreeSet<T2> observations;
	private TreeMap<T1, TreeMap<T1, Integer>> transitionProbabilities;
	private TreeMap<T1, TreeMap<T2, Integer>> observationProbabilities;

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
		transitionProbabilities = new TreeMap<T1, TreeMap<T1, Integer>>();
		observationProbabilities = new TreeMap<T1, TreeMap<T2, Integer>>();
		for (T1 state : states) {
			transitionProbabilities.put(state, new TreeMap<T1, Integer>());
			observationProbabilities.put(state, new TreeMap<T2, Integer>());
		}

		for (HMMTrainingInstance<T1, T2> instance : instances) {
			TreeMap<T1, Integer> statesTransitionProbabilities = transitionProbabilities
					.get(instance.getState());
			TreeMap<T2, Integer> statesObservationProbabilities = observationProbabilities
					.get(instance.getState());

			T1 state = instance.getState();
			T2 observation = instance.getObservation();
			T1 nextState = instance.getNextInstance().getState();

			int nextStateTransitionCount = statesTransitionProbabilities
					.containsKey(nextState) ? statesTransitionProbabilities
					.get(nextState) : 0;

			int observationCount = statesObservationProbabilities
					.containsKey(observation) ? statesObservationProbabilities
					.get(observation) : 0;

			statesTransitionProbabilities.put(nextState,
					nextStateTransitionCount + 1);
			statesObservationProbabilities.put(observation, observationCount + 1);

		}

	}
}
