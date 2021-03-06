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
package org.estatio.dom.index;

import java.math.BigDecimal;
import java.util.SortedSet;
import java.util.TreeSet;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Immutable;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Title;

import org.estatio.dom.Chained;
import org.estatio.dom.EstatioRefDataObject;
import org.estatio.dom.WithStartDate;

@javax.jdo.annotations.PersistenceCapable
@Immutable
public class IndexBase extends EstatioRefDataObject<IndexBase> implements WithStartDate, Chained<IndexBase> {

    public IndexBase() {
        super("index, startDate desc");
    }
    
    // //////////////////////////////////////

    private Index index;
    
    @javax.jdo.annotations.Column(name="INDEX_ID", allowsNull="false")
    @Title(sequence = "1", append = ", ")
    public Index getIndex() {
        return index;
    }

    public void setIndex(final Index index) {
        this.index = index;
    }

    // //////////////////////////////////////

    @javax.jdo.annotations.Persistent
    private LocalDate startDate;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence = "2")
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    public void modifyStartDate(final LocalDate startDate) {
        final LocalDate currentStartDate = getStartDate();
        if (startDate == null || startDate.equals(currentStartDate)) {
            return;
        }
        setStartDate(startDate);
    }

    public void clearStartDate() {
        LocalDate currentStartDate = getStartDate();
        if (currentStartDate == null) {
            return;
        }
        setStartDate(null);
    }


    // //////////////////////////////////////

    @javax.jdo.annotations.Persistent
    @javax.jdo.annotations.Column(scale = 4)
    private BigDecimal factor;

    @Optional
    public BigDecimal getFactor() {
        return factor;
    }

    public void setFactor(final BigDecimal factor) {
        this.factor = factor;
    }

    public String validateFactor(final BigDecimal factor) {
        return (getPrevious() == null) ? null : (factor == null || factor.compareTo(BigDecimal.ZERO) == 0) ? "Factor is mandatory when there is a previous base" : null;
    }

    // //////////////////////////////////////

    @javax.jdo.annotations.Column(name="PREVIOUS_ID")
    @javax.jdo.annotations.Persistent(mappedBy = "next")
    private IndexBase previous;

    /**
     * @see #getNext()
     */
    @Optional
    public IndexBase getPrevious() {
        return previous;
    }

    public void setPrevious(final IndexBase previous) {
        this.previous = previous;
    }
    
    public void modifyPrevious(IndexBase previous) {
        setPrevious(previous);
        if (previous != null) {
            previous.setNext(this);
        }
    }

    // //////////////////////////////////////

    @javax.jdo.annotations.Column(name="NEXT_ID")
    private IndexBase next;

    /**
     * @see #getPrevious()
     */
    @Optional
    public IndexBase getNext() {
        return next;
    }

    public void setNext(final IndexBase nextBase) {
        this.next = nextBase;
    }

    // //////////////////////////////////////

    @javax.jdo.annotations.Persistent(mappedBy = "indexBase")
    private SortedSet<IndexValue> values = new TreeSet<IndexValue>();

    public SortedSet<IndexValue> getValues() {
        return values;
    }

    public void setValues(final SortedSet<IndexValue> values) {
        this.values = values;
    }

    public void addToValues(final IndexValue value) {
        if (value == null || getValues().contains(value)) {
            return;
        }
        value.clearIndexBase();
        value.setIndexBase(this);
        getValues().add(value);
    }

    public void removeFromValues(final IndexValue value) {
        // check for no-op
        if (value == null || !getValues().contains(value)) {
            return;
        }
        value.setIndexBase(null);
        getValues().remove(value);
    }


    // //////////////////////////////////////

    @Programmatic
    public BigDecimal factorForDate(LocalDate date) {
        if (date.isBefore(getStartDate())) {
            return getFactor().multiply(getPrevious().factorForDate(date));
        }
        return BigDecimal.ONE;
    }

}
