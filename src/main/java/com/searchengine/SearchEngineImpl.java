package com.searchengine;

import com.findwise.IndexEntry;
import com.findwise.SearchEngine;
import com.searchengine.db.SearchEngineDB;
import com.searchengine.util.InvertedIndexCreator;
import com.searchengine.util.TFIDFCalculator;
import com.searchengine.util.TokenizerUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SearchEngineImpl implements SearchEngine {
    private final SearchEngineDB searchEngineDB;

    public SearchEngineImpl() {
        this.searchEngineDB = new SearchEngineDB();
    }

    @Override
    public void indexDocument(final String id, final String content) {
        searchEngineDB.addNewEntry(id, content);
        final HashMap<IndexEntry, String> currentIdToContentMap = searchEngineDB.getEntryToContentMap();
        searchEngineDB.persistKeywordToDocuments(InvertedIndexCreator.createInvertedIndex(currentIdToContentMap));
    }

    @Override
    public List<IndexEntry> search(final String term) {
        final List<String> termKeywords = TokenizerUtil.getKeywordsFromString(term);
        if (termKeywords.isEmpty()) {
            return List.of();
        }
        if (termKeywords.size() == 1) {
            return simpleSearch(term);
        }

        return complexSearch(termKeywords);
    }

    private List<IndexEntry> simpleSearch(final String keyword) {
        final HashMap<String, List<IndexEntry>> keywordToDocuments = searchEngineDB.getKeywordToDocuments();
        if (!keywordToDocuments.containsKey(keyword)) {
            return List.of();
        }
        return keywordToDocuments.get(keyword);
    }

    private List<IndexEntry> complexSearch(final List<String> termKeywords) {
        final HashMap<String, List<IndexEntry>> keywordToDocuments = searchEngineDB.getKeywordToDocuments();
        List<IndexEntry> allResultEntries = new ArrayList<>();
        for (final String keyword : termKeywords) {
            if (keywordToDocuments.containsKey(keyword)) {
                allResultEntries.addAll(keywordToDocuments.get(keyword));
            }
        }
        return TFIDFCalculator.sortEntriesByTFIDFForComplexSearch(allResultEntries);
    }
}
