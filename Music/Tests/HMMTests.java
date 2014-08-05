import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.TreeMap;

import org.junit.Test;


public class HMMTests {

	@Test
	public void HMMConstructorTest() {
		
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
		TreeMap<String,Double> initial = model.getInitialProbabilities();
		assertEquals(new Double(4.0/7), initial.get("1"));
		assertEquals(new Double(2.0/7), initial.get("2"));
		assertEquals(new Double(1.0/7), initial.get("3"));
		
		
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
		
		
		
		ArrayList<Pair<String, String>> modelPath = model.getRandomPath(6);
		for(Pair<String,String> p: modelPath){
			System.out.println(p);
		}
		
		fakeClass<String> f1 = new fakeClass<String>("1");
		fakeClass<String> f2 = new fakeClass<String>("1");
		fakeClass<String> f3 = new fakeClass<String>("1");
		
		ArrayList<fakeClass<String>> a1 = new ArrayList<fakeClass<String>>();
		a1.add(f1);
		a1.add(f2);
		ArrayList<fakeClass<String>> a2 = new ArrayList<fakeClass<String>>();
		a2.add(f3);
		ArrayList<fakeClass<String>> a3 = new ArrayList<fakeClass<String>>();
		a3.add(f1);
		a3.add(f2);
		
		TestClass<fakeClass<String>, String> p1 = new TestClass<fakeClass<String>,String>(f1,"1",a1);
		TestClass<fakeClass<String>, String> p2 = new TestClass<fakeClass<String>,String>(f2,"2",a2);
		TestClass<fakeClass<String>, String> p3 = new TestClass<fakeClass<String>,String>(f1,"3",a3);
		
		HMMTrainingInstance<TestClass<fakeClass, String>,String> j1 = new HMMTrainingInstance<TestClass<String, String>,TestClass<String, String>>(p1,p2);
		HMMTrainingInstance<TestClass<fakeClass, String>,TestClass<fakeClass, String>> j2 = new HMMTrainingInstance<TestClass<String, String>,TestClass<String, String>>(p2,p3);
		HMMTrainingInstance<TestClass<fakeClass, String>,TestClass<fakeClass, String>> j3 = new HMMTrainingInstance<TestClass<String, String>,TestClass<String, String>>(p1,p3);
		HMMTrainingInstance<TestClass<fakeClass, String>,TestClass<fakeClass, String>> j4 = new HMMTrainingInstance<TestClass<String, String>,TestClass<String, String>>(p3,p2);
		
		j1.setNextInstance(j2);
		j2.setNextInstance(j3);
		j3.setNextInstance(j4);
		
		ArrayList<HMMTrainingInstance<TestClass<String, String>,TestClass<String, String>>> train = new ArrayList<HMMTrainingInstance<TestClass<String, String>,TestClass<String, String>>>();
		train.add(j1);
		train.add(j2);
		train.add(j3);
		train.add(j4);
		System.out.println("1");
		HMM<TestClass<String,String>,TestClass<String,String>> m = new HMM<TestClass<String,String>, TestClass<String,String>>(train);
		System.out.println(m.getInitialProbabilities());
		System.out.println(m.getTransitionProbabilities());
		System.out.println(m.getObservationProbabilities());
		
		System.out.println(m.getStates());
		System.out.println(m.getObservations());

		System.out.println("2");
		
		ArrayList<Pair<TestClass<String,String>,TestClass<String,String>>> mPath = m.getRandomPath(6);
		System.out.println("3");
		for(Pair<TestClass<String, String>, TestClass<String, String>> p: mPath){
			System.out.println(p);
		}
		
		
	}

}
