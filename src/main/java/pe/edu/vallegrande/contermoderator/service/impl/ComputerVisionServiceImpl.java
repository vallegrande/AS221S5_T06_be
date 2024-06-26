package pe.edu.vallegrande.contermoderator.service.impl;

import pe.edu.vallegrande.contermoderator.dto.ComputerVisionDataUpdateDTO;
import pe.edu.vallegrande.contermoderator.model.ComputerVisionResponse;
import pe.edu.vallegrande.contermoderator.repository.ComputerVisionRepository;
import pe.edu.vallegrande.contermoderator.service.ComputerVisionService;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pe.edu.vallegrande.contermoderator.util.Constant.COMPUTER_VISION_KEY;
import static pe.edu.vallegrande.contermoderator.util.Constant.COMPUTER_VISION_URL;

@Service
public class ComputerVisionServiceImpl implements ComputerVisionService {

    private final ComputerVisionRepository responseRepository;

    @Autowired
    public ComputerVisionServiceImpl(ComputerVisionRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    @Override
    public Mono<ComputerVisionResponse> save(String imageUrl) {
        return callComputerVisionAPI(imageUrl)
                .flatMap(responseRepository::save);
    }

    @Override
    public Flux<ComputerVisionResponse> getAll() {
        return responseRepository.getAll();
    }

    @Override
    public Flux<ComputerVisionResponse> listA() {
        return responseRepository.findAllByStatus("A");
    }

    @Override
    public Flux<ComputerVisionResponse> listI() {
        return responseRepository.findAllByStatus("I");
    }

    @Override
    public Mono<ComputerVisionResponse> update(Long id, ComputerVisionDataUpdateDTO updatedResponse) {
        return responseRepository.findById(id)
                .flatMap(existingResponse -> {
                    if (!existingResponse.getImageUrl().equals(updatedResponse.getImageUrl())) {
                        // Llamar al servicio de visión por computadora con la nueva URL
                        return callComputerVisionAPI(updatedResponse.getImageUrl())
                                .flatMap(apiResponse -> {
                                    // Actualizar los campos con la nueva información de la API
                                    existingResponse.setDescription(apiResponse.getDescription());
                                    existingResponse.setTags(apiResponse.getTags());
                                    existingResponse.setAdultContent(apiResponse.isAdultContent());
                                    existingResponse.setRacyContent(apiResponse.isRacyContent());
                                    existingResponse.setGoryContent(apiResponse.isGoryContent());
                                    existingResponse.setAdultScore(apiResponse.getAdultScore());
                                    existingResponse.setRacyScore(apiResponse.getRacyScore());
                                    existingResponse.setGoreScore(apiResponse.getGoreScore());
                                    existingResponse.setImageUrl(updatedResponse.getImageUrl());
                                    return responseRepository.save(existingResponse);
                                });
                    } else {
                        // Si la URL no cambia, solo guarda la entidad existente
                        return responseRepository.save(existingResponse);
                    }
                });
    }

    @Override
    public Mono<Void> delete(Long id) {
        return responseRepository.delete(id);
    }

    @Override
    public Mono<ComputerVisionResponse> active(Long id) {
        return responseRepository.findById(id)
                .flatMap(existingResponse -> {
                    existingResponse.setStatus("A");
                    return responseRepository.save(existingResponse);
                });
    }

    @Override
    public Mono<ComputerVisionResponse> inactive(Long id) {
        return responseRepository.findById(id)
                .flatMap(existingResponse -> {
                    existingResponse.setStatus("I");
                    return responseRepository.save(existingResponse);
                });
    }

    private Mono<ComputerVisionResponse> callComputerVisionAPI(String imageUrl) {
        return Mono.create(sink -> {
            try {
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\n  \"url\": \"" + imageUrl + "\"\n}\n");
                Request request = new Request.Builder()
                        .url(COMPUTER_VISION_URL)
                        .method("POST", body)
                        .addHeader("Ocp-Apim-Subscription-Key", COMPUTER_VISION_KEY)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    sink.error(new IOException("Unexpected code " + response));
                    return;
                }

                JSONObject jsonObject = new JSONObject(response.body().string());
                ComputerVisionResponse computerVisionResponse = new ComputerVisionResponse();

                computerVisionResponse.setDescription(jsonObject.getJSONObject("description").getJSONArray("captions").getJSONObject(0).getString("text"));

                JSONArray tagsArray = jsonObject.getJSONObject("description").getJSONArray("tags");
                List<String> tags = IntStream.range(0, tagsArray.length())
                        .mapToObj(tagsArray::getString)
                        .collect(Collectors.toList());
                computerVisionResponse.setTags(tags);

                JSONObject adultObject = jsonObject.getJSONObject("adult");
                computerVisionResponse.setAdultContent(adultObject.getBoolean("isAdultContent"));
                computerVisionResponse.setRacyContent(adultObject.getBoolean("isRacyContent"));
                computerVisionResponse.setGoryContent(adultObject.getBoolean("isGoryContent"));
                computerVisionResponse.setAdultScore(adultObject.getDouble("adultScore"));
                computerVisionResponse.setRacyScore(adultObject.getDouble("racyScore"));
                computerVisionResponse.setGoreScore(adultObject.getDouble("goreScore"));

                computerVisionResponse.setImageUrl(imageUrl);

                sink.success(computerVisionResponse);
            } catch (IOException e) {
                sink.error(new RuntimeException("Error en la solicitud HTTP", e));
            }
        });
    }
}
