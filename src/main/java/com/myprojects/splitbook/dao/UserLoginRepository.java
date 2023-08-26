package com.myprojects.splitbook.dao;

import com.myprojects.splitbook.entity.UserLogin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UserLoginRepository {

    @PersistenceContext
    EntityManager entityManager;

    public UserLogin getUserByEmail(String email)
    {
        TypedQuery<UserLogin> query = entityManager.createNamedQuery("query_find_by_email", UserLogin.class);
        query.setParameter("email",email);
        List<UserLogin> resultList = query.getResultList();

        if(resultList.isEmpty())
        {
            return null;
        }
        return resultList.get(0);
    }

    public UserLogin insertUser(UserLogin user)
    {
        return entityManager.merge(user);
    }
}
