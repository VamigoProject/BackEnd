package vamigo.vamigoPJ.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vamigo.vamigoPJ.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkService {
    private final ReviewRepository reviewRepository;




}
