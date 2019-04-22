package cn.xunzi.basiclib.api;


import java.io.File;

import cn.xunzi.annotations.ApiFactory;
import cn.xunzi.basiclib.bean.PostBean;
import cn.xunzi.basiclib.bean.TaskBean;
import cn.xunzi.basiclib.bean.TaskDetaBean;
import cn.xunzi.basiclib.bean.TaskRecordBean;
import cn.xunzi.basiclib.bean.UpLoadBean;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import io.reactivex.Observable;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

@ApiFactory(value = BaseApi.class)
public interface TaskService {

    //用户已接任务
    @POST("user/task/list")
    @FormUrlEncoded
    Observable<TaskRecordBean> taskHaveLsit(@Field("userId")String userId);

    //任务列表
    @POST("index/task/listByType")
    @FormUrlEncoded
    Observable<TaskBean> getTaskList(@Field("userId")String userId,@Field("types")String types);

    //任务详情
    @POST("index/task/detail")
    @FormUrlEncoded
    Observable<TaskDetaBean> getTaskDeta(@Field("taskId")String taskId);

    //提交任务
    @POST("user/task/uploadVoucher")
    @FormUrlEncoded
    Observable<PostBean> submitTask(@Field("userId")String userId,@Field("taskId")String taskId,@Field("image")String image);

    //领取任务
    @POST("user/task/draw")
    @FormUrlEncoded
    Observable<PostBean> receiveTask(@Field("userId")String userId,@Field("taskId")String taskId);
}
