package com.example.visualization;

import com.example.sort.SortMetrics;

import java.util.List;

/**
 * Generates ASCII charts and visualizations for sorting algorithm performance comparison.
 */
public class ChartGenerator {
    
    private static final int DEFAULT_CHART_WIDTH = 60;
    private static final String BAR_CHAR = "█";
    private static final String PARTIAL_BAR_CHARS = " ▏▎▍▌▋▊▉█";
    
    /**
     * Generates a horizontal bar chart comparing execution times.
     * 
     * @param metrics list of metrics to compare
     * @return ASCII bar chart as a string
     */
    public static String generateTimeComparisonChart(List<SortMetrics> metrics) {
        if (metrics == null || metrics.isEmpty()) {
            return "No data to display";
        }
        
        StringBuilder chart = new StringBuilder();
        chart.append("\n┌─────────────────────────────────────────────────────────────────────┐\n");
        chart.append("│              Time Comparison (milliseconds)                         │\n");
        chart.append("└─────────────────────────────────────────────────────────────────────┘\n");
        
        // Find max time for scaling
        double maxTime = metrics.stream()
            .mapToDouble(SortMetrics::timeInMilliseconds)
            .max()
            .orElse(1.0);
        
        for (SortMetrics metric : metrics) {
            chart.append(generateBarLine(
                metric.algorithmName(),
                metric.timeInMilliseconds(),
                maxTime,
                "%.3f ms"
            ));
        }
        
        return chart.toString();
    }
    
    /**
     * Generates a horizontal bar chart comparing total operations.
     * 
     * @param metrics list of metrics to compare
     * @return ASCII bar chart as a string
     */
    public static String generateOperationsComparisonChart(List<SortMetrics> metrics) {
        if (metrics == null || metrics.isEmpty()) {
            return "No data to display";
        }
        
        StringBuilder chart = new StringBuilder();
        chart.append("\n┌─────────────────────────────────────────────────────────────────────┐\n");
        chart.append("│           Total Operations Comparison                               │\n");
        chart.append("└─────────────────────────────────────────────────────────────────────┘\n");
        
        // Find max operations for scaling
        long maxOps = metrics.stream()
            .mapToLong(m -> m.comparisons() + m.swaps())
            .max()
            .orElse(1L);
        
        for (SortMetrics metric : metrics) {
            long totalOps = metric.comparisons() + metric.swaps();
            chart.append(generateBarLine(
                metric.algorithmName(),
                totalOps,
                maxOps,
                "%,d ops"
            ));
        }
        
        return chart.toString();
    }
    
    /**
     * Generates a comparison chart showing comparisons vs swaps breakdown.
     * 
     * @param metrics list of metrics to compare
     * @return ASCII stacked bar chart as a string
     */
    public static String generateOperationsBreakdownChart(List<SortMetrics> metrics) {
        if (metrics == null || metrics.isEmpty()) {
            return "No data to display";
        }
        
        StringBuilder chart = new StringBuilder();
        chart.append("\n┌─────────────────────────────────────────────────────────────────────┐\n");
        chart.append("│         Operations Breakdown (Comparisons vs Swaps)                 │\n");
        chart.append("└─────────────────────────────────────────────────────────────────────┘\n");
        chart.append("Legend: [🔵 Comparisons] [🟢 Swaps]\n\n");
        
        // Find max total operations for scaling
        long maxOps = metrics.stream()
            .mapToLong(m -> m.comparisons() + m.swaps())
            .max()
            .orElse(1L);
        
        for (SortMetrics metric : metrics) {
            chart.append(generateStackedBarLine(metric, maxOps));
        }
        
        return chart.toString();
    }
    
    /**
     * Generates a multi-size performance chart showing algorithm performance across different array sizes.
     * 
     * @param allMetrics list of all metrics grouped by size
     * @param sizes array sizes used in the benchmark
     * @return ASCII line chart as a string
     */
    public static String generateScalabilityChart(List<List<SortMetrics>> allMetrics, int[] sizes) {
        if (allMetrics == null || allMetrics.isEmpty()) {
            return "No data to display";
        }
        
        StringBuilder chart = new StringBuilder();
        chart.append("\n┌─────────────────────────────────────────────────────────────────────┐\n");
        chart.append("│              Algorithm Scalability Chart                            │\n");
        chart.append("│                  (Time vs Array Size)                               │\n");
        chart.append("└─────────────────────────────────────────────────────────────────────┘\n\n");
        
        // Create a simple ASCII line chart
        int chartHeight = 15;
        int chartWidth = 60;
        
        // Find max time across all metrics
        double maxTime = allMetrics.stream()
            .flatMap(List::stream)
            .mapToDouble(SortMetrics::timeInMilliseconds)
            .max()
            .orElse(1.0);
        
        // Group metrics by algorithm
        var algorithmMetrics = allMetrics.stream()
            .flatMap(List::stream)
            .collect(java.util.stream.Collectors.groupingBy(SortMetrics::algorithmName));
        
        // Print chart for each algorithm
        algorithmMetrics.forEach((algName, algMetrics) -> {
            chart.append(String.format("  %s:\n", algName));
            for (int i = 0; i < algMetrics.size(); i++) {
                SortMetrics m = algMetrics.get(i);
                chart.append(String.format("    Size %,5d: ", m.elementCount()));
                int barLength = (int) ((m.timeInMilliseconds() / maxTime) * 40);
                chart.append(BAR_CHAR.repeat(Math.max(0, barLength)));
                chart.append(String.format(" %.3f ms\n", m.timeInMilliseconds()));
            }
            chart.append("\n");
        });
        
        return chart.toString();
    }
    
    /**
     * Generates a space complexity visualization.
     * 
     * @param metrics list of metrics to compare
     * @return ASCII chart showing space usage
     */
    public static String generateSpaceComparisonChart(List<SortMetrics> metrics) {
        if (metrics == null || metrics.isEmpty()) {
            return "No data to display";
        }
        
        StringBuilder chart = new StringBuilder();
        chart.append("\n┌─────────────────────────────────────────────────────────────────────┐\n");
        chart.append("│              Auxiliary Space Usage Comparison                       │\n");
        chart.append("└─────────────────────────────────────────────────────────────────────┘\n");
        
        // Find max space for scaling
        int maxSpace = metrics.stream()
            .mapToInt(SortMetrics::auxiliarySpaceUsed)
            .max()
            .orElse(1);
        
        // If all algorithms use no auxiliary space, show that
        if (maxSpace == 0) {
            chart.append("\nAll algorithms use O(1) constant space (no auxiliary arrays).\n");
            return chart.toString();
        }
        
        for (SortMetrics metric : metrics) {
            chart.append(generateBarLine(
                metric.algorithmName() + " [" + metric.spaceComplexity() + "]",
                metric.auxiliarySpaceUsed(),
                maxSpace,
                "%,d elements"
            ));
        }
        
        return chart.toString();
    }
    
    /**
     * Generates a single bar line for a chart (double value).
     */
    private static String generateBarLine(String label, double value, double maxValue, String valueFormat) {
        int barWidth = DEFAULT_CHART_WIDTH - 25; // Reserve space for label and value
        double ratio = maxValue > 0 ? value / maxValue : 0;
        int filledWidth = (int) (ratio * barWidth);
        
        String bar = BAR_CHAR.repeat(Math.max(0, filledWidth));
        String formattedValue = String.format(valueFormat, value);
        
        return String.format("%-20s │%-60s│ %s\n", 
            truncate(label, 20), 
            bar, 
            formattedValue);
    }
    
    /**
     * Generates a single bar line for a chart (long value).
     */
    private static String generateBarLine(String label, long value, long maxValue, String valueFormat) {
        int barWidth = DEFAULT_CHART_WIDTH - 25; // Reserve space for label and value
        double ratio = maxValue > 0 ? (double) value / maxValue : 0;
        int filledWidth = (int) (ratio * barWidth);
        
        String bar = BAR_CHAR.repeat(Math.max(0, filledWidth));
        String formattedValue = String.format(valueFormat, value);
        
        return String.format("%-20s │%-60s│ %s\n", 
            truncate(label, 20), 
            bar, 
            formattedValue);
    }
    
    /**
     * Generates a single bar line for a chart (int value).
     */
    private static String generateBarLine(String label, int value, int maxValue, String valueFormat) {
        int barWidth = DEFAULT_CHART_WIDTH - 25; // Reserve space for label and value
        double ratio = maxValue > 0 ? (double) value / maxValue : 0;
        int filledWidth = (int) (ratio * barWidth);
        
        String bar = BAR_CHAR.repeat(Math.max(0, filledWidth));
        String formattedValue = String.format(valueFormat, value);
        
        return String.format("%-20s │%-60s│ %s\n", 
            truncate(label, 20), 
            bar, 
            formattedValue);
    }
    
    /**
     * Generates a stacked bar line showing comparisons and swaps.
     */
    private static String generateStackedBarLine(SortMetrics metric, long maxOps) {
        int barWidth = 50;
        long totalOps = metric.comparisons() + metric.swaps();
        
        if (maxOps == 0) maxOps = 1;
        
        int totalBarWidth = (int) ((double) totalOps / maxOps * barWidth);
        int comparisonsWidth = (int) ((double) metric.comparisons() / maxOps * barWidth);
        int swapsWidth = totalBarWidth - comparisonsWidth;
        
        String comparisonsBar = "▓".repeat(Math.max(0, comparisonsWidth));
        String swapsBar = "▒".repeat(Math.max(0, swapsWidth));
        
        return String.format("%-15s │%s%s %,d total (C:%,d S:%,d)\n",
            truncate(metric.algorithmName(), 15),
            comparisonsBar,
            swapsBar,
            totalOps,
            metric.comparisons(),
            metric.swaps());
    }
    
    /**
     * Truncates a string to a maximum length.
     */
    private static String truncate(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
}
