package com.github.juli220620.repo.impl;

import com.github.juli220620.model.BookEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParametrizedBookSearchRepoImpl implements ParametrizedBookSearchRepo {

    private final SessionFactory sessionFactory;


    @Override
    public List<BookEntity> findAllByAuthor(String author) {
        try (var session = sessionFactory.openSession()) {
            var builder = session.getCriteriaBuilder();
            var criteriaQuery = builder.createQuery(BookEntity.class);
            var root = criteriaQuery.from(BookEntity.class);

            criteriaQuery.select(root).where(builder.equal(root.get("author"), author));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<BookEntity> findByDescriptionPart(String descriptionPart) {
        descriptionPart = "%" + descriptionPart + "%";
        try (var session = sessionFactory.openSession()) {
            var builder = session.getCriteriaBuilder();
            var criteriaQuery = builder.createQuery(BookEntity.class);
            var root = criteriaQuery.from(BookEntity.class);

            criteriaQuery.select(root).where(builder.like(root.get("description"), descriptionPart));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
