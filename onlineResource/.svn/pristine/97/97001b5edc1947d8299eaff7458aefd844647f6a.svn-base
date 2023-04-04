package com.qicong.os.web;

import com.alibaba.fastjson.JSONObject;
import com.qicong.os.bean.ClassifyBean;
import com.qicong.os.common.page.Pagination;
import com.qicong.os.common.util.IdUtil;
import com.qicong.os.common.util.JsonView;
import com.qicong.os.common.util.RandomUtil;
import com.qicong.os.domain.AuthUser;
import com.qicong.os.domain.Classify;
import com.qicong.os.domain.Course;
import com.qicong.os.domain.Recommend;
import com.qicong.os.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * User: 祁大聪
 */
@Controller
public class ManagerController {

    @Autowired
    AuthUserService authUserService;

    @Autowired
    ClassifyService classifyService;

    @Autowired
    RecommendService recommendService;

    @Autowired
    AttachmentService attachmentService;

    @RequestMapping("manageUser")
    public ModelAndView manageUser(AuthUser queryEntity, Pagination<AuthUser> page){
        ModelAndView mv = new ModelAndView("manage/user_manage");
        mv.addObject("curNav","user");

        if(null != queryEntity.getStatus() &&
        queryEntity.getStatus() == -1){
            queryEntity.setStatus(null);
        }
        if(StringUtils.isEmpty(queryEntity.getUsername())){
            queryEntity.setUsername(null);
        }

        page.setPageSize(2);
        page = authUserService.queryPage(queryEntity, page);
        mv.addObject("page",page);
        mv.addObject("queryEntity",queryEntity);
        return mv;
    }

    @RequestMapping("getUser")
    @ResponseBody
    public String getUser(AuthUser user){
        user = authUserService.getById(user.getId());
        return JsonView.successJson(user);
    }

    @RequestMapping("mergeUser")
    @ResponseBody
    public String mergeUser(AuthUser user){
        user.setUsername(null);
        user.setRealname(null);
        authUserService.update(user);
        return JsonView.successJson(user);
    }

    //课程分类管理
    @RequestMapping("manageClassify")
    public ModelAndView manageClassify(){
        ModelAndView mv = new ModelAndView("manage/classify_manage");
        mv.addObject("curNav","classify");
        Map<String, ClassifyBean> map = classifyService.queryAllClassifyMap();
        mv.addObject("map",map);
        return mv;
    }

    //添加或修改
    @RequestMapping("mergeClassify")
    @ResponseBody
    public String mergeClassify(Classify classify){
        try {
            if(null == classify.getId()){//添加
                classify.setCode(RandomUtil.createRandomString(8));
                classify.setSort(0L);
                this.classifyService.create(classify);
                classify.setSort(classify.getId());
                classifyService.update(classify);
            }else{//更新
                classifyService.update(classify);
            }
            return JsonView.successJson();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonView.failureJson("失败了");
        }
    }

    //根据id获取分类
    @RequestMapping("getClassify")
    @ResponseBody
    public String getClassify(Long id){
        return JsonView.successJson(classifyService.getById(id));
    }

    //根据id删除分类
    @RequestMapping("deleteClassify")
    @ResponseBody
    public String deleteClassify(Long id){
        return classifyService.deleteClassify(id);
    }

    //课程分类排序
    @RequestMapping("sortClassify")
    @ResponseBody
    public String sortClassify(Long id,Integer sortType){
        return classifyService.sortClassify(id,sortType);
    }

    @RequestMapping("manageRecommend")
    public ModelAndView manageRecommend(Recommend queryEntity , Pagination<Recommend> page){
        ModelAndView mv = new ModelAndView("manage/recommend_manage");
        mv.addObject("curNav","recommend");
//        page.setPageSize(1);//分页的大小
        page = recommendService.queryPage(queryEntity, page);
        mv.addObject("page",page);
        return mv;
    }

    @RequestMapping("editRecommend")
    public ModelAndView editRecommend(Recommend entity){
        ModelAndView mv = new ModelAndView("manage/recommend_merge");
        mv.addObject("curNav","recommend");

        if(null != entity.getId()){//修改
            entity = recommendService.getById(entity.getId());
            mv.addObject("entity",entity);
        }
        return mv;
    }

    @RequestMapping("mergeRecommend")
    public ModelAndView mergeRecommend(Recommend entity, String recommdPicture){
        ModelAndView mv = new ModelAndView("redirect:/manageRecommend");

        if(StringUtils.isNotBlank(recommdPicture)){
            //将图片添加到数据库
            Long atId = attachmentService.createAttachment("RecommdPicture",recommdPicture);
            entity.setPicture(atId.toString());
        }
        if(null == entity.getId()){
            if(StringUtils.isBlank(entity.getPicture())){
                entity.setPicture("0");
            }
            recommendService.create(entity);
        }else{
            recommendService.update(entity);
        }
        return mv;
    }

    @RequestMapping("deleteRecommend")
    @ResponseBody
    public String deleteRecommend(Recommend entity){
        this.recommendService.delete(entity);
        return JsonView.successJson();
    }

}
