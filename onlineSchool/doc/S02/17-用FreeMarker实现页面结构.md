
## 用FreeMarker标签实现页面重构
一、实现页面重构
    1、不同菜单做页面跳转
    2、菜单的背景色设置
    3、完成页面重构功能
    
二、FreeMarker 标签技术点
    1、<@extends name="_manage_base.html"/> 继承父页面
    2、<@override name="title"> </@override> 覆盖父页面内容
    3、<#include "_manage_nav.html"/> 包含页面
    4、<#assign >语法、${var}语法、<#if >语法 
    
三、代码实战（文件）
    1、修改user_manage.html
    2、创建所有页面的父页面：_manage_base.html
    3、创建导航页面：_manage_nav.html
    4、创建课程管理页面：course_manage.html
    5、创建课程分类管理页面：classify_manage.html
    6、首页推荐管理页面：recommend_manage.html
    
## 祁大聪讲编程

