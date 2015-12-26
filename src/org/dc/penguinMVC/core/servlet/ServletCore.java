package org.dc.penguinMVC.core.servlet;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dc.penguinMVC.core.annotation.Controller;
import org.dc.penguinMVC.core.annotation.RequestMapping;
import org.dc.penguinMVC.core.handle.RequestHandle;
import org.dc.penguinMVC.core.util.ClassesUtils;
import org.dc.penguinMVC.core.util.RequestInit;

public class ServletCore implements Servlet{
	public static Log log = LogFactory.getLog(ServletCore.class);
	public void destroy() {
		System.out.println("ServletCore.destroy()");
	}

	public ServletConfig getServletConfig() {
		System.out.println("ServletCore.getServletConfig()");
		return null;
	}

	public String getServletInfo() {
		System.out.println("ServletCore.getServletInfo()");
		return null;
	}
	public void init(ServletConfig config) throws ServletException {
		String basePackage = config.getInitParameter("basePackage");
		String[] bases = basePackage.split(",");
		//绝对地址
		String path = ServletCore.class.getResource("/").getPath();
		for (int k = 0; k < bases.length; k++){
			String packName = bases[k];//com.dc.controller
			String readPath = path+packName.replace(".", "/");
			File file = new File(readPath);

			// 以.class结尾的文件(编译好的java类文件)
			File[] dirfiles = file.listFiles(new FileFilter() {
				public boolean accept(File file) {
					return file.getName().endsWith(".class");
				}
			});
			try {
				for (int i = 0; i < dirfiles.length; i++) {
					File f = dirfiles[i];
					String fileStr = f.getPath();//src/com/dc/Controller/UserController.class
					InputStream is = new FileInputStream(f);
					String classDir = packName+"."+fileStr.substring(fileStr.lastIndexOf(File.separator)+1,fileStr.lastIndexOf("."));
					log.info("正在加载控制层:"+classDir+".class");
					//这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
					Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(classDir);
					Controller c = clazz.getAnnotation(Controller.class);
					if(c!=null){
						RequestMapping r = clazz.getAnnotation(RequestMapping.class);
						String requestSuperUrl = r.value();
						Method[] methods = clazz.getDeclaredMethods();

						for (int j = 0; j < methods.length; j++) {
							Method m = methods[j];
							RequestMapping  requestAnno = m.getAnnotation(RequestMapping.class);
							if(requestAnno!=null){
								String requestURL = requestSuperUrl + requestAnno.value();
								RequestHandle info = new RequestHandle();
								info.setClazz(clazz);
								info.setMethodName(m.getName());
								info.setParameterTypes(m.getParameterTypes());
								info.setParamName(ClassesUtils.getMethodParamNames(m,is));
								is.close();
								RequestInit.put(requestURL, info);
							}
						}
					}
				}
			} catch (Exception e) {
				log.error("",e);
			}  
		}
	}

	public void service(ServletRequest request, ServletResponse response)throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		req.setCharacterEncoding("utf-8");
		String servletPath = req.getServletPath();
		String urlKey = req.getServletPath().substring(0, servletPath.indexOf("."));
		RequestHandle requestInfo = RequestInit.get(urlKey);
		Object url = null;
		try {
			url = requestInfo.excute(req.getParameterMap(),request,response,req.getSession());
		} catch (Exception e) {
			log.error("",e);
		}

		if(url !=null ){
			String outUrl = url.toString();
			if(outUrl.startsWith("redirect://")){
				resp.sendRedirect(outUrl.substring(9,outUrl.length()));
			}else{
				req.getRequestDispatcher(outUrl).forward(req, resp);
			}
		}
	}
}
