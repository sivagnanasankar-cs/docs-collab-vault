package com.doccollab.permissions.dao;

import com.doccollab.permissions.models.Permission;
import com.doccollab.utils.db.BaseDAO;
import com.doccollab.utils.db.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class PermissionDAO extends BaseDAO<Permission> {

    public List<Permission> findByUser(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Permission> query = session.createQuery("FROM Permission WHERE granteeType = 'USER' AND granteeId = :userId", Permission.class);
            query.setParameter("userId", userId);
            return query.list();
        }
    }
}