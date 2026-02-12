package com.example.sort;

/**
 * Interface for sorting algorithms.
 * All implementations should perform in-place sorting.
 */
public interface SortAlgorithm {
    
    /**
     * Sorts the given array in-place in ascending order.
     * 
     * @param array the array to sort (will be modified)
     * @return metrics about the sorting operation
     */
    SortMetrics sort(int[] array);
    
    /**
     * Returns the name of the sorting algorithm.
     * 
     * @return algorithm name
     */
    String getName();
    
    /**
     * Returns the space complexity notation for this algorithm.
     * 
     * @return space complexity (e.g., "O(1)", "O(n)", "O(log n)")
     */
    String getSpaceComplexity();
    
    /**
     * Returns the time complexity notation for this algorithm.
     * 
     * @return time complexity (e.g., "O(n²)", "O(n log n)")
     */
    String getTimeComplexity();
}
