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
package org.estatio.dom.agreement;

import org.estatio.dom.EstatioDomainService;
import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.Hidden;

@Hidden
public class AgreementRoleCommunicationChannels extends EstatioDomainService<AgreementRoleCommunicationChannel> {

    public AgreementRoleCommunicationChannels() {
        super(AgreementRoleCommunicationChannels.class, AgreementRoleCommunicationChannel.class);
    }

    @Hidden
    @ActionSemantics(Of.SAFE)
    public AgreementRoleCommunicationChannel findByRoleAndTypeAndContainsDate(AgreementRole role, AgreementRoleCommunicationChannelType type, LocalDate date) {
        return firstMatch("findByRoleAndTypeAndContainsDate", "role", role, "type", type, "date", date);
    }

}
