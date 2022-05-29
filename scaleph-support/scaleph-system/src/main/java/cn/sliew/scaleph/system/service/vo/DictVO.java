package cn.sliew.scaleph.system.service.vo;

import java.io.Serializable;

import cn.sliew.scaleph.common.constant.Constants;
import cn.sliew.scaleph.system.cache.DictCache;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gleiyu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "数据字典", description = "数据字典对象，用来前后端枚举值交互")
public class DictVO implements Serializable {
    private static final long serialVersionUID = 1357098965682678688L;

    private String value;
    private String label;

    public static DictVO toVO(String dictTypeCode, String dictCode) {
        String dictValue = DictCache.getValueByKey(dictTypeCode + Constants.SEPARATOR + dictCode);
        return new DictVO(dictCode, dictValue);
    }
}
