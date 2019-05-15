package Homework8;
import java.util.Arrays;

/**
 * Class which contains the array of Words of a certain letter (i.e. a/A).
 * 
 * @author Andrew Nieto
 *
 */
public class BookArray {
	private Word[] words;
	private int size;
	private char capital;
	private char lower;
	private static final int INITIAL = 8000;

	/**
	 * Constructor of a BookArray with two char letters passed as arguments which
	 * will be the first letters of all Words contained within the Word array. Sets
	 * the size of the object (number of entries in the Word array to zero and the
	 * initial size of the Word array to 10000.
	 * 
	 * @param capital
	 *            The lower case letter.
	 * @param lower
	 *            The upper case letter.
	 */
	public BookArray(char capital, char lower) {
		this.words = new Word[INITIAL];
		this.capital = capital;
		this.lower = lower;
		this.size = 0;
	}

	/**
	 * Blank constructor. Utilized for all number entries and entries.
	 */
	public BookArray() {
		this('0', '0');
	}

	/**
	 * Determines if a string begins or ends with the respective lower or upper case
	 * letter of the BookArray object
	 * 
	 * @param word
	 *            The word being compared.
	 * @return True if the word begins with the upper or lower case letter of the
	 *         BookArray, false otherwise.
	 */
	public boolean isValid(String word) {
		return (word.charAt(0) == capital || word.charAt(0) == lower);
	}

	/**
	 * Default add method. Calls the addString method with an integer parameter of
	 * 1.
	 * 
	 * @param s
	 *            The string that is to be added to or incremented in the BookArray.
	 */
	public void addTo(String s) {
		this.addString(1, s);
	}

	/**
	 * Adds a string to the BookArray by a certain amount.
	 * 
	 * @param amount
	 *            The amount the frequency of a Word should be increased or set to
	 *            when added if it already exists.
	 * @param s
	 *            The string the Word should be set to when it is added to the
	 *            WordArray.
	 */
	public void addString(int amount, String s) {
		if (size == 0) {
			words[0] = new Word(s, amount);
			size++;
			return;
		}
		int index = BinarySearch(words, s, 0, size);
		if (index == -2) {
			if (size == words.length) {
				words = Arrays.copyOf(words, words.length * 2);
			}
			words[size] = new Word(s, amount);
			size++;
			return;
		} else if (words[index].getWord().equals(s)) {
			words[index].increase(amount);
			return;
		} else {
			if (size == words.length) {
				words = Arrays.copyOf(words, words.length * 2);
			}
			for (int i = size - 1; i >= index; i--) {
				words[i + 1] = words[i];
			}
			words[BinarySearch(words, s, 0, size)] = new Word(s, amount);
			size++;
			return;
		}
	}

	/**
	 * Determines if the the BookArray is empty.
	 * 
	 * @return Returns true if the size of the BookArray is equal to 0, false if
	 *         otherwise.
	 */
	public boolean isEmpty() {
		return (size == 0);
	}

	/**
	 * Gets the size of the BookArray.
	 * 
	 * @return The size of the BookArray.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Method to determine where a word should go or have its frequency changed.
	 * 
	 * @return
	 */
	private int BinarySearch(Word[] list, String insertion, int low, int high) {
		int middle = (low + high) / 2;
		if (list[middle] == null) {
			return -2;
		}
		String word = list[middle].getWord();
		int result = insertion.compareTo(word);
		if (high < low) {
			return middle;
		} else if (result < 0) {
			return BinarySearch(list, insertion, low, middle - 1);
		} else if (result > 0) {
			return BinarySearch(list, insertion, middle + 1, high);
		} else if (result == 0) {
			return middle;
		}
		return -1;
	}

	/**
	 * Gets the Word at the nth position in the BookArray.
	 * 
	 * @param n
	 *            Index of the Word array.
	 * @return The Word at the index, or null if the index was invalid.
	 */
	public Word getWord(int n) {
		if (n > size || n < 0) {
			return null;
		}
		return this.words[n];
	}

	/**
	 * Set the Word at the index n of the BookArray to the Word passed as an
	 * argument.
	 * 
	 * @param n
	 *            The index where the word should be added.
	 * @param word
	 *            The Word being passed as an argument.
	 */
	public void setWord(int n, Word word) {
		if (n < size && n > 0) {
			this.words[n] = word;
		}
	}

	/**
	 * Prints all words and their frequency as they occur.
	 */
	public void printAll() {
		for (int i = 0; i < size; i++) {
			System.out.println(words[i].getFrequency() + " " + words[i].getWord());
		}
	}

	/**
	 * Lookup how often a word occurs in a text.
	 * 
	 * @param The
	 *            string of the Word being searched for.
	 * @return The number of times the string has occurred, 0 if the string has not
	 *         occurred at all or could not be found.
	 */
	public int lookupCount(String s) {
		int index = BinarySearch(words, s, 0, size);
		if (index < 0) {
			return 0;
		}
		return words[index].getFrequency();
	}

	public Word[] getWords() {
		return words;
	}

	// DELTER ALL THE FOLLOWING METHODS BEFORE SUMBISSION OR FINISHING

	/**
	 * Returns the sum of all frequencies in the WordArray.
	 * 
	 * @return Sum of all the frequencies.
	 */
	public long allEntries() {
		long count = 0;
		for (Word word : words) {
			if (word != null) {
				count += word.getFrequency();
			}
		}
		return count;

	}
}
