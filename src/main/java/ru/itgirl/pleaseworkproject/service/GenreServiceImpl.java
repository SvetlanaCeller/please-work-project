package ru.itgirl.pleaseworkproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itgirl.pleaseworkproject.dto.BookDtoWithoutGenre;
import ru.itgirl.pleaseworkproject.dto.GenreDto;
import ru.itgirl.pleaseworkproject.model.Genre;
import ru.itgirl.pleaseworkproject.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor

public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public GenreDto getGenreById(Long id) {
        Genre genre = genreRepository.findById(id).orElseThrow();
        return convertToDto(genre);
    }

    @Override
    public Long getIdByGenreName(String name) {
        return genreRepository.findGenreByName(name).get().getId();
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