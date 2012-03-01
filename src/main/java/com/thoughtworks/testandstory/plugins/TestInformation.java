package com.thoughtworks.testandstory.plugins;

public class TestInformation implements Comparable<TestInformation>{

	private final int number;
	private final StoryType type;
	private final String description;
	private final boolean fromMethod;

	public TestInformation(int number, StoryType type, String description, boolean fromMethod) {
		this.number = number;
		this.type = type;
		this.description = description;
		this.fromMethod = fromMethod;
	}

	public int number() {
		return number;
	}

	public StoryType type() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	public boolean fromMethod() {
		return fromMethod;
	}

	@Override
	public int compareTo(TestInformation other) {
		if (this.number != other.number) return other.number - this.number;
		if (!this.type.equals(other.type)) return other.type.order - this.type.order;
		if (this.fromMethod != other.fromMethod) return (!fromMethod) ? 1 : -1;
		return this.description.compareTo(other.description);
	}

}
