package pe.edu.vallegrande.contermoderator.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.util.List;

@Data
@Table("computervision")
public class ComputerVisionResponse {
    @Id
    private Long id;
    @Column("descripcion")
    private String description;

    @Column("tags")
    private List<String> tags;

    @Column("url_imagen")
    private String imageUrl;

    @Column("is_adult_content")
    private boolean isAdultContent;

    @Column("is_racy_content")
    private boolean isRacyContent;

    @Column("is_gory_content")
    private boolean isGoryContent;

    @Column("adult_score")
    private double adultScore;

    @Column("racy_score")
    private double racyScore;

    @Column("gore_score")
    private double goreScore;

    @Column("status")
    private String status;
}