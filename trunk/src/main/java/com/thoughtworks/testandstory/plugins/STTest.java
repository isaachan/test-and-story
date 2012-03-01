package com.thoughtworks.testandstory.plugins;

import static org.junit.Assert.*;

import org.junit.Test;

public class STTest {

	@Test
	public void replace() {
		ST st = new ST("Hi, I'm $name$, $age$ years old.");
		st.add("name", "Isaac");
		st.add("age", "29");
		
		assertEquals("Hi, I'm Isaac, 29 years old.", st.render());
		
		st.add("name", "YiLin");
		st.add("age", "28");
		assertEquals("Hi, I'm YiLin, 28 years old.", st.render());
	}
}
