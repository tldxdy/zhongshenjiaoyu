package com.yunduancn.zhongshenjiaoyu.utils;

/**
 * Created by Administrator on 2017/5/8.
 */

public class UrlUtils {

    final static String url = "http://ceshi.yunduancn.cn/";

    //final static String url = "http://www.jxsqjy.cn/";


    public final static String AK = "697dfc53a28b4ea2b8df2d13d4298f12";
    /**
     * 轮播图
     */
    public static String adbannerurl = url + "mapi_v1/adbanner";

    /**
     * 2、首页热门课程分类
     */
    public static String hotclassurl = url + "mapi_v1/hotclass";


    /**
     * 3、登陆
     */
    public static String loginurl = url + "mapi_v1/login";

    /**
     * 1.3 首页最新课程
     */
    public static String coursesindexurl = url + "mapi_v1/coursesindex";

    /**
     * 1.2内页课程分类
     * （课程中心）
     *（人群）
     *（学科）
     */
    public static String courseurl = url + "mapi_v1/courseclass/course";
    public static String crowdurl = url + "mapi_v1/courseclass/crowd";
    public static String subjecturl = url + "mapi_v1/courseclass/subject";


    /**
     * 获取注册的短信验证码
     */

    public static String validationdxurl = url + "mapi_v1/validationdx";

    /**
     *4.1 注册会员（接口完成）
     */
    public static String registerurl = url + "mapi_v1/register";

    /**
     * 修改密码
     */
    public static String getpasswordurl = url + "mapi_v1/getpassword";

    /**
     * 找回密码获取验证码
     */
    public static String getcodeurl = url + "mapi_v1/getcode";



    /**
     * 2.2 课程列表
     */
    public static String courselisturl = url + "mapi_v1/courselist ";

    /**
     * 2.3  课程简介
     */
    public static String courseshowurl = url + "mapi_v1/courseshow ";


    /**
     * 2.4 加入学习
     */
    public static String joincourseurl = url + "mapi_v1/joincourse ";

    /**
     * 2.4.1 方法名
     */
    public static String courseplayurl = url + "mapi_v1/courseplay ";
}
