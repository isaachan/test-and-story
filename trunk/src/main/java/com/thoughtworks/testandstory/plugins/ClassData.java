package com.thoughtworks.testandstory.plugins;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ClassData {

	private final Class<?> clazz;

	public ClassData(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getRawClass() {
		return clazz;
	}

	 public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		 return clazz.isAnnotationPresent(annotationClass);
	 }
	
	 public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		 return clazz.getAnnotation(annotationClass);
	 }
	 
	 public String getSimpleName() {
		 return clazz.getSimpleName();
	 }
	 
	 public Method[] getMethods() {
		 return clazz.getMethods();
	 }
}
