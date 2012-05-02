package com.thoughtworks.testandstory.plugins;

public class TestData {

	private String name;

	public TestData(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

    public void setName(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestData testData = (TestData) o;

        if (!name.equals(testData.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
