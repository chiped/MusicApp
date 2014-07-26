
public abstract class HMMTrainingInstance<S,V> {
	private S state;
	private V observation;
	private HMMTrainingInstance<S,V> nextInstance;
}
