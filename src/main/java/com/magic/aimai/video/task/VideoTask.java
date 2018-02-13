package com.magic.aimai.video.task;

import com.magic.aimai.business.cache.MemcachedUtil;
import com.magic.aimai.business.entity.Video;
import com.magic.aimai.business.service.VideoService;
import com.magic.aimai.business.service.VideoWareHouseService;
import com.magic.aimai.business.util.FileUpload;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * 定时任务
 * @author lzh
 * @create 2017/8/31 11:49
 */
@Component
public class VideoTask {
    @Resource
    private VideoService videoService;
    @Resource
    private VideoWareHouseService videoWareHouseService;

    /**
     * 任务检测删除垃圾视频 所有垃圾视频统一删除 定时任务。每小时执行一次
     */
    @Scheduled(cron = "0 0 0/1 * * *")
    public void deleteFileVideo() {
       /* HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        ServletContext servletContext = request.getSession().getServletContext();
        File file = new File(servletContext.getRealPath("/upload"));
        if (file.exists()) {

        }*/
        //获取缓存中待删除的视频
        String urls = (String) MemcachedUtil.getInstance().get(Video.video_session_delete);
        if (null != urls && "".equals(urls)) {
            String[] dirs = urls.split(",");
            FileUpload.deletes(dirs);
            //清除缓存
            MemcachedUtil.getInstance().delObj(Video.video_session_delete);
        }

    }

/*

    public static void main(String[] args) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        ServletContext servletContext = request.getSession().getServletContext();
        File file = new File(servletContext.getRealPath("/upload"));
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        }
    }

    public static void packagingFilePath(File[] tempList,List<String> list) {
        for (File file : tempList) {
            if (null != file.listFiles()) {
                packagingFilePath(file.listFiles(),list);
            } else {
                String path = file.toString();
                if (path.contains("upload")) {
                    list.add("upload" + path.split("upload")[1]);
                }
            }
        }
    }
*/

}
