import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import {WorkspaceScheduleAPI} from "@/services/workspace/schedule/typings";

export const WsScheduleJobInstanceService = {
  url: '/api/carp/schedule/instance',

  list: async (queryParam: WorkspaceScheduleAPI.ScheduleJobInstanceParam) => {
    return request<ResponseBody<PageResponse<WorkspaceScheduleAPI.ScheduleJobInstance>>>(`${WsScheduleJobInstanceService.url}/page`, {
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
    return request<WorkspaceScheduleAPI.ScheduleJobInstance>(`${WsScheduleJobInstanceService.url}/${id}`, {
      method: 'GET',
    });
  },

  add: async (row: WorkspaceScheduleAPI.ScheduleJobInstance) => {
    return request<ResponseBody<any>>(`${WsScheduleJobInstanceService.url}`, {
      method: 'PUT',
      data: row,
    });
  },

  update: async (row: WorkspaceScheduleAPI.ScheduleJobInstance) => {
    return request<ResponseBody<any>>(`${WsScheduleJobInstanceService.url}`, {
      method: 'POST',
      data: row,
    });
  },

  deleteOne: async (row: WorkspaceScheduleAPI.ScheduleJobInstance) => {
    return request<ResponseBody<boolean>>(`${WsScheduleJobInstanceService.url}/${row.id}`, {
      method: 'DELETE'
    });
  },

  deleteBatch: async (rows: WorkspaceScheduleAPI.ScheduleJobInstance[]) => {
    const params = rows.map((row) => row.id);
    return request<ResponseBody<boolean>>(`${WsScheduleJobInstanceService.url}/batch`, {
      method: 'DELETE',
      data: params,
    });
  },
};
