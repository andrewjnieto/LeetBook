package Homework8;
import java.io.FileNotFoundException;

/**
 * Your implementation of the LookupInterface. The only public methods in this
 * class should be the ones that implement the interface. You should write as
 * many other private methods as needed. Of course, you should also have a
 * public constructor.
 * 
 * @author Andrew Nieto
 */

public class StudentLookup implements LookupInterface {
	private Catalog lookup;

	public StudentLookup() {
		this.lookup = new Catalog();
	}

	@Override
	public void addString(int amount, String s) {
		lookup.addString(amount, s);
	}

	@Override
	public int lookupCount(String s) {
		return lookup.lookupCount(s);
	}

	@Override
	public String lookupPopularity(int n) {
		return lookup.lookupPopularity(n);
	}

	@Override
	public int numEntries() {
		return lookup.numEntries();
	}

	/// REMOVE BEFORE SUBMISSION
	public void call() throws FileNotFoundException {
		lookup.forReference();
	}
}
