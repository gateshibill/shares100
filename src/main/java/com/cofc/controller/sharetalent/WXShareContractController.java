package com.cofc.controller.sharetalent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.ContractContent;
import com.cofc.pojo.ContractPunishment;
import com.cofc.pojo.ContractReason;
import com.cofc.pojo.ContractRecord;
import com.cofc.pojo.ShareContract;
import com.cofc.pojo.UserCommon;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.ContractContentService;
import com.cofc.service.ContractPunishmentService;
import com.cofc.service.ContractReasonService;
import com.cofc.service.ContractRecordService;
import com.cofc.service.ShareContractService;
import com.cofc.service.UserCommonService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/shareContract")
public class WXShareContractController extends BaseUtil {

	@Resource
	private ShareContractService shareContractService;
	@Resource
	private ContractContentService contentService;
	@Resource
	private ContractReasonService reasonService;
	@Resource
	private ContractPunishmentService punishmentService;
	@Resource
	private ContractRecordService recordService;
	@Resource
	private UserCommonService userService;
	@Resource
	private ApplicationCommonService appService;

	// 合约内容列表
	@RequestMapping("/contentList")
	public void contentList(HttpServletResponse response) {
		List<ContractContent> contents = contentService.findContractContentList();
		if (contents != null && !contents.isEmpty()) {
			output(response, JsonUtil.buildJson(contents));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "没有数据!"));
		}
	}

	// 合约原因列表
	@RequestMapping("/reasonList")
	public void reasonList(HttpServletResponse response) {
		List<ContractReason> reasons = reasonService.findContractReasonList();
		if (reasons != null && !reasons.isEmpty()) {
			output(response, JsonUtil.buildJson(reasons));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "没有数据!"));
		}
	}

	// 合约惩罚列表
	@RequestMapping("/punishmentList")
	public void punishmentList(HttpServletResponse response) {
		List<ContractPunishment> punishments = punishmentService.findContractPunishmentList();
		if (punishments != null && !punishments.isEmpty()) {
			output(response, JsonUtil.buildJson(punishments));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "没有数据!"));
		}
	}

	// 保存合约
	@RequestMapping("/addshareContract")
	public void addshareContract(HttpServletResponse response, ShareContract contract, String reasonTitle,
			String contentTitle, String punishmentTitle) {
		UserCommon user = userService.getUserByUserId(contract.getSharedUserId());
		if (user == null) {
			output(response, JsonUtil.buildFalseJson("3", "该用户不存在!"));
		} else {
			try {
				ContractContent content = contentService.getContentTitle(contentTitle);
				ContractReason reason = reasonService.getReasonTitle(reasonTitle);
				ContractPunishment punishment = punishmentService.getPunishmentTitle(punishmentTitle);
				if (content == null) {
					content = new ContractContent();
					content.setCreateTime(new Date());
					content.setContentTitle(contentTitle);
					content.setUsedCount(1);
					content.setCreateUser(contract.getSharedUserId());
					contentService.addContractContent(content);
				} else {
					content.setUpdateTime(new Date());
					content.setUsedCount(content.getUsedCount() + 1);
					contentService.updateContractContent(content);
				}
				if (reason == null) {
					reason = new ContractReason();
					reason.setContractTitle(reasonTitle);
					reason.setCreateTime(new Date());
					reason.setCreateUser(contract.getSharedUserId());
					reason.setUsedCount(1);
					reasonService.addContractReason(reason);
				} else {
					reason.setUpdateTime(new Date());
					reason.setUsedCount(content.getUsedCount() + 1);
					reasonService.updateContractReason(reason);
				}
				if (punishment == null) {
					punishment = new ContractPunishment();
					punishment.setCreateTime(new Date());
					punishment.setCreateUser(contract.getSharedUserId());
					punishment.setPunishmentContent(punishmentTitle);
					punishment.setUsedCount(1);
					punishmentService.addContractPunishment(punishment);
				} else {
					punishment.setUpdateTime(new Date());
					punishment.setUsedCount(content.getUsedCount() + 1);
					punishmentService.updateContractPunishment(punishment);
				}
				contract.setCreateTime(new Date());
				contract.setIsClose(0);
				contract.setIsPublic(1);// 默认公开
				contract.setReasonId(reason.getReasonId());
				contract.setContentId(content.getContentId());
				contract.setTotalDeposit(0.0);
				contract.setTotalNumber(0);
				contract.setPunishmentId(punishment.getPunishmentId());
				shareContractService.addShareContract(contract);// 保存合约成功返回合约编号
				ShareContract contract2 = shareContractService.getShareContractById(contract.getContractId());
				List<ShareContract> contracts = new ArrayList<ShareContract>();
				if (contract2 != null) {
					contracts.add(contract2);
					output(response, JsonUtil.buildJson(contracts));
				} else {
					output(response, JsonUtil.buildFalseJson("2", "没有数据!"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				output(response, JsonUtil.buildFalseJson("1", "保存合约失败!"));
			}
		}
	}

	// 合约详情
	@RequestMapping("/contractDetails")
	public void contractDetails(Integer contractId, HttpServletResponse response) {
		ShareContract contract = shareContractService.getShareContractById(contractId);
		UserCommon user = userService.getUserByUserId(contract.getSharedUserId());
		if (user == null) {
			output(response, JsonUtil.buildFalseJson("1", "该用户不存在"));
		} else {
			if (contract != null) {
				ContractContent content = contentService.getContractContentById(contract.getContentId());
				ContractReason reason = reasonService.getContractReasonById(contract.getReasonId());
				ContractPunishment punishment = punishmentService.getContractPunishmentById(contract.getPunishmentId());
				List<ShareContract> contracts = new ArrayList<ShareContract>();
				if (user.getNickName() != null) {
					contract.setNickName(user.getNickName());
				}
				if (user.getHeadImage() != null) {
					contract.setHeadImage(user.getHeadImage());
				}
				contract.setContentTitle(content.getContentTitle());
				contract.setContractTitle(reason.getContractTitle());
				contract.setPunishmentTitle(punishment.getPunishmentContent());
				contracts.add(contract);
				output(response, JsonUtil.buildJson(contracts));
			}
		}
	}

	// 用户合约列表
	@RequestMapping("/userContractList")
	public void userContractList(Integer pageNo, Integer pageSize, Integer userId, HttpServletResponse response) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		List<ShareContract> contracts = shareContractService.getShareContractList(userId, (pageNo - 1) * pageSize,
				pageSize);
		if (contracts != null && !contracts.isEmpty()) {
			output(response, JsonUtil.buildJson(contracts));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "没有数据!"));
		}
	}

	// 我的打赏列表(别人打赏我的)
	@RequestMapping("/userArewardList")
	public void userArewardList(HttpServletResponse response, Integer pageNo, Integer pageSize, Integer userId,
			Integer contractId,int type) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		List<ContractRecord> records = recordService.getContractRecordList(userId, (pageNo - 1) * pageSize, pageSize,
				contractId,type);
		if (records != null && !records.isEmpty()) {
			output(response, JsonUtil.buildJson(records));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "没有数据!"));
		}
	}

	// 所有合约列表
	/**
	 * 目前是只处理了只有一个小程序的情况
	 * 
	 * @param response
	 * @param loginPlat
	 * @param pageNo
	 * @param pageSize
	 */
	@RequestMapping("/getallcontractlist")
	public void getAllContractList(HttpServletResponse response, Integer loginPlat, Integer pageNo, Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		List<ShareContract> allcontracts = new ArrayList<ShareContract>();
		ApplicationCommon currapp = appService.getApplicationById(loginPlat);
		if (currapp.getParentId() == null) {
			allcontracts = shareContractService.getAllShareContract(null, (pageNo - 1) * pageSize, pageSize);
		} else {
			allcontracts = shareContractService.getAllShareContract(loginPlat, (pageNo - 1) * pageSize, pageSize);
		}
		output(response, JsonUtil.buildJson(allcontracts));
	}
}
