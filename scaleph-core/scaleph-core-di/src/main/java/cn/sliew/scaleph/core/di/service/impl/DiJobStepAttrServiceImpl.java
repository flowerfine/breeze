package cn.sliew.scaleph.core.di.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;
import cn.sliew.scaleph.core.di.service.DiJobStepAttrService;
import cn.sliew.scaleph.core.di.service.convert.DiJobStepAttrConvert;
import cn.sliew.scaleph.core.di.service.dto.DiJobStepAttrDTO;
import cn.sliew.scaleph.dao.entity.master.di.DiJobStepAttr;
import cn.sliew.scaleph.dao.mapper.master.di.DiJobStepAttrMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DiJobStepAttrServiceImpl implements DiJobStepAttrService {

    @Autowired
    private DiJobStepAttrMapper diJobStepAttrMapper;

    @Override
    public int deleteByProjectId(Collection<? extends Serializable> projectIds) {
        return this.diJobStepAttrMapper.deleteByProjectId(projectIds);
    }

    @Override
    public int deleteByJobId(Collection<? extends Serializable> jobIds) {
        return this.diJobStepAttrMapper.deleteByJobId(jobIds);
    }

    @Override
    public int deleteSurplusStepAttr(Long jobId, List<String> linkStepList) {
        return this.diJobStepAttrMapper.delete(
            new LambdaQueryWrapper<DiJobStepAttr>()
                .eq(DiJobStepAttr::getJobId, jobId)
                .notIn(CollectionUtil.isNotEmpty(linkStepList), DiJobStepAttr::getStepCode,
                    linkStepList)
        );
    }

    @Override
    public int upsert(DiJobStepAttrDTO diJobStepAttr) {
        DiJobStepAttr stepAttr = this.diJobStepAttrMapper.selectOne(
            new LambdaQueryWrapper<DiJobStepAttr>()
                .eq(DiJobStepAttr::getJobId, diJobStepAttr.getJobId())
                .eq(DiJobStepAttr::getStepCode, diJobStepAttr.getStepCode())
                .eq(DiJobStepAttr::getStepAttrKey, diJobStepAttr.getStepAttrKey())
        );
        DiJobStepAttr attr = DiJobStepAttrConvert.INSTANCE.toDo(diJobStepAttr);
        if (stepAttr == null) {
            return this.diJobStepAttrMapper.insert(attr);
        } else {
            return this.diJobStepAttrMapper.update(attr,
                new LambdaUpdateWrapper<DiJobStepAttr>()
                    .eq(DiJobStepAttr::getJobId, attr.getJobId())
                    .eq(DiJobStepAttr::getStepCode, attr.getStepCode())
                    .eq(DiJobStepAttr::getStepAttrKey, attr.getStepAttrKey())
            );
        }
    }

    @Override
    public List<DiJobStepAttrDTO> listJobStepAttr(Long jobId, String stepCode) {
        List<DiJobStepAttr> list = this.diJobStepAttrMapper.selectList(
            new LambdaQueryWrapper<DiJobStepAttr>()
                .eq(DiJobStepAttr::getJobId, jobId)
                .eq(DiJobStepAttr::getStepCode, stepCode)
        );
        return DiJobStepAttrConvert.INSTANCE.toDto(list);
    }

    @Override
    public int clone(Long sourceJobId, Long targetJobId) {
        return this.diJobStepAttrMapper.clone(sourceJobId, targetJobId);
    }
}
