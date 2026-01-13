package School.LMS.dto;

import java.time.LocalDateTime;

public record AssignmentDetailsDTO(
        Long id,
        String title,
        String description,
        LocalDateTime dueDate,
        String filePath,     // ✅ only here
        Long classId,
        Long subjectId,
        Long teacherId
) {}