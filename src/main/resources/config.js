const webConfig = {
  // 权限认证前端
  authorization_web_url: 'http://172.21.32.110/auth',
  // 权限认证后台
  authorization_api_url: 'http://172.21.32.110/auth_service',

  // 系统设置前端
  sysManage_web_url: 'http://172.21.32.110/system',
  // 系统设置后台
  sysManage_api_url: 'http://172.21.32.110/system_service',

  // 指标管理前端
  target_web_url: 'http://172.21.32.110/target',
  // 指标管理后台
  target_api_url: 'http://172.21.32.110/target_service',

  // 决策系统前端
  cockpit_web_url: 'http://172.21.32.110/decesion',
  
  //驾驶舱系统前端
  decesion_web_url: 'http://172.21.32.110/cockpit',

  // 模型后台
  model_api_url: 'http://172.21.32.110/model_service',

  // 指标数据websocket
  target_websocket_url: 'ws://172.21.32.110/target_socket',
  
  //容知设备诊断
  diagnosis_web_url: 'http://172.22.13.12:8125/diagnosis_web',

  // 环境参数设置，false:发布环境，true：开发环境
  devSwitch: true
};

