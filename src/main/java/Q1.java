
import java.util.*;

public class Q1 {

    public static String[] OccurrenceDataQuestionOne(String[] strArr) {
        // Step 1: Parse input and store changes in occurrences in a TreeMap
        TreeMap<Integer, Integer> occurrenceChanges = new TreeMap<>();

        for (String str : strArr) {
            String[] times = str.split(",");
            int start = Integer.parseInt(times[0]);
            int end = Integer.parseInt(times[1]);

            // Increment count at the start second
            occurrenceChanges.put(start, occurrenceChanges.getOrDefault(start, 0) + 1);
            // Decrement count at the end second (end is exclusive)
            occurrenceChanges.put(end, occurrenceChanges.getOrDefault(end, 0) - 1);
        }

        // Step 2: Traverse the timeline to count occurrences and aggregate intervals
        List<String> result = new ArrayList<>();
        int currentOccurrences = 0;
        int intervalStart = -1;
        Integer previousTime = null;

        for (Map.Entry<Integer, Integer> entry : occurrenceChanges.entrySet()) {
            int time = entry.getKey();

            // If the previousTime is not null and we're currently in an interval
            if (previousTime != null && currentOccurrences != 0) {
                // If the count hasn't changed, just extend the interval
                if (currentOccurrences > 0) {
                    // Continue the current interval
                    if (intervalStart == -1) {
                        intervalStart = previousTime;
                    }
                } else {
                    result.add(intervalStart + "," + previousTime + "," + currentOccurrences);
                    intervalStart = -1; // Reset interval start
                }
            }

            // Update the current occurrences count
            currentOccurrences += entry.getValue();
            previousTime = time;
        }

        // Add the last interval if it was not added
        if (intervalStart != -1) {
            result.add(intervalStart + "," + previousTime + "," + currentOccurrences);
        }

        // Step 3: Return the result as an array of strings
        return result.toArray(new String[0]);
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        String[] inputArray = input.split("\\s*,\\s*");
        System.out.println(Arrays.toString(OccurrenceDataQuestionOne(inputArray)));
    }
}
