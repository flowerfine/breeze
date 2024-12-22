import {Dict, QueryParam} from '@/typings';

declare namespace WorkspaceScheduleAPI {
  export type ScheduleGroup = {

    id: number;
    namespace: string;
    name: string;
    remark?: string;
    createTime?: Date;
    updateTime?: Date;
  };

  export type ScheduleGroupParam = QueryParam & {
    namespace: string;
    name?: string;
  };

  export type ScheduleGroupAddParam = {
    namespace: number;
    name: string;
    remark?: string;
  };

  export type ScheduleGroupUpdateParam = {
    id: number;
    remark?: string;
  };

  export type ScheduleJob = {
    id: number;
    jobGroup: ScheduleGroup;
    type: Dict;
    engineType: Dict;
    jobType: Dict;
    name: string;
    handler: string;
    remark?: string;
    createTime?: Date;
    updateTime?: Date;
  };

  export type ScheduleJobParam = QueryParam & {
    jobGroupId: number;
    type?: string;
    engineType?: string;
    jobType?: string;
    jobType?: string;
    name?: string;
    handler?: string;
  };

  export type ScheduleJobAddParam = {
    jobGroupId?: number;
    type: string;
    engineType: string;
    jobType: string;
    name: string;
    handler: string;
    remark?: string;
  };

  export type ScheduleJobUpdateParam = {
    id: number;
    name: string;
    handler: string;
    remark?: string;
  };

  export type ScheduleJobInstance = {
    id: number;
    jobConfig?: ScheduleJob;
    name: string;
    cron: string;
    timezone: string;
    startTime: string;
    endTime: string;
    props?: Record<string, any>;
    params?: string;
    timeout: string;
    status?: string;
    remark?: string;
    createTime?: Date;
    updateTime?: Date;
  };

  export type ScheduleJobInstanceParam = QueryParam & {
    jobConfigId: number;
    name?: string;
    status?: string;
  };

}


