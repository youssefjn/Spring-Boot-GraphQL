package spring.graphql.service.dataFetcher;

import java.util.List;

import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import spring.graphql.model.Book;
import spring.graphql.repository.BookRepository;


@Component
@RequiredArgsConstructor
public class AllBooksDataFetcher implements DataFetcher<List<Book>> {
    private final BookRepository bookRepository;
    @Override
    public List<Book> get(DataFetchingEnvironment environment) {
        return bookRepository.findAll();
    }
    
}
