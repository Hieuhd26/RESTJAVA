package K21.HAIDUONG.repository;

import K21.HAIDUONG.model.Permission;
import K21.HAIDUONG.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
