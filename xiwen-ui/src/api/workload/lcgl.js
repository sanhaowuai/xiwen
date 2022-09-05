import request from '@/utils/request'


// 查询流程列表
export function listLcgl(query) {
  return request({
    url: '/workload/lcgl/list',
    method: 'get',
    params: query
  })
}

// 查询流程列表-正式可用
export function listLcglBySb() {
  return request({
    url: '/workload/lcgl/listBySb',
    method: 'get'
  })
}


// 新增流程
export function addLcxx(data) {
  return request({
    url: '/workload/lcgl/add',
    method: 'post',
    data: data
  })
}

// 修改流程
export function updateLcxx(data) {
  return request({
    url: '/workload/lcgl/update',
    method: 'post',
    data: data
  })
}

// 删除流程
export function deleteLcxx(id) {
  return request({
    url: '/workload/lcgl/delete',
    method: 'delete',
    params: {id:id}
  })
}

// 查询单条流程
export function getLcxxById(id) {
  return request({
    url: '/workload/lcgl/getLcxxById',
    method: 'get',
    params: {id:id}
  })
}

// 流程状态修改
export function changeLcStatus(id, kyf) {
  const data = {
    id,
    kyf
  }
  return request({
    url: '/workload/lcgl/changeLcStatus',
    method: 'post',
    data: data
  })
}
