package com.qicong.os.web;

import com.qicong.os.common.util.HashUtil;
import com.qicong.os.common.util.HttpUtil;
import com.qicong.os.common.util.JsonView;
import com.qicong.os.common.util.RandomUtil;
import com.qicong.os.domain.AuthUser;
import com.qicong.os.domain.CourseComment;
import com.qicong.os.domain.UserCourseSection;
import com.qicong.os.service.AttachmentService;
import com.qicong.os.service.AuthUserService;
import com.qicong.os.service.CourseCommentService;
import com.qicong.os.service.UserCourseSectionService;
import com.qicong.os.web.idcode.IdentifyCodeKey;
import com.qicong.os.web.shiro.ShiroContext;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * User: 祁大聪
 */
@Controller
public class AuthController {

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private UserCourseSectionService userCourseSectionService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private CourseCommentService courseCommentService;

    @RequestMapping("login")
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView("auth_login");

        return mv;
    }

    @PostMapping("dologin")
    @ResponseBody
    public String dologin(String username, String password, String idcode,
                          HttpServletRequest request){
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return JsonView.failureJson("用户名密码错误");
        }
        if(password.length() != 64){
            return JsonView.failureJson("用户名密码错误");
        }
        Object sessionCode = request.getSession().getAttribute(IdentifyCodeKey.LOGIN);
        if(null == sessionCode || StringUtils.isEmpty(idcode)){
            return JsonView.failureJson("验证码错误");
        }
        if(!idcode.equalsIgnoreCase(sessionCode.toString())){
            return JsonView.failureJson("验证码错误");
        }

        //用shiro来验证登录
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
//            e.printStackTrace();
            return JsonView.failureJson("用户名密码错误");
        }

        return JsonView.successJson();

    }

    //注册
    @RequestMapping("register")
    public ModelAndView register(){
        return new ModelAndView("auth_register");
    }

    @PostMapping("doregister")
    @ResponseBody
    public String doregister(String username, String password, String repassword,
                             String idcode, HttpServletRequest request) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(repassword)) {
            return JsonView.failureJson("用户名密码输入错误");
        }
        if (password.length() != 64 || !password.equals(repassword)) {
            return JsonView.failureJson("用户名密码输入错误");
        }

        Object sessionCode = request.getSession().getAttribute(IdentifyCodeKey.REGISTER);
        if (null == sessionCode || StringUtils.isEmpty(idcode)) {
            return JsonView.failureJson("验证码错误");
        }
        if (!idcode.equalsIgnoreCase(sessionCode.toString())) {
            return JsonView.failureJson("验证码错误");
        }

        //获取username对应的用户
        AuthUser authUser = authUserService.getByUsername(username);
        if(null != authUser){
            return JsonView.failureJson("用户名已经存在了");
        }

        authUser = new AuthUser();
        String salt = RandomUtil.createRandomString(64);
        authUser.setUsername(username);
        authUser.setSalt(salt);
        authUser.setStatus(1);
        authUser.setGender(1);
        authUser.setLoginAt(new Date());
        authUser.setIp("127.0.0.1");//HttpUtil.getIPAddress(request)
        authUser.setPassword(HashUtil.hmacSha256(password,salt));

        authUserService.create(authUser);

        return JsonView.successJson();
    }

    @GetMapping("logout")
    public ModelAndView logout(){
        ShiroContext.logout();
        return new ModelAndView("redirect:/");
    }


    //------------------------------- 个人信息 start
    @RequestMapping("uhome")
    public ModelAndView uhome(){
        ModelAndView mv = new ModelAndView("user_home");
        mv.addObject("curNav","uhome");

        AuthUser authUser = ShiroContext.getSessionUser();
        Long userId = authUser.getId();

        UserCourseSection userCourseSection = new UserCourseSection();
        userCourseSection.setUserId(userId);
        List<UserCourseSection> list = userCourseSectionService.queryUserCourse(userCourseSection);

        mv.addObject("userCourseList",list);
        return mv;
    }

    @RequestMapping("uinfo")
    public ModelAndView uinfo() {
        ModelAndView mv = new ModelAndView("user_info");
        mv.addObject("curNav", "uinfo");

        AuthUser authUser = ShiroContext.getSessionUser();
        Long userId = authUser.getId();
        authUser = this.authUserService.getById(userId);
        mv.addObject("authUser",authUser);

        return mv;
    }

    @RequestMapping("saveinfo")
    @ResponseBody
    public String saveinfo(AuthUser authUser, String userHeader){
        if(StringUtils.isNotBlank(userHeader)){
            //将图片添加到数据库
            Long atId = attachmentService.createAttachment("UserHeader",userHeader);
            authUser.setHeader(atId.toString());
        }

        AuthUser sessionUser = ShiroContext.getSessionUser();
        authUser.setId(sessionUser.getId());
        this.authUserService.update(authUser);

        return JsonView.successJson();
    }

    @RequestMapping("upassword")
    public ModelAndView upassword() {
        ModelAndView mv = new ModelAndView("user_password");
        mv.addObject("curNav", "upassword");
        return mv;
    }

    @RequestMapping("savepassword")
    @ResponseBody
    public String savepassword(String oldPassword, String password, String rePassword){

        if(StringUtils.isEmpty(oldPassword)
        || StringUtils.isEmpty(password)){
            return JsonView.failureJson("密码不能为空");
        }

        AuthUser authUser = ShiroContext.getSessionUser();
        authUser = this.authUserService.getById(authUser.getId());

        oldPassword = HashUtil.hmacSha256(oldPassword,authUser.getSalt());
        if(authUser.getPassword().equals(oldPassword)){
            if(password.equals(rePassword)){
                password = HashUtil.hmacSha256(password,authUser.getSalt());
                authUser.setPassword(password);
                this.authUserService.update(authUser);
                return JsonView.successJson();
            }else{
                return JsonView.failureJson("新密码和重复密码不一致");
            }
        }else{
            return JsonView.failureJson("旧密码输入错误");
        }
    }

    @RequestMapping("ucomment")
    public ModelAndView ucomment() {
        ModelAndView mv = new ModelAndView("user_comment");
        mv.addObject("curNav", "ucomment");

        AuthUser authUser = ShiroContext.getSessionUser();
        List<CourseComment> courseComments = courseCommentService.queryUserCourseComment(authUser.getUsername());
        mv.addObject("courseComments",courseComments);

        return mv;
    }


}
