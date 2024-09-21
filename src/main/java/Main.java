import java.util.*;

class Main {

    public static String[] OccurrenceDataQuestionTwo(String[] strArr) {
        // Step 1: Parse input (start, end, sum_of_viewers)
        List<int[]> occurrences = new ArrayList<>();
        for (String str : strArr) {
            String[] parts = str.split(",");
            int start = Integer.parseInt(parts[0]);
            int end = Integer.parseInt(parts[1]);
            int viewers = Integer.parseInt(parts[2]);
            occurrences.add(new int[]{start, end, viewers});
        }

        // Step 2: Sliding window calculation
        List<String> result = new ArrayList<>();
        int windowSize = 3;  // As String> per the problem example, the sliding window size is 3 seconds
        int minTime = occurrences.get(0)[0];
        int maxTime = occurrences.get(occurrences.size() - 1)[1];

        // Iterate through the sliding windows, moving by one second
        for (int windowStart = minTime; windowStart <= maxTime - windowSize; windowStart++) {
            int windowEnd = windowStart + windowSize;
            int totalViewers = 0;

            // Step 3: Calculate viewers for the current window
            for (int[] occurrence : occurrences) {
                int occurrenceStart = occurrence[0];
                int occurrenceEnd = occurrence[1];
                int occurrenceViewers = occurrence[2];

                // Calculate the overlap between the current window and the occurrence
                int overlapStart = Math.max(windowStart, occurrenceStart);
                int overlapEnd = Math.min(windowEnd, occurrenceEnd);

                // If there is an overlap, calculate the proportional viewers
                if (overlapStart < overlapEnd) {
                    int overlapDuration = overlapEnd - overlapStart;
                    int occurrenceDuration = occurrenceEnd - occurrenceStart;
                    totalViewers += (overlapDuration * occurrenceViewers) / occurrenceDuration;
                }
            }

            // Step 4: Add the result to the list (sum of viewers for the current window)
            result.add(windowStart + "," + windowEnd + "," + totalViewers);
        }

        // Step 5: Convert the list to the output format
        return result.toArray(new String[0]);
    }

    public static void main(String[] args) {
        String[] inputArray = new String[] {"0,5,100000", "2,7,100000", "8,10,100000"};
        System.out.println(Arrays.toString(OccurrenceDataQuestionTwo(inputArray)));
    }
}
