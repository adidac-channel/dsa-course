# AGENTS.md - Project Memory & Best Practices

## Project Overview
- **Language**: Java 21
- **Build Tool**: Gradle
- **Created**: 2026-02-12

## Best Practices

### Code Organization
- Follow standard Maven/Gradle directory structure: `src/main/java`, `src/test/java`
- Use meaningful package names following reverse domain notation
- Keep classes focused and single-responsibility
- Use Java 21 features appropriately (records, pattern matching, virtual threads, etc.)

### Java 21 Specific Best Practices
- **Records**: Use for immutable data carriers
- **Pattern Matching**: Leverage enhanced pattern matching for instanceof and switch
- **Sealed Classes**: Use for restricted inheritance hierarchies
- **Text Blocks**: Use for multi-line strings
- **Virtual Threads**: Consider for high-concurrency scenarios (Project Loom)
- **Sequenced Collections**: Use new collection methods for first/last element access

### Gradle Best Practices
- Use Gradle Wrapper (`gradlew`) for consistent builds across environments
- Keep `build.gradle` clean and well-organized
- Use dependency version catalogs for larger projects
- Leverage Gradle's incremental compilation
- Use appropriate plugins (java, application, etc.)

### Environment Management
- Use SDKMAN! for Java version management (`.sdkmanrc` included)
- Run `sdk env install` to install required Java version
- Run `sdk env` to activate the project's Java version
- Consider enabling auto-activation for seamless version switching

### Testing
- Write unit tests using JUnit 5
- Aim for meaningful test coverage (not just high percentages)
- Use AssertJ or similar for fluent assertions
- Test edge cases and error conditions

### Code Quality
- Follow Java naming conventions (camelCase for methods/variables, PascalCase for classes)
- Use meaningful variable and method names
- Add JavaDoc for public APIs
- Keep methods short and focused
- Use Optional appropriately for nullable values

### Git Practices
- Write clear, descriptive commit messages
- Commit related changes together
- Keep .gitignore updated for Java/Gradle projects

### Documentation
- Update this AGENTS.md file when making architectural decisions
- Document "why" not just "what" in complex code sections
- Keep README.md updated with setup and run instructions

## Project History

### 2026-02-12: Initial Setup
- Created Java 21 project structure
- Configured Gradle build system with Gradle Wrapper (8.5)
- Initialized AGENTS.md with best practices
- Added SDKMAN configuration (.sdkmanrc) for Java version management
- Set up standard directory structure with sample code and tests

### 2026-02-12: Sort Algorithm Comparison Tool
- Implemented Factory Pattern for sort algorithm selection
- Created `SortAlgorithm` interface for algorithm contract
- Implemented Bubble Sort algorithm with in-place sorting
- Implemented Merge Sort algorithm (O(n log n) efficiency)
- Created `SortMetrics` record (Java 21 feature) for comprehensive performance tracking
- Added comprehensive unit tests (100% coverage for implemented features)
- Enhanced metrics tracking with detailed operation counts and space complexity
- Advanced metrics tracked: 
  - Time: nanoseconds, milliseconds, seconds
  - Operations: comparisons, swaps/writes, total operations, operations per element
  - Space: auxiliary space used, memory bytes, space complexity notation
  - Performance: throughput (elements/ms), efficiency rating
  - Complexity: time complexity class (O(n²), O(n log n))
- Demonstrated with multiple test cases (small arrays, large arrays, best/worst cases)
- Performance comparison shows Merge Sort is ~17x faster on 5000 elements
- Added performance benchmark table comparing algorithms across different array sizes
- Implemented ASCII chart/graph visualization system:
  - Time comparison bar charts
  - Operations comparison bar charts
  - Operations breakdown (comparisons vs swaps) with stacked bars
  - Space usage comparison charts
  - Scalability charts showing performance across different array sizes
  - Performance ratio analysis table
- Command-line interface with argument parsing:
  - `-n, --size <number>`: Specify array size (supports 100 to 10+ million)
  - `--html`: Generate HTML report with interactive charts
  - `-o, --output <file>`: Custom output filename (saved to output/ directory)
  - `--no-benchmark`, `--no-charts`: Skip specific outputs
  - `-h, --help`: Display help information
  - All HTML reports automatically saved to `output/` directory (git-ignored)
- HTML report generation with Chart.js:
  - Interactive bar charts for time, operations, space, throughput
  - Stacked bar charts for operations breakdown
  - Responsive design with gradient styling
  - Detailed metrics cards with complexity badges
  - Comparison tables with highlights
  - Bug fix: Corrected data mapping to JavaScript arrays for chart rendering
- Large array support (tested up to 10 million elements):
  - Automatic Bubble Sort skip for arrays > 100,000 elements
  - Memory-efficient handling of large datasets
  - Warning system for performance considerations

## Architectural Decisions

### Sort Algorithm Comparison Tool (2026-02-12)
**Decision**: Implemented a flexible sort algorithm comparison framework using the Factory Pattern.

**Structure**:
- `SortAlgorithm` interface - Contract for all sorting algorithms
- `SortMetrics` record - Immutable data carrier for performance metrics (Java 21 record)
- `SortAlgorithmFactory` - Factory pattern implementation for algorithm selection
- `algorithms/` package - Contains concrete algorithm implementations
- `visualization/` package - ASCII chart generation for visual performance comparison

**Design Choices**:
1. **In-place sorting**: All algorithms modify the array in-place (result stored in original array)
2. **Factory Pattern**: Allows runtime selection of algorithms via enum or string name
3. **Comprehensive metrics tracking**: Tracks time (3 granularities), operations (comparisons, swaps), and space usage
4. **Extensibility**: Easy to add new algorithms by implementing `SortAlgorithm` interface
5. **Performance analysis**: Built-in comparison tools and benchmark tables for algorithm evaluation

**Key Insights from Metrics**:
- Bubble Sort performs better on small arrays (< 10 elements) due to lower overhead
- Bubble Sort excels on already-sorted data (O(n) best case) with early termination
- Merge Sort dominates on larger arrays: ~17x faster on 5000 elements
- Trade-off: Merge Sort uses O(n) auxiliary space vs Bubble Sort's O(1)
- Operation counts show Bubble Sort performs ~160x more operations on 5000 elements

**Implemented Algorithms**:
- Bubble Sort - O(n²) time complexity, O(1) space complexity
- Merge Sort - O(n log n) time complexity, O(n) space complexity

## Known Issues
_(To be documented as issues are discovered)_

## TODO
_(Track ongoing and future work items)_
