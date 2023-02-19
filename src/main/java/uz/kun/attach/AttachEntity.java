package uz.kun.attach;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "attach")
@AllArgsConstructor
@NoArgsConstructor

public class AttachEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "original_name")
    private String originalName;
    private String path;
    private Long size;
    private String extension;
    private Long duration;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
