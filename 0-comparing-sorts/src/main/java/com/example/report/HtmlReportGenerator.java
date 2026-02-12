package com.example.report;

import com.example.sort.SortMetrics;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Generates HTML reports with interactive charts using Chart.js.
 */
public class HtmlReportGenerator {
    
    /**
     * Generates an HTML report with interactive charts.
     * 
     * @param metrics list of sorting metrics
     * @param outputFile output HTML file path
     * @param arraySize size of the array sorted
     * @throws IOException if file writing fails
     */
    public static void generate(List<SortMetrics> metrics, String outputFile, int arraySize) throws IOException {
        String html = generateHtml(metrics, arraySize);
        
        // Create output directory if it doesn't exist
        java.nio.file.Path outputPath = java.nio.file.Paths.get(outputFile);
        java.nio.file.Path parentDir = outputPath.getParent();
        if (parentDir != null && !java.nio.file.Files.exists(parentDir)) {
            java.nio.file.Files.createDirectories(parentDir);
        }
        
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(html);
        }
    }
    
    private static String generateHtml(List<SortMetrics> metrics, int arraySize) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        StringBuilder metricsData = new StringBuilder();
        StringBuilder algorithmNames = new StringBuilder();
        StringBuilder timeData = new StringBuilder();
        StringBuilder comparisonsData = new StringBuilder();
        StringBuilder swapsData = new StringBuilder();
        StringBuilder spaceData = new StringBuilder();
        StringBuilder throughputData = new StringBuilder();
        
        for (int i = 0; i < metrics.size(); i++) {
            SortMetrics m = metrics.get(i);
            if (i > 0) {
                algorithmNames.append(", ");
                timeData.append(", ");
                comparisonsData.append(", ");
                swapsData.append(", ");
                spaceData.append(", ");
                throughputData.append(", ");
            }
            algorithmNames.append("'").append(m.algorithmName()).append("'");
            timeData.append(m.timeInMilliseconds());
            comparisonsData.append(m.comparisons());
            swapsData.append(m.swaps());
            spaceData.append(m.auxiliarySpaceUsed());
            throughputData.append(String.format("%.2f", m.throughput()));
            
            metricsData.append(generateMetricsCard(m));
        }
        
        return String.format(HTML_TEMPLATE,
            timestamp,                          // %s - timestamp in info bar
            arraySize,                          // %,d - array size in info bar
            metricsData.toString(),             // %s - metrics cards HTML
            generateComparisonTable(metrics),   // %s - comparison table HTML
            algorithmNames.toString(),          // %s - algorithms array for charts
            timeData.toString(),                // %s - time data array
            comparisonsData.toString(),         // %s - comparisons data array
            swapsData.toString(),               // %s - swaps data array
            spaceData.toString(),               // %s - space data array
            throughputData.toString()           // %s - throughput data array
        );
    }
    
    private static String generateMetricsCard(SortMetrics m) {
        return String.format("""
            <div class="metric-card">
                <h3>%s</h3>
                <div class="complexity-badges">
                    <span class="badge time">%s</span>
                    <span class="badge space">%s</span>
                </div>
                <div class="metric-grid">
                    <div class="metric-item">
                        <span class="metric-label">Time</span>
                        <span class="metric-value">%.3f ms</span>
                    </div>
                    <div class="metric-item">
                        <span class="metric-label">Throughput</span>
                        <span class="metric-value">%,.0f elem/ms</span>
                    </div>
                    <div class="metric-item">
                        <span class="metric-label">Comparisons</span>
                        <span class="metric-value">%,d</span>
                    </div>
                    <div class="metric-item">
                        <span class="metric-label">Swaps/Writes</span>
                        <span class="metric-value">%,d</span>
                    </div>
                    <div class="metric-item">
                        <span class="metric-label">Auxiliary Space</span>
                        <span class="metric-value">%,d elements</span>
                    </div>
                    <div class="metric-item">
                        <span class="metric-label">Memory Used</span>
                        <span class="metric-value">%.2f MB</span>
                    </div>
                </div>
            </div>
            """,
            m.algorithmName(),
            m.timeComplexityClass(),
            m.spaceComplexity(),
            m.timeInMilliseconds(),
            m.throughput(),
            m.comparisons(),
            m.swaps(),
            m.auxiliarySpaceUsed(),
            m.memoryUsedBytes() / (1024.0 * 1024.0)
        );
    }
    
    private static String generateComparisonTable(List<SortMetrics> metrics) {
        if (metrics.size() < 2) {
            return "<p>Not enough algorithms to compare.</p>";
        }
        
        StringBuilder table = new StringBuilder();
        table.append("<table class='comparison-table'>\n");
        table.append("<thead><tr>");
        table.append("<th>Metric</th>");
        for (SortMetrics m : metrics) {
            table.append("<th>").append(m.algorithmName()).append("</th>");
        }
        if (metrics.size() == 2) {
            table.append("<th>Comparison</th>");
        }
        table.append("</tr></thead>\n<tbody>\n");
        
        // Time comparison
        table.append("<tr><td>Time (ms)</td>");
        for (SortMetrics m : metrics) {
            table.append(String.format("<td>%.3f</td>", m.timeInMilliseconds()));
        }
        if (metrics.size() == 2) {
            double speedup = metrics.get(0).timeInMilliseconds() / metrics.get(1).timeInMilliseconds();
            String faster = speedup > 1 ? metrics.get(1).algorithmName() : metrics.get(0).algorithmName();
            table.append(String.format("<td class='highlight'>%s is %.2fx faster</td>", faster, Math.max(speedup, 1.0/speedup)));
        }
        table.append("</tr>\n");
        
        // Operations comparison
        table.append("<tr><td>Total Operations</td>");
        for (SortMetrics m : metrics) {
            table.append(String.format("<td>%,d</td>", m.comparisons() + m.swaps()));
        }
        if (metrics.size() == 2) {
            long diff = Math.abs((metrics.get(0).comparisons() + metrics.get(0).swaps()) - 
                                 (metrics.get(1).comparisons() + metrics.get(1).swaps()));
            table.append(String.format("<td class='highlight'>%,d difference</td>", diff));
        }
        table.append("</tr>\n");
        
        table.append("</tbody></table>\n");
        return table.toString();
    }
    
    private static final String HTML_TEMPLATE = """
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sort Algorithm Comparison Report</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%);
            padding: 20px;
            color: #333;
        }
        
        .container {
            max-width: 1400px;
            margin: 0 auto;
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            padding: 40px;
        }
        
        header {
            text-align: center;
            padding-bottom: 30px;
            border-bottom: 3px solid #667eea;
            margin-bottom: 40px;
        }
        
        h1 {
            color: #667eea;
            font-size: 2.5em;
            margin-bottom: 10px;
        }
        
        .subtitle {
            color: #666;
            font-size: 1.1em;
        }
        
        .info-bar {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 30px;
            display: flex;
            justify-content: space-around;
            flex-wrap: wrap;
        }
        
        .info-item {
            text-align: center;
            padding: 10px 20px;
        }
        
        .info-label {
            font-size: 0.9em;
            color: #666;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
        
        .info-value {
            font-size: 1.5em;
            font-weight: bold;
            color: #667eea;
            margin-top: 5px;
        }
        
        .metrics-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
            gap: 20px;
            margin-bottom: 40px;
        }
        
        .metric-card {
            background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%);
            color: white;
            padding: 25px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(102, 126, 234, 0.3);
            transition: transform 0.3s ease;
        }
        
        .metric-card:hover {
            transform: translateY(-5px);
        }
        
        .metric-card h3 {
            font-size: 1.5em;
            margin-bottom: 15px;
            text-align: center;
        }
        
        .complexity-badges {
            display: flex;
            justify-content: center;
            gap: 10px;
            margin-bottom: 20px;
        }
        
        .badge {
            background: rgba(255, 255, 255, 0.2);
            padding: 5px 15px;
            border-radius: 20px;
            font-size: 0.9em;
            font-weight: bold;
        }
        
        .metric-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 15px;
        }
        
        .metric-item {
            background: rgba(255, 255, 255, 0.1);
            padding: 15px;
            border-radius: 10px;
            backdrop-filter: blur(10px);
        }
        
        .metric-label {
            display: block;
            font-size: 0.85em;
            opacity: 0.9;
            margin-bottom: 5px;
        }
        
        .metric-value {
            display: block;
            font-size: 1.3em;
            font-weight: bold;
        }
        
        .chart-container {
            background: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.1);
            margin-bottom: 30px;
        }
        
        .chart-container h2 {
            color: #667eea;
            margin-bottom: 20px;
            text-align: center;
        }
        
        canvas {
            max-height: 400px;
        }
        
        .comparison-table {
            width: 100%%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        
        .comparison-table th,
        .comparison-table td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #e0e0e0;
        }
        
        .comparison-table th {
            background: #667eea;
            color: white;
            font-weight: bold;
        }
        
        .comparison-table tr:hover {
            background: #f8f9fa;
        }
        
        .highlight {
            background: #fff3cd !important;
            font-weight: bold;
        }
        
        footer {
            text-align: center;
            margin-top: 40px;
            padding-top: 20px;
            border-top: 2px solid #e0e0e0;
            color: #666;
        }
    </style>
</head>
<body>
    <div class="container">
        <header>
            <h1>🚀 Sort Algorithm Comparison Report</h1>
            <p class="subtitle">Comprehensive Performance Analysis</p>
        </header>
        
        <div class="info-bar">
            <div class="info-item">
                <div class="info-label">Generated</div>
                <div class="info-value">%s</div>
            </div>
            <div class="info-item">
                <div class="info-label">Array Size</div>
                <div class="info-value">%,d</div>
            </div>
            <div class="info-item">
                <div class="info-label">Algorithms Tested</div>
                <div class="info-value">2</div>
            </div>
        </div>
        
        <h2 style="color: #667eea; margin-bottom: 20px;">📊 Algorithm Metrics</h2>
        <div class="metrics-container">
            %s
        </div>
        
        <div class="chart-container">
            <h2>⏱️ Execution Time Comparison</h2>
            <canvas id="timeChart"></canvas>
        </div>
        
        <div class="chart-container">
            <h2>🔢 Operations Comparison</h2>
            <canvas id="operationsChart"></canvas>
        </div>
        
        <div class="chart-container">
            <h2>💾 Space Usage Comparison</h2>
            <canvas id="spaceChart"></canvas>
        </div>
        
        <div class="chart-container">
            <h2>⚡ Throughput Comparison</h2>
            <canvas id="throughputChart"></canvas>
        </div>
        
        <div class="chart-container">
            <h2>📈 Detailed Comparison</h2>
            %s
        </div>
        
        <footer>
            <p>Generated by Sort Algorithm Comparison Tool</p>
            <p>Powered by Chart.js • Java 21</p>
        </footer>
    </div>
    
    <script>
        const algorithms = [%s];
        const timeData = [%s];
        const comparisonsData = [%s];
        const swapsData = [%s];
        const spaceData = [%s];
        const throughputData = [%s];
        
        const chartColors = [
            'rgba(255, 99, 132, 0.8)',
            'rgba(54, 162, 235, 0.8)',
            'rgba(75, 192, 192, 0.8)',
            'rgba(255, 206, 86, 0.8)'
        ];
        
        // Time Chart
        new Chart(document.getElementById('timeChart'), {
            type: 'bar',
            data: {
                labels: algorithms,
                datasets: [{
                    label: 'Time (milliseconds)',
                    data: timeData,
                    backgroundColor: chartColors,
                    borderColor: chartColors.map(c => c.replace('0.8', '1')),
                    borderWidth: 2
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                plugins: {
                    legend: { display: false }
                },
                scales: {
                    y: { beginAtZero: true }
                }
            }
        });
        
        // Operations Chart (Stacked)
        new Chart(document.getElementById('operationsChart'), {
            type: 'bar',
            data: {
                labels: algorithms,
                datasets: [
                    {
                        label: 'Comparisons',
                        data: comparisonsData,
                        backgroundColor: 'rgba(54, 162, 235, 0.8)',
                    },
                    {
                        label: 'Swaps/Writes',
                        data: swapsData,
                        backgroundColor: 'rgba(255, 99, 132, 0.8)',
                    }
                ]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                plugins: {
                    legend: { display: true, position: 'top' }
                },
                scales: {
                    x: { stacked: true },
                    y: { stacked: true, beginAtZero: true }
                }
            }
        });
        
        // Space Chart
        new Chart(document.getElementById('spaceChart'), {
            type: 'bar',
            data: {
                labels: algorithms,
                datasets: [{
                    label: 'Auxiliary Space (elements)',
                    data: spaceData,
                    backgroundColor: chartColors,
                    borderColor: chartColors.map(c => c.replace('0.8', '1')),
                    borderWidth: 2
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                plugins: {
                    legend: { display: false }
                },
                scales: {
                    y: { beginAtZero: true }
                }
            }
        });
        
        // Throughput Chart
        new Chart(document.getElementById('throughputChart'), {
            type: 'bar',
            data: {
                labels: algorithms,
                datasets: [{
                    label: 'Throughput (elements/ms)',
                    data: throughputData,
                    backgroundColor: chartColors,
                    borderColor: chartColors.map(c => c.replace('0.8', '1')),
                    borderWidth: 2
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                plugins: {
                    legend: { display: false }
                },
                scales: {
                    y: { beginAtZero: true }
                }
            }
        });
    </script>
</body>
</html>
""";
}
