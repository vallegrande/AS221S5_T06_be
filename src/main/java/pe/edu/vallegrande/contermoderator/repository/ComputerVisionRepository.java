package pe.edu.vallegrande.contermoderator.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.contermoderator.model.ComputerVisionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ComputerVisionRepository  extends ReactiveCrudRepository<ComputerVisionResponse, Long> {
    @Query("SELECT * FROM computervisionresponse ORDER BY id DESC")
    Flux<ComputerVisionResponse> getAll();

    //@Query("DELETE FROM computervisionresponse WHERE id = :id")
    //Mono<Void> delete(Long id);
}
