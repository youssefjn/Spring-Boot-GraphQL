package spring.graphql.service;

import java.io.File;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import spring.graphql.model.Book;
import spring.graphql.repository.BookRepository;
import spring.graphql.service.dataFetcher.AllBooksDataFetcher;
import spring.graphql.service.dataFetcher.BookDataFetcher;

@Service
@RequiredArgsConstructor
public class GraphQLService {
    
    private final BookRepository bookRepository;
    
    private final AllBooksDataFetcher allBooksDataFetcher;
    
    private final BookDataFetcher bookDataFetcher;
    private GraphQL graphQL;
    @Value("classpath:books.graphql")
    Resource resource;
    @PostConstruct
    private void loadSchema() throws Exception {
        loadDataIntoHSQL();
        File schemaFile = resource.getFile();
        TypeDefinitionRegistry typeDefinitionRegistry= new SchemaParser().parse(schemaFile);
        RuntimeWiring runtimeWiring = buildRuntimeWiring();
        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        graphQL= GraphQL.newGraphQL(graphQLSchema).build();
    }
    private void loadDataIntoHSQL() {
        Stream.of(
            new Book(1,"Effective Java, 3rd Edition", "Addison-Wesley Professional",
                        new String[] {
                        "Joshua Bloch"
                        }, "Dec 2017"),
                
                new Book( 2,"Mastering Java", "Independently published",
                        new String[] {
                                "Michael B. White"
                        }, "Dec 2018"),
                
                new Book(3,"Learning Spring Boot 2.0 - Second Edition", "Orielly",
                        new String[] {
                                "Packt Publishing"
                        }, "Nov 2017")
        ).forEach(book -> {
            bookRepository.save(book);
        }
        );
    }
    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
        .type("Query",typeWiring -> typeWiring
        .dataFetcher("allBooks", allBooksDataFetcher)
        .dataFetcher("book", bookDataFetcher))
        .build();
    }
    public GraphQL getGraphQL(){
        return graphQL;
    }

}
