package com.thoughtworks.testandstory.plugins;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javassist.ClassPool;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.MethodInfo;

public class ClassData {

	private final File classFilePath;
	private ClassFile classFile;

	public ClassData(File classFile) {
		this.classFilePath = classFile;
		try {
			this.classFile = new ClassFile(new DataInputStream(new FileInputStream(classFilePath)));
		} catch (Exception e) {	}
		
	}

	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		AnnotationsAttribute attribute = (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.visibleTag);
		if (attribute == null) return false;
		
		return null != attribute.getAnnotation(annotationClass.getName());
	}
	
	public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		try {
			AnnotationsAttribute attribute = (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.visibleTag);
			return (A) attribute.getAnnotation(annotationClass.getName()).toAnnotationType(Thread.currentThread().getContextClassLoader(), ClassPool.getDefault());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getSimpleName() {
		return classFile.getName();
	}
	 
	public MethodData[] getMethods() {
		List<MethodInfo> rawResults = classFile.getMethods();
		MethodData[] result = new MethodData[rawResults.size()];
		for (int i = 0; i < rawResults.size(); i++) {
			result[i] = new MethodData(rawResults.get(i));
		}
		return result;
	}
	
}