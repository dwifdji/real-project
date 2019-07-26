package com.liuhaolei.dreamer.controller;

import java.io.InputStream;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.liuhaolei.dreamer.common.res.ResponseModel;
import com.liuhaolei.dreamer.common.res.ResultStatus;
import com.liuhaolei.dreamer.conf.FtpConfig;
import com.liuhaolei.dreamer.util.FileUtil;
import com.liuhaolei.dreamer.util.IDUtils;

@RestController
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	private FtpConfig ftpConfig;

	@RequestMapping("/uploadFile")
	public ResponseModel uploadFile(@RequestParam("picture") MultipartFile uploadFile) {
		
		try {
			// 1、给上传的图片生成新的文件名
			// 1.1获取原始文件名
			String oldName = uploadFile.getOriginalFilename();
			// 1.2使用IDUtils工具类生成新的文件名，新文件名 = newName + 文件后缀
			String newName = IDUtils.genImageName();
			newName = newName + oldName.substring(oldName.lastIndexOf("."));
			// 1.3生成文件在服务器端存储的子目录
			String filePath = new DateTime().toString("/yyyy/MM/dd");

			// 3、把图片上传到图片服务器
			// 3.1获取上传的io流

			InputStream input = uploadFile.getInputStream();
			// 3.2调用FtpUtil工具类进行上传
			String result = FileUtil.upFileToFtp(ftpConfig.getAddress(), ftpConfig.getPort(), ftpConfig.getUsername(),
					ftpConfig.getPassword(), ftpConfig.getBasepath(), filePath, newName, input);

			if (result != null) {
				return ResponseModel.successApi(ResultStatus.SUCCESS, result);
			}  

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseModel.failApi(ResultStatus.UPLOAD_ERROR);
	}
	
}
