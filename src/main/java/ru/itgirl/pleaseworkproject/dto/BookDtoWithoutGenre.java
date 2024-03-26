package ru.itgirl.pleaseworkproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itgirl.pleaseworkproject.model.Author;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookDtoWithoutGenre {
    private Long id;
    private String name;

    private Set<Author> authors;
}
