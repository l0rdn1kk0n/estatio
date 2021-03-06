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

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;

import org.apache.isis.core.unittestsupport.comparable.ComparableContractTest_compareTo;

import org.estatio.dom.charge.Charge;


public class InvoiceItemTest_compareTo extends ComparableContractTest_compareTo<InvoiceItem> {

    private Invoice inv1;
    private Invoice inv2;

    private Charge chg1;
    private Charge chg2;

    @Before
    public void setUpParentInvoices() throws Exception {
        inv1 = new Invoice();
        inv2 = new Invoice();
        
        inv1.setInvoiceNumber("000001");
        inv2.setInvoiceNumber("000002");
        
        chg1 = new Charge();
        chg2 = new Charge();
        
        chg1.setCode("ABC");
        chg2.setCode("DEF");
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected List<List<InvoiceItem>> orderedTuples() {
        return listOf(
                listOf(
                        newInvoiceItem(null, null, null, null),
                        newInvoiceItem(inv1, null, null, null),
                        newInvoiceItem(inv1, null, null, null),
                        newInvoiceItem(inv2, null, null, null)
                        ),
                listOf(
                        newInvoiceItem(inv1, new LocalDate(2012,4,2), null, null),
                        newInvoiceItem(inv1, new LocalDate(2012,3,1), null, null),
                        newInvoiceItem(inv1, new LocalDate(2012,3,1), null, null),
                        newInvoiceItem(inv1, null, null, null)
                        ),
                listOf(
                        newInvoiceItem(inv1, new LocalDate(2012,3,1), null, null),
                        newInvoiceItem(inv1, new LocalDate(2012,3,1), chg1, null),
                        newInvoiceItem(inv1, new LocalDate(2012,3,1), chg1, null),
                        newInvoiceItem(inv1, new LocalDate(2012,3,1), chg2, null)
                        ),
                listOf(
                        newInvoiceItem(inv1, new LocalDate(2012,3,1), chg1, null),
                        newInvoiceItem(inv1, new LocalDate(2012,3,1), chg1, "ABC"),
                        newInvoiceItem(inv1, new LocalDate(2012,3,1), chg1, "ABC"),
                        newInvoiceItem(inv1, new LocalDate(2012,3,1), chg1, "DEF")
                        )
                );
    }

    private InvoiceItem newInvoiceItem(
            Invoice invoice,
            LocalDate startDate,
            Charge charge,
            String description) {
        final InvoiceItem ii = new InvoiceItemForTesting();
        ii.setInvoice(invoice);
        ii.setStartDate(startDate);
        ii.setCharge(charge);
        ii.setDescription(description);
        return ii;
    }

}
