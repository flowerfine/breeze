package cn.sliew.scaleph.api.controller.datadev;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.sliew.scaleph.api.annotation.Logging;
import cn.sliew.scaleph.api.util.I18nUtil;
import cn.sliew.scaleph.api.vo.ResponseVO;
import cn.sliew.scaleph.common.enums.ErrorShowTypeEnum;
import cn.sliew.scaleph.common.enums.ResponseCodeEnum;
import cn.sliew.scaleph.core.di.service.DiJobService;
import cn.sliew.scaleph.core.di.service.DiProjectService;
import cn.sliew.scaleph.core.di.service.dto.DiProjectDTO;
import cn.sliew.scaleph.core.di.service.param.DiProjectParam;
import cn.sliew.scaleph.system.service.vo.DictVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gleiyu
 */
@Slf4j
@Api(tags = "数据开发-项目管理")
@RestController
@RequestMapping(path = "/api/datadev/project")
public class ProjectController {
    @Autowired
    private DiProjectService diProjectService;

    @Autowired
    private DiJobService diJobService;

    @Logging
    @GetMapping
    @ApiOperation(value = "查询项目列表", notes = "分页查询项目列表")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).DATADEV_PROJECT_SELECT)")
    public ResponseEntity<Page<DiProjectDTO>> listProject(DiProjectParam param) {
        Page<DiProjectDTO> page = this.diProjectService.listByPage(param);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Logging
    @GetMapping(path = "/all")
    @ApiOperation(value = "查询所有项目列表", notes = "查询所有项目列表")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).DATADEV_PROJECT_SELECT)")
    public ResponseEntity<List<DictVO>> listAll() {
        List<DictVO> result = new ArrayList<>();
        List<DiProjectDTO> list = this.diProjectService.listAll();
        list.forEach(p -> {
            result.add(new DictVO(String.valueOf(p.getId()), p.getProjectCode()));
        });
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Logging
    @PostMapping
    @ApiOperation(value = "新增项目", notes = "新增项目")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).DATADEV_PROJECT_ADD)")
    public ResponseEntity<ResponseVO> addProject(
        @Validated @RequestBody DiProjectDTO diProjectDTO) {
        this.diProjectService.insert(diProjectDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.CREATED);
    }

    @Logging
    @PutMapping
    @ApiOperation(value = "修改项目", notes = "修改项目")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).DATADEV_PROJECT_EDIT)")
    public ResponseEntity<ResponseVO> editProject(
        @Validated @RequestBody DiProjectDTO diProjectDTO) {
        this.diProjectService.update(diProjectDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @DeleteMapping(path = "/{id}")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "删除项目", notes = "删除项目")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).DATADEV_PROJECT_DELETE)")
    public ResponseEntity<ResponseVO> deleteProject(@PathVariable(value = "id") Long projectId) {
        List<Long> projectids = Collections.singletonList(projectId);
        if (this.diJobService.hasValidJob(projectids)) {
            return new ResponseEntity<>(ResponseVO.error(ResponseCodeEnum.ERROR_CUSTOM.getCode(),
                I18nUtil.get("response.error.di.notEmptyProject"), ErrorShowTypeEnum.NOTIFICATION),
                HttpStatus.OK);
        }
        this.diProjectService.deleteById(projectId);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @PostMapping(path = "/batch")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "批量删除项目", notes = "批量删除项目")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).DATADEV_PROJECT_DELETE)")
    public ResponseEntity<ResponseVO> deleteProject(@RequestBody Map<Integer, Long> map) {
        if (this.diJobService.hasValidJob(map.values())) {
            return new ResponseEntity<>(ResponseVO.error(ResponseCodeEnum.ERROR_CUSTOM.getCode(),
                I18nUtil.get("response.error.di.notEmptyProject"), ErrorShowTypeEnum.NOTIFICATION),
                HttpStatus.OK);
        } else {
            this.diProjectService.deleteBatch(map);
            return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
        }
    }
}
