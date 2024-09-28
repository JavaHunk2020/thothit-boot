package com.techtech;

import java.util.*;
import java.util.stream.Collectors;

public class BestAverageGrade {

    public static int bestAverageGrade(List<String[]> scores) {
        if (scores == null || scores.isEmpty()) {
            return 0;  // Return 0 for empty input
        }
        // Step 1: Group scores by student name
        Map<String, List<Integer>> studentScores = scores.stream()
            .collect(Collectors.groupingBy(
                score -> score[0],  // Group by student name
                Collectors.mapping(score -> Integer.parseInt(score[1]), Collectors.toList())  // Convert and collect scores
            ));

        // Step 2: Calculate the average for each student and find the maximum average (floored)
        return studentScores.entrySet().stream()
            .mapToInt(entry -> {
                List<Integer> grades = entry.getValue();
                double average = grades.stream().mapToInt(Integer::intValue).average().orElse(0);
                return (int) Math.floor(average);  // Apply floor to the average
            })
            .max()  // Get the maximum average
            .orElse(0);  // Return 0 if there are no valid entries
    }

    public static void main(String[] args) {
        // Example test case
        List<String[]> scores = Arrays.asList(
            new String[]{"Bobby", "87"},
            new String[]{"Charles", "100"},
            new String[]{"Eric", "64"},
            new String[]{"Charles", "22"}
        );

        // Call the function and print the result
        int result = bestAverageGrade(scores);
        System.out.println(result);  // Expected output: 87
    }
}
