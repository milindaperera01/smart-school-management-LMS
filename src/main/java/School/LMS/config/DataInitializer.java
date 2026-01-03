package School.LMS.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import School.LMS.models.*;
import School.LMS.repos.*;

import java.util.*;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            SubjectRepo subjectRepo,
            GradeSubjectRepo gradeSubjectRepo
    ) {
        return args -> {

            // ---------- SUBJECT MASTER DATA ----------
            Subject maths = subjectRepo.findByName("Mathematics")
                    .orElseGet(() -> subjectRepo.save(new Subject(null, "Mathematics", null, null)));

            Subject english = subjectRepo.findByName("English")
                    .orElseGet(() -> subjectRepo.save(new Subject(null, "English", null, null)));

            Subject sinhala = subjectRepo.findByName("Sinhala")
                    .orElseGet(() -> subjectRepo.save(new Subject(null, "Sinhala", null, null)));

            Subject tamil = subjectRepo.findByName("Tamil")
                    .orElseGet(() -> subjectRepo.save(new Subject(null, "Tamil", null, null)));

            Subject science = subjectRepo.findByName("Science")
                    .orElseGet(() -> subjectRepo.save(new Subject(null, "Science", null, null)));

            Subject health = subjectRepo.findByName("Health")
                    .orElseGet(() -> subjectRepo.save(new Subject(null, "Health", null, null)));

            Subject agriculture = subjectRepo.findByName("Agriculture")
                    .orElseGet(() -> subjectRepo.save(new Subject(null, "Agriculture", null, null)));

            // ---------- GRADE → SUBJECT MAP ----------
            Map<Integer, List<Subject>> gradeMap = new HashMap<>();

            // Grades 1–5
            for (int grade = 1; grade <= 5; grade++) {
                gradeMap.put(grade, List.of(
                        maths,
                        english,
                        sinhala,
                        tamil
                ));
            }

            // Grades 6–11
            for (int grade = 6; grade <= 11; grade++) {
                gradeMap.put(grade, List.of(
                        maths,
                        english,
                        science,
                        health,
                        agriculture
                ));
            }

            // ---------- SAVE TO DATABASE ----------
            for (Map.Entry<Integer, List<Subject>> entry : gradeMap.entrySet()) {
                int gradeLevel = entry.getKey();

                for (Subject subject : entry.getValue()) {

                    boolean exists = gradeSubjectRepo
                            .existsByGradeLevelAndSubject(gradeLevel, subject);

                    if (!exists) {
                        GradeSubject gs = new GradeSubject();
                        gs.setGradeLevel(gradeLevel);
                        gs.setSubject(subject);
                        gradeSubjectRepo.save(gs);
                    }
                }
            }

            System.out.println("✔ Grade–Subject data initialized for Grades 1–11");
        };
    }
}
