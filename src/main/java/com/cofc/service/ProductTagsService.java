package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ProductTags;

public interface ProductTagsService {

	public List<ProductTags> findProductTagsByOrder(@Param("orderId")Integer orderId, @Param("rowsId")Integer rowsId, @Param("pageSize")Integer pageSize);

	public void addNewProductTags(ProductTags pdt);

	public ProductTags getTagByName(String tagName);

	public ProductTags getTagById(String tagId);

}
