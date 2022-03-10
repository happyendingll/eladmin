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
package me.zhengjie.exam.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.exam.domain.BusExampaper;
import me.zhengjie.exam.service.BusExampaperService;
import me.zhengjie.exam.service.dto.BusExampaperQueryCriteria;
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
* @date 2022-03-10
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "业务: 试卷管理")
@RequestMapping("/api/busExampaper")
public class BusExampaperController {

    private final BusExampaperService busExampaperService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('busExampaper:list')")
    public void exportBusExampaper(HttpServletResponse response, BusExampaperQueryCriteria criteria) throws IOException {
        busExampaperService.download(busExampaperService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询业务: 试卷")
    @ApiOperation("查询业务: 试卷")
    @PreAuthorize("@el.check('busExampaper:list')")
    public ResponseEntity<Object> queryBusExampaper(BusExampaperQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(busExampaperService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增业务: 试卷")
    @ApiOperation("新增业务: 试卷")
    @PreAuthorize("@el.check('busExampaper:add')")
    public ResponseEntity<Object> createBusExampaper(@Validated @RequestBody BusExampaper resources){
        return new ResponseEntity<>(busExampaperService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改业务: 试卷")
    @ApiOperation("修改业务: 试卷")
    @PreAuthorize("@el.check('busExampaper:edit')")
    public ResponseEntity<Object> updateBusExampaper(@Validated @RequestBody BusExampaper resources){
        busExampaperService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除业务: 试卷")
    @ApiOperation("删除业务: 试卷")
    @PreAuthorize("@el.check('busExampaper:del')")
    public ResponseEntity<Object> deleteBusExampaper(@RequestBody Integer[] ids) {
        busExampaperService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}