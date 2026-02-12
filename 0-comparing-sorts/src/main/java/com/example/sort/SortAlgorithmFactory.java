package com.example.sort;

import com.example.sort.algorithms.BubbleSort;
import com.example.sort.algorithms.MergeSort;

/**
 * Factory class for creating sort algorithm instances.
 * Implements the Factory Pattern for flexible algorithm selection.
 */
public class SortAlgorithmFactory {
    
    /**
     * Enum representing available sorting algorithms.
     */
    public enum AlgorithmType {
        BUBBLE_SORT,
        MERGE_SORT
    }
    
    /**
     * Creates a SortAlgorithm instance based on the specified type.
     * 
     * @param type the type of sorting algorithm to create
     * @return a new instance of the specified sorting algorithm
     * @throws IllegalArgumentException if the algorithm type is not supported
     */
    public static SortAlgorithm createAlgorithm(AlgorithmType type) {
        return switch (type) {
            case BUBBLE_SORT -> new BubbleSort();
            case MERGE_SORT -> new MergeSort();
        };
    }
    
    /**
     * Creates a SortAlgorithm instance based on a string name.
     * Case-insensitive matching.
     * 
     * @param algorithmName the name of the sorting algorithm
     * @return a new instance of the specified sorting algorithm
     * @throws IllegalArgumentException if the algorithm name is not recognized
     */
    public static SortAlgorithm createAlgorithm(String algorithmName) {
        AlgorithmType type = switch (algorithmName.toUpperCase().replace(" ", "_")) {
            case "BUBBLE", "BUBBLE_SORT" -> AlgorithmType.BUBBLE_SORT;
            case "MERGE", "MERGE_SORT" -> AlgorithmType.MERGE_SORT;
            default -> throw new IllegalArgumentException(
                "Unknown algorithm: " + algorithmName + 
                ". Available algorithms: BUBBLE_SORT, MERGE_SORT"
            );
        };
        
        return createAlgorithm(type);
    }
}
