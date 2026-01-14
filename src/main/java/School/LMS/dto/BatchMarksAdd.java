package School.LMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchMarksAdd {
    private String classroom;
    private String subject;
    private String exam_name;
    private Map<String, Double> marks;
}
