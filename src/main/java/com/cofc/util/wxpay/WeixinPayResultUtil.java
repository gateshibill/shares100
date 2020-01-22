package com.cofc.util.wxpay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.cofc.pojo.WeixinPayResult;

public class WeixinPayResultUtil {
	public static WeixinPayResult addPayResult(Map result) throws ParseException {
		WeixinPayResult payResult = new WeixinPayResult();
		/**
		 * 必定返回的结果
		 */
		String appId = result.get("appid").toString();// 公众账号ID
		String mchId = result.get("mch_id").toString();// 商户号
		String nonceStr = result.get("nonce_str").toString();// 返回的随机字符串
		String sign = result.get("sign").toString();// 返回的签名
		String resultCode = result.get("result_code").toString();// 业务结果
		String openId = result.get("openid").toString();// 返回的openid
		String tradeType = result.get("trade_type").toString();// 交易类型
		String bankType = result.get("bank_type").toString();// 付款银行
		String totalFee = result.get("total_fee").toString();// 订单金额
		String cashFee = result.get("cash_fee").toString();// 现金支付金额
		String transactionId = result.get("transaction_id").toString();// 微信支付订单号
		String outTradeNo = result.get("out_trade_no").toString();// 商户订单号
		String timeEnd = result.get("time_end").toString();// 支付完成时间
		

		payResult.setAppId(appId);
		payResult.setBankType(bankType);
		payResult.setCashFee(Integer.parseInt(cashFee));
		payResult.setMchId(mchId);
		payResult.setNonceStr(nonceStr);
		payResult.setOpenId(openId);
		payResult.setOutTradeNo(outTradeNo);
		payResult.setResultCode(resultCode);
		payResult.setSign(sign);
		payResult.setTimeEnd(StringToDate(timeEnd));
		payResult.setTotalFee(Integer.parseInt(totalFee));
		payResult.setTradeType(tradeType);
		payResult.setTransactionId(transactionId);
		/**
		 * 非必返回的结果
		 */
		String deviceInfo = "";
		String errCode = "";
		String errCodeDes = "";
		String isSubscribe = "";
		String settlementTotalFee = "";
		String feeType = "";
		String cashFeeType = "";
		String couponFee = "";
		String couponCount = "";
		String couponType = "";
		String couponId = "";
		String couponFeeOne = "";
		String attach = "";

		if (result.containsKey("device_info")) {
			deviceInfo = result.get("device_info").toString();// 设备号
			payResult.setDeviceInfo(deviceInfo);
		}
		if (result.containsKey("err_code")) {
			errCode = result.get("err_code").toString();// 错误代码
			payResult.setErrCode(errCode);
		}
		if (result.containsKey("err_code_des")) {
			errCodeDes = result.get("err_code_des").toString();// 错误代码描述
			payResult.setErrCodeDes(errCodeDes);
		}
		if (result.containsKey("is_subscribe")) {
			isSubscribe = result.get("is_subscribe").toString();// 是否关注公众账号
			payResult.setIsSubscribe(isSubscribe);
		}
		if (result.containsKey("settlement_total_fee ")) {
			settlementTotalFee = result.get("settlement_total_fee ").toString();// 应结订单金额
			//payResult.setSettlementTotalFee(Integer.parseInt(settlementTotalFee));
		}
		if (result.containsKey("fee_type")) {
			feeType = result.get("fee_type").toString();// 货币种类
			payResult.setFeeType(feeType);
			
		}
		if (result.containsKey("cash_fee_type")) {
			cashFeeType = result.get("cash_fee_type").toString();// 现金支付货币类型
			payResult.setCashFeeType(cashFeeType);
		}
		if (result.containsKey("coupon_fee")) {
			couponFee = result.get("coupon_fee").toString();// 代金券金额
			payResult.setCouponFee(Integer.parseInt(couponFee));
		}
		if (result.containsKey("coupon_count")) {
			couponCount = result.get("coupon_count").toString();// 代金券使用数量
			payResult.setCouponCount(Integer.parseInt(couponCount));
		}
		if (result.containsKey("coupon_type_$n ")) {
			couponType = result.get("coupon_type_$n ").toString();// 代金券类型
			payResult.setCouponType(couponType);
		}
		if (result.containsKey("coupon_id_$n")) {
			couponId = result.get("coupon_id_$n").toString();// 代金券ID
			payResult.setCouponId(couponId);
		}
		if (result.containsKey("coupon_fee_$n")) {
			couponFeeOne = result.get("coupon_fee_$n").toString();// 单个代金券支付金额
			payResult.setCouponFeeOne(Integer.parseInt(couponFeeOne));
		}
		if (result.containsKey("attach")) {
			attach = result.get("attach").toString();// 商家数据包
			payResult.setAttach(attach);
		}
		return payResult;
	}
	public static Date StringToDate(String eDate) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String b =eDate.substring(0,4);
		String c = eDate.substring(4,6);
		String d = eDate.substring(6,8);
		String e = eDate.substring(8,10);
		String f = eDate.substring(10,12);
		String g = eDate.substring(12,14);
		String h = b+"-"+c+"-"+d+" "+e+":"+f+":"+g;
		System.out.println(h);
		Date da = sdf.parse(h);
		return da;
	}
}
