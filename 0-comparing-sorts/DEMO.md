# 🎬 Sort Algorithm Comparison Tool - Demo

This document shows various usage examples and their outputs.

## Example 1: Quick Comparison (5000 elements)

**Command:**
```bash
./gradlew run --args="-n 5000"
```

**What it does:**
- Sorts 5000 random elements with both Bubble Sort and Merge Sort
- Shows detailed metrics for each algorithm
- Displays comparison charts
- Runs benchmark table

**Key Output:**
- Merge Sort is ~15x faster
- Bubble Sort: 18.7 million operations
- Merge Sort: 117K operations
- Clear efficiency ratings

---

## Example 2: Generate HTML Report

**Command:**
```bash
./gradlew run --args="-n 5000 --html -o my-report.html"
```

**What it does:**
- Performs the same comparison
- Generates interactive HTML report with Chart.js
- Creates beautiful visualizations

**Output file:**
- `my-report.html` - Open in browser for interactive charts

---

## Example 3: Large Array (1 Million Elements)

**Command:**
```bash
./gradlew run --args="-n 1000000 --html --no-charts"
```

**What it does:**
- Automatically skips Bubble Sort (too slow)
- Tests Merge Sort on 1 million elements
- Skips ASCII charts for faster execution
- Generates HTML report

**Performance:**
- Completes in ~84ms
- Throughput: 11,873 elements/ms
- Memory used: 3.81 MB

---

## Example 4: Very Large Array (10 Million Elements)

**Command:**
```bash
./gradlew run --args="-n 10000000 --no-benchmark --no-charts --html"
```

**What it does:**
- Tests extreme scalability
- Merge Sort only
- Minimal console output
- HTML report for detailed analysis

**Expected:**
- Completes in under 1 second
- Memory used: ~38 MB
- Demonstrates O(n log n) efficiency

---

## Example 5: Custom Comparison

**Command:**
```bash
./gradlew run --args="-n 50000 --html"
```

**Best for:**
- Detailed performance analysis
- Comparing algorithm trade-offs
- Understanding space vs time complexity

---

## HTML Report Features

When you open the generated HTML report, you'll see:

1. **Metrics Cards**: Colored cards showing all metrics for each algorithm
2. **Time Chart**: Bar chart comparing execution time
3. **Operations Chart**: Stacked bar showing comparisons vs swaps
4. **Space Chart**: Auxiliary space usage comparison
5. **Throughput Chart**: Elements processed per millisecond
6. **Comparison Table**: Side-by-side metric comparison with highlights

---

## Performance Insights from Testing

| Array Size | Bubble Sort | Merge Sort | Speedup |
|-----------|-------------|------------|---------|
| 100       | 0.01 ms     | 0.006 ms   | 1.7x    |
| 1,000     | 0.42 ms     | 0.055 ms   | 7.6x    |
| 5,000     | 11 ms       | 0.73 ms    | 15x     |
| 10,000    | 40 ms       | 1.5 ms     | 26x     |
| 100,000   | Skipped     | 15 ms      | N/A     |
| 1,000,000 | Skipped     | 84 ms      | N/A     |

---

## Tips for Best Results

1. **Small Arrays (< 1000)**: Use default settings, both algorithms run fast
2. **Medium Arrays (1000-10000)**: Perfect for seeing algorithm differences
3. **Large Arrays (10000-100000)**: Use `--no-benchmark` to skip Bubble Sort
4. **Very Large Arrays (> 100000)**: Use `--no-charts --no-benchmark --html`
5. **HTML Reports**: Always useful for detailed analysis and sharing results

---

## Command-Line Quick Reference

```bash
# Help
./gradlew run --args="--help"

# Default (1000 elements)
./gradlew run

# Custom size
./gradlew run --args="-n 10000"

# With HTML
./gradlew run --args="-n 5000 --html"

# Large array (optimized)
./gradlew run --args="-n 1000000 --no-charts --html"

# Custom filename
./gradlew run --args="-n 2000 --html -o results.html"
```
