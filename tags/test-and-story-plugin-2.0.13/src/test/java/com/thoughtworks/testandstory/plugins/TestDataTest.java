package com.thoughtworks.testandstory.plugins;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: twer
 * Date: 4/25/12
 * Time: 1:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestDataTest {

    @Test
    public void should_return_true_if_two_testdata_name_with_same_value() throws Exception {
        TestData testData = new TestData("testdata");
        TestData testData_another = new TestData("testdata");
        assertThat(testData.equals(testData_another), is(true));
    }

    @Test
    public void should_return_false_if_two_testdata_with_different_value() throws Exception {
        TestData testData = new TestData("testdata");
        TestData aTestData = new TestData("atestdata");
        assertThat(testData.equals(aTestData), is(false));
    }
}
