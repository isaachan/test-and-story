package com.thoughtworks.testandstory.plugins;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class RefelectionMethodData extends MethodData {

	private final Method method;

	public RefelectionMethodData(Method method) {
		this.method = method;
	}
	
	@Override
	public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		return method.getAnnotation(annotationClass);
	}
	
	@Override
	public String getName() {
		return method.getName();
	}
	
	@Override
	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return method.isAnnotationPresent(annotationClass);
	}
	
}