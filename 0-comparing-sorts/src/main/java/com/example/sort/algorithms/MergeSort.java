package com.example.sort.algorithms;

import com.example.sort.SortAlgorithm;
import com.example.sort.SortMetrics;

/**
 * Merge Sort implementation.
 * Time Complexity: O(n log n) in all cases (best, average, worst)
 * Space Complexity: O(n) for the temporary array used during merging
 * 
 * Note: While the sorting modifies the original array in-place,
 * merge sort requires O(n) auxiliary space for the merge operation.
 */
public class MergeSort implements SortAlgorithm {
    
    private long comparisons;
    private long writes;
    private int maxAuxiliarySpace;
    
    @Override
    public SortMetrics sort(int[] array) {
        long startTime = System.nanoTime();
        int n = array.length;
        
        // Reset counters
        comparisons = 0;
        writes = 0;
        maxAuxiliarySpace = 0;
        
        if (n > 1) {
            mergeSort(array, 0, n - 1);
        }
        
        long endTime = System.nanoTime();
        long timeInNanos = endTime - startTime;
        
        return SortMetrics.create(
            getName(),
            n,
            timeInNanos,
            comparisons,
            writes,
            maxAuxiliarySpace,
            getSpaceComplexity(),
            getTimeComplexity()
        );
    }
    
    /**
     * Recursively divides and sorts the array using merge sort algorithm.
     * 
     * @param array the array to sort
     * @param left the starting index of the subarray
     * @param right the ending index of the subarray
     */
    private void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            // Find the middle point
            int mid = left + (right - left) / 2;
            
            // Sort first and second halves
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            
            // Merge the sorted halves
            merge(array, left, mid, right);
        }
    }
    
    /**
     * Merges two sorted subarrays into a single sorted subarray.
     * 
     * @param array the array containing the subarrays
     * @param left the starting index of the first subarray
     * @param mid the ending index of the first subarray
     * @param right the ending index of the second subarray
     */
    private void merge(int[] array, int left, int mid, int right) {
        // Calculate sizes of the two subarrays
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        // Create temporary arrays
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];
        
        // Track auxiliary space usage
        int currentAuxSpace = n1 + n2;
        if (currentAuxSpace > maxAuxiliarySpace) {
            maxAuxiliarySpace = currentAuxSpace;
        }
        
        // Copy data to temporary arrays
        System.arraycopy(array, left, leftArray, 0, n1);
        System.arraycopy(array, mid + 1, rightArray, 0, n2);
        
        // Merge the temporary arrays back into the original array
        int i = 0; // Initial index of first subarray
        int j = 0; // Initial index of second subarray
        int k = left; // Initial index of merged subarray
        
        while (i < n1 && j < n2) {
            comparisons++;
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                writes++;
                i++;
            } else {
                array[k] = rightArray[j];
                writes++;
                j++;
            }
            k++;
        }
        
        // Copy remaining elements of leftArray, if any
        while (i < n1) {
            array[k] = leftArray[i];
            writes++;
            i++;
            k++;
        }
        
        // Copy remaining elements of rightArray, if any
        while (j < n2) {
            array[k] = rightArray[j];
            writes++;
            j++;
            k++;
        }
    }
    
    @Override
    public String getName() {
        return "Merge Sort";
    }
    
    @Override
    public String getSpaceComplexity() {
        return "O(n)";
    }
    
    @Override
    public String getTimeComplexity() {
        return "O(n log n)";
    }
}
