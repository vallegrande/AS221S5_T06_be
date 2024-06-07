package pe.edu.vallegrande.contermoderator.service;

import pe.edu.vallegrande.contermoderator.dto.ComputerVisionDataUpdateDTO;
import pe.edu.vallegrande.contermoderator.model.ComputerVisionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ComputerVisionService {
    Mono<ComputerVisionResponse> save(String imageUrl);

    Flux<ComputerVisionResponse> getAll();

    Mono<ComputerVisionResponse> update(Long id, ComputerVisionDataUpdateDTO updatedResponse);
    //Mono<Void> delete(Long id);

}
