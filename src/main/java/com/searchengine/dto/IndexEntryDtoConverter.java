package com.searchengine.dto;

import com.findwise.IndexEntry;

public class IndexEntryDtoConverter {
    private IndexEntryDtoConverter() {}

    public static IndexEntryDto toDto(final IndexEntry indexEntry) {
        final IndexEntryDto dto = new IndexEntryDto();
        dto.setIndex(indexEntry.getId());
        dto.setSearchScore(indexEntry.getScore());
        return dto;
    }
}
