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
package me.zhengjie.blog.service.impl;

import me.zhengjie.blog.domain.BlogNav;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.blog.repository.BlogNavRepository;
import me.zhengjie.blog.service.BlogNavService;
import me.zhengjie.blog.service.dto.BlogNavDto;
import me.zhengjie.blog.service.dto.BlogNavQueryCriteria;
import me.zhengjie.blog.service.mapstruct.BlogNavMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
* @date 2022-01-25
**/
@Service
@RequiredArgsConstructor
public class BlogNavServiceImpl implements BlogNavService {

    private final BlogNavRepository blogNavRepository;
    private final BlogNavMapper blogNavMapper;

    @Override
    public Map<String,Object> queryAll(BlogNavQueryCriteria criteria, Pageable pageable){
        Page<BlogNav> page = blogNavRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(blogNavMapper::toDto));
    }

    @Override
    public List<BlogNavDto> queryAll(BlogNavQueryCriteria criteria){
        return blogNavMapper.toDto(blogNavRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BlogNavDto findById(Integer id) {
        BlogNav blogNav = blogNavRepository.findById(id).orElseGet(BlogNav::new);
        ValidationUtil.isNull(blogNav.getId(),"BlogNav","id",id);
        return blogNavMapper.toDto(blogNav);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BlogNavDto create(BlogNav resources) throws IOException {
        Document doc = Jsoup.connect(resources.getUrl()).get();
        String Desc = "";
        Elements metas = doc.getElementsByTag("meta");
        for (Element meta : metas) {
            String attr = meta.attr("name");
            if ((attr.equals("description")) || (attr.equals("Description") )) {
                Desc =  meta.attr("content");
            }
        }
        resources.setTitle(doc.title());
        resources.setDescription(Desc);
        resources.setLogo(resources.getUrl()+"favicon.ico");

        return blogNavMapper.toDto(blogNavRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BlogNav resources) {
        BlogNav blogNav = blogNavRepository.findById(resources.getId()).orElseGet(BlogNav::new);
        ValidationUtil.isNull( blogNav.getId(),"BlogNav","id",resources.getId());
        blogNav.copy(resources);
        blogNavRepository.save(blogNav);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            blogNavRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BlogNavDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BlogNavDto blogNav : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("链接地址", blogNav.getUrl());
            map.put("标题", blogNav.getTitle());
            map.put("描述", blogNav.getDescription());
            map.put("logo", blogNav.getLogo());
            map.put("创建时间", blogNav.getCreateTime());
            map.put("更新时间", blogNav.getUpdateTime());
            map.put("分类", blogNav.getKind());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}