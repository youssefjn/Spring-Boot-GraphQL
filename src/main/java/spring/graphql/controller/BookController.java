package spring.graphql.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import graphql.ExecutionResult;
import lombok.RequiredArgsConstructor;
import spring.graphql.service.GraphQLService;

@RestController
@RequestMapping("/rest/book")
@RequiredArgsConstructor
public class BookController {
    private final GraphQLService graphQLService;

    @PostMapping
    public ResponseEntity<?> getAllBooks(@RequestBody String query) {
        ExecutionResult execute = graphQLService.getGraphQL().execute(query);
        return new ResponseEntity<>(execute, HttpStatus.OK);
    }

}
