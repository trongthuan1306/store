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
public class CartItemsJpaController implements Serializable {
     
    public CartItemsJpaController() {
         emf = Persistence.createEntityManagerFactory("ProductionPU"); 
    }
    public CartItemsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CartItems cartItems) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cart cartId = cartItems.getCartId();
            if (cartId != null) {
                cartId = em.getReference(cartId.getClass(), cartId.getCartId());
                cartItems.setCartId(cartId);
            }
            Products productId = cartItems.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getProductId());
                cartItems.setProductId(productId);
            }
            em.persist(cartItems);
            if (cartId != null) {
                cartId.getCartItemsCollection().add(cartItems);
                cartId = em.merge(cartId);
            }
            if (productId != null) {
                productId.getCartItemsCollection().add(cartItems);
                productId = em.merge(productId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CartItems cartItems) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CartItems persistentCartItems = em.find(CartItems.class, cartItems.getCartItemId());
            Cart cartIdOld = persistentCartItems.getCartId();
            Cart cartIdNew = cartItems.getCartId();
            Products productIdOld = persistentCartItems.getProductId();
            Products productIdNew = cartItems.getProductId();
            if (cartIdNew != null) {
                cartIdNew = em.getReference(cartIdNew.getClass(), cartIdNew.getCartId());
                cartItems.setCartId(cartIdNew);
            }
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getProductId());
                cartItems.setProductId(productIdNew);
            }
            cartItems = em.merge(cartItems);
            if (cartIdOld != null && !cartIdOld.equals(cartIdNew)) {
                cartIdOld.getCartItemsCollection().remove(cartItems);
                cartIdOld = em.merge(cartIdOld);
            }
            if (cartIdNew != null && !cartIdNew.equals(cartIdOld)) {
                cartIdNew.getCartItemsCollection().add(cartItems);
                cartIdNew = em.merge(cartIdNew);
            }
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getCartItemsCollection().remove(cartItems);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getCartItemsCollection().add(cartItems);
                productIdNew = em.merge(productIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cartItems.getCartItemId();
                if (findCartItems(id) == null) {
                    throw new NonexistentEntityException("The cartItems with id " + id + " no longer exists.");
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
            CartItems cartItems;
            try {
                cartItems = em.getReference(CartItems.class, id);
                cartItems.getCartItemId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cartItems with id " + id + " no longer exists.", enfe);
            }
            Cart cartId = cartItems.getCartId();
            if (cartId != null) {
                cartId.getCartItemsCollection().remove(cartItems);
                cartId = em.merge(cartId);
            }
            Products productId = cartItems.getProductId();
            if (productId != null) {
                productId.getCartItemsCollection().remove(cartItems);
                productId = em.merge(productId);
            }
            em.remove(cartItems);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CartItems> findCartItemsEntities() {
        return findCartItemsEntities(true, -1, -1);
    }

    public List<CartItems> findCartItemsEntities(int maxResults, int firstResult) {
        return findCartItemsEntities(false, maxResults, firstResult);
    }

    private List<CartItems> findCartItemsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CartItems.class));
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

    public CartItems findCartItems(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CartItems.class, id);
        } finally {
            em.close();
        }
    }

    public int getCartItemsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CartItems> rt = cq.from(CartItems.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public CartItems findByCartAndProduct(int cartId, String productId) {

    EntityManager em = getEntityManager();

    try {

        List<CartItems> list = em.createQuery(
                "SELECT ci \n" +
"FROM CartItems ci\n" +
"WHERE ci.cartId.cartId = :cid\n" +
"AND ci.productId.productId = :pid",
                CartItems.class)
                .setParameter("cid", cartId)
                .setParameter("pid", productId)
                .getResultList();

        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);

    } finally {
        em.close();
    }
}
    public List<CartItems> findByAccount(String acc){

    EntityManager em = getEntityManager();

    try{

        return em.createQuery(
        "SELECT ci FROM CartItems ci WHERE ci.cartId.account.account = :acc",
        CartItems.class)
        .setParameter("acc", acc)
        .getResultList();

    } finally{
        em.close();
    }
}
    
}
