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
package me.zhengjie.blog.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.blog.domain.BlogNav;
import me.zhengjie.blog.service.BlogNavService;
import me.zhengjie.blog.service.dto.BlogNavQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author solomon
* @date 2022-01-25
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "博客： 导航栏管理")
@RequestMapping("/api/blogNav")
public class BlogNavController {

    private final BlogNavService blogNavService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('blogNav:list')")
    public void exportBlogNav(HttpServletResponse response, BlogNavQueryCriteria criteria) throws IOException {
        blogNavService.download(blogNavService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询博客导航栏")
    @ApiOperation("查询博客导航栏")
    @PreAuthorize("@el.check('blogNav:list')")
    public ResponseEntity<Object> queryBlogNav(BlogNavQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(blogNavService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增博客导航栏")
    @ApiOperation("新增博客导航栏")
    @PreAuthorize("@el.check('blogNav:add')")
    public ResponseEntity<Object> createBlogNav(@Validated @RequestBody BlogNav resources) throws IOException {
        return new ResponseEntity<>(blogNavService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改博客导航栏")
    @ApiOperation("修改博客导航栏")
    @PreAuthorize("@el.check('blogNav:edit')")
    public ResponseEntity<Object> updateBlogNav(@Validated @RequestBody BlogNav resources){
        blogNavService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除博客导航栏")
    @ApiOperation("删除博客导航栏")
    @PreAuthorize("@el.check('blogNav:del')")
    public ResponseEntity<Object> deleteBlogNav(@RequestBody Integer[] ids) {
        blogNavService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("导入数据到typecho")
    @ApiOperation("导入数据到typecho")
    @GetMapping(value = "/import")
    public ResponseEntity<Object> importNav(){
        blogNavService.importNav();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}