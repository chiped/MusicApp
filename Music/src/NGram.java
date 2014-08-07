import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class NGram<E extends Comparable<E>> implements Comparable<NGram<E>> {

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

	public static <E extends Comparable<E>> ArrayList<NGram<E>> getNGrams(
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
	public boolean equals(Object o){
		if(o instanceof NGram){
			NGram<E> other = (NGram<E>) o;
			return compareTo(other) == 0;
		}
		return false;
	}
	@Override
	public int compareTo(NGram<E> o) {
		// TODO Auto-generated method stub
		int cmp = size - o.getSize();
		if (cmp == 0) {
			if (getItems().containsAll(o.getItems())
					&& (o.getItems()).containsAll(getItems())) {
				cmp = 0;
			} else {
				cmp = -1;
			}
		}
		return cmp;
	}

	@Override
	public int hashCode(){
		int sum = 0;
		for(int i = 0; i < getItems().size(); i++){
			sum += 1000033 *( i + 2) + getItems().get(i).hashCode();
		}
		return sum;
	}

}