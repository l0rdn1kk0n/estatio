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
package org.estatio.dom.asset;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import com.danhaywood.isis.wicket.gmap3.applib.Location;
import com.danhaywood.isis.wicket.gmap3.service.LocationLookupService;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2.Mode;

import org.estatio.dom.party.Party;
import org.estatio.dom.party.PartyForTesting;

public class FixedAssetTest_lookupLocation {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(Mode.INTERFACES_AND_CLASSES);

    @Mock
    private LocationLookupService mockLocationLookupService;
    
    private FixedAsset fixedAsset;
    
    @Before
    public void setUp() throws Exception {
        fixedAsset = new FixedAssetForTesting();
        fixedAsset.injectLocationLookupService(mockLocationLookupService);
    }
    
    @Test
    public void test() {
        // given
        assertThat(fixedAsset.getLocation(), is(nullValue()));
        
        // when
        final Location location = new Location();
        context.checking(new Expectations() {
            {
                oneOf(mockLocationLookupService).lookup("Buckingham Palace, London");
                will(returnValue(location));
            }
        });
        
        fixedAsset.lookupLocation("Buckingham Palace, London");
        
        // then
        assertThat(fixedAsset.getLocation(), is(location));
    }
    
}
