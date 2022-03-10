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
package me.zhengjie.exam.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author solomon
* @date 2022-03-10
**/
@Entity
@Data
@Table(name="bus_question")
public class BusQuestion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "question_no")
    @ApiModelProperty(value = "题号")
    private String questionNo;

    @Column(name = "type")
    @ApiModelProperty(value = "题目类型")
    private String type;

    @Column(name = "option_a")
    @ApiModelProperty(value = "选项A")
    private String optionA;

    @Column(name = "option_b")
    @ApiModelProperty(value = "选项B")
    private String optionB;

    @Column(name = "option_c")
    @ApiModelProperty(value = "选项C")
    private String optionC;

    @Column(name = "option_d")
    @ApiModelProperty(value = "选项D")
    private String optionD;

    @Column(name = "answer")
    @ApiModelProperty(value = "答案")
    private String answer;

    @Column(name = "answer_explain")
    @ApiModelProperty(value = "答案解析")
    private String answerExplain;

    @Column(name = "creator")
    @ApiModelProperty(value = "创建者")
    private String creator;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    @Column(name = "question_title")
    @ApiModelProperty(value = "题目标题")
    private String questionTitle;

    public void copy(BusQuestion source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}