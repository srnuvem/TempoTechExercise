package com.almworks.structure.cloud.commons.util;

import org.junit.Assert;
import org.junit.Test;

public class HierarchyFilterTest {

    @Test
    public void testFilter() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                //         x  x     x  x     x  x     x   x
                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                new int[]{0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2}
        );
        Hierarchy filteredActual = HierarchyFilter.filter(unfiltered, nodeId -> nodeId % 3 != 0);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[]{1, 2, 5, 8, 10, 11},
                new int[]{0, 1, 1, 0, 1, 2}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }

    @Test
    public void testFilterEvenIds() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                new int[]{0, 1, 2, 3, 3, 3, 4, 0, 1, 1, 2}
        );
        Hierarchy filteredActual = HierarchyFilter.filter(unfiltered, nodeId -> nodeId % 2 == 0);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[]{8, 10},
                new int[]{0, 1}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }

    @Test
    public void testFilterOddNodes() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12},
                new int[]{0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2, 0}
        );
        Hierarchy filteredActual = HierarchyFilter.filter(unfiltered, nodeId -> nodeId % 2 != 0);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[]{1, 5},
                new int[]{0, 1}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }

    @Test
    public void testFilterMultipleOfThree() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                new int[]{0, 1, 0, 1, 2, 3, 4, 1, 0, 0, 1}
        );
        Hierarchy filteredActual = HierarchyFilter.filter(unfiltered, nodeId -> nodeId % 3 == 0);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[]{3, 9},
                new int[]{0, 0}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }

    @Test
    public void testFilterNonMultipleOfThree() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                new int[]{0, 1, 0, 1, 2, 3, 4, 1, 0, 0, 1}
        );
        Hierarchy filteredActual = HierarchyFilter.filter(unfiltered, nodeId -> nodeId % 3 != 0);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[]{1, 2, 10, 11},
                new int[]{0, 1, 0, 1}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }


    @Test
    public void testFilterEmptyHierarchy() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(new int[]{}, new int[]{});
        Hierarchy filteredActual = HierarchyFilter.filter(unfiltered, nodeId -> nodeId % 2 == 0);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(new int[]{}, new int[]{});

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }


    @Test
    public void testFilterAllValid() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                new int[]{2, 4, 6, 8, 10, 12},
                new int[]{0, 1, 2, 3, 4, 5}
        );
        Hierarchy filteredActual = HierarchyFilter.filter(unfiltered, nodeId -> nodeId % 2 == 0);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[]{2, 4, 6, 8, 10, 12},
                new int[]{0, 1, 2, 3, 4, 5}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }

    @Test
    public void testFilterAllInvalid() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                new int[]{2, 4, 6, 8, 10, 12},
                new int[]{0, 1, 2, 3, 4, 5}
        );
        Hierarchy filteredActual = HierarchyFilter.filter(unfiltered, nodeId -> nodeId == 0);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[]{},
                new int[]{}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }

    @Test
    public void testFilterNodesGreaterThan5() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12},
                new int[]{0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2, 3}
        );
        Hierarchy filteredActual = HierarchyFilter.filter(unfiltered, nodeId -> nodeId > 5);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[]{6, 7, 8, 9, 10, 11, 12},
                new int[]{0, 1, 0, 1, 1, 2, 3}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }

    @Test
    public void testFilterPositiveNodes() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                new int[]{-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5},
                new int[]{0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2}
        );
        Hierarchy filteredActual = HierarchyFilter.filter(unfiltered, nodeId -> nodeId > 0);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[]{2, 3, 4, 5},
                new int[]{0, 1, 1, 2}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }


    @Test
    public void testFilterNegativeNodes() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                new int[]{-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5},
                new int[]{0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2}
        );
        Hierarchy filteredActual = HierarchyFilter.filter(unfiltered, nodeId -> nodeId < 0);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[]{-5, -4, -3, -2, -1},
                new int[]{0, 1, 2, 3, 1}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }

    @Test
    public void testFilterLongArraySmallerThanZero() {
        Hierarchy unfiltered = new ArrayBasedHierarchy(
                new int[]{-50, -40, -30, -20, -10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5},
                new int[]{0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2, 0, 1, 2, 3, 4, 5, 6, 7, 8}
        );
        Hierarchy filteredActual = HierarchyFilter.filter(unfiltered, nodeId -> nodeId < 0);
        Hierarchy filteredExpected = new ArrayBasedHierarchy(
                new int[]{-50, -40, -30, -20, -10, -9, -8, -7, -6, -5, -4, -3, -2, -1},
                new int[]{0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2, 0, 1, 2}
        );

        Assert.assertEquals(filteredExpected.formatString(), filteredActual.formatString());
    }
}