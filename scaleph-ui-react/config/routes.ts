﻿import { PRIVILEGE_CODE } from '../src/constant';

export default [
  {
    path: '/',
    redirect: '/studio/databoard',
  },
  {
    path: '/login',
    layout: false,
    component: './User/Login',
  },
  {
    path: '/register',
    layout: false,
    component: './User/Register',
  },
  {
    path: '/user/center',
    component: './User',
  },
  {
    name: 'studio',
    path: '/studio',
    icon: 'codeSandbox',
    pCode: PRIVILEGE_CODE.studioShow,
    access: 'normalRouteFilter',
    routes: [
      {
        path: '/studio',
        redirect: '/studio/databoard',
        pCode: PRIVILEGE_CODE.studioShow,
        access: 'normalRouteFilter',
      },
      {
        name: 'databoard',
        path: '/studio/databoard',
        icon: 'dashboard',
        exact: true,
        component: './Studio/DataBoard',
        pCode: PRIVILEGE_CODE.studioDataBoardShow,
        access: 'normalRouteFilter',
      },
    ],
  },
  {
    name: 'project',
    path: '/project',
    icon: 'project',
    pCode: PRIVILEGE_CODE.datadevProjectShow,
    routes: [
      {
        path: '/project',
        exact: true,
        component: './Project',
        pCode: PRIVILEGE_CODE.datadevProjectShow,
        access: 'normalRouteFilter',
      },
    ],
  },
  {
    path: '/workspace',
    pCode: PRIVILEGE_CODE.datadevShow,
    access: 'normalRouteFilter',
    routes: [
      {
        path: '/workspace',
        redirect: '/workspace/di/job',
        pCode: PRIVILEGE_CODE.datadevJobShow,
        access: 'normalRouteFilter',
      },
      {
        name: 'di',
        path: '/workspace/di',
        icon: 'link',
        pCode: PRIVILEGE_CODE.datadevShow,
        access: 'normalRouteFilter',
        routes: [
          {
            name: 'datasource',
            path: '/workspace/di/datasource',
            exact: true,
            component: './DI/DataSource',
            pCode: PRIVILEGE_CODE.datadevDatasourceShow,
            access: 'normalRouteFilter',
          },
          {
            name: 'job',
            path: '/workspace/di/job',
            exact: true,
            component: './DI/DiJobView',
            pCode: PRIVILEGE_CODE.datadevJobShow,
            access: 'normalRouteFilter',
          },
        ],
      },
      {
        name: 'dev',
        path: '/workspace/dev',
        icon: 'consoleSql',
        pCode: PRIVILEGE_CODE.datadevShow,
        access: 'normalRouteFilter',
        routes: [
          {
            name: 'artifact',
            path: '/workspace/dev/artifact',
            exact: true,
            component: './DEV/Artifact',
            pCode: PRIVILEGE_CODE.datadevDatasourceShow,
            access: 'normalRouteFilter',
          },
          {
            path: '/workspace/dev/artifact/jar',
            exact: true,
            component: './DEV/Artifact/Jar',
            pCode: PRIVILEGE_CODE.datadevDatasourceShow,
            access: 'normalRouteFilter',
          },
          {
            name: 'clusterConfig',
            path: '/workspace/dev/clusterConfig',
            exact: true,
            component: './DEV/ClusterConfig',
            pCode: PRIVILEGE_CODE.datadevDatasourceShow,
            access: 'normalRouteFilter',
          },
          {
            path: '/workspace/dev/clusterConfigOptions',
            exact: true,
            component: './DEV/ClusterConfigOptions/ConfigOptions',
            pCode: PRIVILEGE_CODE.datadevDatasourceShow,
            access: 'normalRouteFilter',
          },
          {
            name: 'kubernetesOptions',
            path: '/workspace/dev/kubernetes',
            exact: true,
            component: './DEV/ClusterConfigOptions/Kubernetes',
            pCode: PRIVILEGE_CODE.datadevDatasourceShow,
            access: 'normalRouteFilter',
          },
          {
            name: 'clusterInstance',
            path: '/workspace/dev/clusterInstance',
            exact: true,
            component: './DEV/ClusterInstance',
            pCode: PRIVILEGE_CODE.datadevDatasourceShow,
            access: 'normalRouteFilter',
          },
          {
            name: 'jobForjar',
            path: '/workspace/dev/job/jar',
            exact: true,
            component: './DEV/Job/Jar',
            pCode: PRIVILEGE_CODE.datadevDatasourceShow,
            access: 'normalRouteFilter',
          },
          {
            path: '/workspace/dev/job/jar/detail',
            exact: true,
            component: './DEV/Job/Detail',
            pCode: PRIVILEGE_CODE.datadevDatasourceShow,
            access: 'normalRouteFilter',
          },
          {
            path: '/workspace/dev/job/jar/options',
            exact: true,
            component: './DEV/JobConfig/Jar',
            pCode: PRIVILEGE_CODE.datadevDatasourceShow,
            access: 'normalRouteFilter',
          },
        ],
      },
    ],
  },
  {
    name: 'resource',
    path: '/resource',
    icon: 'fileText',
    pCode: PRIVILEGE_CODE.datadevResourceShow,
    access: 'normalRouteFilter',
    routes: [
      {
        path: '/resource',
        redirect: '/resource/jar',
        pCode: PRIVILEGE_CODE.stdataShow,
        access: 'normalRouteFilter',
      },
      {
        name: 'jar',
        path: '/resource/jar',
        exact: true,
        component: './Resource/Jar',
        pCode: PRIVILEGE_CODE.datadevResourceShow,
        access: 'normalRouteFilter',
      },
      {
        name: 'flinkRelease',
        path: '/resource/flink-release',
        exact: true,
        component: './Resource/FlinkRelease',
        pCode: PRIVILEGE_CODE.datadevResourceShow,
        access: 'normalRouteFilter',
      },
      {
        name: 'seatunnelRelease',
        path: '/resource/seatunnel-release',
        exact: true,
        component: './Resource/SeaTunnelRelease',
        pCode: PRIVILEGE_CODE.datadevResourceShow,
        access: 'normalRouteFilter',
      },
      {
        name: 'kerberos',
        path: '/resource/kerberos',
        exact: true,
        component: './Resource/Kerberos',
        pCode: PRIVILEGE_CODE.datadevResourceShow,
        access: 'normalRouteFilter',
      },
      {
        name: 'clusterCredential',
        path: '/resource/cluster-credential',
        exact: true,
        component: './Resource/ClusterCredential',
        pCode: PRIVILEGE_CODE.datadevResourceShow,
        access: 'normalRouteFilter',
      },
      {
        path: '/resource/cluster-credential/file',
        exact: true,
        component: './Resource/CredentialFile',
        pCode: PRIVILEGE_CODE.datadevDatasourceShow,
        access: 'normalRouteFilter',
      },
    ],
  },
  {
    name: 'cluster',
    path: '/cluster',
    icon: 'cluster',
    pCode: PRIVILEGE_CODE.datadevClusterShow,
    access: 'normalRouteFilter',
    routes: [
      {
        path: '/cluster',
        exact: true,
        component: './Cluster',
        pCode: PRIVILEGE_CODE.datadevClusterShow,
        access: 'normalRouteFilter',
      },
    ],
  },

  {
    name: 'opscenter',
    path: '/opscenter',
    icon: 'lineChart',
    pCode: PRIVILEGE_CODE.opscenterShow,
    access: 'normalRouteFilter',
    routes: [
      {
        path: '/opscenter',
        redirect: '/opscenter/batch',
        pCode: PRIVILEGE_CODE.opscenterShow,
        access: 'normalRouteFilter',
      },
      {
        name: 'batch',
        path: '/opscenter/batch',
        icon: 'menu',
        exact: true,
        component: './OpsCenter/BatchJob',
        pCode: PRIVILEGE_CODE.opscenterBatchShow,
        access: 'normalRouteFilter',
      },
      {
        name: 'realtime',
        path: '/opscenter/realtime',
        icon: 'menu',
        exact: true,
        component: './OpsCenter/RealtimeJob',
        pCode: PRIVILEGE_CODE.opscenterRealtimeShow,
        access: 'normalRouteFilter',
      },
    ],
  },
  {
    name: 'stdata',
    path: '/stdata',
    icon: 'database',
    pCode: PRIVILEGE_CODE.stdataShow,
    access: 'normalRouteFilter',
    routes: [
      {
        path: '/stdata',
        redirect: '/stdata/dataElement',
        pCode: PRIVILEGE_CODE.stdataShow,
        access: 'normalRouteFilter',
      },
      {
        name: 'dataElement',
        path: '/stdata/dataElement',
        icon: 'menu',
        exact: true,
        component: './Stdata/DataElement',
        pCode: PRIVILEGE_CODE.stdataDataElementShow,
        access: 'normalRouteFilter',
      },
      {
        name: 'refdata',
        path: '/stdata/refdata',
        icon: 'menu',
        exact: true,
        component: './Stdata/RefData',
        pCode: PRIVILEGE_CODE.stdataRefDataShow,
        access: 'normalRouteFilter',
      },
      {
        name: 'refdataMap',
        path: '/stdata/refdataMap',
        icon: 'menu',
        exact: true,
        component: './Stdata/RefDataMap',
        pCode: PRIVILEGE_CODE.stdataRefDataMapShow,
        access: 'normalRouteFilter',
      },
      {
        name: 'system',
        path: '/stdata/system',
        icon: 'menu',
        exact: true,
        component: './Stdata/System',
        pCode: PRIVILEGE_CODE.stdataSystemShow,
        access: 'normalRouteFilter',
      },
    ],
  },
  {
    name: 'admin',
    path: '/admin',
    icon: 'setting',
    pCode: PRIVILEGE_CODE.adminShow,
    access: 'normalRouteFilter',
    routes: [
      {
        path: '/admin',
        redirect: '/admin/user',
        pCode: PRIVILEGE_CODE.adminShow,
        access: 'normalRouteFilter',
      },
      {
        name: 'user',
        path: '/admin/user',
        icon: 'user',
        exact: true,
        component: './Admin/User',
        pCode: PRIVILEGE_CODE.userShow,
        access: 'normalRouteFilter',
      },
      {
        name: 'privilege',
        path: '/admin/privilege',
        icon: 'team',
        exact: true,
        component: './Admin/Privilege',
        pCode: PRIVILEGE_CODE.privilegeShow,
        access: 'normalRouteFilter',
      },
      {
        name: 'dict',
        path: '/admin/dict',
        icon: 'table',
        exact: true,
        component: './Admin/Dict',
        pCode: PRIVILEGE_CODE.dictShow,
        access: 'normalRouteFilter',
      },
      {
        name: 'setting',
        path: '/admin/setting',
        icon: 'setting',
        exact: true,
        component: './Admin/Setting',
        pCode: PRIVILEGE_CODE.settingShow,
        access: 'normalRouteFilter',
      },
    ],
  },
  {
    component: './404',
  },
];
