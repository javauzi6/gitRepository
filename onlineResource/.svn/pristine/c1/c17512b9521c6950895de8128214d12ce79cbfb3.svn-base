package com.qicong.os.web;

import com.qicong.os.bean.DownloadBean;
import com.qicong.os.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: 祁大聪
 */
@Controller
public class FileController {

    protected static final  String ID = "{id:[0-9]{1,17}}";

    @Autowired
    AttachmentService attachmentService;

    //原图
    @GetMapping("/file/attachment/" + ID + "/0")
    public void process0(@PathVariable("id")long id, HttpServletResponse response) throws IOException {
        process(id,'0', response);
    }

    void process(long id, char size, HttpServletResponse response) throws IOException{
        DownloadBean bean = attachmentService.downloadAttachment(id,size);
        response.setContentType(bean.mime);
        response.setContentLength(bean.data.length);
        response.setHeader("Cache-Control", "max-age=" + 3600*24*30);
        ServletOutputStream output = response.getOutputStream();
        output.write(bean.data);
        output.flush();
    }

    //large 大图
    @GetMapping("/file/attachment/" + ID + "/l")
    public void processL(@PathVariable("id") long id, HttpServletResponse response) throws IOException {
        process(id, 'l', response);
    }

    //mini 最小图
    @GetMapping("/file/attachment/" + ID + "/m")
    public void processM(@PathVariable("id") long id, HttpServletResponse response) throws IOException {
        process(id, 'm', response);
    }

    //small 小图
    @GetMapping("/file/attachment/" + ID + "/s")
    public void processS(@PathVariable("id") long id, HttpServletResponse response) throws IOException {
        process(id, 's', response);
    }

}
