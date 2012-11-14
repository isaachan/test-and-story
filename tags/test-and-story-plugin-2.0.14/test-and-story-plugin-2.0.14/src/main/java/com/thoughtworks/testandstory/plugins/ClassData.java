package com.thoughtworks.testandstory.plugins;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.MethodInfo;

public class ClassData extends MetaData {

	private ClassFile classFile;

	protected ClassData() { }
	
	protected ClassData(File classFilePath) {
		try {
			this.classFile = new ClassFile(new DataInputStream(new FileInputStream(classFilePath)));
			this.attribute = (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.visibleTag);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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