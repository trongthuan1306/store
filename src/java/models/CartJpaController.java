/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import models.exceptions.NonexistentEntityException;

/**
 *
 * @author ASUS
 */
public class CartJpaController implements Serializable {
    
    public CartJpaController() {
         emf = Persistence.createEntityManagerFactory("ProductionPU"); 
    }
    public CartJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cart cart) {
        if (cart.getCartItemsCollection() == null) {
            cart.setCartItemsCollection(new ArrayList<CartItems>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Accounts account = cart.getAccount();
            if (account != null) {
                account = em.getReference(account.getClass(), account.getAccount());
                cart.setAccount(account);
            }
            Collection<CartItems> attachedCartItemsCollection = new ArrayList<CartItems>();
            for (CartItems cartItemsCollectionCartItemsToAttach : cart.getCartItemsCollection()) {
                cartItemsCollectionCartItemsToAttach = em.getReference(cartItemsCollectionCartItemsToAttach.getClass(), cartItemsCollectionCartItemsToAttach.getCartItemId());
                attachedCartItemsCollection.add(cartItemsCollectionCartItemsToAttach);
            }
            cart.setCartItemsCollection(attachedCartItemsCollection);
            em.persist(cart);
            if (account != null) {
                account.getCartCollection().add(cart);
                account = em.merge(account);
            }
            for (CartItems cartItemsCollectionCartItems : cart.getCartItemsCollection()) {
                Cart oldCartIdOfCartItemsCollectionCartItems = cartItemsCollectionCartItems.getCartId();
                cartItemsCollectionCartItems.setCartId(cart);
                cartItemsCollectionCartItems = em.merge(cartItemsCollectionCartItems);
                if (oldCartIdOfCartItemsCollectionCartItems != null) {
                    oldCartIdOfCartItemsCollectionCartItems.getCartItemsCollection().remove(cartItemsCollectionCartItems);
                    oldCartIdOfCartItemsCollectionCartItems = em.merge(oldCartIdOfCartItemsCollectionCartItems);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cart cart) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cart persistentCart = em.find(Cart.class, cart.getCartId());
            Accounts accountOld = persistentCart.getAccount();
            Accounts accountNew = cart.getAccount();
            Collection<CartItems> cartItemsCollectionOld = persistentCart.getCartItemsCollection();
            Collection<CartItems> cartItemsCollectionNew = cart.getCartItemsCollection();
            if (accountNew != null) {
                accountNew = em.getReference(accountNew.getClass(), accountNew.getAccount());
                cart.setAccount(accountNew);
            }
            Collection<CartItems> attachedCartItemsCollectionNew = new ArrayList<CartItems>();
            for (CartItems cartItemsCollectionNewCartItemsToAttach : cartItemsCollectionNew) {
                cartItemsCollectionNewCartItemsToAttach = em.getReference(cartItemsCollectionNewCartItemsToAttach.getClass(), cartItemsCollectionNewCartItemsToAttach.getCartItemId());
                attachedCartItemsCollectionNew.add(cartItemsCollectionNewCartItemsToAttach);
            }
            cartItemsCollectionNew = attachedCartItemsCollectionNew;
            cart.setCartItemsCollection(cartItemsCollectionNew);
            cart = em.merge(cart);
            if (accountOld != null && !accountOld.equals(accountNew)) {
                accountOld.getCartCollection().remove(cart);
                accountOld = em.merge(accountOld);
            }
            if (accountNew != null && !accountNew.equals(accountOld)) {
                accountNew.getCartCollection().add(cart);
                accountNew = em.merge(accountNew);
            }
            for (CartItems cartItemsCollectionOldCartItems : cartItemsCollectionOld) {
                if (!cartItemsCollectionNew.contains(cartItemsCollectionOldCartItems)) {
                    cartItemsCollectionOldCartItems.setCartId(null);
                    cartItemsCollectionOldCartItems = em.merge(cartItemsCollectionOldCartItems);
                }
            }
            for (CartItems cartItemsCollectionNewCartItems : cartItemsCollectionNew) {
                if (!cartItemsCollectionOld.contains(cartItemsCollectionNewCartItems)) {
                    Cart oldCartIdOfCartItemsCollectionNewCartItems = cartItemsCollectionNewCartItems.getCartId();
                    cartItemsCollectionNewCartItems.setCartId(cart);
                    cartItemsCollectionNewCartItems = em.merge(cartItemsCollectionNewCartItems);
                    if (oldCartIdOfCartItemsCollectionNewCartItems != null && !oldCartIdOfCartItemsCollectionNewCartItems.equals(cart)) {
                        oldCartIdOfCartItemsCollectionNewCartItems.getCartItemsCollection().remove(cartItemsCollectionNewCartItems);
                        oldCartIdOfCartItemsCollectionNewCartItems = em.merge(oldCartIdOfCartItemsCollectionNewCartItems);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cart.getCartId();
                if (findCart(id) == null) {
                    throw new NonexistentEntityException("The cart with id " + id + " no longer exists.");
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
            Cart cart;
            try {
                cart = em.getReference(Cart.class, id);
                cart.getCartId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cart with id " + id + " no longer exists.", enfe);
            }
            Accounts account = cart.getAccount();
            if (account != null) {
                account.getCartCollection().remove(cart);
                account = em.merge(account);
            }
            Collection<CartItems> cartItemsCollection = cart.getCartItemsCollection();
            for (CartItems cartItemsCollectionCartItems : cartItemsCollection) {
                cartItemsCollectionCartItems.setCartId(null);
                cartItemsCollectionCartItems = em.merge(cartItemsCollectionCartItems);
            }
            em.remove(cart);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cart> findCartEntities() {
        return findCartEntities(true, -1, -1);
    }

    public List<Cart> findCartEntities(int maxResults, int firstResult) {
        return findCartEntities(false, maxResults, firstResult);
    }

    private List<Cart> findCartEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cart.class));
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

    public Cart findCart(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cart.class, id);
        } finally {
            em.close();
        }
    }

    public int getCartCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cart> rt = cq.from(Cart.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public Cart findCartByAccount(String acc) {

    EntityManager em = getEntityManager();

    try {

        List<Cart> list = em.createQuery(
                "SELECT c FROM Cart c WHERE c.account.account = :acc", Cart.class)
                .setParameter("acc", acc)
                .getResultList();

        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);

    } finally {
        em.close();
    }
}
    
}
