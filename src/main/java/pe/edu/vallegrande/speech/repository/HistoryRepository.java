package pe.edu.vallegrande.speech.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import pe.edu.vallegrande.speech.model.History;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HistoryRepository extends R2dbcRepository<History, Long> {
    Flux<History> findAll();
    @Query("UPDATE history SET active = 'I' WHERE id = :id")
    Mono<Integer> inactiveHistory(Long id);

    @Query("UPDATE history SET active = 'A' WHERE id = :id")
    Mono<Integer> activeHistory(Long id);
}