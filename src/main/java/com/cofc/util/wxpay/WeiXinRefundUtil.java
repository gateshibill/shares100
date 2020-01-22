package com.cofc.util.wxpay;

import java.util.Map;

import com.cofc.pojo.WeiXinRefundResult;

public class WeiXinRefundUtil {
	public static WeiXinRefundResult addWeiXinRefundResult(Map<String,String> result){
		WeiXinRefundResult refund = new WeiXinRefundResult();
		//必定返回的
		String appId = result.get("appid");
		String mchId = result.get("mch_id");
		String resultCode = result.get("result_code");
		String nonceStr = result.get("nonce_str");
		String sign = result.get("sign");
		String transactionId = result.get("transaction_id");
		String outTradeNo = result.get("out_trade_no");
		String outRefundNo = result.get("out_refund_no");
		String refundId = result.get("refund_id");
		String refundFee = result.get("refund_fee");
		String totalFee = result.get("total_fee");
		String cashFee = result.get("cash_fee");		
		
		refund.setAppId(appId);
		refund.setMchId(mchId);
		refund.setResultCode(resultCode);
		refund.setNonceStr(nonceStr);
		refund.setSign(sign);
		refund.setTransactionId(transactionId);
		refund.setOutTradeNo(outTradeNo);
		refund.setOutRefundNo(outRefundNo);
		refund.setRefundId(refundId);
		refund.setRefundFee(Integer.parseInt(refundFee));
		refund.setTotalFee(Integer.parseInt(totalFee));
		refund.setCashFee(Integer.parseInt(cashFee));
		
		//非必定返回的
		if(result.containsKey("err_code")){
			refund.setErrCode(result.get("err_code"));
		}
		if(result.containsKey("err_code_des")){
			refund.setErrCodeDes(result.get("err_code_des"));
		}
		if(result.containsKey("settlement_refund_fee")){
			refund.setSettlementRefundFee(Integer.parseInt(result.get("settlement_refund_fee")));
		}
		if(result.containsKey("settlement_total_fee")){
			refund.setSettlementTotalFee(Integer.parseInt(result.get("settlement_total_fee")));
		}
		if(result.containsKey("fee_type")){
			refund.setFeeType(result.get("fee_type"));
		}
		if(result.containsKey("cash_fee_type")){
			refund.setCashFeeType(result.get("cash_fee_type"));
		}
		if(result.containsKey("cash_refund_fee")){
			refund.setCashRefundFee(Integer.parseInt(result.get("cash_refund_fee")));
		}
		if(result.containsKey("coupon_type_$n")){
			refund.setCouponType$n(result.get("coupon_type_$n"));
		}
		if(result.containsKey("coupon_refund_fee")){
			refund.setCouponRefundFee(Integer.parseInt(result.get("coupon_refund_fee")));
		}
		if(result.containsKey("coupon_refund_fee_$n")){
			refund.setCouponRefundFee$n(Integer.parseInt(result.get("coupon_refund_fee_$n")));
		}
		if(result.containsKey("coupon_refund_count")){
			refund.setCouponRefundCount(Integer.parseInt(result.get("coupon_refund_count")));
		}
		if(result.containsKey("coupon_refund_id_$n")){
			refund.setCouponRefundId$n(result.get("coupon_refund_id_$n"));
		}
		return refund;
	}
}
