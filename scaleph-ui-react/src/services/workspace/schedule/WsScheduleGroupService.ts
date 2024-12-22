import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import {WorkspaceScheduleAPI} from "@/services/workspace/schedule/typings";

export const WsScheduleGroupService = {
  url: '/api/carp/schedule/group',

  list: async (queryParam: WorkspaceScheduleAPI.ScheduleGroupParam) => {
    return request<ResponseBody<PageResponse<WorkspaceScheduleAPI.ScheduleGroup>>>(`${WsScheduleGroupService.url}/page`, {
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

  listByType: async () => {
    return request<ResponseBody<Array<WorkspaceScheduleAPI.ScheduleGroup>>>(`${WsScheduleGroupService.url}`, {
      method: 'GET'
    });
  },

  selectOne: async (id: number) => {
    return request<WorkspaceScheduleAPI.ScheduleGroup>(`${WsScheduleGroupService.url}/${id}`, {
      method: 'GET',
    });
  },

  add: async (row: WorkspaceScheduleAPI.ScheduleGroupAddParam) => {
    return request<ResponseBody<any>>(`${WsScheduleGroupService.url}`, {
      method: 'PUT',
      data: row,
    });
  },

  update: async (row: WorkspaceScheduleAPI.ScheduleGroupUpdateParam) => {
    return request<ResponseBody<any>>(`${WsScheduleGroupService.url}`, {
      method: 'POST',
      data: row,
    });
  },

  deleteOne: async (row: WorkspaceScheduleAPI.ScheduleGroup) => {
    return request<ResponseBody<boolean>>(`${WsScheduleGroupService.url}/${row.id}`, {
      method: 'DELETE'
    });
  },

  deleteBatch: async (rows: WorkspaceScheduleAPI.ScheduleGroup[]) => {
    const params = rows.map((row) => row.id);
    return request<ResponseBody<boolean>>(`${WsScheduleGroupService.url}/batch`, {
      method: 'DELETE',
      data: params,
    });
  },
};
