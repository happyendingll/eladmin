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
package me.zhengjie.newspaper.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author solomon
* @date 2022-01-29
**/
@Data
public class ArticleDto implements Serializable {

    /** id */
    private Integer id;

    /** 标题 */
    private String title;

    /** 图片 */
    private String pic;

    /** url地址 */
    private String url;

    /** 状态，1收藏，2稍后再看 */
    private String status;

    /** 创建时间 */
    private Timestamp createtime;

    /** 修改时间 */
    private Timestamp updatetime;
}