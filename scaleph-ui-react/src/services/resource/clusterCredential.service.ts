import {ClusterCredential, ClusterCredentialListParam, CredentialFile} from "@/services/resource/typings";
import {request} from "@@/exports";
import {PageResponse, ResponseBody} from "@/app.d";

const url: string = '/api/resource/cluster-credential';

export async function list(queryParam: ClusterCredentialListParam) {
  return request<PageResponse<ClusterCredential>>(`${url}`, {
    method: 'GET',
    params: queryParam
  }).then((res) => {
    const result = {
      data: res.records,
      total: res.total,
      pageSize: res.size,
      current: res.current,
    };
    return result;
  })
}

export async function selectOne(id: number) {
  return request<ResponseBody<ClusterCredential>>(`${url}/` + id, {
    method: 'GET'
  })
}

export async function add(row: ClusterCredential) {
  return request<ResponseBody<any>>(`${url}`, {
    method: 'PUT',
    data: row
  })
}

export async function update(row: ClusterCredential) {
  return request<ResponseBody<any>>(`${url}`, {
    method: 'POST',
    data: row
  })
}

export async function deleteBatch(rows: ClusterCredential[]) {
  const params = rows.map((row) => row.id);
  return request<ResponseBody<any>>(`${url}/batch`, {
    method: 'DELETE',
    data: params
  })
}

export async function listFiles(id: number) {
  return request<ResponseBody<Array<CredentialFile>>>(`${url}/` + id, {
    method: 'GET'
  })
}

export async function uploadFiles(id: number, files: File[]) {
  const params: FormData = new FormData();
  files.forEach(function (file) {
    params.append("files", file)
  })
  return request<ResponseBody<any>>(`${url}/` + id + "/file", {
    method: 'POST',
    data: params
  })
}

export async function deleteFiles(id: number, files: CredentialFile[]) {
  const params = files.map((row) => row.name);
  return request<ResponseBody<any>>(`${url}/` + id + "/file", {
    method: 'DELETE',
    data: params
  })
}


