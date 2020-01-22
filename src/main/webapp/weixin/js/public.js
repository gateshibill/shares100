var url = "http://www.huanyoutec.com/cofC/weixin"; //页面路径
var apiUrl = "http://www.huanyoutec.com/cofC/aida";//api路径
var workId = getUrlParam("workId")?getUrlParam("workId"):1;  //企业号id
var appId = 701; //小程序id
//var userId = getCookie("feikan_qiyehao_"+workId);
/**
 * 微信企业号授权登陆
 * @param workId：企业号id
 * @param state:当前页面
 */
function login(workId,state){
//	var appid = "ww6c19a54e88180452";
//	var callback_url = encodeURI("http://www.huanyoutec.com/cofC/aida/login/callback.do?workId="+workId);
//	var scope = "snsapi_privateinfo";
//	var auth_url = "https://open.weixin.qq.com/connect/oauth2/authorize?"+
//			"appid="+appid+"&redirect_uri="+callback_url+"&response_type=code&scope="+scope+"&agentid=1000010&state="+state+"#wechat_redirect";
	var auth_url = "http://www.huanyoutec.com/cofC/aida/login/authorize.do?workId="+workId+"&state="+state;
	window.location.href = auth_url;
}
/**
 * post请求
 * @param name
 * @param params
 * @param callback
 * @param error
 */
function http_post(name, params, callback, error) {
	$.ajax({
		url : apiUrl + '/' + name,
		type : "POST",
		dataType : 'json',
		data : params,
		success : function(data) {
			if (typeof callback === 'function') {
				callback(data);
			}
		},
		error : function(data) {
			if (typeof error === 'function') {
				error(data);
			} else {
				console.log('网络错误，请检查');
			}
		}
	});
}
/**
 * get请求
 * @param name
 * @param params
 * @param callback
 * @param error
 */
function http_get(name, params, callback, error) {
	$.ajax({
		url : apiUrl + '/' + name,
		type : "GET",
		dataType : 'json',
		data : params,
		success : function(data) {
			if (typeof callback === 'function') {
				callback(data);
			}
		},
		error : function(data) {
			if (typeof error === 'function') {
				error(data);
			} else {
				console.log('网络错误，请检查');
			}
		}
	});
}

/**
 * 获取配置
 * @param workId : 企业号id
 */
function wxConfig(workId) {
	var params = {
	    workId:workId,
		url : location.href.split('#')[0]
	};
	http_post("login/getWxConfig.do", params, function(data) {
		 var data = data.objects;
		 wx.config({
			    beta: true,// 必须这么写，否则wx.invoke调用形式的jsapi会有问题
			    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			    appId: data.appId, // 必填，企业微信的corpID
			    timestamp:data.timestamp, // 必填，生成签名的时间戳
			    nonceStr: data.nonceStr, // 必填，生成签名的随机串
			    signature: data.signature,// 必填，签名，见附录1
			    jsApiList: ['chooseImage','uploadImage','onMenuShareAppMessage','onMenuShareWechat','onMenuShareTimeline'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		 });
		 wx.ready(function(res){
			 console.log(res);
		 });
		 wx.error(function(res){
			 console.log(res)
		 });
	});
}

/**
 * 获取url上的参数:实现扫码进店
 * @param name：参数名称
 * @returns
 */
//获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); //匹配目标参数
	if (r != null){
		return unescape(r[2]);
	}else{
		return null; //返回参数值
	}	
}

/**
 * 获取cookie
 * @param cookie_name
 * @returns
 */
function getCookie(cookie_name) {
	var allcookies = document.cookie;
	var cookie_pos = allcookies.indexOf(cookie_name); //索引的长度

	// 如果找到了索引，就代表cookie存在，
	// 反之，就说明不存在。
	if (cookie_pos != -1) {
		// 把cookie_pos放在值的开始，只要给值加1即可。
		cookie_pos += cookie_name.length + 1; //这里容易出问题，所以请大家参考的时候自己好好研究一下
		var cookie_end = allcookies.indexOf(";", cookie_pos);

		if (cookie_end == -1) {
			cookie_end = allcookies.length;
		}

		var value = unescape(allcookies.substring(cookie_pos, cookie_end)); //这里就可以得到你想要的cookie的值了。。。
	}
	return value;
}
