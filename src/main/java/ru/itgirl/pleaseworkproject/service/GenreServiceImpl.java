package ru.itgirl.pleaseworkproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itgirl.pleaseworkproject.dto.AuthorDto;
import ru.itgirl.pleaseworkproject.dto.BookDtoWithoutGenre;
import ru.itgirl.pleaseworkproject.dto.GenreDto;
import ru.itgirl.pleaseworkproject.model.Author;
import ru.itgirl.pleaseworkproject.model.Genre;
import ru.itgirl.pleaseworkproject.repository.GenreRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j

public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public GenreDto getGenreById(Long id) {
        log.info("Try to find genre by id {}", id);
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isPresent()) {
            GenreDto genreDto = convertToDto(genre.get());
            log.info("Genre: {}", genreDto.toString());
            return genreDto;
        } else {
            log.error("Genre with id {} not found", id);
            throw new NoSuchElementException("No value present");
        }
    }

    private GenreDto convertToDto(Genre genre) {
        List<BookDtoWithoutGenre> bookDtoList = genre.getBooks()
                .stream()
                .map(book -> BookDtoWithoutGenre.builder()
                        .name(book.getName())
                        .id(book.getId()).build()
                ).toList();
        return GenreDto.builder()
                .books(bookDtoList)
                .id(genre.getId())
                .genre(genre.getName())
                .build();
    }

}