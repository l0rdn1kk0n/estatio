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
package org.estatio.dom.communicationchannel;

import java.util.List;

import javax.jdo.annotations.InheritanceStrategy;

import org.estatio.dom.geography.Countries;
import org.estatio.dom.geography.Country;
import org.estatio.dom.geography.State;
import org.estatio.dom.geography.States;

import org.apache.isis.applib.annotation.Mandatory;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.TitleBuffer;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Query(
        name = "findByAddress", language = "JDOQL", 
        value = "SELECT FROM " +
                "org.estatio.dom.communicationchannel.CommunicationChannel " +
                "WHERE owner == :owner" +
                "&& address1 == :address1 " +
                "&& postalCode == :postalCode " +
                "&& city == :city " +
                "&& country == :country ")
@javax.jdo.annotations.Index(name="POSTAL_ADDRESS_IDX", members={"owner", "address1","postalCode","city","country"})
public class PostalAddress extends CommunicationChannel {

    private String address1;

    @javax.jdo.annotations.Column(allowsNull="true")
    @Mandatory
    @Title(sequence = "1", append = ", ")
    @Named("Address Line 1")
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(final String address1) {
        this.address1 = address1;
    }

    // //////////////////////////////////////

    private String address2;

    @javax.jdo.annotations.Column(allowsNull="true")
    @Title(sequence = "2", append = ", ")
    @Optional
    @Named("Address Line 2")
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(final String address2) {
        this.address2 = address2;
    }

    // //////////////////////////////////////

    private String postalCode;

    @javax.jdo.annotations.Column(allowsNull="true")
    @Mandatory
    @Title(sequence = "3", append = ", ")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    // //////////////////////////////////////

    private String city;

    @javax.jdo.annotations.Column(allowsNull="true")
    @Mandatory
    @Title(sequence = "4")
    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    // //////////////////////////////////////

    // TODO: Country does not render as dropdown
    // TODO: When no country has been selected, the UI renders
    // "no objects returned" and an ok button. After that it's impossible to to
    // a search again.
    private Country country;

    @javax.jdo.annotations.Column(name = "COUNTRY_ID", allowsNull="true")
    @Optional // TODO: make mandatory
    public Country getCountry() {
        return country;
    }

    public void setCountry(final Country country) {
        this.country = country;
    }

    public List<Country> choicesCountry() {
        return countries.allCountries();
    }

    public void modifyCountry(final Country country) {
        setCountry(country);
        if (getState() != null && getState().getCountry() != country) {
            setState(null);
        }
    }
    
    public void clearCountry() {
        setCountry(null);
        setState(null);
    }
    
    // //////////////////////////////////////

    private State state;

    @javax.jdo.annotations.Column(name = "STATE_ID", allowsNull="true")
    @Optional // TODO: make mandatory
    public State getState() {
        return state;
    }

    public void setState(final State state) {
        this.state = state;
    }

    public List<State> choicesState() {
        return states.findStatesByCountry(getCountry());
    }
    
    // //////////////////////////////////////

    private States states;

    public final void injectStates(final States states) {
        this.states = states;
    }

    private Countries countries;

    public final void injectCountries(Countries countries) {
        this.countries = countries;
    }

}
