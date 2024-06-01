package pe.edu.vallegrande.speech.service;

import pe.edu.vallegrande.speech.model.History;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HistoryService {
    Flux<History> listSpeech();
    Mono<String> create(String text);
    Mono<History> edit(Long id, String text);
    Mono<History> inactiveHistory(Long id);
    Mono<History> activeHistory(Long id);
}
