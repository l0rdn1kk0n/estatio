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
package org.estatio.dom.lease;

import org.estatio.dom.AbstractBeanPropertiesTest;
import org.estatio.dom.Lockable;
import org.estatio.dom.PojoTester.FilterSet;
import org.estatio.dom.PojoTester.FixtureDatumFactory;
import org.estatio.dom.index.Index;

import org.junit.Ignore;
import org.junit.Test;

public class LeaseTermForIndexableRentTest_beanProperties extends AbstractBeanPropertiesTest {

	@Test
	public void test() {
	    newPojoTester()
	        .withFixture(pojos(LeaseItem.class))
	        .withFixture(pojos(LeaseTerm.class, LeaseTermForTesting.class))
	        .withFixture(pojos(Index.class))
            .withFixture(statii())
	        .exercise(new LeaseTermForIndexableRent(),
	                // TODO: bug in PojoTester; claims there's interference between 
	                // startDate and baseIndexStartDate, however are just the same datatype
	                FilterSet.excluding("baseIndexStartDate"));
	}

	
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static FixtureDatumFactory<Lockable> statii() {
        return new FixtureDatumFactory(Lockable.class, (Object[])LeaseTermStatus.values());
    }

}
