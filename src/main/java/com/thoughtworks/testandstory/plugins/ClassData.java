package com.thoughtworks.testandstory.plugins;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.annotation.Annotation;
import java.util.List;

import javassist.ClassPool;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.NoSuchClassError;

public class ClassData {

	private ClassFile classFile;
	private AnnotationsAttribute attribute;

	public ClassData(File classFilePath) {
		try {
			this.classFile = new ClassFile(new DataInputStream(new FileInputStream(classFilePath)));
			this.attribute = (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.visibleTag);
		} catch (Exception e) {	}
		
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

	@SuppressWarnings("unchecked")
	private <A> A getAnnotationBy(Class<A> annotationClass)	throws ClassNotFoundException, NoSuchClassError {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ClassPool classPool = ClassPool.getDefault();
		return (A) attribute.getAnnotation(annotationClass.getName()).toAnnotationType(classLoader, classPool);
	}
	
	public String getSimpleName() {
		return classFile.getName();
	}
	 
	@SuppressWarnings("unchecked")
	public MethodData[] getMethods() {
		List<MethodInfo> methodInfos = classFile.getMethods();
		MethodData[] methodDatas = new MethodData[methodInfos.size()];
		for (int i = 0; i < methodInfos.size(); i++) {
			methodDatas[i] = new MethodData(methodInfos.get(i));
		}
		return methodDatas;
	}
	
}