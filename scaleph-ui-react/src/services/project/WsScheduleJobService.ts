import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import {
  ScheduleGroup,
  ScheduleGroupAddParam,
  ScheduleGroupParam,
  ScheduleGroupUpdateParam,
  ScheduleJob, ScheduleJobAddParam, ScheduleJobParam, ScheduleJobUpdateParam
} from './typings';

export const WsScheduleJobService = {
  url: '/api/carp/schedule/config',

  list: async (queryParam: ScheduleJobParam) => {
    return request<ResponseBody<PageResponse<ScheduleJob>>>(`${WsScheduleJobService.url}/page`, {
      method: 'GET',
      params: queryParam,
    }).then((res) => {
      const result = {
        data: res.data?.records,
        total: res.data?.total,
        pageSize: res.data?.size,
        current: res.data?.current,
      };
      return result;
    });
  },

  selectOne: async (id: number) => {
    return request<ScheduleJob>(`${WsScheduleJobService.url}/${id}`, {
      method: 'GET',
    });
  },

  add: async (row: ScheduleJobAddParam) => {
    return request<ResponseBody<any>>(`${WsScheduleJobService.url}`, {
      method: 'PUT',
      data: row,
    });
  },

  update: async (row: ScheduleJobUpdateParam) => {
    return request<ResponseBody<any>>(`${WsScheduleJobService.url}`, {
      method: 'POST',
      data: row,
    });
  },

  deleteOne: async (row: ScheduleGroup) => {
    return request<ResponseBody<boolean>>(`${WsScheduleJobService.url}/${row.id}`, {
      method: 'DELETE'
    });
  },

  deleteBatch: async (rows: ScheduleGroup[]) => {
    const params = rows.map((row) => row.id);
    return request<ResponseBody<boolean>>(`${WsScheduleJobService.url}/batch`, {
      method: 'DELETE',
      data: params,
    });
  },
};
