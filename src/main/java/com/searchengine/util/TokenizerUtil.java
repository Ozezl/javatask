package com.searchengine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TokenizerUtil {
    //possible to change tokenizer to be more advanced
    final static String DELIMINATOR = " ";

    public static List<String> getKeywordsFromString(final String documentContent) {
        final StringTokenizer stringTokenizer = new StringTokenizer(documentContent, DELIMINATOR);
        final List<String> keywords = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            keywords.add(stringTokenizer.nextToken());
        }
        return keywords;
    }

    public static int countSpecificWordInContent(final String specificWord, final String content) {
        final StringTokenizer stringTokenizer = new StringTokenizer(content, DELIMINATOR);
        final List<String> words = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            final String word = stringTokenizer.nextToken();
            if (word.equals(specificWord)) {
                words.add(word);
            }
        }
        return words.size();
    }

    public static int countWordsInContent(final String content) {
        final StringTokenizer stringTokenizer = new StringTokenizer(content, DELIMINATOR);
        return stringTokenizer.countTokens();
    }
}
