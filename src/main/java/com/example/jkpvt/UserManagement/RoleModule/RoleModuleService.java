package com.example.jkpvt.UserManagement.RoleModule;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleModuleService {

    private final RoleModuleRepository repository;
    private final RoleModuleMapper mapper;
    private final RoleModuleDAO dao;


    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    public List<RoleModuleDTO> get(RoleModuleDTO roleModuleDTO) {
        List<RoleModule> roleModules = dao.get(roleModuleDTO);
        return mapper.map(roleModules);
    }

    @Transactional
    public RoleModuleDTO create(RoleModuleDTO roleModuleDTO) {
        try {
            RoleModule roleModule = mapper.map(roleModuleDTO);
            roleModule = repository.save(roleModule);
            return mapper.map(roleModule);
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional
    public RoleModuleDTO update(RoleModuleDTO roleModuleDTO) {
        try {
            RoleModule roleModule = getByRoleAndModuleId(roleModuleDTO.getRoleId(), roleModuleDTO.getModuleId());
            if(roleModuleDTO.getModelOrder() != null){
                roleModule.setModelOrder(roleModuleDTO.getModelOrder());
            }
            roleModule = repository.save(roleModule);
            return mapper.map(roleModule);
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional
    public String delete(RoleModuleDTO roleModuleDTO) {
        try {
            RoleModule roleModule = getByRoleAndModuleId(roleModuleDTO.getRoleId(), roleModuleDTO.getModuleId());
            if (repository.existsById(roleModule.getId())) {
                repository.deleteById(roleModule.getId());
                return "Data deleted successfully";
            }else {
                throw new CommonException("RoleModule not found");
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional
    public RoleModule getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CommonException("RoleModule not found"));
    }

    @Transactional
    public RoleModule getByRoleAndModuleId(Long roleId, Long moduleId) {
        Optional<RoleModule> roleModule = repository.findByRoleIdAndModuleId(roleId, moduleId);
        if(roleModule.isPresent()){
            return roleModule.get();
        }else{
            throw new CommonException("RoleModule not found");
        }
    }
}
