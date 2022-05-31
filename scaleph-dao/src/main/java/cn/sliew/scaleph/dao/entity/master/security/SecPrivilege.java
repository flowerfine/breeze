package cn.sliew.scaleph.dao.entity.master.security;

import cn.sliew.scaleph.dao.entity.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author liyu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sec_privilege")
@ApiModel(value = "SecPrivilege对象", description = "权限表")
public class SecPrivilege extends BaseDO {

    private static final long serialVersionUID = 4366151466958631600L;

    @ApiModelProperty(value = "权限标识")
    private String privilegeCode;

    @ApiModelProperty(value = "权限名称")
    private String privilegeName;

    @ApiModelProperty(value = "资源类型")
    private String resourceType;

    @ApiModelProperty(value = "资源路径")
    private String resourcePath;

    @ApiModelProperty(value = "上级权限id")
    private Long pid;
}
