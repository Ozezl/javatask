package com.searchengine.util;

import com.findwise.IndexEntry;
import com.searchengine.domain.SimpleIndexEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InvertedIndexCreator {
    public static HashMap<String, List<IndexEntry>> createInvertedIndex(final HashMap<IndexEntry, String> currentEntryToContent) {
        return createSortedInvertedIndex(currentEntryToContent, createUnsortedInvertedIndex(currentEntryToContent));
    }

    private static HashMap<String, List<IndexEntry>> createSortedInvertedIndex(final HashMap<IndexEntry, String> currentEntryToContent,
                                                                               final HashMap<String, List<IndexEntry>> unsortedInvertedIndex) {
        final int numberOfEntries = currentEntryToContent.size();
        for (Map.Entry<String, List<IndexEntry>> entry : unsortedInvertedIndex.entrySet()) {
            final String keyword = entry.getKey();
            final List<IndexEntry> entriesForKeyword = entry.getValue();
            final int numberOfEntriesContainingKeyword = entriesForKeyword.size();
            for (IndexEntry indexEntry : entriesForKeyword) {
                final String content = currentEntryToContent.get(indexEntry);
                final double tfidf = TFIDFCalculator.getTfidf(numberOfEntries, keyword, numberOfEntriesContainingKeyword, content);
                indexEntry.setScore(tfidf);
            }

            unsortedInvertedIndex.replace(keyword, TFIDFCalculator.sortEntriesByTFIDFDesc(entriesForKeyword));
        }
        return unsortedInvertedIndex;
    }

    private static HashMap<String, List<IndexEntry>> createUnsortedInvertedIndex(final HashMap<IndexEntry, String> currentEntryToContent) {
        final HashMap<String, List<IndexEntry>> invertedIndex = new HashMap<>();
        currentEntryToContent.forEach((key, value) -> {
            final List<String> keywords = TokenizerUtil.getKeywordsFromString(value);
            keywords.forEach(keyword -> {
                if (invertedIndex.containsKey(keyword)) {
                    if (!invertedIndex.get(keyword).contains(key)) {
                        invertedIndex.get(keyword).add(new SimpleIndexEntry(key.getId(), key.getScore()));
                    }
                } else {
                    invertedIndex.put(keyword, new ArrayList<>(List.of(new SimpleIndexEntry(key.getId(), key.getScore()))));
                }
            });
        });
        return invertedIndex;
    }

}
