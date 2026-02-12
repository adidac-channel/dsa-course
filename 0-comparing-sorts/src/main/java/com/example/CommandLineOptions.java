package com.example;

/**
 * Command-line options for the sort comparison tool.
 */
public class CommandLineOptions {
    private int arraySize = 1000;
    private boolean generateHtmlReport = false;
    private String outputFile = "output/sort-comparison-report.html";
    private boolean runBenchmark = true;
    private boolean showCharts = true;
    
    /**
     * Parses command-line arguments.
     * 
     * @param args command-line arguments
     * @return parsed options
     */
    public static CommandLineOptions parse(String[] args) {
        CommandLineOptions options = new CommandLineOptions();
        
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            
            switch (arg) {
                case "-h", "--help" -> {
                    printHelp();
                    System.exit(0);
                }
                case "-n", "--size" -> {
                    if (i + 1 < args.length) {
                        try {
                            options.arraySize = Integer.parseInt(args[++i]);
                            if (options.arraySize <= 0) {
                                System.err.println("Error: Array size must be positive");
                                System.exit(1);
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Error: Invalid array size: " + args[i]);
                            System.exit(1);
                        }
                    } else {
                        System.err.println("Error: --size requires a value");
                        System.exit(1);
                    }
                }
                case "--html" -> {
                    options.generateHtmlReport = true;
                }
                case "-o", "--output" -> {
                    if (i + 1 < args.length) {
                        String filename = args[++i];
                        // Ensure output goes to output directory
                        if (!filename.startsWith("output/") && !filename.startsWith("output\\")) {
                            options.outputFile = "output/" + filename;
                        } else {
                            options.outputFile = filename;
                        }
                    } else {
                        System.err.println("Error: --output requires a value");
                        System.exit(1);
                    }
                }
                case "--no-benchmark" -> {
                    options.runBenchmark = false;
                }
                case "--no-charts" -> {
                    options.showCharts = false;
                }
                default -> {
                    System.err.println("Unknown option: " + arg);
                    printHelp();
                    System.exit(1);
                }
            }
        }
        
        return options;
    }
    
    private static void printHelp() {
        System.out.println("""
                
                ╔════════════════════════════════════════════════════════════════╗
                ║        Sort Algorithm Comparison Tool - Help                  ║
                ╚════════════════════════════════════════════════════════════════╝
                
                Usage: java -jar sort-comparison.jar [OPTIONS]
                
                OPTIONS:
                  -n, --size <number>      Number of elements to sort (default: 1000)
                                          Supports: 100, 1000, 10000, 1000000, etc.
                                          
                  --html                   Generate HTML report with charts
                  
                  -o, --output <file>      Output HTML file name (default: output/sort-comparison-report.html)
                                          Files are automatically saved to output/ directory
                  
                  --no-benchmark           Skip benchmark table
                  
                  --no-charts              Skip ASCII chart generation
                  
                  -h, --help               Show this help message
                
                EXAMPLES:
                  # Sort 10,000 elements with console output
                  ./gradlew run --args="-n 10000"
                  
                  # Sort 1 million elements and generate HTML report
                  ./gradlew run --args="-n 1000000 --html"
                  
                  # Custom output file (automatically saved to output/ directory)
                  ./gradlew run --args="-n 5000 --html -o my-report.html"
                  
                  # Quick comparison without charts
                  ./gradlew run --args="-n 100000 --no-charts"
                
                PERFORMANCE NOTES:
                  - Arrays < 10,000: Fast with all algorithms
                  - Arrays 10,000-100,000: Bubble Sort becomes slow
                  - Arrays 100,000-1,000,000: Use Merge Sort only
                  - Arrays > 1,000,000: Merge Sort recommended, Bubble Sort not advised
                
                """);
    }
    
    // Getters
    public int getArraySize() { return arraySize; }
    public boolean shouldGenerateHtmlReport() { return generateHtmlReport; }
    public String getOutputFile() { return outputFile; }
    public boolean shouldRunBenchmark() { return runBenchmark; }
    public boolean shouldShowCharts() { return showCharts; }
}
