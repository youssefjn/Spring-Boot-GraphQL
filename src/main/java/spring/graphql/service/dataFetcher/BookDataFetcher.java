package spring.graphql.service.dataFetcher;

import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import spring.graphql.model.Book;
import spring.graphql.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class BookDataFetcher implements DataFetcher<Book>   {
private final BookRepository bookRepository;
    @Override
    public Book get(DataFetchingEnvironment environment){
       Long id = environment.getArgument("id");
       return bookRepository.findById(id).orElse(null);
    }
    
}
