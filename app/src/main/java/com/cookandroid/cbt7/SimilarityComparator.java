package com.cookandroid.cbt7;

import java.util.Comparator;

public class SimilarityComparator implements Comparator<found_list> {
    @Override
    public int compare(found_list o1, found_list o2) {
        double similarity1 = o1.getFound_similarity();
        double similarity2 = o2.getFound_similarity();

        if (similarity1 > similarity2) {
            return -1;
        } else if (similarity1 < similarity2) {
            return 1;
        } else {
            return 0;
        }
    }

//    private double extractSimilarity(String data) {
//        int startIndex = data.indexOf("Similarity: ") + 12;
//        int endIndex = data.length();
//        String similarityString = data.substring(startIndex, endIndex);
//        return Double.parseDouble(similarityString);
//    }
}
