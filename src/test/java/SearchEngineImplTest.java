import com.findwise.IndexEntry;
import com.searchengine.SearchEngineImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SearchEngineImplTest {

    @Test
    public void testIfThreeEntriesAddedToSearchDB() {
        final SearchEngineImpl searchEngine = new SearchEngineImpl();
        searchEngine.indexDocument("document 1", "the brown fox jumped over the brown dog");
        searchEngine.indexDocument("document 2", "the lazy brown dog sat in the corner");
        searchEngine.indexDocument("document 3", "the red fox bit the lazy dog");

        List<IndexEntry> results1 = searchEngine.search("the brown fox jumped over the brown dog");
        final boolean document1AddedResult = results1.get(0).getId().equals("document 1");

        List<IndexEntry> results2 = searchEngine.search("the lazy brown dog sat in the corner");
        final boolean document2AddedResult = results2.get(0).getId().equals("document 2");

        List<IndexEntry> results3 = searchEngine.search("the red fox bit the lazy dog");
        final boolean document3AddedResult = results3.get(0).getId().equals("document 3");

        Assert.assertTrue(document1AddedResult && document2AddedResult && document3AddedResult);
    }

    @Test
    public void testSimpleSearch() {
        final SearchEngineImpl searchEngine = new SearchEngineImpl();
        searchEngine.indexDocument("document 1", "random keywords I wrote");
        searchEngine.indexDocument("document 2", "word no sense");
        searchEngine.indexDocument("document 3", "sentence makes sound");

        List<IndexEntry> results1 = searchEngine.search("keywords");
        final boolean document1AddedResult = results1.get(0).getId().equals("document 1");

        List<IndexEntry> results2 = searchEngine.search("sense");
        final boolean document2AddedResult = results2.get(0).getId().equals("document 2");

        List<IndexEntry> results3 = searchEngine.search("sentence");
        final boolean document3AddedResult = results3.get(0).getId().equals("document 3");

        Assert.assertTrue(document1AddedResult && document2AddedResult && document3AddedResult);
    }

    @Test
    public void testBestMatchForComplexSearch() {
        final SearchEngineImpl searchEngine = new SearchEngineImpl();
        searchEngine.indexDocument("document 1", "word random keywords I wrote");
        searchEngine.indexDocument("document 2", "word no sense");
        searchEngine.indexDocument("document 3", "sentence makes sound");

        List<IndexEntry> results = searchEngine.search("word sense");
        final boolean bestMatchResult = results.get(0).getId().equals("document 2");

        Assert.assertTrue(bestMatchResult);
    }

    @Test
    public void testEmptyEngineSearch() {
        final SearchEngineImpl searchEngine = new SearchEngineImpl();
        List<IndexEntry> results = searchEngine.search("empty search");

        Assert.assertTrue(results.isEmpty());
    }

    @Test
    public void testNoMatchSearch() {
        final SearchEngineImpl searchEngine = new SearchEngineImpl();
        searchEngine.indexDocument("document 1", "the brown fox jumped over the brown dog");
        List<IndexEntry> result = searchEngine.search("big white cat");

        Assert.assertTrue(result.isEmpty());
    }
}
