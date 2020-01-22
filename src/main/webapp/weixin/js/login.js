/**
 * 微信企业号授权登陆
 * @param workId：企业号id
 * @param state:当前页面
 */
function login(workId,state){
//	var appid = "ww6c19a54e88180452";
//	var callback_url = encodeURI("http://www.huanyoutec.com/cofC/aida/login/callback.do?workId=1&appId=701");
//	var scope = "snsapi_privateinfo";
//	var auth_url = "https://open.weixin.qq.com/connect/oauth2/authorize?"+
//			"appid="+appid+"&redirect_uri="+callback_url+"&response_type=code&scope="+scope+"&agentid=1000010&state="+state+"#wechat_redirect";
	var auth_url = "http://www.huanyoutec.com/cofC/aida/login/authorize.do?workId="+workId+"&state="+state;
	window.location.href = auth_url;
}

	