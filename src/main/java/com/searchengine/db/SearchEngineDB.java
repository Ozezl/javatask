package com.searchengine.db;

import com.findwise.IndexEntry;
import com.searchengine.domain.SimpleIndexEntry;

import java.util.HashMap;
import java.util.List;

public class SearchEngineDB {
    private final HashMap<IndexEntry, String> entryToContentMap;
    private HashMap<String, List<IndexEntry>> keywordToDocuments;

    public SearchEngineDB() {
        this.entryToContentMap = new HashMap<>();
        this.keywordToDocuments = new HashMap<>();
    }

    public void addNewEntry(final String id, final String content) {
        final SimpleIndexEntry entry = new SimpleIndexEntry(id, 0.0);
        if (entryToContentMap.containsKey(entry)) {
            throw new IllegalStateException("The given entry id was already persisted to the DB " + id);
        }

        entryToContentMap.put(entry, content);
    }

    public void persistKeywordToDocuments(final HashMap<String, List<IndexEntry>> keywordToDocuments) {
        this.keywordToDocuments = keywordToDocuments;
    }

    public HashMap<IndexEntry, String> getEntryToContentMap() {
        return entryToContentMap;
    }

    public HashMap<String, List<IndexEntry>> getKeywordToDocuments() {
        return keywordToDocuments;
    }
}
