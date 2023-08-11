package project.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.domain.Item;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    @Transactional//여기다 하면 어떻게 진행되는지알기
    public void save(Item item) { em.persist(item);}

    public List<Item> findByName(String itemname) {
        return em.createQuery("select m from Item m where m.itemname = :itemname",Item.class)
                .setParameter("itemname",itemname).getResultList(); }

    public List<Item> findAll() {
        return em.createQuery("select m from Item m").getResultList(); }

    public void update(Long itemId, Item updateParam) {
//        Item findItem = findById(itemId);
//        findItem.setItemName(updateParam.getItemName());
//        findItem.setPrice(updateParam.getPrice());
//        findItem.setQuantity(updateParam.getQuantity());
        em.createQuery
        ("update Item m set m.itemname = :name, m.price=:price, m.quantity=:quantity" +
        " where m.id=:id").setParameter("name",updateParam.getItemname()).
                setParameter("price",updateParam.getPrice()).
                setParameter("quantity",updateParam.getQuantity()).
                setParameter("id",itemId).executeUpdate();

    }

    public void clearStore() {
       em.createQuery("delete from Item m").executeUpdate();
    }

}
