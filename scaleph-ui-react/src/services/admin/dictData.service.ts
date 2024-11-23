import {Dict, PageResponse} from '@/typings';
import {request} from '@umijs/max';
import {SysDictData, SysDictDataParam} from './typings';

export const DictDataService = {
  url: '/api/admin/dict/data',

  listDictDataByType2: async (dictTypeCode: string) => {
    return request<Dict[]>(`${DictDataService.url}/v2/` + dictTypeCode, {
      method: 'GET',
    });
  },
};
