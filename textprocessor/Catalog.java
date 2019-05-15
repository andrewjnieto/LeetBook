package Homework8;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Class which contains an array of BookArray objects which functions as a
 * dictionary of all words that have been read
 * 
 * @author Andrew Nieto
 *
 */
public class Catalog {
	private BookArray[] array;
	private boolean ranked;

	/**
	 * Default constructor for the Catalog. Creates an array of 28 BookArray of
	 * objects with each being associated with a letter of the alphabet, with the
	 * 27th being any Word that does not fit in the others (i.e. numbers), and the
	 * 28th having all words and for the sole purpose of being used to determine
	 * rank of frequency.
	 */
	public Catalog() {
		array = new BookArray[28];
		// For all letters of the alphabet.
		for (int i = 65; i <= 90; i++) {
			array[i - 65] = new BookArray((char) i, (char) (i + 32));
		}
		// For numbers and other words that do not fit.
		array[26] = new BookArray();
		// For all words to be sorted by rank.
		array[27] = new BookArray();
		ranked = false;
	}

	/**
	 * Adds a string to the Catalog by a certain amount passed as an integer
	 * argument.
	 * 
	 * @param amount
	 *            The amount by which the Word, if it exists, should be added to the
	 *            frequency, or if it does not, set to.
	 * @param s
	 *            The string being added.
	 */
	public void addString(int amount, String s) {
		array[this.indexOfArray(s)].addString(amount, s);
		array[27].addString(amount, s);
	}

	/**
	 * Looks up how often a word occurs (frequency).
	 * 
	 * @param s
	 *            The string being search for.
	 * @return The frequency of the word, or 0 if the word never occurs or does not
	 *         exist in the Catalog.
	 */
	public int lookupCount(String s) {
		return array[this.indexOfArray(s)].lookupCount(s);
	}

	/**
	 * Returns the number of UNIQUE entries within
	 * 
	 * @return The number of unique entries in the Catalog.
	 */
	public int numEntries() {
		int count = 0;
		for (int i = 0; i < array.length - 1; i++) {
			count += this.array[i].getSize();
		}
		return count;
	}

	/**
	 * Looks up the Word of nth popularity.
	 * 
	 * @param n
	 *            The popularity rank of the word being looked for.
	 * @return The string of the Word that is being looked for.
	 */
	public String lookupPopularity(int n) {
		if (!ranked) {
			rank();
			ranked = true;
		}
		return array[27].getWord(n).getWord();
	}

	/**
	 * Organizes the BookArray by rank.
	 */
	public void rank() {
		mergeSort(this.array[27].getWords(), 0, this.array[27].getSize() - 1);
	}

	/**
	 * Determines the proper array the word should be added to.
	 * 
	 * @param string
	 *            The string that needs to be added.
	 * @return The index of the BookArray array that the word should be added to.
	 */
	private int indexOfArray(String string) {
		for (int i = 0; i <= 25; i++) {
			if (array[i].isValid(string)) {
				return i;
			}
		}
		return 26;
	}

	private void merge(Word[] array, int first, int mid, int last) {
		// Sizes of two smaller word arrays
		int size1 = mid - first + 1;
		int size2 = last - mid;
		// Creation of smaller word arrays.
		Word array1[] = new Word[size1];
		Word array2[] = new Word[size2];

		// Copy the Words into subarrays
		for (int i = 0; i < size1; i++) {
			array1[i] = array[first + i];
		}
		for (int j = 0; j < size2; j++) {
			array2[j] = array[(mid + 1) + j];
		}

		// Merging of the subarrays
		int i = 0;
		int j = 0;
		int k = first;
		while (i < size1 && j < size2) {
			if (array1[i].getFrequency() >= array2[j].getFrequency()) {
				array[k] = array1[i];
				i++;
			} else {
				array[k] = array2[j];
				j++;
			}
			k++;
		}

		// Following copies the remaining elements.
		while (i < size1) {
			array[k] = array1[i];
			i++;
			k++;
		}
		while (j < size2) {
			array[k] = array2[j];
			j++;
			k++;
		}
	}

	private void mergeSort(Word[] array, int first, int last) {
		if (first < last) {
			int mid = (first + last) / 2;
			mergeSort(array, first, mid);
			mergeSort(array, mid + 1, last);
			merge(array, first, mid, last);
		}
	}

	// THE FOLLOWING ARE UNNECESSARY AND SHOULD BE DELETED WHEN SUBMITTING
	public Long runAll(File file) throws FileNotFoundException {
		Scanner reader = new Scanner(file);
		String[] line;
		while (reader.hasNextLine()) {
			line = reader.nextLine().replaceAll("[^\\p{Alnum}^\\p{javaWhitespace}]", "").split("\\s+");
			for (int i = 0; i < line.length; i++) {
				if (!line[i].equals("")) {
					this.addString(1, line[i]);
				}
			}
		}
		reader.close();
		return System.nanoTime();
	}

	public void forReference() throws FileNotFoundException {
		PrintWriter out = new PrintWriter("new.txt");
		for (Word word : array[27].getWords()) {
			if (word != null)
				out.println(word.getFrequency() + " " + word.getWord());
		}
		out.close();

	}

	public long allEntries() {
		long count = 0;
		for (int i = 0; i < array.length - 1; i++) {
			count += array[i].allEntries();
		}
		return count;
	}

	public static void main(String[] args) throws FileNotFoundException {
		long startTime = System.nanoTime();
		Catalog catalog = new Catalog();
		System.out.println(catalog.runAll(new File("1.txt")) - startTime);
		System.out.println("1.txt Done");
		System.out.println(catalog.runAll(new File("2.txt")) - startTime);
		System.out.println("2.txt Done");
		System.out.println(catalog.runAll(new File("3.txt")) - startTime);
		System.out.println("3.txt Done");
		System.out.println(catalog.runAll(new File("4.txt")) - startTime);
		System.out.println("4.txt Done");
		System.out.println(catalog.runAll(new File("5.txt")) - startTime);
		System.out.println("5.txt Done");
		System.out.println(catalog.runAll(new File("6.txt")) - startTime);
		System.out.println("6.txt Done");
		System.out.println(catalog.runAll(new File("7.txt")) - startTime);
		System.out.println("7.txt Done");
		System.out.println("BEFORE MERGE: " + (System.nanoTime() - startTime));
		System.out.println("ALL BUT 27:  " + catalog.allEntries());
		System.out.println("27 NON_ranked: " + catalog.array[27].allEntries());
		catalog.rank();
		System.out.println("27 ranked: " + catalog.array[27].allEntries());
		System.out.println("AFTERF MERGE: " + (System.nanoTime() - startTime));
		System.out.println("TOP TEN");
		for (int i = 0; i < 10; i++) {
			System.out.println(catalog.lookupPopularity(i));
		}
		System.out.println(catalog.lookupPopularity(147));
	}
}
