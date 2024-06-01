package pe.edu.vallegrande.speech.service.impl;

import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.speech.model.History;
import pe.edu.vallegrande.speech.repository.HistoryRepository;
import pe.edu.vallegrande.speech.service.HistoryService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final SpeechSynthesizer speechSynthesizer;
    private static final Logger LOGGER = LogManager.getLogger(HistoryServiceImpl.class);
    @Override
    public Flux<History> listSpeech() {
        return historyRepository.findAll();
    }

    @Override
    public Mono<String> create(String text) {
        return Mono.just(text)
                .filter(t -> !t.isEmpty())
                .flatMap(t -> {
                    try {
                        speechSynthesizer.SpeakTextAsync(t).get();
                        History newText = History.builder().text(t).build();
                        return historyRepository.save(newText)
                                .thenReturn("Texto: [" + t + "] sintetizado correctamente.");
                    } catch (InterruptedException e) {
                        LOGGER.log(Level.WARN, "Interrupted!", e);
                        Thread.currentThread().interrupt();
                        return Mono.error(new RuntimeException("Error al sintetizar el texto a voz: " + e.getMessage()));
                    } catch (ExecutionException e) {
                        return Mono.error(new RuntimeException("Error al sintetizar el texto a voz: " + e.getMessage()));
                    }
                });
    }

    @Override
    public Mono<History> edit(Long id, String text) {
        return historyRepository.findById(id)
                .flatMap(history -> {
                    history.setText(text);
                    return historyRepository.save(history);
                });
    }

    @Override
    public Mono<History> inactiveHistory(Long id) {
        return historyRepository.inactiveHistory(id)
                .flatMap(i -> historyRepository.findById(id));
    }

    @Override
    public Mono<History> activeHistory(Long id) {
        return historyRepository.activeHistory(id)
                .flatMap(i -> historyRepository.findById(id));
    }

}