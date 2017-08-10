package com.mindaugas.task.duplicate;

public class Duplicate {

	private String string;
	private int count;

	public Duplicate(String word, int count) {
		this.string = word;
		this.count = count;
	}

	public String getWord() {
		return string;
	}

	@Override
	public String toString() {
		return string + " = " + count + "\n";
	}
}
