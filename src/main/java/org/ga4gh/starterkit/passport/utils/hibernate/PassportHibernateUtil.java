package org.ga4gh.starterkit.passport.utils.hibernate;

import java.util.ArrayList;
import org.ga4gh.starterkit.common.hibernate.HibernateUtil;
import org.ga4gh.starterkit.passport.model.PassportUser;
import org.ga4gh.starterkit.passport.model.PassportVisa;
import org.ga4gh.starterkit.passport.model.PassportVisaAssertion;

public class PassportHibernateUtil extends HibernateUtil {

    public PassportHibernateUtil() {
        super();
        setAnnotatedClasses(new ArrayList<>(){{
            add(PassportUser.class);
            add(PassportVisa.class);
            add(PassportVisaAssertion.class);
        }});
    }
}
