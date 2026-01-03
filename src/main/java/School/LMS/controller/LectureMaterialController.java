package School.LMS.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import School.LMS.dto.LectureMaterialDTO;
import School.LMS.models.LectureMaterial;
import School.LMS.services.LectureMaterialService;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/lectures")
public class LectureMaterialController {

    private final LectureMaterialService lectureService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadLecture(
            Authentication auth,
            @RequestParam Long subjectId,
            @RequestParam Long classId,
            @RequestParam String title,
            @RequestParam MultipartFile file
    ) throws Exception {

        lectureService.uploadLecture(
            auth.getName(), subjectId, classId, title, file
        );

    return ResponseEntity.ok("Lecture uploaded successfully");
    }

   @GetMapping("/class/{classId}/subject/{subjectId}")
    public List<LectureMaterialDTO> getLecturesBySubject(
            @PathVariable Long classId,
            @PathVariable Long subjectId
    ) {
        return lectureService
                .getLecturesForClassAndSubject(classId, subjectId)
                .stream()
                .map(l -> new LectureMaterialDTO(
                        l.getId(),
                        l.getTitle(),
                        l.getFileName(),
                        l.getSubject().getName(),
                        l.getUploadedAt()
                ))
                .toList();
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) throws Exception {

        LectureMaterial lm = lectureService.getLectureById(id);
        File file = new File(lm.getFilePath());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + lm.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(Files.readAllBytes(file.toPath()));
    }

    

}
