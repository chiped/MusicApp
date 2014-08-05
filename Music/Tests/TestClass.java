
public class TestClass<L extends Comparable<L>,R extends Comparable<R>> implements Comparable<TestClass<L,R>> {
	private L l;
    private R r;
    public TestClass(L l, R r){
        this.l = l;
        this.r = r;
    }
    public L getL(){ return l; }
    public R getR(){ return r; }
    public void setL(L l){ this.l = l; }
    public void setR(R r){ this.r = r; }
    public String toString(){
    	return "<" + getL().toString() + "," +getR().toString() +">";
    }
	@Override
	public int compareTo(TestClass<L, R> o) {
		// TODO Auto-generated method stub
		int cmp = getL().compareTo(o.getL());
		if(cmp == 0){
			cmp = getR().compareTo(o.getR());
		}
		return cmp;
	}
}
