package com.thoughtworks.testandstory.plugins;

import java.lang.annotation.Annotation;

import javassist.ClassPool;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.annotation.NoSuchClassError;

public class MetaData {
	
	protected AnnotationsAttribute attribute;

	@SuppressWarnings("unchecked")
	private <A> A getAnnotationBy(Class<A> annotationClass)	throws ClassNotFoundException, NoSuchClassError {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ClassPool classPool = ClassPool.getDefault();
		return (A) attribute.getAnnotation(annotationClass.getName()).toAnnotationType(classLoader, classPool);
	}
	
	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		if (attribute == null) return false;
		return null != attribute.getAnnotation(annotationClass.getName());
	}
	
	public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		try {
			return getAnnotationBy(annotationClass);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
