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
package me.zhengjie.blog.service;

import me.zhengjie.blog.domain.BlogNav;
import me.zhengjie.blog.service.dto.BlogNavDto;
import me.zhengjie.blog.service.dto.BlogNavQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 * @author solomon
 * @website https://el-admin.vip
 * @description 服务接口
 * @date 2022-01-25
 **/
public interface BlogNavService {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Map<String, Object> queryAll(BlogNavQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<BlogNavDto>
     */
    List<BlogNavDto> queryAll(BlogNavQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return BlogNavDto
     */
    BlogNavDto findById(Integer id);

    /**
     * 创建
     *
     * @param resources /
     * @return BlogNavDto
     */
    BlogNavDto create(BlogNav resources) throws IOException;

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(BlogNav resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Integer[] ids);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<BlogNavDto> all, HttpServletResponse response) throws IOException;

    /**
     * @return: void
     * @author: T480Mac
     * @date: 2022/1/26 15:26
     * @description:
     */
    void importNav();
}