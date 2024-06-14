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
@CrossOrigin(origins = "*")
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

    @GetMapping("/list/A")
    public Flux<ComputerVisionResponse> listA() {
        return visionService.listA();
    }

    @GetMapping("/list/I")
    public Flux<ComputerVisionResponse> listI() {
        return visionService.listI();
    }

    @PutMapping("/update/{id}")
    public Mono<ComputerVisionResponse> update(@PathVariable Long id, @RequestBody ComputerVisionDataUpdateDTO updatedResponse) {
        return visionService.update(id, updatedResponse);
    }
    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return visionService.delete(id);
    }

    @PutMapping("/active/{id}")
    public Mono<ComputerVisionResponse> active(@PathVariable Long id) {
        return visionService.active(id);
    }

    @PutMapping("/inactive/{id}")
    public Mono<ComputerVisionResponse> inactive(@PathVariable Long id) {
        return visionService.inactive(id);
    }

}
