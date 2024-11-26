import {SysDictService} from "@/services/admin/system/sysDict.service";

export const DictDataService = {
    url: '/api/admin/dict/data',

    listDictDataByType2: async (dictTypeCode: string) => {
        return SysDictService.listDictByDefinition(dictTypeCode)
    },
};
