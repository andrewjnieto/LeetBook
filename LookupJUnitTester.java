package Homework8;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.Test;

/**
 *
 * @author Luke Skon, John Karro, Norm Krumpe
 */
public class LookupJUnitTester {

	@Test
	public void test01() {
		LookupInterface test = new StudentLookup();
		assertEquals(0, test.lookupCount("bogus"));
	}

	@Test
	public void test02() {
		LookupInterface test = new StudentLookup();
		assertEquals(0, test.numEntries());
	}

	@Test
	public void test03() {
		LookupInterface test = new StudentLookup();
		test.addString(1, "one");
		assertEquals(0, test.lookupCount("zero"));
		assertEquals(1, test.lookupCount("one"));
	}

	@Test
	public void test04() {
		LookupInterface test = new StudentLookup();
		test.addString(2, "one");
		assertEquals(0, test.lookupCount("zero"));
		assertEquals(2, test.lookupCount("one"));
	}

	@Test
	public void test05() {
		LookupInterface test = new StudentLookup();
		test.addString(1, "one");
		test.addString(2, "two");
		assertEquals(1, test.lookupCount("one"));
		assertEquals(2, test.lookupCount("two"));
	}

	@Test
	public void test06() {
		LookupInterface test = new StudentLookup();
		test.addString(1, "one");
		test.addString(1, "two");
		test.addString(1, "one");
		assertEquals(2, test.lookupCount("one"));
		assertEquals(1, test.lookupCount("two"));
	}

	@Test
	public void test07() {
		LookupInterface test = new StudentLookup();
		test.addString(1, "one");
		test.addString(2, "two");
		test.addString(3, "three");
		assertEquals("two", test.lookupPopularity(1));
		assertEquals("one", test.lookupPopularity(2));
		test.addString(3, "three");
	}

	@Test
	public void test08() {
		LookupInterface test = new StudentLookup();
		test.addString(3, "one");
		test.addString(2, "two");
		test.addString(1, "three");
		assertEquals("three", test.lookupPopularity(2));
		assertEquals("two", test.lookupPopularity(1));
		assertEquals("one", test.lookupPopularity(0));
	}

	@Test
	public void test09() {
		LookupInterface test = new StudentLookup();
		test.addString(1, "one");
		test.addString(3, "two");
		test.addString(2, "three");
		assertEquals(test.lookupPopularity(1), "three");
		assertEquals(test.lookupPopularity(0), "two");
		assertEquals(test.lookupPopularity(2), "one");
	}

	@Test
	public void test10() throws FileNotFoundException {
		StudentLookup test = new StudentLookup();
		test.addString(1, "one");
		test.addString(1, "two");
		test.addString(1, "three");
		test.addString(1, "two");
		test.addString(1, "three");
		test.addString(1, "two");
		test.call();
		assertEquals("three", test.lookupPopularity(1));
		assertEquals("two", test.lookupPopularity(0));
		assertEquals("one", test.lookupPopularity(2));
	}

	@Test
	public void test11() {
		LookupInterface test = new StudentLookup();
		test.addString(1, "BBB");
		test.addString(1, "AAA");
		assertEquals("AAA", test.lookupPopularity(0));
		assertEquals("BBB", test.lookupPopularity(1));
	}

	@Test
	public void test12() {
		LookupInterface test = new StudentLookup();
		test.addString(1, "AAA");
		test.addString(1, "BBB");
		assertEquals("AAA", test.lookupPopularity(0));
		assertEquals("BBB", test.lookupPopularity(1));
	}

	@Test
	public void test13() {
		LookupInterface test = new StudentLookup();
		test.addString(1, "AAA");
		test.addString(2, "BBB");
		test.addString(1, "CCC");
		assertEquals("BBB", test.lookupPopularity(0));
		assertEquals("AAA", test.lookupPopularity(1));
		assertEquals("CCC", test.lookupPopularity(2));
	}

	@Test
	public void test14() {
		LookupInterface test = new StudentLookup();
		 test.addString(1, "AAA");
		 test.addString(1, "BBB");
		 test.addString(1, "CCC");
		 test.addString(1, "AAA");
		 test.addString(1, "CCC");
		 test.addString(1, "AAA");
		 test.addString(1, "CCC");
		 assertEquals("AAA", test.lookupPopularity(0));
		 assertEquals("CCC", test.lookupPopularity(1));
		 assertEquals("BBB", test.lookupPopularity(2));
	}

	@Test
	public void test15() {
		LookupInterface test = new StudentLookup();
		test.addString(1, "AAA");
		test.addString(1, "BBB");
		assertEquals(2, test.numEntries());
	}

	@Test
	public void test16() {
		LookupInterface test = new StudentLookup();
		test.addString(2, "AAA");
		test.addString(2, "BBB");
		assertEquals(2, test.numEntries());
	}

	@Test
	public void test17() {
		LookupInterface test = new StudentLookup();
		test.addString(1, "AAA");
		test.addString(1, "BBB");
		test.addString(1, "AAA");
		test.addString(1, "BBB");
		test.addString(1, "AAA");
		test.addString(1, "BBB");
		assertEquals(2, test.numEntries());
		assertEquals(3, test.lookupCount("AAA"));
		assertEquals(3, test.lookupCount("BBB"));
	}

}
