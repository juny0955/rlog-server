package junyoung.dev.rlogserver.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import junyoung.dev.rlogserver.project.repository.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	boolean existsByName(String name);

	Optional<Project> findByProjectKey(String projectKeyHash);
}
