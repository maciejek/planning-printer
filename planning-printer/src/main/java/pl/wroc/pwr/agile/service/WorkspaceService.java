package pl.wroc.pwr.agile.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.repository.WorkspaceRepository;

@Service
@Transactional
public class WorkspaceService {
    
    @Autowired
    private WorkspaceRepository workspaceRepository;
    
    public void save(Workspace workspace) {
        workspaceRepository.save(workspace);
    }

}
