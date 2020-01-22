package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.ProjectCheckItem;

public interface ProjectCheckItemService {
	
	void addProjectCheckItem(ProjectCheckItem pCheckItem);
	
	void updateProjectCheckItem(ProjectCheckItem pCheckItem);
	
	void deleteProjectCheckItem(@Param("itemId")Integer itemId);
	
	ProjectCheckItem getInfoById(@Param("itemId")Integer itemId);
	
	int getProjectCheckItemCount(@Param("pCheckItem")ProjectCheckItem pCheckItem);
	
	List<ProjectCheckItem> getProjectCheckItemList(@Param("pCheckItem")ProjectCheckItem pCheckItem,
			@Param("page")Integer page,@Param("limit")Integer limit);
	
	int checkisAll(@Param("itemStatus")Integer itemStatus,@Param("checkId")Integer checkId);

}
