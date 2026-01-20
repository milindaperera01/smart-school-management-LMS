package School.LMS.dto;

public record StudentExamMarkDTO(
        Long id,
        String examName,
        Double score,
        Long subjectId,
        Long classId
) {}
