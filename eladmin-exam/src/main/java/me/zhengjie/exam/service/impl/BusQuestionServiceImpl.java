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

import me.zhengjie.exam.domain.BusQuestion;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.exam.repository.BusQuestionRepository;
import me.zhengjie.exam.service.BusQuestionService;
import me.zhengjie.exam.service.dto.BusQuestionDto;
import me.zhengjie.exam.service.dto.BusQuestionQueryCriteria;
import me.zhengjie.exam.service.mapstruct.BusQuestionMapper;
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
public class BusQuestionServiceImpl implements BusQuestionService {

    private final BusQuestionRepository busQuestionRepository;
    private final BusQuestionMapper busQuestionMapper;

    @Override
    public Map<String,Object> queryAll(BusQuestionQueryCriteria criteria, Pageable pageable){
        Page<BusQuestion> page = busQuestionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(busQuestionMapper::toDto));
    }

    @Override
    public List<BusQuestionDto> queryAll(BusQuestionQueryCriteria criteria){
        return busQuestionMapper.toDto(busQuestionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BusQuestionDto findById(Integer id) {
        BusQuestion busQuestion = busQuestionRepository.findById(id).orElseGet(BusQuestion::new);
        ValidationUtil.isNull(busQuestion.getId(),"BusQuestion","id",id);
        return busQuestionMapper.toDto(busQuestion);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusQuestionDto create(BusQuestion resources) {
        return busQuestionMapper.toDto(busQuestionRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BusQuestion resources) {
        BusQuestion busQuestion = busQuestionRepository.findById(resources.getId()).orElseGet(BusQuestion::new);
        ValidationUtil.isNull( busQuestion.getId(),"BusQuestion","id",resources.getId());
        busQuestion.copy(resources);
        busQuestionRepository.save(busQuestion);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            busQuestionRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BusQuestionDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BusQuestionDto busQuestion : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("题号", busQuestion.getQuestionNo());
            map.put("题目类型", busQuestion.getType());
            map.put("选项A", busQuestion.getOptionA());
            map.put("选项B", busQuestion.getOptionB());
            map.put("选项C", busQuestion.getOptionC());
            map.put("选项D", busQuestion.getOptionD());
            map.put("答案", busQuestion.getAnswer());
            map.put("答案解析", busQuestion.getAnswerExplain());
            map.put("创建者", busQuestion.getCreator());
            map.put("创建时间", busQuestion.getCreateTime());
            map.put("更新时间", busQuestion.getUpdateTime());
            map.put("题目标题", busQuestion.getQuestionTitle());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}