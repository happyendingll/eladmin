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
import me.zhengjie.exam.domain.BusQuestion;
import me.zhengjie.exam.service.BusQuestionService;
import me.zhengjie.exam.service.dto.BusQuestionQueryCriteria;
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
@Api(tags = "业务: 试题管理")
@RequestMapping("/api/busQuestion")
public class BusQuestionController {

    private final BusQuestionService busQuestionService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('busQuestion:list')")
    public void exportBusQuestion(HttpServletResponse response, BusQuestionQueryCriteria criteria) throws IOException {
        busQuestionService.download(busQuestionService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询业务: 试题")
    @ApiOperation("查询业务: 试题")
    @PreAuthorize("@el.check('busQuestion:list')")
    public ResponseEntity<Object> queryBusQuestion(BusQuestionQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(busQuestionService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增业务: 试题")
    @ApiOperation("新增业务: 试题")
    @PreAuthorize("@el.check('busQuestion:add')")
    public ResponseEntity<Object> createBusQuestion(@Validated @RequestBody BusQuestion resources){
        return new ResponseEntity<>(busQuestionService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改业务: 试题")
    @ApiOperation("修改业务: 试题")
    @PreAuthorize("@el.check('busQuestion:edit')")
    public ResponseEntity<Object> updateBusQuestion(@Validated @RequestBody BusQuestion resources){
        busQuestionService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除业务: 试题")
    @ApiOperation("删除业务: 试题")
    @PreAuthorize("@el.check('busQuestion:del')")
    public ResponseEntity<Object> deleteBusQuestion(@RequestBody Integer[] ids) {
        busQuestionService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}