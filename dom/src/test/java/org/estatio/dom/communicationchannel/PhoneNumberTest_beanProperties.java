package org.estatio.dom.communicationchannel;

import org.estatio.dom.communicationchannel.PhoneNumber;
import org.junit.Test;

import com.danhaywood.testsupport.coverage.PojoTester;
import com.danhaywood.testsupport.coverage.PojoTester.FilterSet;

public class PhoneNumberTest_beanProperties {

	@Test
	public void test() {
		new PojoTester().exercise(new PhoneNumber(), FilterSet.excluding("container"));
	}

}