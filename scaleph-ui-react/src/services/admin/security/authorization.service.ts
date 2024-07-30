import {PageResponse, ResponseBody} from '@/typings';
import {request} from '@umijs/max';
import {SecRole, SecUser} from "@/services/admin/typings";

export const AuthorizationService = {
  url: '/api/carp/security/authorization',

  unauthorizedRoles: async (param: any) => {
    return request<ResponseBody<PageResponse<SecRole>>>(`${AuthorizationService.url}/resource-web/unauthorized-roles`, {
      method: 'GET',
      params: param,
    });
  },
  authorizedRoles: async (param: any) => {
    return request<ResponseBody<PageResponse<SecRole>>>(`${AuthorizationService.url}/resource-web/authorized-roles`, {
      method: 'GET',
      params: param,
    });
  },

  resourceWebRoles: async (param: any) => {
    return request<ResponseBody<any>>(`${AuthorizationService.url}/resource-web/roles`, {
      method: 'PUT',
      data: param,
    });
  },
  resourceWebRolesDelete: async (param: any) => {
    return request<ResponseBody<any>>(`${AuthorizationService.url}/resource-web/roles`, {
      method: 'DELETE',
      data: param,
    });
  },

  //查询角色绑定用户列表
  requestAuthorizedUsers: async (param: any) => {
    return request<ResponseBody<PageResponse<SecUser>>>(`${AuthorizationService.url}/role/authorized-users`, {
      method: 'GET',
      params: param,
    });
  },
  //查询角色未绑定用户列表
  requestUnauthorizedUsers: async (param: any) => {
    return request<ResponseBody<PageResponse<SecUser>>>(`${AuthorizationService.url}/role/unauthorized-users`, {
      method: 'GET',
      params: param,
    });
  },
  //批量为角色绑定用户
  rolesUser: async (param: any) => {
    return request<ResponseBody<any>>(`${AuthorizationService.url}/role/users`, {
      method: 'PUT',
      data: param,
    });
  },
  //批量为角色解除用户绑定
  deleteRolesUser: async (param: any) => {
    return request<ResponseBody<any>>(`${AuthorizationService.url}/role/users`, {
      method: 'DELETE',
      data: param,
    });
  },

  //查询用户未绑定角色列表
  requestUnauthorizedRoles: async (param: any) => {
    return request<ResponseBody<any>>(`${AuthorizationService.url}/user/unauthorized-roles`, {
      method: 'GET',
      params: param,
    });
  },
  //查询角色绑定用户列表
  requestUserAuthorizedRoles: async (param: any) => {
    return request<ResponseBody<any>>(`${AuthorizationService.url}/user/authorized-roles`, {
      method: 'GET',
      params: param,
    });
  },

  //批量为用户绑定角色
  requestUserRoles: async (param: any) => {
    return request<ResponseBody<any>>(`${AuthorizationService.url}/user/roles`, {
      method: 'PUT',
      data: param,
    });
  },

  //批量为用户解除角色绑定
  requestDeleteUserRoles: async (param: any) => {
    return request<ResponseBody<any>>(`${AuthorizationService.url}/user/roles`, {
      method: 'DELETE',
      data: param,
    });
  },

  //查询所有 资源-web 和指定角色绑定状态
  requestResourceWebs: async (param: any) => {
    return request<ResponseBody<any>>(`${AuthorizationService.url}/role/resource-webs`, {
      method: 'GET',
      params: param,
    });
  },

  //批量为角色绑定 资源-web
  requestRoleResourceWebs: async (param: any) => {
    return request<ResponseBody<any>>(`${AuthorizationService.url}/role/resource-webs`, {
      method: 'PUT',
      data: param,
    });
  },

  //批量为角色解除 资源-web 绑定
  requestDeleteRoleResourceWebs: async (param: any) => {
    return request<ResponseBody<any>>(`${AuthorizationService.url}/role/resource-webs`, {
      method: 'DELETE',
      data: param,
    });
  },
};
