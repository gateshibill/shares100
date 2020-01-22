package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ApplicationModel;

public interface ApplicationModelService {

	public ApplicationModel getModelById(Integer modelId);

	public void updateModelInfo(ApplicationModel model);

	public List<ApplicationModel> findAPPModels();

	public List<ApplicationModel> getApplicationModelList(@Param("modelName")String modelName, @Param("page")int page, @Param("limit")Integer limit);

	public int getApplicationModelCount(@Param("modelName")String modelName);

	public ApplicationModel getModelName(@Param("modelName")String modelName);

	public void addApplicationModel(ApplicationModel model);

	public void deleteApplicationModel(ApplicationModel model);

}
