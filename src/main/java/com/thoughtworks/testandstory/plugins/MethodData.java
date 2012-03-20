package com.thoughtworks.testandstory.plugins;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.MethodInfo;

class MethodData extends MetaData {

	private MethodInfo methodInfo;

	protected MethodData() {}
	
	public MethodData(MethodInfo methodInfo) {
		this.methodInfo = methodInfo;
		this.attribute = (AnnotationsAttribute) methodInfo.getAttribute(AnnotationsAttribute.visibleTag);
	}
	
	public String getName() {
		return methodInfo.getName();
	}	
	
}