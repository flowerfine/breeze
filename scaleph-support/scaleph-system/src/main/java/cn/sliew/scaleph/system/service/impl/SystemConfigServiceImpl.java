package cn.sliew.scaleph.system.service.impl;

import cn.sliew.milky.common.util.JacksonUtil;
import cn.sliew.scaleph.common.constant.Constants;
import cn.sliew.scaleph.dao.entity.master.system.SystemConfig;
import cn.sliew.scaleph.dao.mapper.master.system.SystemConfigMapper;
import cn.sliew.scaleph.system.service.SystemConfigService;
import cn.sliew.scaleph.system.service.convert.SystemConfigConvert;
import cn.sliew.scaleph.system.service.dto.SystemConfigDTO;
import cn.sliew.scaleph.system.service.vo.BasicConfigVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 系统配置信息表 服务实现类
 * </p>
 *
 * @author liyu
 * @since 2021-10-10
 */
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Override
    public int insert(SystemConfigDTO systemConfigDTO) {
        SystemConfig systemConfig = SystemConfigConvert.INSTANCE.toDo(systemConfigDTO);
        return this.systemConfigMapper.insert(systemConfig);
    }

    @Override
    public int updateByCode(SystemConfigDTO systemConfigDTO) {
        SystemConfig systemConfig = SystemConfigConvert.INSTANCE.toDo(systemConfigDTO);
        return this.systemConfigMapper.update(systemConfig, new LambdaQueryWrapper<SystemConfig>()
            .eq(SystemConfig::getCfgCode, systemConfig.getCfgCode()));
    }

    @Override
    public int deleteByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return 0;
        } else {
            return this.systemConfigMapper.delete(new LambdaQueryWrapper<SystemConfig>()
                .eq(SystemConfig::getCfgCode, code));
        }

    }

    @Override
    public SystemConfigDTO selectByCode(String code) {
        SystemConfig systemConfig =
            this.systemConfigMapper.selectOne(new LambdaQueryWrapper<SystemConfig>()
                .eq(SystemConfig::getCfgCode, code));
        return SystemConfigConvert.INSTANCE.toDto(systemConfig);
    }

    @Override
    public String getSeatunnelHome() {
        SystemConfigDTO systemConfig = this.selectByCode(Constants.CFG_BASIC_CODE);
        if (systemConfig != null) {
            BasicConfigVO config =
                JacksonUtil.parseJsonString(systemConfig.getCfgValue(), BasicConfigVO.class);
            return config.getSeatunnelHome();
        } else {
            return null;
        }
    }
}
