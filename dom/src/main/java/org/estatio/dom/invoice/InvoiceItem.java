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
package org.estatio.dom.invoice;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;

import org.estatio.dom.EstatioTransactionalObject;
import org.estatio.dom.Status;
import org.estatio.dom.WithDescriptionGetter;
import org.estatio.dom.WithInterval;
import org.estatio.dom.charge.Charge;
import org.estatio.dom.charge.Charges;
import org.estatio.dom.tax.Tax;
import org.estatio.dom.valuetypes.LocalDateInterval;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Discriminator(strategy = DiscriminatorStrategy.CLASS_NAME)
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "VERSION")
@Bookmarkable(BookmarkPolicy.AS_CHILD)
public abstract class InvoiceItem extends EstatioTransactionalObject<InvoiceItem, Status> implements WithInterval<InvoiceItem>, WithDescriptionGetter {

    public InvoiceItem() {
        super("invoice, startDate desc nullsLast, charge, description, sequence", null, null);
    }

    // //////////////////////////////////////

    private Status status;

    // @javax.jdo.annotations.Column(allowsNull="false")
    @Optional

    @Hidden
    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(final Status status) {
        this.status = status;
    }

    // //////////////////////////////////////

    private BigInteger sequence;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Hidden
    public BigInteger getSequence() {
        return sequence;
    }

    public void setSequence(BigInteger sequence) {
        this.sequence = sequence;
    }

    // //////////////////////////////////////

    private Invoice invoice;

    // REVIEW: this is optional because of the #remove() method, 
    // also because the ordering of flushes in #attachToInvoice()
    //
    // suspect this should be mandatory, however (ie get rid of #remove(),
    // and refactor #attachToInvoice())
    @javax.jdo.annotations.Column(name = "INVOICE_ID", allowsNull="true")
    @Render(Type.EAGERLY)
    @Disabled
    @Hidden(where = Where.REFERENCES_PARENT)
    @Title(sequence = "1", append = ":")
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(final Invoice invoice) {
        this.invoice = invoice;
    }

    // //////////////////////////////////////

    private Charge charge;

    @javax.jdo.annotations.Column(name = "CHARGE_ID", allowsNull="true")
    @Title(sequence = "2")
    @Disabled
    public Charge getCharge() {
        return charge;
    }

    public void setCharge(final Charge charge) {
        this.charge = charge;
    }

    public List<Charge> choicesCharge() {
        return charges.allCharges();

    }

    // //////////////////////////////////////

    private BigDecimal quantity;

    @javax.jdo.annotations.Column(scale = 2, allowsNull="true")
    @Disabled
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(final BigDecimal quantity) {
        this.quantity = quantity;
    }

    // //////////////////////////////////////

    private BigDecimal netAmount;

    @javax.jdo.annotations.Column(scale = 2, allowsNull="true")
    @Disabled
    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(final BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public BigDecimal defaultNetAmount() {
        return BigDecimal.ZERO;
    }

    // //////////////////////////////////////

    private BigDecimal vatAmount;

    @javax.jdo.annotations.Column(scale = 2, allowsNull="true")
    @Hidden(where = Where.PARENTED_TABLES)
    @Disabled
    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(final BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }

    // //////////////////////////////////////

    private BigDecimal grossAmount;

    @javax.jdo.annotations.Column(scale = 2, allowsNull="true")
    @Disabled
    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(final BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    // //////////////////////////////////////

    private Tax tax;

    @javax.jdo.annotations.Column(name = "TAX_ID", allowsNull="true")
    @Hidden(where = Where.PARENTED_TABLES)
    @Disabled
    public Tax getTax() {
        return tax;
    }

    public void setTax(final Tax tax) {
        this.tax = tax;
    }

    // //////////////////////////////////////

    private String description;

    @javax.jdo.annotations.Column(allowsNull="true")
    @Hidden(where = Where.PARENTED_TABLES)
    @Disabled
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    // //////////////////////////////////////

    @javax.jdo.annotations.Persistent
    private LocalDate dueDate;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Disabled
    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(final LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    // //////////////////////////////////////

    @javax.jdo.annotations.Persistent
    private LocalDate startDate;

    @Disabled
    @Optional
    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    @javax.jdo.annotations.Persistent
    private LocalDate endDate;

    @Disabled
    @Optional
    @Override
    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }

    // //////////////////////////////////////

    @Hidden
    @Override
    public WithInterval<?> getWithIntervalParent() {
        return null;
    }

    @Hidden
    @Override
    public LocalDate getEffectiveStartDate() {
        return WithInterval.Util.effectiveStartDateOf(this);
    }

    @Hidden
    @Override
    public LocalDate getEffectiveEndDate() {
        return WithInterval.Util.effectiveEndDateOf(this);
    }

    @Programmatic
    @Override
    public LocalDateInterval getInterval() {
        return LocalDateInterval.including(getStartDate(), getEndDate());
    }

    // //////////////////////////////////////

    public boolean isCurrent() {
        return isActiveOn(getClockService().now());
    }

    private boolean isActiveOn(LocalDate localDate) {
        return getInterval().contains(localDate);
    }

    // //////////////////////////////////////

    /**
     * Attaches this item to an invoice with similar attributes. Creates a new
     * invoice when no matching found.
     */
    public abstract void attachToInvoice();

    // //////////////////////////////////////

    @Bulk
    public InvoiceItem verify() {
        calculateTax();
        return this;
    }

    // //////////////////////////////////////

    @Programmatic
    public void remove() {
        // no safeguard, assuming being called with precaution
        setInvoice(null);
        getContainer().flush();
        getContainer().remove(this);
    }

    @Programmatic
    private void calculateTax() {
        BigDecimal vatAmount = BigDecimal.ZERO;
        if (getTax() != null) {
            BigDecimal percentage = tax.percentageFor(getDueDate());
            if (percentage != null) {
                BigDecimal rate = percentage.divide(BigDecimal.valueOf(100));
                vatAmount = getNetAmount().multiply(rate).setScale(2, RoundingMode.HALF_UP);
            }
        }
        BigDecimal currentVatAmount = getVatAmount();
        if (currentVatAmount == null || vatAmount.compareTo(currentVatAmount) != 0) {
            setVatAmount(vatAmount);
            setGrossAmount(getNetAmount().add(vatAmount));
        }
    }

    @Programmatic
    public void initialize() {
        // set defaults
        setVatAmount(BigDecimal.ZERO);
        setGrossAmount(BigDecimal.ZERO);
        setNetAmount(BigDecimal.ZERO);
    }

    // //////////////////////////////////////

    /**
     * Lifecycle
     */
    public void created() {
        initialize();
    }

    // //////////////////////////////////////

    private Charges charges;

    public final void injectCharges(Charges charges) {
        this.charges = charges;
    }

}