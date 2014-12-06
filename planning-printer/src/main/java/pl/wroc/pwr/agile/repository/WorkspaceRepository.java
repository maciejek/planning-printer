package pl.wroc.pwr.agile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.wroc.pwr.agile.entity.Workspace;

public interface WorkspaceRepository extends JpaRepository<Workspace, Integer> {

}
