/*
 *
 *  Copyright 2012-2013 Eurocommercial Properties NV
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.estatio.dom.valuetypes;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

public final class LocalDateInterval {

    private static final long OPEN_START_INSTANT = Long.MIN_VALUE;
    private static final long OPEN_END_INSTANT = Long.MAX_VALUE;
    private long startInstant;
    private long endInstant;
    private IntervalEnding ending = IntervalEnding.INCLUDING_END_DATE;

    private enum IntervalEnding {
        INCLUDING_END_DATE, EXCLUDING_END_DATE
    }

    public static LocalDateInterval excluding(LocalDate startDate, LocalDate endDate) {
        return new LocalDateInterval(startDate, endDate, IntervalEnding.EXCLUDING_END_DATE);
    }

    public static LocalDateInterval including(LocalDate startDate, LocalDate endDate) {
        return new LocalDateInterval(startDate, endDate, IntervalEnding.INCLUDING_END_DATE);
    }

    public LocalDateInterval(LocalDate startDate, LocalDate endDate, IntervalEnding ending) {
        this.ending = ending;
        startInstant = startDate == null ? OPEN_START_INSTANT : startDate.toInterval().getStartMillis();
        endInstant = endDate == null ? OPEN_END_INSTANT : ending == IntervalEnding.EXCLUDING_END_DATE ? endDate.toInterval().getStartMillis() : endDate.toInterval().getEndMillis();
    }

    public LocalDateInterval(Interval interval) {
        if (interval != null) {
            startInstant = interval.getStartMillis();
            endInstant = interval.getEndMillis();
        }
    }

    public Interval asInterval() {
        return new Interval(startInstant, endInstant);
    }

    public LocalDate startDate() {
        if (startInstant == OPEN_START_INSTANT || startInstant == 0)
            return null;
        return new LocalDate(startInstant);
    }

    public LocalDate endDate() {
        return endDate(ending);
    }

    public LocalDate endDate(IntervalEnding ending) {
        // REVIEW: is the following line correct, using startInstant?  looks like a copy-n-paste error?
        // if it isn't, please replace this text with an explanatory comment...
        if (endInstant == OPEN_END_INSTANT || startInstant == 0)
            return null;
        LocalDate date = new LocalDate(endInstant);
        return adjustDate(date, ending);
    }

    public LocalDate endDateExcluding() {
        return endDate(IntervalEnding.EXCLUDING_END_DATE);
    }

    public LocalDate endDateFromStartDate() {
        return adjustDate(startDate(), ending);
    }

    private LocalDate adjustDate(LocalDate date, IntervalEnding ending) {
        return ending == IntervalEnding.INCLUDING_END_DATE ? date.minusDays(1) : date;

    }

    /**
     * Does this time interval contain the specified time interval.
     * 
     * @param localDateInterval
     * @return
     */
    public boolean contains(LocalDateInterval localDateInterval) {
        return asInterval().contains(localDateInterval.asInterval());
    }

    /**
     * Does this date contain the specified time interval.
     * 
     * @param localDate
     * @return
     */
    public boolean contains(LocalDate localDate) {
        return asInterval().contains(localDate.toInterval());
    }

    /**
     * Does this time interval contain the specified time interval.
     * 
     * @param interval
     * @return
     */
    public boolean overlaps(LocalDateInterval interval) {
        return asInterval().overlaps(interval.asInterval());
    }

    /**
     * Gets the overlap between this interval and another interval.
     * 
     * @param interval
     * @return
     */
    public LocalDateInterval overlap(LocalDateInterval interval) {
        Interval interval2 = asInterval().overlap(interval.asInterval());
        if (interval2 == null)
            return null;
        return new LocalDateInterval(interval2);
    }

    /**
     * Does this interval is within the specified interval
     * 
     * @param interval
     * @return
     */
    public boolean within(LocalDateInterval interval) {
        return interval.asInterval().contains(asInterval());
    }

    public int days() {
        Period p = new Period(asInterval(), PeriodType.days());
        return p.getDays();
    }

}
