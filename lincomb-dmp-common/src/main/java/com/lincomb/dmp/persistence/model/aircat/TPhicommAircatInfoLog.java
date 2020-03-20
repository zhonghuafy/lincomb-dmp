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
 * @author shiyu.cao
 * @since 2017-12-28
 */
@TableName("t_phicomm_aircat_info_log")
public class TPhicommAircatInfoLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Integer id;
	/**
	 * 用户手机号码
	 */
	@TableField("mac")
	private String mac;
	/**
	 * 温度
	 */
	private String temperature;
	/**
	 * 湿度
	 */
	private String humidity;
	/**
	 * 甲醛
	 */
	private String hcho;
	/**
	 * PM2.5
	 */
	@TableField("pm_value")
	private String pmValue;
	/**
	 * 发生数据的位置
	 */
	private String position;
	/**
	 * 体感温度
	 */
	private String kinect;
	/**
	 * 空气猫记录数据时间
	 */
	@TableField("cat_time")
	private String catTime;
	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private Date createTime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMac() { return mac; }

	public void setMac(String mac) { this.mac = mac; }

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getHcho() {
		return hcho;
	}

	public void setHcho(String hcho) {
		this.hcho = hcho;
	}

	public String getKinect() { return kinect; }

	public void setKinect(String kinect) { this.kinect = kinect; }

	public String getPmValue() {
		return pmValue;
	}

	public void setPmValue(String pmValue) {
		this.pmValue = pmValue;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCatTime() { return catTime; }

	public void setCatTime(String catTime) {
		this.catTime = catTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "TPhicommAircatInfoLog{" +
				"id=" + id +
				", mac=" + mac +
				", temperature=" + temperature +
				", humidity=" + humidity +
				", hcho=" + hcho +
				", pmValue=" + pmValue +
				", kinect=" + kinect +
				", position=" + position +
				", catTime=" + catTime +
				", createTime=" + createTime +
				"}";
	}
}
