package cn.sliew.scaleph.security.service.convert;

import cn.sliew.scaleph.common.convert.BaseConvert;
import cn.sliew.scaleph.dao.entity.master.security.SecUser;
import cn.sliew.scaleph.security.service.dto.SecUserDTO;
import cn.sliew.scaleph.system.service.convert.DictVoConvert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author gleiyu
 */
@Mapper(uses = {DictVoConvert.class})
public interface SecUserConvert extends BaseConvert<SecUser, SecUserDTO> {
    SecUserConvert INSTANCE = Mappers.getMapper(SecUserConvert.class);

    @Override
    @Mapping(expression = "java(cn.sliew.scaleph.system.service.vo.DictVO.toVO(cn.sliew.scaleph.common.constant.DictConstants.ID_CARD_TYPE,entity.getIdCardType()))", target = "idCardType")
    @Mapping(expression = "java(cn.sliew.scaleph.system.service.vo.DictVO.toVO(cn.sliew.scaleph.common.constant.DictConstants.GENDER,entity.getGender()))", target = "gender")
    @Mapping(expression = "java(cn.sliew.scaleph.system.service.vo.DictVO.toVO(cn.sliew.scaleph.common.constant.DictConstants.NATION,entity.getNation()))", target = "nation")
    @Mapping(expression = "java(cn.sliew.scaleph.system.service.vo.DictVO.toVO(cn.sliew.scaleph.common.constant.DictConstants.USER_STATUS,entity.getUserStatus()))", target = "userStatus")
    @Mapping(expression = "java(cn.sliew.scaleph.system.service.vo.DictVO.toVO(cn.sliew.scaleph.common.constant.DictConstants.REGISTER_CHANNEL,entity.getRegisterChannel()))", target = "registerChannel")
    SecUserDTO toDto(SecUser entity);
}
