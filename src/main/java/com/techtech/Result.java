package com.techtech;

import java.util.*;

class Result {

    public static List<Integer> rodOffcut(List<Integer> lengths) {
        // Sort the lengths first
        Collections.sort(lengths);
        
        List<Integer> result = new ArrayList<>();
        int n = lengths.size();
        
        result.add(n);  // Add the initial number of rods
        
        // Traverse through sorted lengths
        for (int i = 1; i < n; i++) {
            // Only act when the next length is greater than the previous one
            if (lengths.get(i) > lengths.get(i - 1)) {
                result.add(n - i);  // Add the number of remaining rods
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
    	List<Integer> list=new ArrayList<>();
    	list.add(1);list.add(1);list.add(3);list.add(4);
    	
		System.out.println(rodOffcut(list));
		System.out.println(Math.ceil(-4.7));
	}
}
