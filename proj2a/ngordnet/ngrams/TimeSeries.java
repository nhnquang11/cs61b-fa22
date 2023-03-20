package ngordnet.ngrams;

import java.util.*;

/** An object for mapping a year number (e.g. 1996) to numerical data. Provides
 *  utility methods useful for data analysis.
 *  @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     *  inclusive of both end points. */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for (int year: ts.keySet()) {
            if (year >= startYear && year <= endYear) {
                this.put(year, ts.get(year));
            }
        }
    }

    /** Returns all years for this TimeSeries (in any order). */
    public List<Integer> years() {
        List<Integer> years = new ArrayList<>();
        for (int year: this.keySet()) {
            years.add(year);
        }
        return years;
    }

    /** Returns all data for this TimeSeries (in any order).
     *  Must be in the same order as years(). */
    public List<Double> data() {
        List<Double> data = new ArrayList<>();
        for (int year: this.keySet()) {
            data.add(this.get(year));
        }
        return data;
    }

    /** Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     *  each year, sum the data from this TimeSeries with the data from TS. Should return a
     *  new TimeSeries (does not modify this TimeSeries). */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries newTS = new TimeSeries();
        for (int year: this.keySet()) {
            newTS.put(year, this.get(year));
        }
        for (int year: ts.keySet()) {
            double tsValue = ts.get(year);
            if (newTS.containsKey(year)) {
                newTS.put(year, newTS.get(year) + tsValue);
            } else {
                newTS.put(year, tsValue);
            }
        }
        return newTS;
    }

     /** Returns the quotient of the value for each year this TimeSeries divided by the
      *  value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
      *  throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
      *  Should return a new TimeSeries (does not modify this TimeSeries). */
     public TimeSeries dividedBy(TimeSeries ts) {
         TimeSeries returnTS = new TimeSeries();
         for (int year: this.keySet()) {
             if (!ts.containsKey(year)) {
                 throw new IllegalArgumentException("The ts time series is missing year " + year + "!");
             }
             returnTS.put(year, this.get(year) / ts.get(year));
         }
        return returnTS;
    }
}
