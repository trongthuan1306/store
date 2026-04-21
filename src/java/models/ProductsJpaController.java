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
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.exceptions.NonexistentEntityException;
import models.exceptions.PreexistingEntityException;

/**
 *
 * @author ASUS
 */
public class ProductsJpaController implements Serializable {

    public ProductsJpaController() {
        emf = Persistence.createEntityManagerFactory("ProductionPU");
    }

    public ProductsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Products products) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Accounts account = products.getAccount();
            if (account != null) {
                account = em.getReference(account.getClass(), account.getAccount());
                products.setAccount(account);
            }
            Categories typeId = products.getTypeId();
            if (typeId != null) {
                typeId = em.getReference(typeId.getClass(), typeId.getTypeId());
                products.setTypeId(typeId);
            }
            em.persist(products);
            if (account != null) {
                account.getProductsCollection().add(products);
                account = em.merge(account);
            }
            if (typeId != null) {
                typeId.getProductsCollection().add(products);
                typeId = em.merge(typeId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProducts(products.getProductId()) != null) {
                throw new PreexistingEntityException("Products " + products + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Products products) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Products persistentProducts = em.find(Products.class, products.getProductId());
            Accounts accountOld = persistentProducts.getAccount();
            Accounts accountNew = products.getAccount();
            Categories typeIdOld = persistentProducts.getTypeId();
            Categories typeIdNew = products.getTypeId();
            if (accountNew != null) {
                accountNew = em.getReference(accountNew.getClass(), accountNew.getAccount());
                products.setAccount(accountNew);
            }
            if (typeIdNew != null) {
                typeIdNew = em.getReference(typeIdNew.getClass(), typeIdNew.getTypeId());
                products.setTypeId(typeIdNew);
            }
            products = em.merge(products);
            if (accountOld != null && !accountOld.equals(accountNew)) {
                accountOld.getProductsCollection().remove(products);
                accountOld = em.merge(accountOld);
            }
            if (accountNew != null && !accountNew.equals(accountOld)) {
                accountNew.getProductsCollection().add(products);
                accountNew = em.merge(accountNew);
            }
            if (typeIdOld != null && !typeIdOld.equals(typeIdNew)) {
                typeIdOld.getProductsCollection().remove(products);
                typeIdOld = em.merge(typeIdOld);
            }
            if (typeIdNew != null && !typeIdNew.equals(typeIdOld)) {
                typeIdNew.getProductsCollection().add(products);
                typeIdNew = em.merge(typeIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = products.getProductId();
                if (findProducts(id) == null) {
                    throw new NonexistentEntityException("The products with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Products products;
            try {
                products = em.getReference(Products.class, id);
                products.getProductId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The products with id " + id + " no longer exists.", enfe);
            }
            Accounts account = products.getAccount();
            if (account != null) {
                account.getProductsCollection().remove(products);
                account = em.merge(account);
            }
            Categories typeId = products.getTypeId();
            if (typeId != null) {
                typeId.getProductsCollection().remove(products);
                typeId = em.merge(typeId);
            }
            em.remove(products);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Products> findProductsEntities() {
        return findProductsEntities(true, -1, -1);
    }

    public List<Products> findProductsEntities(int maxResults, int firstResult) {
        return findProductsEntities(false, maxResults, firstResult);
    }

    private List<Products> findProductsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Products.class));
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

    public List<Products> findNewestProducts() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Products> rt = cq.from(Products.class);
            cq.select(rt);
            cq.orderBy(em.getCriteriaBuilder().desc(rt.get("postedDate")), em.getCriteriaBuilder().desc(rt.get("productId")));
            TypedQuery<Products> q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Products findProducts(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Products.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Products> rt = cq.from(Products.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Products> findProductsByName(String name) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Products> query = em.createQuery(
                    "SELECT p FROM Products p WHERE p.productName LIKE :name",
                    Products.class
            );
            query.setParameter("name", "%" + name + "%");

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Products> findByCategory(int cateId) {

        EntityManager em = getEntityManager();

        try {

            return em.createQuery(
                    "SELECT p FROM Products p WHERE p.typeId.typeId = :cid ORDER BY p.postedDate DESC, p.productId DESC",
                    Products.class
            )
                    .setParameter("cid", cateId)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    public List<Products> filterByPrice(double min, double max) {
        EntityManager em = getEntityManager();

        String jpql = "SELECT p FROM Products p WHERE p.price BETWEEN :min AND :max";

        TypedQuery<Products> q = em.createQuery(jpql, Products.class);

        q.setParameter("min", min);
        q.setParameter("max", max);

        return q.getResultList();
    }

    public List<Products> getDiscountProducts() {
        EntityManager em = getEntityManager();

        String jpql = "SELECT p FROM Products p WHERE p.discount > 0";

        return em.createQuery(jpql, Products.class).getResultList();
    }

    public List<Products> getNoDiscountProducts() {
        EntityManager em = getEntityManager();

        String jpql = "SELECT p FROM Products p WHERE p.discount = 0";

        return em.createQuery(jpql, Products.class).getResultList();
    }

    public List<Products> sortByPrice(String sort) {
        EntityManager em = getEntityManager();

        String jpql = "SELECT p FROM Products p ORDER BY p.price " + sort;

        return em.createQuery(jpql, Products.class).getResultList();
    }
}
