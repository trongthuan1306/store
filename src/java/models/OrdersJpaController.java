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
public class OrdersJpaController implements Serializable {

    public OrdersJpaController() {
        emf = Persistence.createEntityManagerFactory("ProductionPU");
    }

    public OrdersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orders orders) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Accounts account = orders.getAccount();
            if (account != null) {
                account = em.getReference(account.getClass(), account.getAccount());
                orders.setAccount(account);
            }
            em.persist(orders);
            if (account != null) {
                account.getOrdersCollection().add(orders);
                account = em.merge(account);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orders orders) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orders persistentOrders = em.find(Orders.class, orders.getOrderId());
            Accounts accountOld = persistentOrders.getAccount();
            Accounts accountNew = orders.getAccount();
            if (accountNew != null) {
                accountNew = em.getReference(accountNew.getClass(), accountNew.getAccount());
                orders.setAccount(accountNew);
            }
            orders = em.merge(orders);
            if (accountOld != null && !accountOld.equals(accountNew)) {
                accountOld.getOrdersCollection().remove(orders);
                accountOld = em.merge(accountOld);
            }
            if (accountNew != null && !accountNew.equals(accountOld)) {
                accountNew.getOrdersCollection().add(orders);
                accountNew = em.merge(accountNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orders.getOrderId();
                if (findOrders(id) == null) {
                    throw new NonexistentEntityException("The orders with id " + id + " no longer exists.");
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
            Orders orders;
            try {
                orders = em.getReference(Orders.class, id);
                orders.getOrderId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orders with id " + id + " no longer exists.", enfe);
            }
            Accounts account = orders.getAccount();
            if (account != null) {
                account.getOrdersCollection().remove(orders);
                account = em.merge(account);
            }
            em.remove(orders);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orders> findOrdersEntities() {
        return findOrdersEntities(true, -1, -1);
    }

    public List<Orders> findOrdersEntities(int maxResults, int firstResult) {
        return findOrdersEntities(false, maxResults, firstResult);
    }

    private List<Orders> findOrdersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orders> rt = cq.from(Orders.class);
            cq.select(rt);
            cq.orderBy(em.getCriteriaBuilder().desc(rt.get("createdDate")), em.getCriteriaBuilder().desc(rt.get("orderId")));
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

    public Orders findOrders(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orders.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orders> rt = cq.from(Orders.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Orders> findByAccount(String acc) {

        EntityManager em = getEntityManager();

        try {

            return em.createQuery(
                    "SELECT o FROM Orders o WHERE o.account.account = :acc ORDER BY o.createdDate DESC",
                    Orders.class
            )
                    .setParameter("acc", acc)
                    .getResultList();

        } finally {
            em.close();
        }
    }
    public List<Orders> findOrdersByRoles(List<Integer> roles) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT o FROM Orders o WHERE o.account.roleInSystem IN :roles ORDER BY o.createdDate DESC",
                    Orders.class
            )
                    .setParameter("roles", roles)
                    .getResultList();
        } finally {
            em.close();
        }

    }
}
