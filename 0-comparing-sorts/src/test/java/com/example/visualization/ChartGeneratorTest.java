package com.example.visualization;

import com.example.sort.SortMetrics;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for ChartGenerator.
 */
class ChartGeneratorTest {
    
    @Test
    @DisplayName("Should generate time comparison chart")
    void shouldGenerateTimeComparisonChart() {
        // Given
        List<SortMetrics> metrics = createTestMetrics();
        
        // When
        String chart = ChartGenerator.generateTimeComparisonChart(metrics);
        
        // Then
        assertThat(chart).isNotNull();
        assertThat(chart).contains("Time Comparison");
        assertThat(chart).contains("Bubble Sort");
        assertThat(chart).contains("Merge Sort");
        assertThat(chart).contains("ms");
    }
    
    @Test
    @DisplayName("Should generate operations comparison chart")
    void shouldGenerateOperationsComparisonChart() {
        // Given
        List<SortMetrics> metrics = createTestMetrics();
        
        // When
        String chart = ChartGenerator.generateOperationsComparisonChart(metrics);
        
        // Then
        assertThat(chart).isNotNull();
        assertThat(chart).contains("Total Operations Comparison");
        assertThat(chart).contains("Bubble Sort");
        assertThat(chart).contains("Merge Sort");
        assertThat(chart).contains("ops");
    }
    
    @Test
    @DisplayName("Should generate operations breakdown chart")
    void shouldGenerateOperationsBreakdownChart() {
        // Given
        List<SortMetrics> metrics = createTestMetrics();
        
        // When
        String chart = ChartGenerator.generateOperationsBreakdownChart(metrics);
        
        // Then
        assertThat(chart).isNotNull();
        assertThat(chart).contains("Operations Breakdown");
        assertThat(chart).contains("Comparisons");
        assertThat(chart).contains("Swaps");
        assertThat(chart).contains("Bubble Sort");
        assertThat(chart).contains("Merge Sort");
    }
    
    @Test
    @DisplayName("Should generate space comparison chart")
    void shouldGenerateSpaceComparisonChart() {
        // Given
        List<SortMetrics> metrics = createTestMetrics();
        
        // When
        String chart = ChartGenerator.generateSpaceComparisonChart(metrics);
        
        // Then
        assertThat(chart).isNotNull();
        assertThat(chart).contains("Auxiliary Space Usage");
    }
    
    @Test
    @DisplayName("Should handle empty metrics list")
    void shouldHandleEmptyMetricsList() {
        // Given
        List<SortMetrics> metrics = new ArrayList<>();
        
        // When
        String chart = ChartGenerator.generateTimeComparisonChart(metrics);
        
        // Then
        assertThat(chart).contains("No data to display");
    }
    
    @Test
    @DisplayName("Should handle null metrics list")
    void shouldHandleNullMetricsList() {
        // When
        String chart = ChartGenerator.generateTimeComparisonChart(null);
        
        // Then
        assertThat(chart).contains("No data to display");
    }
    
    @Test
    @DisplayName("Should generate scalability chart")
    void shouldGenerateScalabilityChart() {
        // Given
        List<List<SortMetrics>> allMetrics = new ArrayList<>();
        allMetrics.add(createTestMetrics());
        allMetrics.add(createTestMetrics());
        int[] sizes = {100, 500};
        
        // When
        String chart = ChartGenerator.generateScalabilityChart(allMetrics, sizes);
        
        // Then
        assertThat(chart).isNotNull();
        assertThat(chart).contains("Scalability Chart");
        assertThat(chart).contains("Bubble Sort");
        assertThat(chart).contains("Merge Sort");
    }
    
    @Test
    @DisplayName("Should show message when all algorithms use O(1) space")
    void shouldShowMessageForConstantSpace() {
        // Given
        List<SortMetrics> metrics = List.of(
            SortMetrics.create("Bubble Sort", 100, 500000L, 1000, 500, 0, "O(1)", "O(n²)"),
            SortMetrics.create("Selection Sort", 100, 600000L, 1000, 500, 0, "O(1)", "O(n²)")
        );
        
        // When
        String chart = ChartGenerator.generateSpaceComparisonChart(metrics);
        
        // Then
        assertThat(chart).contains("O(1) constant space");
    }
    
    /**
     * Helper method to create test metrics.
     */
    private List<SortMetrics> createTestMetrics() {
        List<SortMetrics> metrics = new ArrayList<>();
        
        metrics.add(SortMetrics.create(
            "Bubble Sort", 100, 500000L, 5000, 2500, 0, "O(1)", "O(n²)"
        ));
        
        metrics.add(SortMetrics.create(
            "Merge Sort", 100, 50000L, 500, 300, 100, "O(n)", "O(n log n)"
        ));
        
        return metrics;
    }
}
