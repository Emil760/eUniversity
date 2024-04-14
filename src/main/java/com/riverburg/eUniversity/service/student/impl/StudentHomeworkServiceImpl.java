package com.riverburg.eUniversity.service.student.impl;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.model.dto.request.post.AddStudentHomeworkRequest;
import com.riverburg.eUniversity.model.dto.response.student.homework.StudentHomeworkResponse;
import com.riverburg.eUniversity.model.entity.StudentWorkEntity;
import com.riverburg.eUniversity.repository.FacultyDisciplineRepository;
import com.riverburg.eUniversity.repository.StudentWorkRepository;
import com.riverburg.eUniversity.repository.ThemeRepository;
import com.riverburg.eUniversity.service.file.FileService;
import com.riverburg.eUniversity.service.student.StudentService;
import com.riverburg.eUniversity.service.student.StudentWorkService;
import com.riverburg.eUniversity.service.theme.ThemeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentHomeworkServiceImpl implements StudentWorkService {

    private Path storagePath;

    private final StudentService studentService;

    private final FacultyDisciplineRepository facultyDisciplineRepository;

    private final ThemeService themeService;

    private final ThemeRepository themeRepository;

    private final StudentWorkRepository studentWorkRepository;

    private final FileService fileService;

    public StudentHomeworkServiceImpl(@Value("${storage.directory.homeworks}") String storageName,
                                      StudentService studentService,
                                      FacultyDisciplineRepository facultyDisciplineRepository,
                                      ThemeRepository themeRepository,
                                      ThemeService themeService,
                                      StudentWorkRepository studentWorkRepository,
                                      FileService fileService) throws IOException {
        this.studentService = studentService;
        this.facultyDisciplineRepository = facultyDisciplineRepository;
        this.themeRepository = themeRepository;
        this.themeService = themeService;
        this.studentWorkRepository = studentWorkRepository;
        this.fileService = fileService;

        this.storagePath = Paths.get(storageName).toAbsolutePath().normalize();
        Files.createDirectories(storagePath);
    }

    @Override
    public List<StudentHomeworkResponse> getAllWorks(UUID accountId, int facultyDisciplineId, int eduProcessId) {
        final var student = studentService.findByAccountId(accountId);

        final var facultyDiscipline = facultyDisciplineRepository.findAllByGroupId(
                        Objects.requireNonNull(student.getGroupEntity()).getId())
                .stream()
                .filter(f -> f.getId().equals(facultyDisciplineId))
                .findAny().orElseThrow(() -> RestException.of(ErrorConstant.DISCIPLINE_NOT_FOUND));

        var themes = themeRepository.findByGroupAndDisciplineAndEduProcess(facultyDiscipline.getId(), eduProcessId);

        return themes
                .stream()
                .map(t -> {
                    StudentHomeworkResponse response =
                            StudentHomeworkResponse.builder()
                                    .themeId(t.getId())
                                    .themeName(t.getName())
                                    .description(t.getDescription())
                                    .from(t.getFrom())
                                    .to(t.getTo())
                                    .isUploaded(false)
                                    .fileId(t.getFileEntity() == null ? 0 : t.getFileEntity().getId())
                                    .fileName(t.getFileEntity() == null ? "" : t.getFileEntity().getOriginalFileName())
                                    .build();

                    var work = studentWorkRepository.findByThemeEntityAndStudentEntity(t, student);

                    if (work.isEmpty()) return response;

                    response.setStudentWorkId(work.get().getId());
                    response.setIsUploaded(true);
                    response.setUploadedDate(work.get().getDate());
                    response.setStudentFileId(Objects.requireNonNull(work.get().getFileEntity()).getId());
                    response.setStudentFileName(work.get().getFileEntity().getOriginalFileName());
                    response.setGrade(work.get().getGrade());
                    response.setFeedback(work.get().getFeedback());

                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean uploadWork(UUID accountId, AddStudentHomeworkRequest request) throws IOException {
        final var theme = themeService.findById(request.getThemeId());

        final var student = studentService.findByAccountId(accountId);

        var existingWork = studentWorkRepository.findByThemeEntityAndStudentEntity(theme, student);

        if (existingWork.isPresent()) {
            if (Objects.nonNull(existingWork.get().getGrade()))
                throw RestException.of(ErrorConstant.HOMEWORK_HAS_GRADE);

            fileService.deleteFile(existingWork.get().getFileEntity());
            studentWorkRepository.delete(existingWork.get());
        }

        var savedFile = fileService.uploadFile(this.storagePath, accountId, request.getFile());

        studentWorkRepository.save(new StudentWorkEntity(student, theme, savedFile));

        return true;
    }

    @Override
    public ByteArrayResource download(int studentWorkId) throws IOException {
        var work = findById(studentWorkId);

        return fileService.downloadFile(Objects.requireNonNull(work.getFileEntity()));
    }

    @Override
    public StudentWorkEntity findById(int id) {
        return studentWorkRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.WORK_NOT_FOUND);
                });
    }
}
