package com.thoughtworks.testandstory.plugins;

import java.lang.annotation.Annotation;

import javassist.ClassPool;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.MethodInfo;

class MethodData {

	private final MethodInfo methodInfo;

	public MethodData(MethodInfo methodInfo) {
		this.methodInfo = methodInfo;
	}
	
	public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		try {
			AnnotationsAttribute attribute = (AnnotationsAttribute) methodInfo.getAttribute(AnnotationsAttribute.visibleTag);
			return (A) attribute.getAnnotation(Story.class.getName()).toAnnotationType(Thread.currentThread().getContextClassLoader(), ClassPool.getDefault());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getName() {
		return methodInfo.getName();
	}
	
	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		AnnotationsAttribute attribute = (AnnotationsAttribute) methodInfo.getAttribute(AnnotationsAttribute.visibleTag);
		if (attribute == null) return false;
		
		return null != attribute.getAnnotation(Story.class.getName());
	}
	
	
}