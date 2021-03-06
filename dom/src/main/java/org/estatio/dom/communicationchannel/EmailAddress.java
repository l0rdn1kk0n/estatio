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

import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Mandatory;
import org.apache.isis.applib.annotation.Title;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy=InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Query(
        name = "findByEmailAddress", language = "JDOQL", 
        value = "SELECT FROM org.estatio.dom.communicationchannel.EmailAddress " + 
                "WHERE owner == :owner && " +
                "emailAddress == :emailAddress")
    @javax.jdo.annotations.Index(name="EMAILADDRESS_IDX", members={"emailAddress"})

public class EmailAddress extends CommunicationChannel {


    // //////////////////////////////////////

    private String emailAddress;

    @javax.jdo.annotations.Column(allowsNull="true")
    @Title
    @Mandatory
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(final String address) {
        this.emailAddress = address;
    }

}