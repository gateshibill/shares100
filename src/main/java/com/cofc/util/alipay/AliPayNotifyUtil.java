package com.cofc.util.alipay;

import java.util.Map;

import com.cofc.pojo.AliPayNotifyResult;

public class AliPayNotifyUtil {
	public static AliPayNotifyResult getNotifyResult(Map<String,String> result){
		AliPayNotifyResult notify = new AliPayNotifyResult();
		//必须的
		String notifyTime = result.get("notify_time");
		String notifyType = result.get("notify_type");
		String notifyId = result.get("notify_id");
		String appId = result.get("app_id");
		String charset = result.get("charset");
		String version = result.get("version");
		String signType = result.get("sign_type");
		String sign = result.get("sign");
		String tradeNo = result.get("trade_no");
		String outTradeNo = result.get("out_trade_no");
		
		notify.setNotifyTime(notifyTime);
		notify.setNotifyType(notifyType);
		notify.setNotifyId(notifyId);
		notify.setAppId(appId);
		notify.setCharset(charset);
		notify.setVersion(version);
		notify.setSignType(signType);
		notify.setSign(sign);
		notify.setTradeNo(tradeNo);
		notify.setOutTradeNo(outTradeNo);
		
		//非必须的
		if(result.containsKey("out_biz_no")){
			notify.setOutBizNo(result.get("out_biz_no"));
		}
		if(result.containsKey("buyer_id")){
			notify.setBuyerId(result.get("buyer_id"));
		}
		if(result.containsKey("buyer_logon_id")){
			notify.setBuyerLogonId(result.get("buyer_logon_id"));
		}
		if(result.containsKey("seller_id")){
			notify.setSellerId(result.get("seller_id"));
		}
		if(result.containsKey("seller_email")){
			notify.setSellerEmail(result.get("seller_email"));
		}
		if(result.containsKey("trade_status")){
			notify.setTradeStatus(result.get("trade_status"));
		}
		if(result.containsKey("total_amount")){
			notify.setTotalAmount(result.get("total_amount"));
		}
		
		
		if(result.containsKey("receipt_amount")){
			notify.setReceiptAmount(result.get("receipt_amount"));
		}
		if(result.containsKey("invoice_amount")){
			notify.setInvoiceAmount(result.get("invoice_amount"));
		}
		if(result.containsKey("buyer_pay_amount")){
			notify.setBuyerPayAmount(result.get("buyer_pay_amount"));
		}
		
		if(result.containsKey("point_amount")){
			notify.setPointAmount(result.get("point_amount"));
		}
		if(result.containsKey("refund_fee")){
			notify.setRefundFee(result.get("refund_fee"));
		}
		if(result.containsKey("subject")){
			notify.setSubject(result.get("subject"));
		}
		if(result.containsKey("body")){
			notify.setBody(result.get("body"));
		}
		if(result.containsKey("gmt_create")){
			notify.setGmtCreate(result.get("gmt_create"));
		}
		if(result.containsKey("gmt_payment")){
			notify.setGmtPayMent(result.get("gmt_payment"));
		}
		if(result.containsKey("gmt_refund")){
			notify.setGmtRefund(result.get("gmt_refund"));
		}
		if(result.containsKey("gmt_close")){
			notify.setGmtClose(result.get("gmt_close"));
		}
		if(result.containsKey("fund_bill_list")){
			notify.setFundBillList(result.get("fund_bill_list"));
		}
		if(result.containsKey("passback_params")){
			notify.setPassbackParams(result.get("passback_params"));
		}
		if(result.containsKey("voucher_detail_list")){
			notify.setVoucherDetailList(result.get("voucher_detail_list"));
		}
		return notify;
	}
}
