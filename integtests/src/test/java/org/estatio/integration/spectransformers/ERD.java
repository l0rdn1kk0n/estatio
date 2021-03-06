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
package org.estatio.integration.spectransformers;

import cucumber.api.Transformer;

import org.apache.isis.core.specsupport.scenarios.ScenarioExecution;

import org.estatio.dom.EstatioRefDataObject;
import org.estatio.dom.agreement.AgreementRoleCommunicationChannelTypes;
import org.estatio.dom.agreement.AgreementRoleTypes;


/**
 * A set of Estatio-specific converters for {@link EstatioRefDataObject reference data} objects.
 * 
 * <p>
 * These converters look up from the repository only.
 */
public class ERD  {
    public static class AgreementRoleCommunicationChannelType extends Transformer<org.estatio.dom.agreement.AgreementRoleCommunicationChannelType> {
        @Override
        public org.estatio.dom.agreement.AgreementRoleCommunicationChannelType transform(String id) {
            return ScenarioExecution.current().service(AgreementRoleCommunicationChannelTypes.class).findByTitle(id);
        }
    }

    public static class AgreementRoleType extends Transformer<org.estatio.dom.agreement.AgreementRoleType> {
        @Override
        public org.estatio.dom.agreement.AgreementRoleType transform(String id) {
            return ScenarioExecution.current().service(AgreementRoleTypes.class).findByTitle(id);
        }
    }

    private ERD() {}

}