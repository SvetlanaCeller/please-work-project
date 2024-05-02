package ru.itgirl.pleaseworkproject.service;

import freemarker.template.utility.NullArgumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgirl.pleaseworkproject.dto.BookCreateDto;
import ru.itgirl.pleaseworkproject.dto.BookDto;
import ru.itgirl.pleaseworkproject.model.Book;
import ru.itgirl.pleaseworkproject.model.Genre;
import ru.itgirl.pleaseworkproject.repository.AuthorRepository;
import ru.itgirl.pleaseworkproject.repository.BookRepository;
import ru.itgirl.pleaseworkproject.repository.GenreRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Override
    public BookDto getByNameV1(String name) {
        log.info("Trying to find the book {} by name", name);
        Optional<Book> book = bookRepository.findBookByName(name);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        } else {
            log.warn("Book with the name {} not found", name);
            throw new NoSuchElementException("There is no such element.");
        }
    }

    private BookDto convertEntityToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .genre(book.getGenre().getName())
                .build();
    }

    @Override
    public BookDto getByNameV2(String name) {
        log.info("Trying to find the book {} by name", name);
        Optional<Book> book = bookRepository.findBookByNameBySql(name);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        } else {
            log.warn("Book with the name {} not found", name);
            throw new NoSuchElementException("There is no such element.");
        }
    }

    @Override
    public BookDto getByNameV3(String name) {
        log.info("Trying to find the book {} by name", name);
        Specification<Book> specification = Specification.where((Specification<Book>)
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name));

        Optional<Book> book = bookRepository.findOne(specification);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        } else {
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException("There is no such element");
        }
    }

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        log.info("Try to create the book");
        if (!bookCreateDto.getName().isBlank()) {
            Book book = bookRepository.save(convertDtoToEntity(bookCreateDto));
            log.info("Book is saved");
            BookDto bookDto = convertEntityToDto(book);
            if (bookDto == null) throw new NoSuchElementException("No value present");
            return bookDto;
        } else {
            log.error("Book's name is required.");
            throw new NullArgumentException("You didn't specify an book's name.");
        }
    }


    private Book convertDtoToEntity(BookCreateDto bookCreateDto) {
        Genre genre = genreRepository.findGenreByName(bookCreateDto.getGenre()).orElseThrow();
        return Book.builder()
                .name(bookCreateDto.getName())
                .genre(genre)
                .build();

    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        log.info("Trying to find the book by id for updating");
        Book book = bookRepository.findById(bookDto.getId()).orElseThrow();
        Genre genre = genreRepository.findGenreByName(bookDto.getGenre()).orElseThrow();
        if (Optional.ofNullable(book.getId()).isPresent()) {
            log.info("The book is found");
            if (!bookDto.getName().isEmpty()) {
                book.setName(bookDto.getName());
                log.info("The name is changed to {}", book.getName());
            } else {
                log.error("The name of book can't be empty");
                throw new NullArgumentException("You didn't specify an book's genre");
            }

            if (!genre.getName().isEmpty()) {
                book.setGenre(genre);
                log.info("The genre is changed to {}", book.getGenre());
            } else {
                log.error("Genre's name can't ba empty");
                throw new NullArgumentException("You didn't specify an book's genre");
            }
            Book savedBook = bookRepository.save(book);
            return convertEntityToDto(savedBook);

        } else {
            log.error("Book with id {} not found", bookDto.getId());
            throw new NoSuchElementException("No value present");
        }
    }


    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> getAllBooks() {
        log.info("Trying to find all the books.");
        List<Book> books = bookRepository.findAll();
        if (!books.isEmpty()) {
            log.info("The books are found.");
            return books.stream().map(this::convertEntityToDto).collect(Collectors.toList());
        } else {
            log.error("Books not found");
            throw new NoSuchElementException("There are no such elements");
        }
    }
}
