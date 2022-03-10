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
package me.zhengjie.exam.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author solomon
* @date 2022-03-10
**/
@Data
public class BusQuestionDto implements Serializable {

    /** id */
    private Integer id;

    /** 题号 */
    private String questionNo;

    /** 题目类型 */
    private String type;

    /** 选项A */
    private String optionA;

    /** 选项B */
    private String optionB;

    /** 选项C */
    private String optionC;

    /** 选项D */
    private String optionD;

    /** 答案 */
    private String answer;

    /** 答案解析 */
    private String answerExplain;

    /** 创建者 */
    private String creator;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;

    /** 题目标题 */
    private String questionTitle;
}