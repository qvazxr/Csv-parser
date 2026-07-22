package ru.shift.userimporter.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.shift.userimporter.core.model.UploadedFiles;

import java.util.Optional;

public interface UploadedFilesRepository extends JpaRepository<UploadedFiles, Integer> {
    @EntityGraph(attributePaths = {"errors"})
    Optional<UploadedFiles> findWithErrorsById(Integer id);
}
