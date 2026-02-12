package com.example.sort;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for SortMetrics record.
 */
class SortMetricsTest {
    
    @Test
    @DisplayName("Should create SortMetrics with correct values using create method")
    void shouldCreateSortMetricsWithCorrectValues() {
        // Given
        String algorithmName = "Test Sort";
        int elementCount = 100;
        long timeInNanos = 123456000L;
        long comparisons = 500;
        long swaps = 200;
        int auxSpace = 100;
        String spaceComplexity = "O(n)";
        String timeComplexity = "O(n log n)";
        
        // When
        SortMetrics metrics = SortMetrics.create(
            algorithmName, elementCount, timeInNanos, comparisons, 
            swaps, auxSpace, spaceComplexity, timeComplexity
        );
        
        // Then
        assertThat(metrics.algorithmName()).isEqualTo(algorithmName);
        assertThat(metrics.elementCount()).isEqualTo(elementCount);
        assertThat(metrics.timeInNanoseconds()).isEqualTo(timeInNanos);
        assertThat(metrics.comparisons()).isEqualTo(comparisons);
        assertThat(metrics.swaps()).isEqualTo(swaps);
        assertThat(metrics.auxiliarySpaceUsed()).isEqualTo(auxSpace);
        assertThat(metrics.spaceComplexity()).isEqualTo(spaceComplexity);
        assertThat(metrics.timeComplexityClass()).isEqualTo(timeComplexity);
        assertThat(metrics.throughput()).isGreaterThan(0);
        assertThat(metrics.memoryUsedBytes()).isEqualTo(100L * 4L);
    }
    
    @Test
    @DisplayName("Should print detailed metrics in correct format")
    void shouldPrintDetailedMetricsInCorrectFormat() {
        // Given
        SortMetrics metrics = SortMetrics.create(
            "Bubble Sort", 50, 1234000L, 100, 50, 0, "O(1)", "O(n²)"
        );
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        
        try {
            System.setOut(printStream);
            
            // When
            metrics.printMetrics();
            
            // Then
            String output = outputStream.toString();
            assertThat(output).contains("Algorithm: Bubble Sort");
            assertThat(output).contains("Input Size: 50");
            assertThat(output).contains("Comparisons: 100");
            assertThat(output).contains("Swaps/Writes: 50");
            assertThat(output).contains("Space Complexity: O(1)");
            assertThat(output).contains("Time Complexity: O(n²)");
            assertThat(output).contains("Throughput:");
        } finally {
            System.setOut(originalOut);
        }
    }
    
    @Test
    @DisplayName("Should print compact metrics in single line")
    void shouldPrintCompactMetricsInSingleLine() {
        // Given
        SortMetrics metrics = SortMetrics.create(
            "Merge Sort", 1000, 5000000L, 5000, 10000, 500, "O(n)", "O(n log n)"
        );
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        
        try {
            System.setOut(printStream);
            
            // When
            metrics.printCompactMetrics();
            
            // Then
            String output = outputStream.toString();
            assertThat(output).contains("Merge Sort");
            assertThat(output).contains("1,000");
            assertThat(output).contains("5,000");
            assertThat(output).contains("10,000");
            assertThat(output).contains("O(n)");
        } finally {
            System.setOut(originalOut);
        }
    }
    
    @Test
    @DisplayName("Records with same values should be equal")
    void recordsWithSameValuesShouldBeEqual() {
        // Given
        SortMetrics metrics1 = SortMetrics.create(
            "Bubble Sort", 10, 500000000L, 50, 25, 0, "O(1)", "O(n²)"
        );
        SortMetrics metrics2 = SortMetrics.create(
            "Bubble Sort", 10, 500000000L, 50, 25, 0, "O(1)", "O(n²)"
        );
        
        // Then
        assertThat(metrics1).isEqualTo(metrics2);
        assertThat(metrics1.hashCode()).isEqualTo(metrics2.hashCode());
    }
}
