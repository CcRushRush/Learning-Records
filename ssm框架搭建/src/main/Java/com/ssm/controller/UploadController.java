package com.ssm.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@RequestMapping("/upload")
@Controller
public class UploadController {

    //商品图片上传
    @RequestMapping("/managerPicture")
    public void managerPicture(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(request);
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
//        获取数据图片
        MultipartFile file = multipartHttpServletRequest.getFile("managerPicture");
        /**
         * request.getSession().getServletContext().getRealPath("/upload")：获取的是服务器路径webapp下的路径
         * 图片保存文件夹的路径
         */
        String uploadFolder = request.getSession().getServletContext().getRealPath("/upload");
        System.out.println("uploadFolder:" + uploadFolder);
        File uploadFolderFile = new File(uploadFolder);
        if (!uploadFolderFile.exists()) {
            uploadFolderFile.mkdirs();
        }

//        文件名
        String suffix = file.getOriginalFilename().split("\\.")[1];
        String fileName = UUID.randomUUID().toString()+"."+suffix;
        String totalPath = uploadFolder + "\\" + fileName;
        System.out.println("totalPath"+totalPath);
//        保存图片
        FileCopyUtils.copy(file.getInputStream(),new FileOutputStream(new File(totalPath)));

//        返回数据给客户端
        String imgUrl = "http://localhost:8080/upload/"+fileName;
        String respJson="{\"imgUrl\":\""+ imgUrl +"\"}";
        response.getWriter().write(respJson);
    }
}
