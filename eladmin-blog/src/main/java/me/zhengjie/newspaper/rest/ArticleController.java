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
package me.zhengjie.newspaper.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.newspaper.domain.Article;
import me.zhengjie.newspaper.service.ArticleService;
import me.zhengjie.newspaper.service.dto.ArticleQueryCriteria;
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
* @date 2022-01-29
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "newspaper管理")
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('article:list')")
    public void exportArticle(HttpServletResponse response, ArticleQueryCriteria criteria) throws IOException {
        articleService.download(articleService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询newspaper")
    @ApiOperation("查询newspaper")
    @PreAuthorize("@el.check('article:list')")
    public ResponseEntity<Object> queryArticle(ArticleQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(articleService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增newspaper")
    @ApiOperation("新增newspaper")
    @PreAuthorize("@el.check('article:add')")
    public ResponseEntity<Object> createArticle(@Validated @RequestBody Article resources){
        return new ResponseEntity<>(articleService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改newspaper")
    @ApiOperation("修改newspaper")
    @PreAuthorize("@el.check('article:edit')")
    public ResponseEntity<Object> updateArticle(@Validated @RequestBody Article resources){
        articleService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除newspaper")
    @ApiOperation("删除newspaper")
    @PreAuthorize("@el.check('article:del')")
    public ResponseEntity<Object> deleteArticle(@RequestBody Integer[] ids) {
        articleService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}