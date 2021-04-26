package org.jeecg.modules.system.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.system.entity.SysRole;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @Author scott
 * @since 2018-12-19
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * @Author scott
     * @Date 2019/12/13 16:12
     * @Description: 删除角色与用户关系
     */
    @Delete("delete from sys_user_role where role_id = #{roleId}")
    void deleteRoleUserRelation(@Param("roleId") String roleId);


    /**
     * @Author scott
     * @Date 2019/12/13 16:12
     * @Description: 删除角色与权限关系
     */
    @Delete("delete from sys_role_permission where role_id = #{roleId}")
    void deleteRolePermissionRelation(@Param("roleId") String roleId);
    @Select("select id,username from sys_user  WHERE del_Flag=0  and id in(select user_Id from sys_user_role WHERE role_id in (SELECT id from sys_role WHERE role_Name='党员') )")
    List<Map<String, Object>> userRole();
}
