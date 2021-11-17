package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.repository.IngredientRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class IngredientRepositoryImpl extends AbstractRepositoryImpl<Ingredient> implements IngredientRepository {
    private static final String SELECT_ALL = "from Ingredient i";
    private static final String SELECT_INGREDIENT_BY_COMPOSITION_ID =
            "from Ingredient i left join fetch i.compositions c where c.id=:id";
    private static final String SELECT_COMPOSITIONS_BY_INGREDIENT_ID =
            "from Composition c left join fetch c.ingredient i where i.id=:id";

    public IngredientRepositoryImpl(SessionFactory sessionFactory) {
        super(Ingredient.class, sessionFactory);
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected void doDeletionOperations(Session session, Long id) {
        Ingredient ingredient = session.get(Ingredient.class, id);
        deleteIngredientLinks(session, ingredient.getCompositions());
        session.delete(ingredient);
    }

    private void deleteIngredientLinks(Session session, List<Composition> compositions){
        for(Composition composition : compositions) {
            session.delete(composition);
        }
    }

    @Override
    public List<Composition> findAllCompositionsThatIncludeIngredientById(Long ingredientId) {
        try(Session session = getSessionFactory().openSession()) {
            return session.createQuery(SELECT_COMPOSITIONS_BY_INGREDIENT_ID, Composition.class)
                    .setParameter(ID_COLUMN, ingredientId)
                    .list();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Ingredient getIngredientByCompositionId(Long compositionId) {
        try(Session session = getSessionFactory().openSession()) {
            return session.createQuery(SELECT_INGREDIENT_BY_COMPOSITION_ID, Ingredient.class)
                    .setParameter(ID_COLUMN, compositionId)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}