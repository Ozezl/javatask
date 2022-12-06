package com.searchengine.util;

import com.findwise.IndexEntry;
import com.searchengine.domain.SimpleIndexEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TFIDFCalculator {

    public static double getTfidf(final int numberOfEntries,
                                  final String keyword,
                                  final int numberOfEntriesContainingKeyword,
                                  final String content) {
        final int keywordOccurrences = TokenizerUtil.countSpecificWordInContent(keyword, content);
        final int wordNumber = TokenizerUtil.countWordsInContent(content);
        final double termFrequency = (double) keywordOccurrences / wordNumber;
        final double inverseDocumentFrequency = Math.log((double) numberOfEntries / numberOfEntriesContainingKeyword);
        return termFrequency * inverseDocumentFrequency;
    }
    public static List<IndexEntry> sortEntriesByTFIDFForComplexSearch(final List<IndexEntry> allFoundEntries) {
        List<IndexEntry> unsortedResultEntries = new ArrayList<>();
        final Map<String, List<Double>> documentIdToTFIDFListMap = new HashMap<>();
        allFoundEntries.forEach(entry -> {
            if (documentIdToTFIDFListMap.containsKey(entry.getId())) {
                documentIdToTFIDFListMap.get(entry.getId()).add(entry.getScore());
            } else {
                documentIdToTFIDFListMap.put(entry.getId(), new ArrayList<>(List.of(entry.getScore())));
            }
        });

        documentIdToTFIDFListMap.forEach((key, value) -> {
            unsortedResultEntries.add(calculateAverageTFIDF(key, value));
        });

        return sortEntriesByTFIDFDesc(unsortedResultEntries);
    }

    private static IndexEntry calculateAverageTFIDF(final String documentId, final List<Double> tfidfList) {
        double averageTfidf = 0;
        int tfidfCount = 1;
        for (final double tfidf : tfidfList) {
            averageTfidf += tfidf;
            tfidfCount++;
        }

        return new SimpleIndexEntry(documentId, averageTfidf / tfidfCount);
    }

    public static List<IndexEntry> sortEntriesByTFIDFDesc(final List<IndexEntry> unsortedEntries) {
        return unsortedEntries.stream()
                .sorted((entry1, entry2) -> Double.compare(entry2.getScore(), entry1.getScore()))
                .toList();
    }
}
