package com.qicong.os.web.idcode;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * User：祁大聪
 * 验证码生成器
 */
@Controller
@RequestMapping("idcode")
public class IdentifyCodeController {

	@GetMapping("/register")
	public void register(HttpServletRequest request, HttpServletResponse response){
		String random=RandomStringUtils.randomAlphanumeric(4);
		//将随机的验证码放到session中
		request.getSession().setAttribute(IdentifyCodeKey.REGISTER, random);
		this.processIdCode(random,request,response);
	}

	@GetMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response){
		String random=RandomStringUtils.randomAlphanumeric(4);
		//将随机的验证码放到session中
		request.getSession().setAttribute(IdentifyCodeKey.LOGIN, random);
		this.processIdCode(random,request,response);
	}

	@GetMapping("/comment")
	public void comment(HttpServletRequest request, HttpServletResponse response){
		String random=RandomStringUtils.randomAlphanumeric(4);
		//将随机的验证码放到session中
		request.getSession().setAttribute(IdentifyCodeKey.COMMENT, random);
		this.processIdCode(random,request,response);
	}

	private void processIdCode(String random,HttpServletRequest request, HttpServletResponse response){
		response.setContentType("image/jpeg");
		response.addHeader("pragma", "NO-cache");
		response.addHeader("Cache-Control","no-cache");
		response.addDateHeader("Expries",0);
		int width=110, height=33;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		//以下填充背景色
		g.setColor(new Color(225,225,225));
		Font DeFont=new Font("SansSerif", Font.PLAIN, 26);
		g.setFont(DeFont);
		g.fillRect(0, 0, width, height);
		//设置字体色
		g.setColor(Color.BLACK);
		g.drawString(random,20,25);
		g.dispose();
		try {
			ServletOutputStream outStream = response.getOutputStream();
			ImageIO.write(image, "JPG", outStream);
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
