package pe.edu.vallegrande.contermoderator.service;

import pe.edu.vallegrande.contermoderator.dto.ComputerVisionDataUpdateDTO;
import pe.edu.vallegrande.contermoderator.model.ComputerVisionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ComputerVisionService {
    Mono<ComputerVisionResponse> save(String imageUrl);

    Flux<ComputerVisionResponse> getAll();

    Flux<ComputerVisionResponse> listA();

    Flux<ComputerVisionResponse> listI();

    Mono<ComputerVisionResponse> update(Long id, ComputerVisionDataUpdateDTO updatedResponse);

    Mono<Void> delete(Long id);

    Mono<ComputerVisionResponse> active(Long id);

    Mono<ComputerVisionResponse> inactive(Long id);

}
