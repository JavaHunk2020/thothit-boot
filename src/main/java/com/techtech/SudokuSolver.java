package com.techtech;

import java.util.*;

public class SudokuSolver {

    public static int getMinScore(List<Integer> watch_history, int series1, int series2) {
        int n = watch_history.size();
        if (n == 0) return 0;

        // Special case where series1 and series2 are the same
        if (series1 == series2) {
            return getMinScoreForSameSeries(watch_history, series1);
        }

        // A map to track the most recent index of the series
        Map<Integer, Integer> lastSeen = new HashMap<>();
        // Set to maintain distinct series in the current window
        Set<Integer> distinctSeries = new HashSet<>();

        int minScore = Integer.MAX_VALUE;
        int left = 0;

        for (int right = 0; right < n; right++) {
            int series = watch_history.get(right);

            // Add current series to the set
            distinctSeries.add(series);
            // Update the last seen position of the series
            lastSeen.put(series, right);

            // Check if both series1 and series2 are in the current window
            if (lastSeen.containsKey(series1) && lastSeen.containsKey(series2)) {
                // Move the left pointer to shrink the window as much as possible
                while (left < right && lastSeen.get(series1) >= left && lastSeen.get(series2) >= left) {
                    minScore = Math.min(minScore, distinctSeries.size());

                    // Try shrinking the window by moving the left pointer
                    distinctSeries.remove(watch_history.get(left));
                    left++;
                }
            }
        }

        return minScore == Integer.MAX_VALUE ? 0 : minScore; // Handle case where no valid window was found
    }

    // Special case when series1 == series2
    private static int getMinScoreForSameSeries(List<Integer> watch_history, int series) {
        int n = watch_history.size();
        int minScore = Integer.MAX_VALUE;
        Set<Integer> distinctSeries = new HashSet<>();
        int lastSeenIndex = -1;

        for (int i = 0; i < n; i++) {
            int currentSeries = watch_history.get(i);
            distinctSeries.add(currentSeries);

            // If we encounter the series, calculate the score
            if (currentSeries == series) {
                if (lastSeenIndex != -1) {
                    // Calculate the number of distinct elements between last occurrence and current occurrence
                    minScore = Math.min(minScore, distinctSeries.size());
                    // Shrink the window by resetting the distinct set after calculating
                    distinctSeries.clear();
                    distinctSeries.add(series); // Keep the current series in the set
                }
                lastSeenIndex = i; // Update the last seen index
            }
        }

        return minScore == Integer.MAX_VALUE ? 1 : minScore;
    }

    public static void main(String[] args) {
        // Test case 1
        List<Integer> watch_history1 = Arrays.asList(1, 3, 2, 1, 4);
        int series1_1 = 1;
        int series2_1 = 2;
        int result1 = getMinScore(watch_history1, series1_1, series2_1);
        System.out.println(result1); // Expected output: 2

        // Test case 2
        List<Integer> watch_history2 = Arrays.asList(5, 1, 2, 3, 4, 1);
        int series1_2 = 5;
        int series2_2 = 5;
        int result2 = getMinScore(watch_history2, series1_2, series2_2);
        System.out.println(result2); // Expected output: 1
    }
}
