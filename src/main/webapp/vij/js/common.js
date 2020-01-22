var LOGIN_PLAT = 709; /* 正式 */
(function() {
    var updateBaseFontSize = function() {
        var dWidth = document.documentElement.clientWidth;
        console.log(dWidth)
        var baseFontSize = dWidth / 1900 * 20;
        $('html').css('font-size', baseFontSize + 'px');
    };
    window.addEventListener('resize', updateBaseFontSize);
    updateBaseFontSize();
}());

/**
 * 事件上报
 * @param {[type]} ctx     网站开头
 * @param {[type]} userId     [用户ID]
 * @param {[type]} type       [事件行为]
 * @param {[type]} objectType [事件对象] //7091:首页：7092：新品；7093：搭配；7094：顾问；7095：我们 ；7096：分类：7097：购物车
 * @param {[type]} objectId   [对象Id]
 */
function addUserAction(userId, type, objectType, objectId) {
    let origin = window.location.origin;
    console.log(objectType)
    $.ajax({
        url: origin + '/cofC/aida/reportUserCardBehavior.do',
        type: 'POST',
        dataType: 'json',
        data: {
            appId: LOGIN_PLAT,
            userId: userId,
            type: type,
            objectType: objectType,
            objectId: objectId,
            salesPersonId: 1,
        },
    }).done((res) => {
        console.log(res)
    });
}

//获取url中"?"符后的字串
function getUrlFooter() {
    // decodeURI(URL中可能带有中文)
    var url = decodeURI(location.search);
    var paramsList = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            paramsList[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    console.log(paramsList)
    return paramsList;
}
