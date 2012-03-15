package com.thoughtworks.testandstory.plugins;

import java.util.HashMap;
import java.util.Map;

public class ST {

	private final String template;
	private Map<String, String> replacements = new HashMap<String, String>();

	public ST(String template) {
		this.template = template;
	}

	public void add(String toReplaced, Object replacement) {
		String replacementString = replacement.toString().replace("$", "\\$");
		replacements.put(toReplaced, replacementString);
	}

	public String render() {
		String result = new String(template);
		for(String toReplaced : replacements.keySet()) {
			try {
			result = result.replaceAll(String.format("#%s#", toReplaced), replacements.get(toReplaced));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
