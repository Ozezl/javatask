package com.mocksearchbar;

import com.findwise.SearchEngine;
import com.searchengine.SearchEngineImpl;

import java.util.Scanner;

public class MenuLoader {
    private SearchEngine searchEngine;
    private boolean run;
    public void startApp() {
        initializeSearchEngine();
        while (run) {
            printInitialMessage();
            final Scanner scanner = new Scanner(System.in);
            final String input = scanner.nextLine();
            if (input.equals("1")) {
                System.out.println("Enter id for the document:");
                final String id = scanner.nextLine();
                System.out.println("Enter document content:");
                final String content = scanner.nextLine();
                searchEngine.indexDocument(id, content);
                printSeparator();
            } else if (input.equals("2")) {
                System.out.println("Enter term to be searched:");
                final String term = scanner.nextLine();
                System.out.println(searchEngine.search(term).toString());
                printSeparator();
            } else if (input.equals("3")) {
                System.out.println("Bye");
                run = false;
                break;
            }
        }
    }

    private void initializeSearchEngine() {
        this.run = true;
        this.searchEngine = new SearchEngineImpl();
    }

    private void printInitialMessage() {
        System.out.println("The Search engine");
        System.out.println("*****************");
        System.out.println("*****************");
        System.out.println("*****************");
        System.out.println("Choose option:");
        System.out.println("1.Add new document");
        System.out.println("2.Search for the document");
        System.out.println("3.Exit");
    }

    private void printSeparator() {
        System.out.println();
        System.out.println();
        System.out.println();
    }
}
