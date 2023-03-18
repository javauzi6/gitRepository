package com.qicong.os.web;

import com.alibaba.fastjson.JSONArray;
import com.qicong.os.bean.ChaptSectionBean;
import com.qicong.os.bean.ClassifyBean;
import com.qicong.os.common.page.Pagination;
import com.qicong.os.common.util.JsonView;
import com.qicong.os.domain.Classify;
import com.qicong.os.domain.Course;
import com.qicong.os.domain.CourseSection;
import com.qicong.os.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * User: 祁大聪
 * 处理客户管理的业务类逻辑
 */
@Controller
public class ManagerCourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    ClassifyService classifyService;

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    CourseSectionService courseSectionService;

    @RequestMapping("manageCourse")
    public ModelAndView manageCourse(Course queryEntity , Pagination<Course> page){
        ModelAndView mv = new ModelAndView("manage/course_manage");
        mv.addObject("curNav","course");
        page.setPageSize(6);
        page = courseService.queryPage(queryEntity,page);
        mv.addObject("page", page);
        mv.addObject("queryEntity",queryEntity);
        return mv;
    }

    @RequestMapping("addCourse")
    public ModelAndView addCourse(){
        ModelAndView mv = new ModelAndView("manage/course_add");
        mv.addObject("curNav","course");

        //加载课程分类的数据
        Map<String, ClassifyBean> map = classifyService.queryAllClassifyMap();
        mv.addObject("map",map);
        return mv;
    }

    //添加课程的信息
    @PostMapping("mergeCourseSection")
    @ResponseBody
    public String mergeCourseSection(Course entity ,String coursePicture, String chaptSections){
        if(StringUtils.isNotBlank(coursePicture)){
            //将图片保存到数据库
            Long atId = attachmentService.createAttachment("Course", coursePicture);
            entity.setPicture(atId.toString());
        }
        //课程的分类处理
        if(StringUtils.isNotEmpty(entity.getClassify())){
            Classify c = this.classifyService.getByCode(entity.getClassify());
            if(null != c){
                entity.setClassifyName(c.getName());
            }
        }
        if(StringUtils.isNotEmpty(entity.getSubClassify())){
            Classify c = this.classifyService.getByCode(entity.getSubClassify());
            if(null != c){
                entity.setSubClassifyName(c.getName());
            }
        }

        if(null == entity.getId()){//创建
            //处理章节的数据
            List<ChaptSectionBean> sectionList = null;
            if(StringUtils.isNotEmpty(chaptSections)){
                sectionList = JSONArray.parseArray(chaptSections,ChaptSectionBean.class);
            }
            //保存数据入库
            this.courseService.createCourse(entity, sectionList);
        }else{
            this.courseService.update(entity);
        }

        return JsonView.successJson(entity);
    }

    //课程的信息
    @RequestMapping("courseInfo")
    public ModelAndView courseInfo(Long id){
        ModelAndView mv = new ModelAndView("manage/course_info");
        mv.addObject("curNav","course");
        Course course = this.courseService.getById(id);
        mv.addObject("course", course);

        Map<Long, ChaptSectionBean> chaptSections = courseSectionService.queryChaptSections(id);
        mv.addObject("chaptSectionsMap", chaptSections);

        //加载课程分类的数据
        Map<String, ClassifyBean> classifyMap = classifyService.queryAllClassifyMap();
        mv.addObject("classifyMap",classifyMap);

        return mv;
    }

    @RequestMapping("getCourse")
    @ResponseBody
    public String getCourse(Long id){
        if(null == id){
            return JsonView.failureJson("失败");
        }
        Course course = this.courseService.getById(id);
        if(null == course){
            return JsonView.failureJson("失败");
        }
        return JsonView.successJson(course);
    }

    @RequestMapping("appendSection")
    public ModelAndView appendSection(Long id){
        ModelAndView mv = new ModelAndView("manage/course_info_append_section");
        mv.addObject("curNav","course");
        Course course = this.courseService.getById(id);
        mv.addObject("course", course);
        return mv;
    }

    /**
     * 添加课程章节
     */
    @RequestMapping("saveSections")
    @ResponseBody
    public String saveSections(Long courseId, String chaptSections){
        if(null == courseId){
            return JsonView.failureJson("失败");
        }
        Course course = this.courseService.getById(courseId);
        if(null == course){
            return JsonView.failureJson("失败");
        }

        //处理章节的数据并保存入库
        List<ChaptSectionBean> sectionList = null;
        if(StringUtils.isNotEmpty(chaptSections)){
            sectionList = JSONArray.parseArray(chaptSections,ChaptSectionBean.class);
            Integer totalTime = this.courseSectionService.createCourseSections(courseId, sectionList);
            this.courseService.updateCourseTime(courseId, totalTime);
        }

        return JsonView.successJson();
    }

    //获取课程章节数据
    @RequestMapping("getSection")
    @ResponseBody
    public String getSection(Long id){
        CourseSection section = courseSectionService.getById(id);
        if(null == section){
            return JsonView.failureJson("失败");
        }
        return JsonView.successJson(section);
    }

    //保存课程章节
    @RequestMapping("updateSection")
    @ResponseBody
    public String updateSection(CourseSection entity){
        courseSectionService.updateSection(entity);
        return JsonView.successJson();
    }

    //移动课程章节
    @RequestMapping("moveSection")
    @ResponseBody
    public String moveSection(Long id, String direct){
        courseSectionService.moveSection(id, direct);
        return JsonView.successJson();
    }

    //删除章节
    @RequestMapping("deleteSection")
    @ResponseBody
    public String deleteSection(Long id){
        courseSectionService.deleteSection(id);
        return JsonView.successJson();
    }

    //课程上下架
    @RequestMapping("onsaleCourse")
    @ResponseBody
    public String onsaleCourse(Long id){
        Course course = courseService.getById(id);
        if(null != course){
            Integer onsale = course.getOnsale();
            if(onsale == 0){
                onsale = 1;
            }else{
                onsale = 0;
            }
            course.setOnsale(onsale);
            courseService.update(course);
        }
        return JsonView.successJson();
    }

    //删除课程
    @RequestMapping("deleteCourse")
    @ResponseBody
    public String deleteCourse(Long id){
        Course course = courseService.getById(id);
        if(null != course){
            //判断这个课程是否有人在学？如果有人在学，不给删除；
            courseService.delete(course);
        }
        return JsonView.successJson();
    }


}
