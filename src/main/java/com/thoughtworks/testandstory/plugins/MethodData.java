package com.thoughtworks.testandstory.plugins;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MethodData {

	private final Method method;

	public MethodData(Method method) {
		this.method = method;
	}
	
	public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		return method.getAnnotation(annotationClass);
	}
	
	public String getName() {
		return method.getName();
	}
	
	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return method.isAnnotationPresent(annotationClass);
	}
	
}