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

import org.apache.isis.core.unittestsupport.comparable.ComparableContractTest_compareTo;


public class CommunicationChannelTest_compareTo extends ComparableContractTest_compareTo<CommunicationChannel> {

    @SuppressWarnings("unchecked")
    @Override
    protected List<List<CommunicationChannel>> orderedTuples() {
        
        // the CCT enum is not in alphabetical order, as you can see
        return listOf(
                listOf(
                        newCommunicationChannel(null),
                        newCommunicationChannel(CommunicationChannelType.ACCOUNTING_POSTAL_ADDRESS),
                        newCommunicationChannel(CommunicationChannelType.ACCOUNTING_POSTAL_ADDRESS),
                        newCommunicationChannel(CommunicationChannelType.FAX_NUMBER)
                    ),
                listOf(
                    newCommunicationChannel(CommunicationChannelType.ACCOUNTING_POSTAL_ADDRESS),
                    newCommunicationChannel(CommunicationChannelType.POSTAL_ADDRESS),
                    newCommunicationChannel(CommunicationChannelType.POSTAL_ADDRESS),
                    newCommunicationChannel(CommunicationChannelType.ACCOUNTING_EMAIL_ADDRESS)
                   )
            );
    }

    private CommunicationChannel newCommunicationChannel(CommunicationChannelType type) {
        final CommunicationChannel cc = new CommunicationChannel(){
            public String getName() {
                return null;
            }};
        cc.setType(type);
        return cc;
    }

}
