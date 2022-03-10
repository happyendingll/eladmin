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
package me.zhengjie.exam.service.impl;

import me.zhengjie.exam.domain.BusExampaper;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.exam.repository.BusExampaperRepository;
import me.zhengjie.exam.service.BusExampaperService;
import me.zhengjie.exam.service.dto.BusExampaperDto;
import me.zhengjie.exam.service.dto.BusExampaperQueryCriteria;
import me.zhengjie.exam.service.mapstruct.BusExampaperMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author solomon
* @date 2022-03-10
**/
@Service
@RequiredArgsConstructor
public class BusExampaperServiceImpl implements BusExampaperService {

    private final BusExampaperRepository busExampaperRepository;
    private final BusExampaperMapper busExampaperMapper;

    @Override
    public Map<String,Object> queryAll(BusExampaperQueryCriteria criteria, Pageable pageable){
        Page<BusExampaper> page = busExampaperRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(busExampaperMapper::toDto));
    }

    @Override
    public List<BusExampaperDto> queryAll(BusExampaperQueryCriteria criteria){
        return busExampaperMapper.toDto(busExampaperRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BusExampaperDto findById(Integer id) {
        BusExampaper busExampaper = busExampaperRepository.findById(id).orElseGet(BusExampaper::new);
        ValidationUtil.isNull(busExampaper.getId(),"BusExampaper","id",id);
        return busExampaperMapper.toDto(busExampaper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusExampaperDto create(BusExampaper resources) {
        return busExampaperMapper.toDto(busExampaperRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BusExampaper resources) {
        BusExampaper busExampaper = busExampaperRepository.findById(resources.getId()).orElseGet(BusExampaper::new);
        ValidationUtil.isNull( busExampaper.getId(),"BusExampaper","id",resources.getId());
        busExampaper.copy(resources);
        busExampaperRepository.save(busExampaper);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            busExampaperRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BusExampaperDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BusExampaperDto busExampaper : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("试卷号", busExampaper.getPaperNo());
            map.put("试卷名", busExampaper.getPaperName());
            map.put("出卷者", busExampaper.getPaperCreator());
            map.put("创建时间", busExampaper.getCreateTime());
            map.put("更新时间", busExampaper.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}