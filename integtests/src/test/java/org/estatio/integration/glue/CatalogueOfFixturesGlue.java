/*
 *  Copyright 2012-2013 Eurocommercial Properties NV
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
package org.estatio.integration.glue;

import cucumber.api.java.Before;

import org.apache.isis.core.specsupport.specs.CukeGlueAbstract;

import org.estatio.fixture.EstatioTransactionalObjectsFixture;
import org.estatio.fixture.EstatioTransactionalObjectsTeardownFixture;
import org.estatio.fixture.asset.PropertiesAndUnitsFixture;
import org.estatio.fixture.lease.LeasesAndRolesAndLeaseUnitsAndTagsFixture;
import org.estatio.fixture.party.PersonsAndOrganisationsAndBankAccountsAndCommunicationChannelsFixture;

/**
 * A catalogue of different fixtures for features to use;
 * select by specifying the appropriate tag.
 */
public class CatalogueOfFixturesGlue extends CukeGlueAbstract {

    @Before({"@integration", "@EstatioTransactionalObjectsFixture"})
    public void beforeScenarioEstatioTransactionalObjectsFixture() {
        scenarioExecution().install(new EstatioTransactionalObjectsFixture());
    }
    
    @Before({"@integration", "@LeasesOnlyFixture"})
    public void beforeScenarioLeasesOnlyFixture() {
        scenarioExecution().install(
                new EstatioTransactionalObjectsTeardownFixture(),
                new PersonsAndOrganisationsAndBankAccountsAndCommunicationChannelsFixture(),
                new PropertiesAndUnitsFixture(),
                new LeasesAndRolesAndLeaseUnitsAndTagsFixture()
                // no lease items or terms
                // no invoices or invoice items
            );
    }

}
