package cn.sliew.scaleph.system.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import cn.sliew.scaleph.common.constant.Constants;
import cn.sliew.scaleph.common.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 数据字典表
 * </p>
 *
 * @author liyu
 * @since 2021-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "数据字典信息", description = "数据字典表")
public class SysDictDTO extends BaseDTO {

    private static final long serialVersionUID = -4136245238746831595L;

    @NotNull
    @ApiModelProperty(value = "字典类型")
    private SysDictTypeDTO dictType;

    @NotBlank
    @Length(min = 1, max = 30)
    @Pattern(regexp = "\\w+$")
    @ApiModelProperty(value = "字典编码")
    private String dictCode;

    @NotBlank
    @Length(min = 1, max = 100)
    @ApiModelProperty(value = "字典值")
    private String dictValue;

    @Length(max = 200)
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否有效")
    private String isValid;

    public String getKey() {
        return this.getDictType().getDictTypeCode() + Constants.SEPARATOR + this.getDictCode();
    }
}
