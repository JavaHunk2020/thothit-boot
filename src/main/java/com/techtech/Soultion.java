package com.techtech;

import java.util.*;

public class Soultion {

    public static int suitableLocations(List<Integer> center, long d) {
        int n = center.size();

        // Sort the centers
        Collections.sort(center);

        // Initialize count of suitable locations
        int suitableCount = 0;

        // Iterate over all possible points x from left to right
        for (int x = center.get(0) - 1; x <= center.get(n - 1) + 1; x++) {
            long totalDistance = 0;

            // Calculate the total distance for current point x
            for (int i = 0; i < n; i++) {
                totalDistance += 2 * Math.abs(x - center.get(i));  // Each center's trip involves a round trip
            }

            // If the total distance is less than or equal to d, we have a suitable location
            if (totalDistance <= d) {
                suitableCount++;
            }
        }

        return suitableCount;
    }

    public static void main(String[] args) {
        // Test case
        List<Integer> center = Arrays.asList(-2, 1, 0);
        long d = 8;

        // Expected output: 3
        int result = suitableLocations(center, d);
        System.out.println(result); // Output: 3
    }
}
