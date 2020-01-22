package com.cofc.service.tx;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.tx.Agent;

public interface AgentService {
	public void addAgent(Agent agent);
	public void updateAgent(Agent agent);
	public void delAgent(@Param("agentId")Integer agentId);
	public Agent getAgentInfo(@Param("agentId")Integer agentId,@Param("agentCode")String agentCode,
			@Param("isEffect")Integer isEffect,@Param("agentIP")String agentIP);
	public int getAgentCount(@Param("agent")Agent agent);
	public List<Agent> getAgentList(@Param("agent")Agent agent,@Param("page")Integer page,
			@Param("limit")Integer limit);
}
