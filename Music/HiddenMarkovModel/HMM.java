import java.util.ArrayList;
import java.util.TreeMap;
public class HMM<T1,T2>{
	private T1[] states;
	private T2[] observations;
	private TreeMap<T1, TreeMap<T1,Integer>> transitionProbabilities;
	private TreeMap<T1, TreeMap<T2,Integer>> emissionProbailities;
	
	public HMM(ArrayList<HMMTrainingInstance<T1,T2>> instances){
		
	}
	
}
