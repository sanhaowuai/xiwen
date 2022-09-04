import request from '@/utils/request'


// 获取通知公告list
export function tzggList(data) {
  return request({
    url: '/system/notice/tzggList',
    headers: {
      isToken: true
    },
    method: 'get',
    params: data
  })
}
