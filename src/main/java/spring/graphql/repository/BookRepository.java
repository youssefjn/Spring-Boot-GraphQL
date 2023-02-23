package spring.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.graphql.model.Book;

public interface BookRepository extends JpaRepository<Book,Long> {
    
}
