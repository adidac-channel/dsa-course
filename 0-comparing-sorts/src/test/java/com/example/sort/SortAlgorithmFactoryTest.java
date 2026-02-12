package com.example.sort;

import com.example.sort.algorithms.BubbleSort;
import com.example.sort.algorithms.MergeSort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for SortAlgorithmFactory.
 */
class SortAlgorithmFactoryTest {
    
    @Test
    @DisplayName("Should create BubbleSort using enum type")
    void shouldCreateBubbleSortUsingEnum() {
        // When
        SortAlgorithm algorithm = SortAlgorithmFactory.createAlgorithm(
            SortAlgorithmFactory.AlgorithmType.BUBBLE_SORT
        );
        
        // Then
        assertThat(algorithm).isInstanceOf(BubbleSort.class);
        assertThat(algorithm.getName()).isEqualTo("Bubble Sort");
    }
    
    @Test
    @DisplayName("Should create MergeSort using enum type")
    void shouldCreateMergeSortUsingEnum() {
        // When
        SortAlgorithm algorithm = SortAlgorithmFactory.createAlgorithm(
            SortAlgorithmFactory.AlgorithmType.MERGE_SORT
        );
        
        // Then
        assertThat(algorithm).isInstanceOf(MergeSort.class);
        assertThat(algorithm.getName()).isEqualTo("Merge Sort");
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"bubble", "Bubble", "BUBBLE", "bubble_sort", "BUBBLE_SORT", "Bubble Sort"})
    @DisplayName("Should create BubbleSort using string name (case-insensitive)")
    void shouldCreateBubbleSortUsingStringName(String algorithmName) {
        // When
        SortAlgorithm algorithm = SortAlgorithmFactory.createAlgorithm(algorithmName);
        
        // Then
        assertThat(algorithm).isInstanceOf(BubbleSort.class);
        assertThat(algorithm.getName()).isEqualTo("Bubble Sort");
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"merge", "Merge", "MERGE", "merge_sort", "MERGE_SORT", "Merge Sort"})
    @DisplayName("Should create MergeSort using string name (case-insensitive)")
    void shouldCreateMergeSortUsingStringName(String algorithmName) {
        // When
        SortAlgorithm algorithm = SortAlgorithmFactory.createAlgorithm(algorithmName);
        
        // Then
        assertThat(algorithm).isInstanceOf(MergeSort.class);
        assertThat(algorithm.getName()).isEqualTo("Merge Sort");
    }
    
    @Test
    @DisplayName("Should throw exception for unknown algorithm name")
    void shouldThrowExceptionForUnknownAlgorithm() {
        // When/Then
        assertThatThrownBy(() -> SortAlgorithmFactory.createAlgorithm("quick_sort"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unknown algorithm: quick_sort");
    }
    
    @Test
    @DisplayName("Should create new instance on each call")
    void shouldCreateNewInstanceOnEachCall() {
        // When
        SortAlgorithm algorithm1 = SortAlgorithmFactory.createAlgorithm(
            SortAlgorithmFactory.AlgorithmType.BUBBLE_SORT
        );
        SortAlgorithm algorithm2 = SortAlgorithmFactory.createAlgorithm(
            SortAlgorithmFactory.AlgorithmType.BUBBLE_SORT
        );
        
        // Then
        assertThat(algorithm1).isNotSameAs(algorithm2);
    }
}
