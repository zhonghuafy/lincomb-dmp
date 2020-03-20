package com.lincomb.dmp.persistence.model.aircat;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.hibernate.validator.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author kaiyi.chen
 * @since 2018-1-23
 */
@TableName("t_phicomm_aircat_device")
public class TPhicommAircatDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    
	private Integer id;
	
	@TableField("mac")
	private String mac;
	
	private String deviceId;
    
	@TableField("update_time")
	private Date updateTime;
	
	@TableField("create_time")
	private Date createTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "TPhicommAircatDevice [id=" + id + ", mac=" + mac + ", deviceId=" + deviceId + ", updateTime="
				+ updateTime + ", createTime=" + createTime + "]";
	}
    
	
}
