import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.TreeMap;

import org.junit.Test;


public class HMMTests {

	@Test
	public void test() {
		
		HMMTrainingInstance<String, String> i1 = new HMMTrainingInstance<String,String>("1","a");
		HMMTrainingInstance<String, String> i2 = new HMMTrainingInstance<String,String>("2","b");
		HMMTrainingInstance<String, String> i3 = new HMMTrainingInstance<String,String>("1","a");
		HMMTrainingInstance<String, String> i4 = new HMMTrainingInstance<String,String>("1","b");
		HMMTrainingInstance<String, String> i5 = new HMMTrainingInstance<String,String>("2","a");
		HMMTrainingInstance<String, String> i6 = new HMMTrainingInstance<String,String>("3","a");
		HMMTrainingInstance<String, String> i7 = new HMMTrainingInstance<String,String>("1","b");
		
		i1.setNextInstance(i2);
		i2.setNextInstance(i3);
		i3.setNextInstance(i4);
		i4.setNextInstance(i5);
		i5.setNextInstance(i6);
		i6.setNextInstance(i7);
		
		ArrayList<HMMTrainingInstance<String,String>> trainingSet = new ArrayList<HMMTrainingInstance<String,String>>();
		trainingSet.add(i1);
		trainingSet.add(i2);
		trainingSet.add(i3);
		trainingSet.add(i4);
		trainingSet.add(i5);
		trainingSet.add(i6);
		trainingSet.add(i7);
		
		HMM<String,String> model = new HMM<String,String>(trainingSet);
		
		assertEquals(true, model.getStates().contains("1"));
		assertEquals(true, model.getStates().contains("2"));
		assertEquals(true, model.getStates().contains("3"));
		assertEquals(true, model.getObservations().contains("a"));
		assertEquals(true, model.getObservations().contains("b"));
		
		TreeMap<String,Double> transition1 = model.getTransitionProbabilities().get("1");
		TreeMap<String,Double> observation1 = model.getObservationProbabilities().get("1");
		assertEquals(new Double(1/3.0),transition1.get("1"));
		assertEquals(new Double(2/3.0),transition1.get("2"));
		assertEquals(new Double(1/2.0),observation1.get("a"));
		assertEquals(new Double(1/2.0),observation1.get("b"));
		
		
		TreeMap<String,Double> transition2 = model.getTransitionProbabilities().get("2");
		TreeMap<String,Double> observation2 = model.getObservationProbabilities().get("2");
		assertEquals(new Double(1/2.0),transition2.get("1"));
		assertEquals(new Double(1/2.0),transition2.get("3"));
		assertEquals(new Double(1/2.0),observation2.get("a"));
		assertEquals(new Double(1/2.0),observation2.get("b"));
		
		TreeMap<String,Double> transition3 = model.getTransitionProbabilities().get("3");
		TreeMap<String,Double> observation3 = model.getObservationProbabilities().get("3");		
		assertEquals(new Double(1),transition3.get("1"));
		assertEquals(new Double(1),observation3.get("a"));
		
		
	}

}
