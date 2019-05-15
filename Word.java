package Homework8;
/**
 * Word class that contains the String word and the frequency of the word's
 * occurence.
 * 
 * @author Andrew Nieto
 *
 */
public class Word implements Comparable<Word> {
	private String word;
	private int frequency;

	/**
	 * Constructor which will set the word and the frequency the parameters passed
	 * as arguments.
	 * 
	 * @param word
	 *            The Word of the object.
	 */
	public Word(String word, int frequency) {
		this.word = word;
		this.frequency = frequency;
	}

	/**
	 * Increases the occurrence of a word by an integer passed as an argument.
	 * 
	 * @param number
	 *            The amount by which the frequency will increase.
	 */
	public void increase(int number) {
		frequency += number;
	}

	/**
	 * Get the frequency of a word.
	 * 
	 * @return The frequency of a word.
	 */
	public int getFrequency() {
		return frequency;
	}

	/**
	 * Get the word of the Word object.
	 * 
	 * @return The String word.
	 */
	public String getWord() {
		return word;
	}

	/**
	 * Compares a word against another word.
	 */
	@Override
	public int compareTo(Word o) {
		return word.compareTo(o.word);
	}

	/**
	 * Compares a word against another word without respect to capitalization.
	 */
	public int compareToIgnoreCase(Word o) {
		return word.compareToIgnoreCase(o.word);
	}

	/**
	 * Standard generated eclipse hashCode method.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + frequency;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	/**
	 * Standard eclipse-generated equals method
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (frequency != other.frequency)
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}

}
