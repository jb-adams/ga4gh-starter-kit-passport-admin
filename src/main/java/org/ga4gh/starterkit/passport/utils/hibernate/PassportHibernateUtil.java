package org.ga4gh.starterkit.passport.utils.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.ga4gh.starterkit.common.hibernate.HibernateEntity;
import org.ga4gh.starterkit.common.hibernate.HibernateUtil;
import org.ga4gh.starterkit.passport.model.PassportUser;
import org.ga4gh.starterkit.passport.model.PassportVisa;
import org.ga4gh.starterkit.passport.model.PassportVisaAssertion;
import org.hibernate.Session;

public class PassportHibernateUtil extends HibernateUtil {

    public PassportHibernateUtil() {
        super();
        setAnnotatedClasses(new ArrayList<>(){{
            add(PassportUser.class);
            add(PassportVisa.class);
            add(PassportVisaAssertion.class);
        }});
    }

    private <T extends HibernateEntity<? extends Serializable>> List<T> getEntityList(Class<T> entityClass) {
        Session session = newTransaction();
        List<T> entities = null;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(entityClass);
            criteria.from(entityClass);
            entities = session.createQuery(criteria).getResultList();
        } finally {
            endTransaction(session);
        }
        return entities;
    }

    public List<PassportUser> getPassportUsers() {
        return getEntityList(PassportUser.class);
    }

    public List<PassportVisa> getPassportVisas() {
        return getEntityList(PassportVisa.class);
    }
}
