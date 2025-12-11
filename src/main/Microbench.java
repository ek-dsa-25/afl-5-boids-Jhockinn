package main;

import main.simulation.FlockSimulation;
import main.spatial.*;

import java.util.ArrayList;
import java.util.List;

public class Microbench {
    
    // Benchmark parametre
    private static final int WARMUP_ITERATIONS = 50;
    private static final int BENCHMARK_ITERATIONS = 200;
    private static final int[] BOID_COUNTS = {50, 100, 200, 500, 1000};
    private static final double[] NEIGHBOR_RADII = {30.0, 50.0, 100.0};
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    
    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("BOIDS MICROBENCHMARK");
        System.out.println("=".repeat(80));
        System.out.println();
        
        System.out.println("Parametre:");
        System.out.println("  - Warmup iterations: " + WARMUP_ITERATIONS);
        System.out.println("  - Benchmark iterations: " + BENCHMARK_ITERATIONS);
        System.out.println("  - Boid counts: " + arrayToString(BOID_COUNTS));
        System.out.println("  - Neighbor radii: " + arrayToString(NEIGHBOR_RADII));
        System.out.println("  - Area: " + WIDTH + "x" + HEIGHT + " pixels");
        System.out.println();
        
        // Test hver radius
        for (double radius : NEIGHBOR_RADII) {
            System.out.println("=".repeat(80));
            System.out.println("NEIGHBOR RADIUS: " + radius + " pixels");
            System.out.println("=".repeat(80));
            System.out.println();
            
            // Test hver boid count
            for (int boidCount : BOID_COUNTS) {
                System.out.println("-".repeat(80));
                System.out.println("Testing with " + boidCount + " boids");
                System.out.println("-".repeat(80));
                
                // Test hver spatial index
                // Cell size er ca. 2x neighbor radius (optimal for spatial hashing)
                double cellSize = radius * 2.0;
                List<BenchmarkResult> results = new ArrayList<>();
                results.add(benchmarkSpatialIndex(new NaiveSpatialIndex(), boidCount, radius));
                results.add(benchmarkSpatialIndex(new KDTreeSpatialIndex(), boidCount, radius));
                results.add(benchmarkSpatialIndex(new QuadTreeSpatialIndex(WIDTH, HEIGHT), boidCount, radius));
                results.add(benchmarkSpatialIndex(new SpatialHashIndex(WIDTH, HEIGHT, cellSize), boidCount, radius));
                
                // Print resultater
                printResults(results);
                System.out.println();
            }
        }
        
        System.out.println("=".repeat(80));
        System.out.println("BENCHMARK COMPLETED");
        System.out.println("=".repeat(80));
    }
    
    private static BenchmarkResult benchmarkSpatialIndex(SpatialIndex spatialIndex, int boidCount, double radius) {
        FlockSimulation simulation = new FlockSimulation(WIDTH, HEIGHT);
        simulation.setSpatialIndex(spatialIndex);
        simulation.setNeighborRadius(radius);
        simulation.setBoidCount(boidCount);
        
        // Warmup fase
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            simulation.update();
        }
        
        // Benchmark fase - mål kun update tiden
        long totalNanos = 0;
        for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
            long startTime = System.nanoTime();
            simulation.update();
            long endTime = System.nanoTime();
            totalNanos += (endTime - startTime);
        }
        
        double avgMillis = (totalNanos / (double) BENCHMARK_ITERATIONS) / 1_000_000.0;
        
        return new BenchmarkResult(
            spatialIndex.getName(),
            boidCount,
            radius,
            avgMillis,
            BENCHMARK_ITERATIONS
        );
    }
    
    private static void printResults(List<BenchmarkResult> results) {
        // Find den hurtigste til sammenligning
        BenchmarkResult fastest = results.stream()
            .min((a, b) -> Double.compare(a.avgTimeMs, b.avgTimeMs))
            .orElse(results.get(0));
        
        System.out.printf("%-25s %12s %12s %12s%n", 
            "Spatial Index", "Avg Time (ms)", "vs Fastest", "Status");
        System.out.println("-".repeat(80));
        
        for (BenchmarkResult result : results) {
            double vsRatio = result.avgTimeMs / fastest.avgTimeMs;
            String status = result == fastest ? "FASTEST" : String.format("%.2fx slower", vsRatio);
            
            System.out.printf("%-25s %12.4f %12.4f %12s%n",
                result.spatialIndexName,
                result.avgTimeMs,
                vsRatio,
                status
            );
        }
    }
    
    private static String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
    
    private static String arrayToString(double[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
    
    private static class BenchmarkResult {
        final String spatialIndexName;
        final int boidCount;
        final double neighborRadius;
        final double avgTimeMs;
        final int iterations;
        
        BenchmarkResult(String spatialIndexName, int boidCount, double neighborRadius, 
                       double avgTimeMs, int iterations) {
            this.spatialIndexName = spatialIndexName;
            this.boidCount = boidCount;
            this.neighborRadius = neighborRadius;
            this.avgTimeMs = avgTimeMs;
            this.iterations = iterations;
        }
    }
}
