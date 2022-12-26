package com.searchengine.web;

import com.findwise.SearchEngine;
import com.searchengine.dto.IndexEntryDto;
import com.searchengine.dto.IndexEntryDtoConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchEngineController {
    private SearchEngine searchEngine;

    public SearchEngineController(final SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    @GetMapping("/search")
    public List<IndexEntryDto> search(@RequestParam final String term) {
        return searchEngine.search(term).stream()
                .map(IndexEntryDtoConverter::toDto)
                .toList();
    }

    @PostMapping("/indexDocument")
    public void indexDocument(@RequestParam final String id, @RequestParam final String content) {
        searchEngine.indexDocument(id, content);
    }
}
