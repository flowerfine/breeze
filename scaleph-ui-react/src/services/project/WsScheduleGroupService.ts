import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import {ScheduleGroup, ScheduleGroupAddParam, ScheduleGroupParam, ScheduleGroupUpdateParam} from './typings';

export const WsScheduleGroupService = {
  url: '/api/carp/schedule/group',

  list: async (queryParam: ScheduleGroupParam) => {
    return request<PageResponse<ScheduleGroup>>(`${WsScheduleGroupService.url}/page`, {
      method: 'GET',
      params: queryParam,
    }).then((res) => {
      const result = {
        data: res.records,
        total: res.total,
        pageSize: res.size,
        current: res.current,
      };
      return result;
    });
  },

  listByType: async () => {
    return request<ResponseBody<Array<ScheduleGroup>>>(`${WsScheduleGroupService.url}`, {
      method: 'GET'
    });
  },

  selectOne: async (id: number) => {
    return request<ScheduleGroup>(`${WsScheduleGroupService.url}/${id}`, {
      method: 'GET',
    });
  },

  add: async (row: ScheduleGroupAddParam) => {
    return request<ResponseBody<any>>(`${WsScheduleGroupService.url}`, {
      method: 'PUT',
      data: row,
    });
  },

  update: async (row: ScheduleGroupUpdateParam) => {
    return request<ResponseBody<any>>(`${WsScheduleGroupService.url}`, {
      method: 'POST',
      data: row,
    });
  },

  deleteOne: async (row: ScheduleGroup) => {
    return request<ResponseBody<boolean>>(`${WsScheduleGroupService.url}/${row.id}`, {
      method: 'DELETE'
    });
  },

  deleteBatch: async (rows: ScheduleGroup[]) => {
    const params = rows.map((row) => row.id);
    return request<ResponseBody<boolean>>(`${WsScheduleGroupService.url}/batch`, {
      method: 'DELETE',
      data: params,
    });
  },
};
