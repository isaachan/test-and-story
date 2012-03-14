package com.thoughtworks.testandstory.plugins;

public class ClassData {

	private final Class<?> clazz;

	public ClassData(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getRawClass() {
		return clazz;
	}

}
