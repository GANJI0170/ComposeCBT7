package com.cookandroid.cbt7;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class Levenshtin {

    public static class SimilarityCalculator {
        public static double calculateSimilarity(String sentence1, String sentence2) {
            int distance = LevenshteinDistance.getDefaultInstance().apply(sentence1, sentence2);
            int maxLength = Math.max(sentence1.length(), sentence2.length());
            double similarity = 1 - ((double) distance / maxLength);
            similarity = Math.round(similarity * 1000) / 1000.0;
            return similarity;
        }
    }
}
