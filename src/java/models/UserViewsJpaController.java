/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.exceptions.NonexistentEntityException;

/**
 *
 * @author ASUS
 */
public class UserViewsJpaController implements Serializable {

    public UserViewsJpaController() {
        emf = Persistence.createEntityManagerFactory("ProductionPU");
    }

    public UserViewsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserViews userViews) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Accounts account = userViews.getAccount();
            if (account != null) {
                account = em.getReference(account.getClass(), account.getAccount());
                userViews.setAccount(account);
            }
            Products productId = userViews.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getProductId());
                userViews.setProductId(productId);
            }
            em.persist(userViews);
            if (account != null) {
                account.getUserViewsCollection().add(userViews);
                account = em.merge(account);
            }
            if (productId != null) {
                productId.getUserViewsCollection().add(userViews);
                productId = em.merge(productId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserViews userViews) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserViews persistentUserViews = em.find(UserViews.class, userViews.getViewId());
            Accounts accountOld = persistentUserViews.getAccount();
            Accounts accountNew = userViews.getAccount();
            Products productIdOld = persistentUserViews.getProductId();
            Products productIdNew = userViews.getProductId();
            if (accountNew != null) {
                accountNew = em.getReference(accountNew.getClass(), accountNew.getAccount());
                userViews.setAccount(accountNew);
            }
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getProductId());
                userViews.setProductId(productIdNew);
            }
            userViews = em.merge(userViews);
            if (accountOld != null && !accountOld.equals(accountNew)) {
                accountOld.getUserViewsCollection().remove(userViews);
                accountOld = em.merge(accountOld);
            }
            if (accountNew != null && !accountNew.equals(accountOld)) {
                accountNew.getUserViewsCollection().add(userViews);
                accountNew = em.merge(accountNew);
            }
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getUserViewsCollection().remove(userViews);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getUserViewsCollection().add(userViews);
                productIdNew = em.merge(productIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = userViews.getViewId();
                if (findUserViews(id) == null) {
                    throw new NonexistentEntityException("The userViews with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserViews userViews;
            try {
                userViews = em.getReference(UserViews.class, id);
                userViews.getViewId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userViews with id " + id + " no longer exists.", enfe);
            }
            Accounts account = userViews.getAccount();
            if (account != null) {
                account.getUserViewsCollection().remove(userViews);
                account = em.merge(account);
            }
            Products productId = userViews.getProductId();
            if (productId != null) {
                productId.getUserViewsCollection().remove(userViews);
                productId = em.merge(productId);
            }
            em.remove(userViews);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserViews> findUserViewsEntities() {
        return findUserViewsEntities(true, -1, -1);
    }

    public List<UserViews> findUserViewsEntities(int maxResults, int firstResult) {
        return findUserViewsEntities(false, maxResults, firstResult);
    }

    private List<UserViews> findUserViewsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserViews.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public UserViews findUserViews(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserViews.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserViewsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserViews> rt = cq.from(UserViews.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

  public List<Object[]> getUserSegments(){
        EntityManager em = getEntityManager();

    String jpql = "SELECT uv.account, AVG(p.price) " +
                  "FROM UserViews uv " +
                  "JOIN uv.productId p " +
                 
                  "GROUP BY uv.account";

    return em.createQuery(jpql).getResultList();
}
}
