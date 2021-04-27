package org.ga4gh.starterkit.passport.configuration;

import org.ga4gh.starterkit.common.hibernate.HibernateProps;

public class PassportConfig {

    private HibernateProps hibernateProps;

    public PassportConfig() {
        hibernateProps = new HibernateProps();
    }

    public void setHibernateProps(HibernateProps hibernateProps) {
        this.hibernateProps = hibernateProps;
    }

    public HibernateProps getHibernateProps() {
        return hibernateProps;
    }
}
