
public class fakeClass<M extends Comparable<M>> implements Comparable<fakeClass<M>> {
	private M m;
	public fakeClass(M m){
		this.m = m;
	}
	public M getM() {
		return m;
	}

	public void setM(M m) {
		this.m = m;
	}
	@Override
	public int compareTo(fakeClass arg0) {
		// TODO Auto-generated method stub
		fakeClass other = (fakeClass) arg0;
		return m.compareTo((M) other.getM());
	}
	public boolean equals(fakeClass o){
		return compareTo(o) == 0;
	}
	
	
	
}
