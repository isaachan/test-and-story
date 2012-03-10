package com.thoughtworks.testandstory.plugins;

public class TestInformation implements Comparable<TestInformation>{

	private final int number;
	private final StoryType type;
	private final String methodOrClassName;
	private final boolean fromMethod;

	public TestInformation(int number, StoryType type, String methodOrClassName, boolean fromMethod) {
		this.number = number;
		this.type = type;
		this.methodOrClassName = methodOrClassName;
		this.fromMethod = fromMethod;
	}

	public int number() {
		return number;
	}

	public StoryType type() {
		return type;
	}

	public String getMethodOrClassName() {
		return methodOrClassName;
	}

	public boolean fromMethod() {
		return fromMethod;
	}

	@Override
	public int compareTo(TestInformation other) {
		if (this.number != other.number) return other.number - this.number;
		if (!this.type.equals(other.type)) return other.type.order - this.type.order;
		if (this.fromMethod != other.fromMethod) return (!fromMethod) ? 1 : -1;
		return this.methodOrClassName.compareTo(other.methodOrClassName);
	}

}
