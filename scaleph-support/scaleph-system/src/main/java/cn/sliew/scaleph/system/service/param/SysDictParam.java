package cn.sliew.scaleph.system.service.param;

import cn.sliew.scaleph.common.param.PaginationParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gleiyu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictParam extends PaginationParam {
    @ApiModelProperty(value = "字典类型编码")
    private String dictTypeCode;

    @ApiModelProperty(value = "字典编码")
    private String dictCode;

    @ApiModelProperty(value = "字典值")
    private String dictValue;

    @ApiModelProperty(value = "是否有效")
    private String isValid;
}
