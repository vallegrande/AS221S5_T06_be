package pe.edu.vallegrande.speech.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.speech.model.History;
import pe.edu.vallegrande.speech.service.impl.HistoryServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/speech")
public class HistoryController {

    private final HistoryServiceImpl historyServiceImpl;

    @GetMapping("/list")
    public Flux<History> listSpeech() {
        return historyServiceImpl.listSpeech();
    }

    @PostMapping("/save")
    public Mono<String> generateSpeech(@RequestBody String text) {
        return historyServiceImpl.create(text);
    }

    @PutMapping("/edit/{id}")
    public Mono<History> editSpeech(@PathVariable Long id, @RequestBody String text) {
        return historyServiceImpl.edit(id, text);
    }

    @PutMapping("/inactive/{id}")
    public Mono<History> inactiveHistory(@PathVariable Long id) {
        return historyServiceImpl.inactiveHistory(id);
    }

    @PutMapping("/active/{id}")
    public Mono<History> activeHistory(@PathVariable Long id) {
        return historyServiceImpl.activeHistory(id);
    }

}