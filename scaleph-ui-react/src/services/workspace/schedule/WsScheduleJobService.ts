import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import {WorkspaceScheduleAPI} from "@/services/workspace/schedule/typings";

export const WsScheduleJobService = {
  url: '/api/carp/schedule/config',

  list: async (queryParam: WorkspaceScheduleAPI.ScheduleJobParam) => {
    return request<ResponseBody<PageResponse<WorkspaceScheduleAPI.ScheduleJob>>>(`${WsScheduleJobService.url}/page`, {
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
    return request<WorkspaceScheduleAPI.ScheduleJob>(`${WsScheduleJobService.url}/${id}`, {
      method: 'GET',
    });
  },

  add: async (row: WorkspaceScheduleAPI.ScheduleJobAddParam) => {
    return request<ResponseBody<any>>(`${WsScheduleJobService.url}`, {
      method: 'PUT',
      data: row,
    });
  },

  update: async (row: WorkspaceScheduleAPI.ScheduleJobUpdateParam) => {
    return request<ResponseBody<any>>(`${WsScheduleJobService.url}`, {
      method: 'POST',
      data: row,
    });
  },

  deleteOne: async (row: WorkspaceScheduleAPI.ScheduleJob) => {
    return request<ResponseBody<boolean>>(`${WsScheduleJobService.url}/${row.id}`, {
      method: 'DELETE'
    });
  },

  deleteBatch: async (rows: WorkspaceScheduleAPI.ScheduleJob[]) => {
    const params = rows.map((row) => row.id);
    return request<ResponseBody<boolean>>(`${WsScheduleJobService.url}/batch`, {
      method: 'DELETE',
      data: params,
    });
  },
};
