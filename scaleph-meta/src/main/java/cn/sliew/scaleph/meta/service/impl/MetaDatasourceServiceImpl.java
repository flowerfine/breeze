package cn.sliew.scaleph.meta.service.impl;

import cn.sliew.scaleph.dao.entity.master.meta.MetaDatasource;
import cn.sliew.scaleph.dao.mapper.master.meta.MetaDatasourceMapper;
import cn.sliew.scaleph.meta.service.MetaDatasourceService;
import cn.sliew.scaleph.meta.service.convert.MetaDataSourceConvert;
import cn.sliew.scaleph.meta.service.dto.MetaDatasourceDTO;
import cn.sliew.scaleph.meta.service.param.MetaDatasourceParam;
import cn.sliew.scaleph.plugin.datasource.DatasourceManager;
import cn.sliew.scaleph.plugin.framework.core.PluginInfo;
import cn.sliew.scaleph.plugin.framework.property.PropertyDescriptor;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class MetaDatasourceServiceImpl implements MetaDatasourceService {

    private DatasourceManager datasourceManager = new DatasourceManager();

    @Autowired
    private MetaDatasourceMapper metaDatasourceMapper;

    @Override
    public Set<PluginInfo> getAvailableDataSources() {
        return datasourceManager.getAvailableDataSources();
    }

    @Override
    public List<PropertyDescriptor> getSupportedProperties(PluginInfo pluginInfo) {
        return datasourceManager.getSupportedProperties(pluginInfo);
    }

    /**
     * fixme validate properties and encode password
     */
    @Override
    public int insert(MetaDatasourceDTO metaDatasourceDTO) {
        final MetaDatasource metaDatasource = MetaDataSourceConvert.INSTANCE.toDo(metaDatasourceDTO);
        return metaDatasourceMapper.insert(metaDatasource);
    }

    /**
     * fixme validate properties and encode password
     */
    @Override
    public int update(MetaDatasourceDTO metaDatasourceDTO) {
        final MetaDatasource metaDatasource = MetaDataSourceConvert.INSTANCE.toDo(metaDatasourceDTO);
        return metaDatasourceMapper.updateById(metaDatasource);
    }

    @Override
    public int deleteById(Long id) {
        return metaDatasourceMapper.deleteById(id);
    }

    @Override
    public int deleteBatch(Map<Integer, ? extends Serializable> map) {
        return metaDatasourceMapper.deleteBatchIds(map.values());
    }

    @Override
    public MetaDatasourceDTO selectOne(Long id) {
        final MetaDatasource datasource = metaDatasourceMapper.selectById(id);
        return MetaDataSourceConvert.INSTANCE.toDto(datasource);
    }

    @Override
    public Page<MetaDatasourceDTO> selectPage(MetaDatasourceParam param) {
        Page<MetaDatasource> list = metaDatasourceMapper.selectPage(
                new Page<>(param.getCurrent(), param.getPageSize()),
                Wrappers.lambdaQuery(MetaDatasource.class)
                        .like(StringUtils.hasText(param.getName()), MetaDatasource::getName, param.getName())
                        .eq(StringUtils.hasText(param.getVersion()), MetaDatasource::getVersion, param.getVersion())
        );
        Page<MetaDatasourceDTO> result = new Page<>(list.getCurrent(), list.getSize(), list.getTotal());
        List<MetaDatasourceDTO> dtoList = MetaDataSourceConvert.INSTANCE.toDto(list.getRecords());
        result.setRecords(dtoList);
        return result;
    }
}
