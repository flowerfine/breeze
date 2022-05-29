package cn.sliew.scaleph.security.service.convert;

import cn.sliew.scaleph.common.convert.BaseConvert;
import cn.sliew.scaleph.dao.entity.master.security.UserRole;
import cn.sliew.scaleph.security.service.dto.UserRoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author gleiyu
 */
@Mapper
public interface UserRoleConvert extends BaseConvert<UserRole, UserRoleDTO> {

    UserRoleConvert INSTANCE = Mappers.getMapper(UserRoleConvert.class);
}
