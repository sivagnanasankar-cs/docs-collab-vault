package com.doccollab.auth.dao;

import com.doccollab.auth.models.OAuthClient;
import com.doccollab.utils.db.BaseDAO;
import com.doccollab.utils.db.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class OAuthClientDAO extends BaseDAO<OAuthClient> {

    public OAuthClient findByClientId(String clientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<OAuthClient> query = session.createQuery("FROM OAuthClient WHERE clientId = :clientId", OAuthClient.class);
            query.setParameter("clientId", clientId);
            return query.uniqueResult();
        }
    }
}