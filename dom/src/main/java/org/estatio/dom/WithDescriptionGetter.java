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
package org.estatio.dom;

import org.apache.isis.applib.util.ObjectContracts.ToStringEvaluator;

public interface WithDescriptionGetter {

    public String getDescription();
    
    public static class ToString {
        private ToString() {}
        public static ToStringEvaluator evaluator() {
            return new ToStringEvaluator() {
                @Override
                public boolean canEvaluate(Object o) {
                    return o instanceof WithDescriptionGetter;
                }
                
                @Override
                public String evaluate(Object o) {
                    return ((WithDescriptionGetter)o).getDescription();
                }
            };
        }
    }

}
