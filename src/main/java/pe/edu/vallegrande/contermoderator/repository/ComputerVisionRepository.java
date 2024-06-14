package pe.edu.vallegrande.contermoderator.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.contermoderator.model.ComputerVisionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ComputerVisionRepository  extends ReactiveCrudRepository<ComputerVisionResponse, Long> {
    
    Flux<ComputerVisionResponse> findAllByStatus(String status);
    
    @Query("SELECT * FROM computervision ORDER BY id DESC")
    Flux<ComputerVisionResponse> getAll();

    @Query("DELETE FROM computervision WHERE id = :id")
    Mono<Void> delete(Long id);  
    
    @Query("UPDATE computervision SET status = 'A' WHERE id = :id")
    Mono<ComputerVisionResponse> active(Long id);

    @Query("UPDATE computervision SET status = 'I' WHERE id = :id")
    Mono<ComputerVisionResponse> inactive(Long id);
}
