/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.newspaper.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author solomon
* @date 2022-01-29
**/
@Entity
@Data
@Table(name="article")
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "title")
    @ApiModelProperty(value = "标题")
    private String title;

    @Column(name = "pic")
    @ApiModelProperty(value = "图片")
    private String pic;

    @Column(name = "url")
    @ApiModelProperty(value = "url地址")
    private String url;

    @Column(name = "status")
    @ApiModelProperty(value = "状态，1收藏，2稍后再看")
    private String status;

    @Column(name = "createTime")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createtime;

    @Column(name = "updateTime")
    @ApiModelProperty(value = "修改时间")
    private Timestamp updatetime;

    public void copy(Article source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}