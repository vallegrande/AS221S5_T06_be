package pe.edu.vallegrande.contermoderator.rest;

import pe.edu.vallegrande.contermoderator.dto.ComputerVisionDataUpdateDTO;
import pe.edu.vallegrande.contermoderator.dto.ComputerVisionImageAnalysisDTO;
import pe.edu.vallegrande.contermoderator.model.ComputerVisionResponse;
import pe.edu.vallegrande.contermoderator.service.ComputerVisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@RequestMapping("/computer-vision")
public class ComputerVisionRest {

    @Autowired
    private ComputerVisionService visionService;

    @PostMapping("/analyze")
    public Mono<ComputerVisionResponse> analyzeImage(@RequestBody ComputerVisionImageAnalysisDTO request) {
        return visionService.save(request.getImageUrl());
    }

    @GetMapping("/list")
    public Flux<ComputerVisionResponse> getAll() {
        return visionService.getAll();
    }

    @PutMapping("/update/{id}")
    public Mono<ComputerVisionResponse> update(@PathVariable Long id, @RequestBody ComputerVisionDataUpdateDTO updatedResponse) {
        return visionService.update(id, updatedResponse);
    }
}
