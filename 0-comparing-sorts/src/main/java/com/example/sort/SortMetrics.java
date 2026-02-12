package com.example.sort;

/**
 * Record to hold comprehensive sorting operation metrics.
 * Uses Java 21 records for immutable data carrier.
 * 
 * @param algorithmName the name of the sorting algorithm
 * @param elementCount the number of elements in the input array
 * @param timeInSeconds the total time taken in seconds
 * @param timeInMilliseconds the total time taken in milliseconds
 * @param timeInNanoseconds the total time taken in nanoseconds
 * @param comparisons the number of comparison operations performed
 * @param swaps the number of swap/write operations performed
 * @param auxiliarySpaceUsed the amount of auxiliary space used (in array elements)
 * @param spaceComplexity human-readable space complexity notation
 * @param memoryUsedBytes estimated memory used in bytes
 * @param throughput elements processed per millisecond
 * @param timeComplexityClass time complexity classification (e.g., "O(n log n)")
 */
public record SortMetrics(
    String algorithmName,
    int elementCount,
    double timeInSeconds,
    double timeInMilliseconds,
    long timeInNanoseconds,
    long comparisons,
    long swaps,
    int auxiliarySpaceUsed,
    String spaceComplexity,
    long memoryUsedBytes,
    double throughput,
    String timeComplexityClass
) {
    
    /**
     * Prints the metrics in a formatted way with all details.
     */
    public void printMetrics() {
        System.out.println("""
                
                ========================================
                Algorithm: %s
                ========================================
                Input Size: %,d elements
                
                Time Metrics:
                  - Nanoseconds:  %,d ns
                  - Milliseconds: %.3f ms
                  - Seconds:      %.6f s
                  - Throughput:   %,.0f elements/ms
                
                Operation Metrics:
                  - Comparisons: %,d
                  - Swaps/Writes: %,d
                  - Total Operations: %,d
                  - Operations per Element: %.2f
                
                Space Metrics:
                  - Auxiliary Space: %,d elements
                  - Memory Used: %,d bytes (%.2f MB)
                  - Space Complexity: %s
                
                Complexity Analysis:
                  - Time Complexity: %s
                  - Efficiency Rating: %s
                ========================================
                """.formatted(
                    algorithmName,
                    elementCount,
                    timeInNanoseconds,
                    timeInMilliseconds,
                    timeInSeconds,
                    throughput,
                    comparisons,
                    swaps,
                    comparisons + swaps,
                    elementCount > 0 ? (double)(comparisons + swaps) / elementCount : 0,
                    auxiliarySpaceUsed,
                    memoryUsedBytes,
                    memoryUsedBytes / (1024.0 * 1024.0),
                    spaceComplexity,
                    timeComplexityClass,
                    getEfficiencyRating()
                ));
    }
    
    /**
     * Calculates an efficiency rating based on operations per element.
     */
    private String getEfficiencyRating() {
        if (elementCount == 0) return "N/A";
        double opsPerElement = (double)(comparisons + swaps) / elementCount;
        
        if (opsPerElement < 10) return "⭐⭐⭐⭐⭐ Excellent";
        if (opsPerElement < 50) return "⭐⭐⭐⭐ Very Good";
        if (opsPerElement < 200) return "⭐⭐⭐ Good";
        if (opsPerElement < 1000) return "⭐⭐ Fair";
        return "⭐ Needs Optimization";
    }
    
    /**
     * Prints a compact single-line summary of the metrics.
     */
    public void printCompactMetrics() {
        System.out.printf("%-15s | %,8d elements | %8.3f ms | %,10d comparisons | %,10d swaps | %s%n",
            algorithmName, elementCount, timeInMilliseconds, comparisons, swaps, spaceComplexity);
    }
    
    /**
     * Creates a builder-style constructor with calculated fields.
     */
    public static SortMetrics create(
        String algorithmName,
        int elementCount,
        long timeInNanos,
        long comparisons,
        long swaps,
        int auxiliarySpaceUsed,
        String spaceComplexity,
        String timeComplexityClass
    ) {
        double timeInMillis = timeInNanos / 1_000_000.0;
        double timeInSeconds = timeInNanos / 1_000_000_000.0;
        
        // Calculate memory used (4 bytes per int for auxiliary space)
        long memoryUsedBytes = (long) auxiliarySpaceUsed * 4L;
        
        // Calculate throughput (elements per millisecond)
        double throughput = timeInMillis > 0 ? elementCount / timeInMillis : 0;
        
        return new SortMetrics(
            algorithmName,
            elementCount,
            timeInSeconds,
            timeInMillis,
            timeInNanos,
            comparisons,
            swaps,
            auxiliarySpaceUsed,
            spaceComplexity,
            memoryUsedBytes,
            throughput,
            timeComplexityClass
        );
    }
}
