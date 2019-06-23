package com.cndym.servlet;

import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cndym.utils.ImagesUtils;
import com.cndym.utils.Md5;
import com.cndym.utils.Utils;

public class Code extends HttpServlet {

	private boolean svgMode = false;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String vcode = Utils.random(4);
		if (vcode.length() < 4)
			return;
		//request.getSession().setAttribute("code", vcode);

		Cookie c = new Cookie("code", Md5.Md5(vcode));
		c.setMaxAge(60 * 30);
		c.setPath("/"); // 路径
		// c.setDomain(".");// 域名
		response.addCookie(c); // 在本地硬盘上产生文件
        String[] code = new String[] { vcode.substring(0, 1), vcode.substring(1, 2), vcode.substring(2, 3), vcode.substring(3, 4) };
		if (svgMode == false) {
			outJPEG(code, response);
		}
	}

	@Override
	public void init() throws ServletException {
		try {
			GraphicsEnvironment.getLocalGraphicsEnvironment();
		} catch (Throwable e) {
			svgMode = true;
		}
	}

	private void outJPEG(String[] code, HttpServletResponse response) throws IOException {
		response.setContentType("image/jpeg");
		BufferedImage image = ImagesUtils.randomImage(code);
		ImageWriter writer = (ImageWriter) ImageIO.getImageWritersByFormatName("jpeg").next();
		JPEGImageWriteParam params = new JPEGImageWriteParam(null);
		params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		params.setCompressionQuality(1f);
		ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(response.getOutputStream());
		writer.setOutput(imageOutputStream);
		writer.write(null, new IIOImage(image, null, null), params);
		writer.dispose();
		imageOutputStream.close();

	}
}
