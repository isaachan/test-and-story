package com.thoughtworks.testandstory.plugins;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

class RefelectionClassData extends ClassData {
	
	private Class<?> clazz;

	public RefelectionClassData(Class<?> clazz) {
		super(null);
		this.clazz = clazz;
	}

	@Override
	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return clazz.isAnnotationPresent(annotationClass);
	}
	
	@Override
	public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		return clazz.getAnnotation(annotationClass);
	}
	
	@Override
	public String getSimpleName() {
		return clazz.getSimpleName();
	}
	 
	@Override
	public RefelectionMethodData[] getMethods() {
		Method[] rawResults = clazz.getMethods();
		RefelectionMethodData[] result = new RefelectionMethodData[rawResults.length];
		for (int i = 0; i < rawResults.length; i++) {
			result[i] = new RefelectionMethodData(rawResults[i]);
		}
		return result ;
	}
	
}