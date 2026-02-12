package com.example.sort.algorithms;

import com.example.sort.SortAlgorithm;
import com.example.sort.SortMetrics;

/**
 * Bubble Sort implementation.
 * Time Complexity: O(n²) average and worst case, O(n) best case
 * Space Complexity: O(1) - in-place sorting
 */
public class BubbleSort implements SortAlgorithm {
    
    @Override
    public SortMetrics sort(int[] array) {
        long startTime = System.nanoTime();
        int n = array.length;
        
        long comparisons = 0;
        long swaps = 0;
        
        // Bubble sort algorithm
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                comparisons++;
                if (array[j] > array[j + 1]) {
                    // Swap elements
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swaps++;
                    swapped = true;
                }
            }
            
            // If no swaps occurred, array is already sorted
            if (!swapped) {
                break;
            }
        }
        
        long endTime = System.nanoTime();
        long timeInNanos = endTime - startTime;
        
        return SortMetrics.create(
            getName(),
            n,
            timeInNanos,
            comparisons,
            swaps,
            0, // No auxiliary space used
            getSpaceComplexity(),
            getTimeComplexity()
        );
    }
    
    @Override
    public String getName() {
        return "Bubble Sort";
    }
    
    @Override
    public String getSpaceComplexity() {
        return "O(1)";
    }
    
    @Override
    public String getTimeComplexity() {
        return "O(n²)";
    }
}
