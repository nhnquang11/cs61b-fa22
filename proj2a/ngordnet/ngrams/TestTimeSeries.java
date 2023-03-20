package ngordnet.ngrams;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TestTimeSeries {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertEquals(expectedYears, totalPopulation.years());

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertEquals(expectedTotal.get(i), totalPopulation.data().get(i), 1E-10);
        }
    }

    @Test
    public void testDividedBy() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(1991, 2.0);
        ts1.put(1992, 12.0);
        ts1.put(1994, 20.0);

        TimeSeries ts2 = new TimeSeries();
        ts2.put(1991, 8.0);
        ts2.put(1992, 12.0);
        ts2.put(1994, 4.0);
        ts2.put(1995, 1.0);

        TimeSeries dividedTS = ts1.dividedBy(ts2);

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994));

        assertEquals(expectedYears, dividedTS.years());


        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(2.0/8.0, 12.0/12.0, 20.0/4.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertEquals(expectedTotal.get(i), dividedTS.data().get(i), 1E-10);
        }
    }
} 