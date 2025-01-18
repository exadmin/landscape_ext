package com.github.exadmin.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ListstUtilsTest {

    @Test
    public void testEqualsArraysWithoutDuplicates() {
        List<String> expList = Arrays.asList("111", "222", "333", "444", "555");
        List<String> actList = Arrays.asList("555", "444", "333", "222", "111");

        ListstUtils.assertListsSimilar(expList, actList);
    }

    @Test(expected = AssertionError.class)
    public void testNonEqualsArraysWithoutDuplicates() {
        List<String> expList = Arrays.asList("111", "222", "333", "444", "555");
        List<String> actList = Arrays.asList("555", "444", "3333", "222", "111");


        ListstUtils.assertListsSimilar(expList, actList);
    }

    @Test
    public void testEqualsArraysWithDuplicates() {
        List<String> expList = Arrays.asList("111", "222", "333", "333", "555");
        List<String> actList = Arrays.asList("555", "333", "222", "333", "111");

        ListstUtils.assertListsSimilar(expList, actList);
    }

    @Test
    public void testEqualsArraysWithDuplicatesAndNulls() {
        List<String> expList = Arrays.asList("111", null, "333", "333", "555");
        List<String> actList = Arrays.asList("555", "333", null, "333", "111");

        ListstUtils.assertListsSimilar(expList, actList);
    }

    @Test
    public void testEqualsArraysWithDuplicatesAndNullsDuplicates() {
        List<String> expList = Arrays.asList("111", null, "333", "333", "555", null, null);
        List<String> actList = Arrays.asList(null, "555", "333", null, null, "333", "111");

        ListstUtils.assertListsSimilar(expList, actList);
    }

    @Test(expected = AssertionError.class)
    public void testNonEqualsArraysWithDuplicatesAndNullsDuplicates() {
        List<String> expList = Arrays.asList("111", null, "333", "333", "555", null, null);
        List<String> actList = Arrays.asList("555", "333", null, null, "333", "111");

        ListstUtils.assertListsSimilar(expList, actList);
    }
}
