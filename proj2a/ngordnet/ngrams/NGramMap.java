package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;

/** An object that provides utility methods for making queries on the
 *  Google NGrams dataset (or a subset thereof).
 *
 *  An NGramMap stores pertinent data from a "words file" and a "counts
 *  file". It is not a map in the strict sense, but it does provide additional
 *  functionality.
 *
 *  @author Josh Hug
 */
public class NGramMap {
    private HashMap<String, TimeSeries> wordsMap = new HashMap<>();
    private TimeSeries countsTS = new TimeSeries();

    private final int DEFAULT_START_YEAR = 1400;
    private final int DEFAULT_END_YEAR = 2100;

    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFilename, String countsFilename) {
        In words = new In(wordsFilename);
        // Read file and store the data in wordsMap
        while (!words.isEmpty()) {
            String word = words.readString();
            int year = words.readInt();
            double count = words.readDouble();
            words.readInt();
            // If wordsMap already has the word -> put value to the ts
            if (wordsMap.containsKey(word)) {
                wordsMap.get(word).put(year, count);
            } else { // Else -> create a new ts and put new value
                TimeSeries ts = new TimeSeries();
                ts.put(year, count);
                wordsMap.put(word, ts);
            }
        }

        // Read file and store the data in countsTS
        In counts = new In(countsFilename);
        while (counts.hasNextLine()) {
            String line = counts.readLine();
            String[] fields = line.split(",");
            int year = Integer.parseInt(fields[0]);
            double count = Double.parseDouble(fields[1]);
            countsTS.put(year, count);
        }
    }

    /** Provides the history of WORD. The returned TimeSeries should be a copy,
     *  not a link to this NGramMap's TimeSeries. In other words, changes made
     *  to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word) {
        return countHistory(word, DEFAULT_START_YEAR, DEFAULT_END_YEAR);
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     *  returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     *  changes made to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        return new TimeSeries(wordsMap.get(word), startYear, endYear);
    }

    /** Returns a defensive copy of the total number of words recorded per year in all volumes. */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(countsTS, DEFAULT_START_YEAR, DEFAULT_END_YEAR);
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD compared to
     *  all words recorded in that year. */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, DEFAULT_START_YEAR, DEFAULT_END_YEAR);
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     *  and ENDYEAR, inclusive of both ends. */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries wordTS = new TimeSeries(wordsMap.get(word), startYear, endYear);
        return wordTS.dividedBy(countsTS);
    }

    /** Returns the summed relative frequency per year of all words in WORDS. */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, DEFAULT_START_YEAR, DEFAULT_END_YEAR);
    }

    /** Provides the summed relative frequency per year of all words in WORDS
     *  between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     *  this time frame, ignore it rather than throwing an exception. */
    public TimeSeries summedWeightHistory(Collection<String> words,
                              int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        for (String word: words) {
            ts = ts.plus(new TimeSeries(wordsMap.get(word), startYear, endYear));
        }
        return ts.dividedBy(countsTS);
    }
}
