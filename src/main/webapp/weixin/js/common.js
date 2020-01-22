var url = "http://wxmark.hipivot.net/weixin/";
var hosturl = "http://wxmark.hipivot.net/wx";
var applicationId = 1;
function http_request(name, params, callback, error) {
	$.ajax({
		url : hosturl + '/' + name,
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

function http_get(name, params, callback, error) {
	$.ajax({
		url : hosturl + '/' + name,
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
 * @param callback
 */
function wxConfig(callback) {
	var params = {
		url : location.href.split('#')[0]
	};
	http_request("webLogin/getConfig.do", params, function(data) {
		var data = data.objects;
		wx.ready(function() {
			// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
			if (typeof callback === 'function') {
				callback();
			}
		});

		wx.error(function(res) {
		});
		wx.config({
			debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			appId : 'wx2dc773236a658ff1', // 必填，公众号的唯一标识
			timestamp : data.timestamp, // 必填，生成签名的时间戳
			nonceStr : data.noncestr, // 必填，生成签名的随机串
			signature : data.signature,// 必填，签名，见附录1
			jsApiList : [ "scanQRCode", "chooseWXPay" ]
		// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
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

