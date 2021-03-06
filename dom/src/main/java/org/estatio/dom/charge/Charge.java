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
package org.estatio.dom.charge;

import org.estatio.dom.EstatioRefDataObject;
import org.estatio.dom.WithCodeGetter;
import org.estatio.dom.WithDescriptionGetter;
import org.estatio.dom.WithReferenceGetter;
import org.estatio.dom.tax.Tax;

import org.apache.isis.applib.annotation.Bounded;
import org.apache.isis.applib.annotation.Immutable;
import org.apache.isis.applib.annotation.Title;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Query(
        name = "findByReference", language = "JDOQL",
        value = "SELECT " +
                "FROM org.estatio.dom.charge.Charge " +
                "WHERE reference.matches(:reference)")
@Bounded
@Immutable
public class Charge extends EstatioRefDataObject<Charge> implements WithReferenceGetter, WithCodeGetter, WithDescriptionGetter {

    public Charge() {
        super("code");
    }

    // //////////////////////////////////////

    private String reference;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence = "1")
    public String getReference() {
        return reference;
    }

    public void setReference(final String reference) {
        this.reference = reference;
    }

    // //////////////////////////////////////

    private String code;

    @javax.jdo.annotations.Unique(name = "CHARGE_CODE_UNIQUE_IDX")
    @javax.jdo.annotations.Column(allowsNull="false")
    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    // //////////////////////////////////////

    private Tax tax;

    @javax.jdo.annotations.Column(name = "TAX_ID", allowsNull="false")
    public Tax getTax() {
        return tax;
    }

    public void setTax(final Tax tax) {
        this.tax = tax;
    }

    // //////////////////////////////////////

    private String description;

    @javax.jdo.annotations.Column(allowsNull="false")
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    // //////////////////////////////////////

    private ChargeGroup group;

    // REVIEW: Api#putCharge() does not seem to populate this? (hence not mandatory)
    @javax.jdo.annotations.Column(name = "GROUP_ID", allowsNull="true")
    public ChargeGroup getGroup() {
        return group;
    }

    public void setGroup(final ChargeGroup group) {
        this.group = group;
    }

}
