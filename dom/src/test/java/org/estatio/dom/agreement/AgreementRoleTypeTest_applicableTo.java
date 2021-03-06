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

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2.Mode;

import org.estatio.dom.agreement.AgreementRoleType;
import org.estatio.dom.agreement.AgreementRoleTypes;
import org.estatio.dom.agreement.AgreementType;

public class AgreementRoleTypeTest_applicableTo {

    private AgreementRoleType art;
    private AgreementType at;
    
    @Mock
    private AgreementRoleTypes mockAgreementRoleTypes;
    
    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(Mode.INTERFACES_AND_CLASSES);

    @Before
    public void setUp() throws Exception {
        at = new AgreementType();
        at.injectAgreementRoleTypes(mockAgreementRoleTypes);
        
        art = new AgreementRoleType();
        art.setAppliesTo(at);
    }
    

    @Test
    public void delegatesToService() {
        context.checking(new Expectations() {
            {
                oneOf(mockAgreementRoleTypes).findApplicableTo(at);
            }
        }
        );

        AgreementRoleType.applicableTo(at);
    }
    


}
