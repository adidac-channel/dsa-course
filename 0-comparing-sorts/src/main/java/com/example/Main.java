package com.example;

import com.example.report.HtmlReportGenerator;
import com.example.sort.SortAlgorithm;
import com.example.sort.SortAlgorithmFactory;
import com.example.sort.SortMetrics;
import com.example.visualization.ChartGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Main application class for Sort Algorithm Comparison.
 */
public class Main {
    public static void main(String[] args) {
        // Parse command-line arguments
        CommandLineOptions options = CommandLineOptions.parse(args);
        
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║   Sort Algorithm Comparison Tool with Metrics     ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.printf("\nConfiguration: Array size = %,d elements\n", options.getArraySize());
        
        // Check if array size is very large and warn about Bubble Sort
        if (options.getArraySize() > 100_000) {
            System.out.println("⚠️  Warning: Large array size detected. Bubble Sort will be very slow!");
            System.out.println("    Consider using --no-benchmark to skip Bubble Sort on large arrays.\n");
        }
        
        runComparison(options);
        
    }
    
    /**
     * Main comparison logic based on options.
     */
    private static void runComparison(CommandLineOptions options) {
        // Run main comparison with user-specified size
        System.out.println("\n┌─────────────────────────────────────────────────────");
        System.out.printf("│ Main Comparison: %,d elements\n", options.getArraySize());
        System.out.println("└─────────────────────────────────────────────────────");
        int[] mainArray = generateRandomArray(options.getArraySize());
        System.out.printf("Input: [%,d random elements between 0-9999]\n\n", options.getArraySize());
        
        List<SortMetrics> allMetrics = compareAlgorithms(mainArray, options.getArraySize() > 100_000);
        
        
        // Optionally run benchmark
        if (options.shouldRunBenchmark() && options.getArraySize() <= 10_000) {
            System.out.println("\n┌─────────────────────────────────────────────────────");
            System.out.println("│ Performance Benchmark Table");
            System.out.println("└─────────────────────────────────────────────────────");
            performanceBenchmark();
        }
        
        // Optionally show charts
        if (options.shouldShowCharts()) {
            System.out.println("\n┌─────────────────────────────────────────────────────");
            System.out.println("│ Visual Performance Charts");
            System.out.println("└─────────────────────────────────────────────────────");
            generateVisualCharts(allMetrics, options.getArraySize());
        }
        
        // Optionally generate HTML report
        if (options.shouldGenerateHtmlReport()) {
            System.out.println("\n┌─────────────────────────────────────────────────────");
            System.out.println("│ Generating HTML Report");
            System.out.println("└─────────────────────────────────────────────────────");
            generateHtmlReport(allMetrics, options);
        }
    }
    
    /**
     * Compares both algorithms on the same input and prints detailed metrics.
     */
    private static List<SortMetrics> compareAlgorithms(int[] originalArray, boolean skipBubbleSort) {
        List<SortMetrics> metrics = new ArrayList<>();
        
        // Test Bubble Sort (skip for very large arrays)
        if (!skipBubbleSort) {
            int[] array1 = originalArray.clone();
            SortAlgorithm bubbleSort = SortAlgorithmFactory.createAlgorithm("Bubble Sort");
            System.out.println("Sorting with Bubble Sort...");
            SortMetrics bubbleMetrics = bubbleSort.sort(array1);
            bubbleMetrics.printMetrics();
            metrics.add(bubbleMetrics);
        } else {
            System.out.println("⏭️  Skipping Bubble Sort (array too large)\n");
        }
        
        // Test Merge Sort
        int[] array2 = originalArray.clone();
        SortAlgorithm mergeSort = SortAlgorithmFactory.createAlgorithm("Merge Sort");
        System.out.println("Sorting with Merge Sort...");
        SortMetrics mergeMetrics = mergeSort.sort(array2);
        mergeMetrics.printMetrics();
        metrics.add(mergeMetrics);
        
        // Print comparison if both algorithms ran
        if (metrics.size() == 2) {
            printComparison(metrics.get(0), metrics.get(1));
        }
        
        return metrics;
    }
    
    /**
     * Prints a comparison between two sorting metrics.
     */
    private static void printComparison(SortMetrics metrics1, SortMetrics metrics2) {
        System.out.println("📊 Quick Comparison:");
        double speedup = metrics1.timeInMilliseconds() / metrics2.timeInMilliseconds();
        String faster = speedup > 1 ? metrics2.algorithmName() : metrics1.algorithmName();
        double factor = Math.max(speedup, 1.0 / speedup);
        
        System.out.printf("   ⚡ Speed: %s is %.2fx faster%n", faster, factor);
        
        long opDiff = Math.abs(metrics1.comparisons() + metrics1.swaps() - 
                               metrics2.comparisons() - metrics2.swaps());
        System.out.printf("   🔢 Total operations difference: %,d%n", opDiff);
        
        int spaceDiff = Math.abs(metrics1.auxiliarySpaceUsed() - metrics2.auxiliarySpaceUsed());
        System.out.printf("   💾 Auxiliary space difference: %,d elements%n", spaceDiff);
        System.out.println();
    }
    
    /**
     * Runs a performance benchmark with different array sizes.
     */
    private static void performanceBenchmark() {
        System.out.println("\nBenchmarking different array sizes...\n");
        System.out.println("╔═════════════════╦════════════╦═══════════╦══════════════╦══════════════╦════════════╗");
        System.out.println("║   Algorithm     ║    Size    ║  Time(ms) ║  Comparisons ║    Swaps     ║   Space    ║");
        System.out.println("╠═════════════════╬════════════╬═══════════╬══════════════╬══════════════╬════════════╣");
        
        int[] sizes = {100, 500, 1000, 2000, 5000};
        
        for (int size : sizes) {
            int[] array1 = generateRandomArray(size);
            int[] array2 = array1.clone();
            
            SortMetrics bubbleMetrics = SortAlgorithmFactory.createAlgorithm("Bubble Sort").sort(array1);
            SortMetrics mergeMetrics = SortAlgorithmFactory.createAlgorithm("Merge Sort").sort(array2);
            
            System.out.printf("║ %-15s ║ %,10d ║ %9.3f ║ %,12d ║ %,12d ║ %-10s ║%n",
                bubbleMetrics.algorithmName(), bubbleMetrics.elementCount(),
                bubbleMetrics.timeInMilliseconds(), bubbleMetrics.comparisons(),
                bubbleMetrics.swaps(), bubbleMetrics.spaceComplexity());
            
            System.out.printf("║ %-15s ║ %,10d ║ %9.3f ║ %,12d ║ %,12d ║ %-10s ║%n",
                mergeMetrics.algorithmName(), mergeMetrics.elementCount(),
                mergeMetrics.timeInMilliseconds(), mergeMetrics.comparisons(),
                mergeMetrics.swaps(), mergeMetrics.spaceComplexity());
            
            if (size != sizes[sizes.length - 1]) {
                System.out.println("╟─────────────────╫────────────╫───────────╫──────────────╫──────────────╫────────────╢");
            }
        }
        
        System.out.println("╚═════════════════╩════════════╩═══════════╩══════════════╩══════════════╩════════════╝");
    }
    
    /**
     * Generates visual charts comparing algorithm performance.
     */
    private static void generateVisualCharts(List<SortMetrics> metrics, int arraySize) {
        System.out.println("\nGenerating visual comparisons...\n");
        
        // Generate various charts with provided metrics
        if (!metrics.isEmpty()) {
            System.out.println(ChartGenerator.generateTimeComparisonChart(metrics));
            System.out.println(ChartGenerator.generateOperationsComparisonChart(metrics));
            System.out.println(ChartGenerator.generateOperationsBreakdownChart(metrics));
            System.out.println(ChartGenerator.generateSpaceComparisonChart(metrics));
        }
        
        // Only run scalability if array size is reasonable
        if (arraySize <= 5000) {
            System.out.println("\n" + "=".repeat(73));
            System.out.println("                    SCALABILITY ANALYSIS");
            System.out.println("=".repeat(73));
            
            int[] sizes = arraySize <= 1000 ? new int[]{100, 500, 1000} : new int[]{1000, 2000, 5000};
            List<List<SortMetrics>> allMetrics = new ArrayList<>();
            
            for (int size : sizes) {
                List<SortMetrics> sizeMetrics = new ArrayList<>();
                int[] arr1 = generateRandomArray(size);
                int[] arr2 = arr1.clone();
                
                if (size <= 10000) {
                    sizeMetrics.add(SortAlgorithmFactory.createAlgorithm("Bubble Sort").sort(arr1));
                }
                sizeMetrics.add(SortAlgorithmFactory.createAlgorithm("Merge Sort").sort(arr2));
                
                allMetrics.add(sizeMetrics);
            }
            
            System.out.println(ChartGenerator.generateScalabilityChart(allMetrics, sizes));
        }
    }
    
    /**
     * Generates HTML report with charts.
     */
    private static void generateHtmlReport(List<SortMetrics> metrics, CommandLineOptions options) {
        try {
            HtmlReportGenerator.generate(metrics, options.getOutputFile(), options.getArraySize());
            System.out.println("✅ HTML report generated: " + options.getOutputFile());
            System.out.println("   Open this file in your browser to view interactive charts.");
        } catch (Exception e) {
            System.err.println("❌ Error generating HTML report: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Generates a random array of specified size.
     */
    private static int[] generateRandomArray(int size) {
        Random random = new Random(42); // Fixed seed for reproducibility
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(10000);
        }
        return array;
    }
}
