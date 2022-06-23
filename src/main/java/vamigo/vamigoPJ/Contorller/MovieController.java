package vamigo.vamigoPJ.Contorller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vamigo.vamigoPJ.DTO.Movie;
import vamigo.vamigoPJ.Service.MovieService;
import vamigo.vamigoPJ.search.SearchRequestDTO;

import java.io.File;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService service;

    @Autowired
    public MovieController(MovieService service) {
        this.service = service;
    }

    @PostMapping
    public void index(@RequestBody final Movie movie) {
        service.index(movie);
    }

    @GetMapping("/{id}")
    public Movie getById(@PathVariable final String id) {
        return service.getById(id);
    }

    @PostMapping("/search")
    public List<Movie> search(@RequestBody final SearchRequestDTO dto) {
        return service.search(dto);
    }

    @PostMapping("/image")
    public List<Movie> image(@RequestBody final SearchRequestDTO dto) {
        return service.imagesearch(dto);
    }

}
