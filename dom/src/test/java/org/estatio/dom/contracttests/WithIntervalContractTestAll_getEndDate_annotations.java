package org.estatio.dom.contracttests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import com.google.common.base.Predicate;

import org.junit.Test;
import org.reflections.Reflections;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Optional;

import org.estatio.dom.WithInterval;


public class WithIntervalContractTestAll_getEndDate_annotations {

    @Test
    public void searchAndTest() throws Exception {
        
        Reflections reflections = new Reflections(Constants.packagePrefix);

        Set<Class<? extends WithInterval>> domainObjectClasses = reflections.getSubTypesOf(WithInterval.class);
        for (final Class<? extends WithInterval> subtype : domainObjectClasses) {
            if(subtype.isInterface() || subtype.isAnonymousClass() || subtype.isLocalClass() || subtype.isMemberClass()) {
                // skip (probably a testing class)
                continue;
            }
            
            System.out.println("checking " + subtype.getName());

            Predicate<? super Method> getEndDatePredicate = new Predicate<Method>() {
                public boolean apply(Method f) {
                    return f.getName().equals("getEndDate") && 
                           f.getParameterTypes().length == 0;
                }
            };
            final Method method = Reflections.getAllMethods(subtype, getEndDatePredicate).iterator().next();
            
            assertMethodAnnotated(subtype, method, Disabled.class);
            assertMethodAnnotated(subtype, method, Optional.class);
        }
    }

    private static void assertMethodAnnotated(final Class<? extends WithInterval> subtype, final Method method, final Class<? extends Annotation> annotationClass) {
        final Annotation annotation = method.getAnnotation(annotationClass);
        final String methodName = method.getName();
        assertThat(subtype.getName() + "#" +
        		methodName +
        		"() must be annotated as @" +
                annotationClass.getSimpleName(),  
                annotation, is(not(nullValue())));
    }

}