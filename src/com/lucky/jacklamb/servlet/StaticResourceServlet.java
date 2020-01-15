package com.lucky.jacklamb.servlet;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StaticResourceServlet extends HttpServlet {

	private static final long serialVersionUID = -2356307482633634613L;

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String realPath = request.getServletContext().getRealPath("/ims(33).jpg");
		System.out.println(realPath);
		byte[] buffer;
		FileInputStream fos=new FileInputStream(realPath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream(fos.available());
		byte[] bytes = new byte[fos.available()];
		int temp;
		while ((temp = fos.read(bytes)) != -1) {
			baos.write(bytes, 0, temp);
		}
		fos.close();
		baos.close();
		buffer = baos.toByteArray();
		response.setContentType("image/jpg");
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(buffer);
		outputStream.flush();
	}


}
