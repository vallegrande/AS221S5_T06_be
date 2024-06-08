package pe.edu.vallegrande.contermoderator.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class ComputerVisionDataUpdateDTO {

    private Long id;
    private String description;
    private List<String> tags;
}
