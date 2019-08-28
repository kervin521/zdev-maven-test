const webConfig = {
  // 权限认证前端
  authorization_web_url: 'http://172.21.32.110:8552',
  // 权限认证后台
  authorization_api_url: 'http://172.21.32.110:8551',

  // 系统设置前端
  sysManage_web_url: 'http://172.21.32.110:8570',
  // 系统设置后台
  sysManage_api_url: 'http://172.21.32.110:8571',

  // 指标管理前端
  target_web_url: 'http://172.21.32.110:9090',
  // 指标管理后台
  target_api_url: 'http://172.21.32.110:8631',

  // 决策系统前端
  cockpit_web_url: 'http://172.21.32.110:7070',
  
  //驾驶舱系统前端
  decesion_web_url: 'http://172.21.32.110:8080',

  // 模型后台
  model_api_url: 'http://172.21.32.110:8170/dbsznjc',

  // 指标数据websocket
  target_websocket_url: 'ws://172.21.32.110:8172/dbsznjc',
  
  //容知设备诊断
  diagnosis_web_url: 'http://172.22.13.12:8125/#/user/loginAcc?account=Admin',

  // 环境参数设置，false:发布环境，true：开发环境
  devSwitch: true
};

