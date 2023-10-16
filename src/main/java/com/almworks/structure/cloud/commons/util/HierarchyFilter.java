package com.almworks.structure.cloud.commons.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;

/**
 * <p>A <tt>Hierarchy</tt> stores an arbitrary <em>forest</em> (an ordered collection of ordered trees)
 * as an array indexed by DFS-order traversal.
 * A node is represented by a unique ID.
 * Parent-child relationships are identified by the position in the array and the associated depth.
 * Tree root has depth 0, immediate children have depth 1, their children have depth 2, etc.
 * </p>
 *
 * <p>Depth of the first element is 0. If the depth of a node is D, the depth of the next node in the array can be:</p>
 * <ul>
 *   <li>D + 1 if the next node is a child of this node;</li>
 *   <li>D if the next node is a sibling of this node;</li>
 *   <li>d < D - in this case the next node is not related to this node.</li>
 * </ul>
 *
 * <p>Example:</p>
 * <code>
 * nodeIds: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11
 * depths: 0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2
 * the forest can be visualized as follows:
 * 1
 * - 2
 * - - 3
 * - - - 4
 * - 5
 * 6
 * - 7
 * 8
 * - 9
 * - 10
 * - - 11
 * </code>
 * Note that the depth is equal to the number of hyphens for each node.
 */

class HierarchyFilter {
    /**
     * A node is present in the filtered hierarchy if its node ID passes the predicate and all of its ancestors pass it as well.
     */
    static Hierarchy filter(Hierarchy hierarchy, IntPredicate nodeIdPredicate) {
        List<Integer> filteredNodeIds = new ArrayList<>();
        List<Integer> filteredDepths = new ArrayList<>();

        int size = hierarchy.size();

        for (int i = 0; i < size; i++) {
            int nodeId = hierarchy.nodeId(i);
            int depth = hierarchy.depth(i);

            if (isItemValid(i, hierarchy, nodeIdPredicate)) {
                filteredNodeIds.add(nodeId);
                filteredDepths.add(depth);
            }
        }

        int[] resultNodeIds = filteredNodeIds.stream().mapToInt(Integer::intValue).toArray();

        int[] resultDepths = filteredDepths.stream().mapToInt(Integer::intValue).toArray();

        return new ArrayBasedHierarchy(resultNodeIds, resultDepths);
    }

    private static boolean isItemValid(int index, Hierarchy hierarchy, IntPredicate nodeIdPredicate) {
        int nodeId = hierarchy.nodeId(index);
        int depth = hierarchy.depth(index);

        if (!nodeIdPredicate.test(nodeId)) {
            return false;
        }

        if (nodeIdPredicate.test(nodeId) && depth == 0) {
            return true;
        }

        return isItemValid(findParentIndex(hierarchy, index, depth), hierarchy, nodeIdPredicate);
    }

    public static int findParentIndex(Hierarchy hierarchy, int childIndex, int childDepth) {
        for (int i = childIndex - 1; i >= 0; i--) {
            if (hierarchy.depth(i) < childDepth) {
                return i;
            }
        }
        return -1;
    }
}