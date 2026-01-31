package junyoung.dev.rlogserver.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import junyoung.dev.rlogserver.project.repository.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	boolean existsByName(String name);

	@Query("SELECT p FROM Project p " +
		"LEFT JOIN FETCH p.agentConfig ac " +
		"LEFT JOIN FETCH ac.sources " +
		"WHERE p.projectKey = :projectKey")
	Optional<Project> findByProjectKeyWithConfigAndSources(@Param("projectKey") String projectKey);
}
