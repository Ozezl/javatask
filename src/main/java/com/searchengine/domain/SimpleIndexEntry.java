package com.searchengine.domain;

import com.findwise.IndexEntry;

public class SimpleIndexEntry implements IndexEntry {
    private String id;
    private double score;

    public SimpleIndexEntry(final String id, final double score) {
        this.id = id;
        this.score = score;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleIndexEntry that = (SimpleIndexEntry) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public double getScore() {
        return score;
    }

    @Override
    public void setScore(final double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return id;
    }
}
