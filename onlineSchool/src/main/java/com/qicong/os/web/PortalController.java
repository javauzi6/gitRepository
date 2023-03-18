package com.qicong.os.web;

import com.qicong.os.bean.ChaptSectionBean;
import com.qicong.os.bean.ClassifyBean;
import com.qicong.os.common.page.Pagination;
import com.qicong.os.common.util.JsonView;
import com.qicong.os.domain.*;
import com.qicong.os.service.*;
import com.qicong.os.web.idcode.IdentifyCodeKey;
import com.qicong.os.web.shiro.ShiroContext;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: 祁大聪
 */
@Controller
public class PortalController {

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private ClassifyService classifyService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseSectionService courseSectionService;

    @Autowired
    private CourseCommentService courseCommentService;

    @Autowired
    private UserCourseSectionService userCourseSectionService;

    @RequestMapping()
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("index");

        List<Recommend> recommendList = recommendService.queryRecommend(4);
        mv.addObject("recommendList", recommendList);

        Map<String, ClassifyBean> classifyMap = classifyService.queryAllClassifyMap();
        //获取分类下推荐的课程
        courseService.prepareClassifyCourses(classifyMap);
        mv.addObject("classifyMap", classifyMap);

        //获取名师公开课
        List<Course> publicCourses = courseService.queryPublicCourses();
        mv.addObject("publicCourses", publicCourses);

        //获取直播课
        List<Course> liveCourses = courseService.queryLiveCourses();
        mv.addObject("liveCourses", liveCourses);

        //系列课（classify=bianchengkaifa的课程）
        List<Course> seriesCourses = courseService.querySeriesCourses();
        mv.addObject("seriesCourses", seriesCourses);

        //精品课
        List<Course> freeCourses = courseService.queryFreeCourses();
        mv.addObject("freeCourses", freeCourses);

        //最新课
        List<Course> newCourses = courseService.queryNewCourses();
        mv.addObject("newCourses", newCourses);

        return mv;
    }

    //课程的分类页面
    @GetMapping("/courses")
    public ModelAndView courses(String c, String sort, Pagination<Course> page){
        ModelAndView mv = new ModelAndView("course_page");

        Map<String, ClassifyBean> classifyMap = classifyService.queryAllClassifyMap();
        //获取分类下推荐的课程
        courseService.prepareClassifyCourses(classifyMap);
        mv.addObject("classifyMap", classifyMap);

        Course queryEntity = new Course();
        //处理分类
        if(StringUtils.isNotEmpty(c)){
           Classify classify = classifyService.getByCode(c);
           if(null != classify){
               if("0".equals(classify.getParentCode())){
                   mv.addObject("classify", classify);//代表的是一级分类
                   queryEntity.setClassify(classify.getCode());
               }else{
                   mv.addObject("classify", classifyService.getByCode(classify.getParentCode()));//代表的是一级分类
                   mv.addObject("subClassify", classify);//代表二级分类
                   queryEntity.setSubClassify(classify.getCode());
               }
           }
           mv.addObject("c",c);
        }

        queryEntity.setOnsale(1);//上架了的课程
        page.setPageSize(12);
        if("last".equals(sort)){
            page.setSortField("id");
        }
        if("pop".equals(sort)){
            page.setSortField("study_count");//字段的值
        }
        mv.addObject("sort",sort);

        page = courseService.queryPage(queryEntity,page);
        mv.addObject("page",page);
        return mv;
    }

    //课程详情页
    @GetMapping("/course/{courseId}")
    public ModelAndView course(@PathVariable Long courseId){
        if(null == courseId){
            return new ModelAndView("error/404");
        }
        ModelAndView mv = new ModelAndView("course_info");

        Course course = this.courseService.getById(courseId);
        if(null == course){
            return new ModelAndView("error/404");
        }
        Map<Long, ChaptSectionBean> sectionMap = courseSectionService.queryChaptSections(courseId);
        mv.addObject("course",course);
        mv.addObject("sectionMap",sectionMap);

        CourseSection curCourseSection = null;
        if(ShiroContext.isLogin()){
            UserCourseSection entity = new UserCourseSection();
            entity.setUserId(ShiroContext.getSessionUser().getId());
            entity.setCourseId(course.getId());
            entity = this.userCourseSectionService.getUserCourseSection(entity);
            if(null != entity){
                curCourseSection = this.courseSectionService.getById(entity.getSectionId());
                mv.addObject("curSection",curCourseSection);
            }
        }
        if(null == curCourseSection){
            //取课程的第一节课
            if(null != sectionMap){
                Iterator<ChaptSectionBean> it = sectionMap.values().iterator();
                if(it.hasNext()){
                    ChaptSectionBean fistChapt = it.next();
                    if(!CollectionUtils.isEmpty(fistChapt.getSections())){
                        mv.addObject("curSection",fistChapt.getSections().get(0));
                    }
                }
            }
        }
        return mv;
    }

    //加载课程的评论
    @RequestMapping("/course/comment")
    public ModelAndView courseComment(Long courseId, Long sectionId, String from){
        ModelAndView mv = new ModelAndView("course_comment");
        if(null != courseId){
            CourseComment courseComment = new CourseComment();
            courseComment.setCourseId(courseId);
            if(null != sectionId){
                courseComment.setSectionId(sectionId);
            }
            List<CourseComment> comments = courseCommentService.queryCourseComment(courseComment);
            mv.addObject("comments",comments);
            mv.addObject("from",from);
        }
        return mv;
    }

    //课程学习页
    @GetMapping("/learn/{sectionId}")
    public ModelAndView learn(@PathVariable Long sectionId){
        ModelAndView mv = new ModelAndView("course_learn");

        if(null == sectionId){
            return new ModelAndView("error/404");
        }
        CourseSection courseSection = this.courseSectionService.getById(sectionId);
        if(null == courseSection){
            return new ModelAndView("error/404");
        }
        mv.addObject("courseSection",courseSection);

        Map<Long, ChaptSectionBean> sectionMap = courseSectionService.queryChaptSections(courseSection.getCourseId());
        mv.addObject("sectionMap",sectionMap);

        //如果用户登录了，那么就更新或插入用户的学习记录
        userCourseSectionService.mergeUserCourseSection(courseSection);

        return mv;
    }

    //提交课程评论
    @RequestMapping("docomment")
    @ResponseBody
    public String docomment(
            HttpServletRequest request,
            CourseComment courseComment, String indityCode){

        Object sessionCode = request.getSession().getAttribute(IdentifyCodeKey.COMMENT);
        if(null != sessionCode && StringUtils.isNotEmpty(indityCode)){
            if(!sessionCode.toString().equalsIgnoreCase(indityCode)){
                return JsonView.failureJson("验证码错误");
            }
        }else{
            return JsonView.failureJson("验证码错误");
        }

        //保存数据
        if(StringUtils.isEmpty(courseComment.getContent())){
            return JsonView.failureJson("评论输入错误");
        }

        Long sectionId = courseComment.getSectionId();
        if(null == sectionId){
            return JsonView.failureJson("小节数据错误");
        }
        CourseSection courseSection = this.courseSectionService.getById(sectionId);
        if(null == courseSection){
            return JsonView.failureJson("小节数据错误");
        }
        courseComment.setSectionTitle(courseSection.getName());
        courseComment.setCourseId(courseSection.getCourseId());
        courseComment.setUsername("admin");
        courseComment.setType(0);

        //回复评论
        boolean flag = false;
        if(null != courseComment.getRefId()){
            CourseComment refCourseComment = courseCommentService.getById(courseComment.getRefId());
            if(null != refCourseComment){
                courseComment.setToUsername(refCourseComment.getUsername());
                courseComment.setRefContent(refCourseComment.getContent());
                flag = true;
            }
        }
        if(!flag){
            courseComment.setToUsername("admin");
            courseComment.setRefContent("");
            courseComment.setRefId(0L);
        }

        this.courseCommentService.create(courseComment);
        return JsonView.successJson();
    }

}
