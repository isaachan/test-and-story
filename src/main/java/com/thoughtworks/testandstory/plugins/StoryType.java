package com.thoughtworks.testandstory.plugins;

public enum StoryType {
	UNIT(1), INTEGRATION(2), FUNCTIONAL(3);
	
	public final int order;

	private StoryType(int order) {
		this.order = order;
	}
}
