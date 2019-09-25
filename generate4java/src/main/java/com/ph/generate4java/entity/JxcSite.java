package com.ph.generate4java.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @PACKAGE_NAME: com.jxc.site.entity
 * @NAME: JxcSite
 * @Description: 消纳场表
 * @Author ph
 * @DATE: 2019-09-03 10:11
 */
@Getter
@Setter
@ApiModel(value = "消纳场表")
public class JxcSite implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("银行账号")
    private String bankAccountCode;

    @ApiModelProperty("银行账户名称")
    private String bankAccountName;

    @ApiModelProperty("营业执照")
    private String businessLicense;

    @ApiModelProperty("购买说明")
    private String buyDescription;

    @ApiModelProperty("城市编码")
    private Integer cityCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("停业结束时间")
    private Date closingEndTime;

    @ApiModelProperty("停业公告")
    private String closingNotice;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("停业开始时间")
    private Date closingStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("删除标记（1：未删除 0：已删除）")
    private Integer delState;

    @ApiModelProperty("区编码")
    private Integer districtCode;

    @ApiModelProperty("消纳场id")
    private Integer id;

    @ApiModelProperty("是否入驻平台（1：是 0：否 2 : 驳回）")
    private Integer intoFlag;

    @ApiModelProperty("纬度")
    private String latitude;

    @ApiModelProperty("经度")
    private String longitude;

    @ApiModelProperty("是否停业（1：营业 0：停业）")
    private Integer openFlag;

    @ApiModelProperty("联系方式")
    private String phone;

    @ApiModelProperty("省份编码")
    private Integer provinceCode;

    @ApiModelProperty("负责人姓名")
    private String shoulder;

    @ApiModelProperty("消纳场详细地址")
    private String siteAddress;

    @ApiModelProperty("消纳场名")
    private String siteName;

    @ApiModelProperty("消纳场管理员id")
    private Long siteUserId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("修改时间")
    private Date updateTime;
}