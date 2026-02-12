package com.example.sort.algorithms;

import com.example.sort.SortMetrics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for BubbleSort algorithm.
 */
class BubbleSortTest {
    
    private BubbleSort bubbleSort;
    
    @BeforeEach
    void setUp() {
        bubbleSort = new BubbleSort();
    }
    
    @Test
    @DisplayName("Should return correct algorithm name")
    void shouldReturnCorrectAlgorithmName() {
        assertThat(bubbleSort.getName()).isEqualTo("Bubble Sort");
    }
    
    @Test
    @DisplayName("Should sort an unsorted array in ascending order")
    void shouldSortUnsortedArray() {
        // Given
        int[] array = {64, 34, 25, 12, 22, 11, 90};
        
        // When
        SortMetrics metrics = bubbleSort.sort(array);
        
        // Then
        assertThat(array).isSorted();
        assertThat(array).containsExactly(11, 12, 22, 25, 34, 64, 90);
        assertThat(metrics.elementCount()).isEqualTo(7);
        assertThat(metrics.timeInSeconds()).isGreaterThanOrEqualTo(0);
        assertThat(metrics.algorithmName()).isEqualTo("Bubble Sort");
        assertThat(metrics.comparisons()).isGreaterThan(0);
        assertThat(metrics.swaps()).isGreaterThan(0);
        assertThat(metrics.auxiliarySpaceUsed()).isEqualTo(0);
        assertThat(metrics.spaceComplexity()).isEqualTo("O(1)");
    }
    
    @Test
    @DisplayName("Should handle already sorted array (best case)")
    void shouldHandleAlreadySortedArray() {
        // Given
        int[] array = {1, 2, 3, 4, 5};
        
        // When
        SortMetrics metrics = bubbleSort.sort(array);
        
        // Then
        assertThat(array).isSorted();
        assertThat(array).containsExactly(1, 2, 3, 4, 5);
        assertThat(metrics.elementCount()).isEqualTo(5);
    }
    
    @Test
    @DisplayName("Should handle reverse sorted array (worst case)")
    void shouldHandleReverseSortedArray() {
        // Given
        int[] array = {5, 4, 3, 2, 1};
        
        // When
        SortMetrics metrics = bubbleSort.sort(array);
        
        // Then
        assertThat(array).isSorted();
        assertThat(array).containsExactly(1, 2, 3, 4, 5);
        assertThat(metrics.elementCount()).isEqualTo(5);
    }
    
    @Test
    @DisplayName("Should handle array with duplicate elements")
    void shouldHandleArrayWithDuplicates() {
        // Given
        int[] array = {5, 2, 8, 2, 9, 1, 5};
        
        // When
        SortMetrics metrics = bubbleSort.sort(array);
        
        // Then
        assertThat(array).isSorted();
        assertThat(array).containsExactly(1, 2, 2, 5, 5, 8, 9);
    }
    
    @Test
    @DisplayName("Should handle single element array")
    void shouldHandleSingleElementArray() {
        // Given
        int[] array = {42};
        
        // When
        SortMetrics metrics = bubbleSort.sort(array);
        
        // Then
        assertThat(array).containsExactly(42);
        assertThat(metrics.elementCount()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Should handle empty array")
    void shouldHandleEmptyArray() {
        // Given
        int[] array = {};
        
        // When
        SortMetrics metrics = bubbleSort.sort(array);
        
        // Then
        assertThat(array).isEmpty();
        assertThat(metrics.elementCount()).isEqualTo(0);
    }
    
    @Test
    @DisplayName("Should handle array with negative numbers")
    void shouldHandleNegativeNumbers() {
        // Given
        int[] array = {-5, 3, -1, 7, -9, 0};
        
        // When
        SortMetrics metrics = bubbleSort.sort(array);
        
        // Then
        assertThat(array).isSorted();
        assertThat(array).containsExactly(-9, -5, -1, 0, 3, 7);
    }
    
    @Test
    @DisplayName("Should perform in-place sorting (same array reference)")
    void shouldPerformInPlaceSorting() {
        // Given
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6};
        int[] originalReference = array;
        
        // When
        bubbleSort.sort(array);
        
        // Then
        assertThat(array).isSameAs(originalReference);
        assertThat(array).isSorted();
    }
}
