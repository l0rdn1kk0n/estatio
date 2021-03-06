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
package org.estatio.dom.geography;

import javax.jdo.annotations.DiscriminatorStrategy;

import org.apache.isis.applib.annotation.Immutable;
import org.apache.isis.applib.annotation.Title;

import org.estatio.dom.EstatioRefDataObject;
import org.estatio.dom.WithNameUnique;
import org.estatio.dom.WithReferenceComparable;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Discriminator(strategy = DiscriminatorStrategy.CLASS_NAME)
@javax.jdo.annotations.Query(
        name = "findGeographyByReference", language = "JDOQL", value = "SELECT FROM org.estatio.dom.geography.Geography WHERE reference == :reference") 
@Immutable
public abstract class Geography extends EstatioRefDataObject<Geography> implements WithReferenceComparable<Geography>, WithNameUnique {

    public Geography() {
        super("reference");
    }
    

    // //////////////////////////////////////

    @javax.jdo.annotations.Unique(name = "GEOGRAPHY_REFERENCE_UNIQUE_IDX")
    private String reference;

    /**
     * As per ISO standards for <a href=
     * "http://www.commondatahub.com/live/geography/country/iso_3166_country_codes"
     * >countries</a> and <a href=
     * "http://www.commondatahub.com/live/geography/state_province_region/iso_3166_2_state_codes"
     * >states</a>.
     */
    @javax.jdo.annotations.Column(allowsNull="false")
    public String getReference() {
        return reference;
    }

    public void setReference(final String reference) {
        this.reference = reference;
    }


    // //////////////////////////////////////

    @javax.jdo.annotations.Unique(name = "GEOGRAPHY_NAME_UNIQUE_IDX")
    private String name;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
