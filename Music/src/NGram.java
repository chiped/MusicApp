import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class NGram<E extends Comparable> implements Comparable<Object> {

	private int size;
	private ArrayList<E> items;

	public NGram(int size) {
		this.size = size;
	}

	public NGram(E[] items) {
		this.items = new ArrayList<E>(Arrays.asList(items));
	}

	public NGram(Collection<E> items) {
		this.items = new ArrayList<E>(items);
	}

	public int getSize() {
		return size;
	}

	public void setItems(E[] items) throws Exception {
		if (this.size != items.length) {
			Exception exception = new Exception(
					"Size of argument must be same as size of NGram");
			throw exception;
		}
		this.items = new ArrayList<E>(Arrays.asList(items));
		;
	}

	public ArrayList<E> getItems() {
		return items;
	}

	public void setItems(Collection<E> items) throws Exception {
		if (items.size() != this.size) {
			Exception exception = new Exception(
					"Size of argument must be same as size of NGram");
			throw exception;
		}
		this.items = new ArrayList<E>(items);
	}

	public static <E extends Comparable> ArrayList<NGram<E>> getNGrams(
			ArrayList<E> objects, int n) {
		ArrayList<NGram<E>> returnList = new ArrayList<NGram<E>>();
		for (int i = n; i <= objects.size(); i++) {
			NGram<E> nGram = new NGram<E>(objects.subList(i - n, i));
			returnList.add(nGram);
		}
		return returnList;
	}

	public String toString() {
		return items.toString();
	}

	public boolean equals(Object other) {
	
		return compareTo(other) == 0;

	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		NGram other = (NGram) o;
		int cmp = this.getSize() - other.getSize();
		if (cmp == 0) {
			
			if ((other.getItems()).containsAll(this.getItems()) == false ||(this.getItems()).containsAll(other.getItems()) == false) {
				cmp = -100000;
			}
		}

		return cmp;
	}

}
