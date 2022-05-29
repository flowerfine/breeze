package cn.sliew.scaleph.security.service.convert;

import cn.sliew.scaleph.common.convert.BaseConvert;
import cn.sliew.scaleph.dao.entity.master.security.Privilege;
import cn.sliew.scaleph.security.service.dto.PrivilegeDTO;
import cn.sliew.scaleph.system.service.convert.DictVoConvert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author gleiyu
 */
@Mapper(uses = DictVoConvert.class)
public interface PrivilegeConvert extends BaseConvert<Privilege, PrivilegeDTO> {

    PrivilegeConvert INSTANCE = Mappers.getMapper(PrivilegeConvert.class);

    @Override
    @Mapping(expression = "java(cn.sliew.scaleph.system.service.vo.DictVO.toVO(cn.sliew.scaleph.common.constant.DictConstants.RESOURCE_TYPE,entity.getResourceType()))", target = "resourceType")
    PrivilegeDTO toDto(Privilege entity);
}
