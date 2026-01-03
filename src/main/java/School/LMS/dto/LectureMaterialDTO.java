package School.LMS.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureMaterialDTO {
    private Long id;
    private String title;
    private String fileName;
    private String subjectName;
    private LocalDateTime uploadedAt;
}
