package com.cofc.pojo;

public class TaskSatisfiedService {
	private Integer serviceId;
	private String serviceName;
	private Integer serviceType;

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	@Override
	public String toString() {
		return "SatisfiedService [serviceId=" + serviceId + ", serviceName=" + serviceName + ", serviceType="
				+ serviceType + "]";
	}

}
