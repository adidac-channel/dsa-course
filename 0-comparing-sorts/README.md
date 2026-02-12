# 🚀 Sort Algorithm Comparison Tool

A comprehensive Java 21 application for comparing sorting algorithm performance with advanced metrics, visual charts, and HTML reports.

## ✨ Features

- **Multiple Sorting Algorithms**: Bubble Sort, Merge Sort (easily extensible)
- **Advanced Performance Metrics**: Time, operations, space usage, throughput, efficiency ratings
- **ASCII Chart Visualizations**: Bar charts, stacked charts, scalability analysis
- **HTML Report Generation**: Interactive charts using Chart.js
- **Command-Line Interface**: Flexible options for array size, output format, and features
- **Large Array Support**: Tested with up to 10 million elements
- **Smart Optimizations**: Automatic algorithm selection based on array size

## 📋 Prerequisites

- Java 21 or later
- Gradle 8.x (or use the included Gradle Wrapper)

### Using SDKMAN! (Recommended)

This project includes a `.sdkmanrc` file for automatic Java version management with [SDKMAN!](https://sdkman.io/).

**Setup:**
```bash
# Install SDKMAN! if you haven't already
curl -s "https://get.sdkman.io" | bash

# Install the project's Java and Gradle versions
sdk env install

# Activate them (or enable auto-activation)
sdk env
```

## 🚀 Quick Start

### Build the Project

```bash
./gradlew build
```

### Run with Default Settings (1000 elements)

```bash
./gradlew run
```

### Run with Custom Array Size

```bash
./gradlew run --args="-n 10000"
```

### Generate HTML Report

```bash
./gradlew run --args="-n 5000 --html"
```

Open `output/sort-comparison-report.html` in your browser to view interactive charts.

## 📖 Usage

### Command-Line Options

```
Usage: ./gradlew run --args="[OPTIONS]"

OPTIONS:
  -n, --size <number>      Number of elements to sort (default: 1000)
                          Supports: 100, 1000, 10000, 1000000, etc.
                          
  --html                   Generate HTML report with charts
  
  -o, --output <file>      Output HTML file name (default: output/sort-comparison-report.html)
                          Files are automatically saved to output/ directory
  
  --no-benchmark           Skip benchmark table
  
  --no-charts              Skip ASCII chart generation
  
  -h, --help               Show this help message
```

### Examples

```bash
# Sort 10,000 elements with console output
./gradlew run --args="-n 10000"

# Sort 1 million elements and generate HTML report
./gradlew run --args="-n 1000000 --html"

# Custom output file
./gradlew run --args="-n 5000 --html -o my-report.html"

# Quick comparison without charts (faster for large arrays)
./gradlew run --args="-n 100000 --no-charts"

# Very large array (Merge Sort only)
./gradlew run --args="-n 10000000 --no-benchmark --html"
```

## 📊 Metrics Tracked

### Time Metrics
- Nanoseconds, milliseconds, seconds
- Throughput (elements processed per millisecond)

### Operation Metrics
- Comparisons
- Swaps/Writes
- Total operations
- Operations per element

### Space Metrics
- Auxiliary space used (elements)
- Memory used (bytes and MB)
- Space complexity notation (O(1), O(n))

### Complexity Analysis
- Time complexity (O(n²), O(n log n))
- Efficiency rating (⭐ to ⭐⭐⭐⭐⭐)

## 📈 Sample Output

```
========================================
Algorithm: Merge Sort
========================================
Input Size: 5,000 elements

Time Metrics:
  - Nanoseconds:  734,750 ns
  - Milliseconds: 0.735 ms
  - Seconds:      0.000735 s
  - Throughput:   6,805 elements/ms

Operation Metrics:
  - Comparisons: 55,268
  - Swaps/Writes: 61,808
  - Total Operations: 117,076
  - Operations per Element: 23.42

Space Metrics:
  - Auxiliary Space: 5,000 elements
  - Memory Used: 20,000 bytes (0.02 MB)
  - Space Complexity: O(n)

Complexity Analysis:
  - Time Complexity: O(n log n)
  - Efficiency Rating: ⭐⭐⭐⭐ Very Good
========================================
```

## 🏗️ Project Structure

```
.
├── src/
│   ├── main/
│   │   └── java/com/example/
│   │       ├── Main.java
│   │       ├── CommandLineOptions.java
│   │       ├── sort/
│   │       │   ├── SortAlgorithm.java
│   │       │   ├── SortMetrics.java
│   │       │   ├── SortAlgorithmFactory.java
│   │       │   └── algorithms/
│   │       │       ├── BubbleSort.java
│   │       │       └── MergeSort.java
│   │       ├── visualization/
│   │       │   └── ChartGenerator.java
│   │       └── report/
│   │           └── HtmlReportGenerator.java
│   └── test/
│       └── java/com/example/
│           ├── sort/
│           └── visualization/
├── build.gradle
├── settings.gradle
├── AGENTS.md
└── README.md
```

## 🧪 Testing

Run all tests:
```bash
./gradlew test
```

View test report:
```bash
open build/reports/tests/test/index.html
```

## 🎯 Performance Notes

- **Arrays < 10,000**: Both algorithms perform well
- **Arrays 10,000-100,000**: Bubble Sort becomes noticeably slow
- **Arrays 100,000-1,000,000**: Bubble Sort is automatically skipped, use Merge Sort
- **Arrays > 1,000,000**: Merge Sort recommended, excellent performance

### Benchmark Results (5000 elements)
- Merge Sort: **~15x faster** than Bubble Sort
- Merge Sort: **~160x fewer operations** than Bubble Sort
- Trade-off: Merge Sort uses O(n) auxiliary space vs Bubble Sort's O(1)

## 🛠️ Technologies Used

- **Java 21**: Latest LTS with records, pattern matching, text blocks
- **Gradle 8.5**: Build automation
- **JUnit 5**: Testing framework
- **AssertJ**: Fluent assertions
- **Chart.js**: Interactive HTML charts
- **SDKMAN**: Java version management

## 📝 Documentation

See [AGENTS.md](AGENTS.md) for:
- Project history and evolution
- Architectural decisions
- Best practices
- Performance insights

## 🤝 Contributing

To add a new sorting algorithm:

1. Implement the `SortAlgorithm` interface
2. Add to `SortAlgorithmFactory`
3. Write comprehensive tests
4. Update documentation

## 📄 License

This project is for educational purposes.
