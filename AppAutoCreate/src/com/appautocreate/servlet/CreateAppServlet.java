package com.appautocreate.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appautocreate.utils.AntUtil;
import com.appautocreate.utils.DownloadUtil;
import com.appautocreate.utils.NetUtil;
import com.appautocreate.utils.PropertiesUtil;

/**
 * Servlet implementation class CreateAppServlet
 */
@WebServlet("/CreateAppServlet")
public class CreateAppServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateAppServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		excute(request, out);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	public void excute(HttpServletRequest request, PrintWriter out)
			throws IOException {
		String name = request.getParameter("name");// 应用名称 *必须
		String color = request.getParameter("color");// 主题颜色 *可选
		String typedata = request.getParameter("typedata");// 分类数据
		String chid = request.getParameter("chid");// 渠道号
		String packagename = request.getParameter("packagename");// 包名
		String apptype = request.getParameter("apptype"); // 框架 1.侧边栏 2底部导航栏
															// 3win8
		String iconurl = request.getParameter("icon"); // 启动图标地址
		String welcomeimg = request.getParameter("welcomeimg"); // 欢迎页地址
		String version = request.getParameter("version"); // 生成软件版本号
		String price = request.getParameter("price");// 价格金额
		String pricetype = request.getParameter("pricetype");// 价格类型1为包月，2为按日，3按次

		String op = request.getParameter("op");// 运营商 1.移动 2联通 0移动联通

		if (name == null)
			name = "boboVideo";
		if (color == null)
			color = "#FF0000";
		if (typedata == null)
			typedata = "[{name:\"\u5973\u661F\u516B\u5366\",id:100000},{name:\"\u9999\u8F66\u7F8E\u5973\",id:102},{name:\"\u7535\u5F71\u5973\u5B69\",id:103}]";
		if (chid == null)
			chid = "6017";
		if (packagename == null)
			packagename = "com.android.bobo";
		if (version == null)
			version = "1";
		if (iconurl == null)
			iconurl = "http://img0.bdstatic.com/img/image/shouye/mndf-9410895057.jpg";
		if (welcomeimg == null)
			welcomeimg = "http://img0.bdstatic.com/img/image/shouye/mnxz-9431881658.jpg";
		if (price == null)
			price = "2";
		if (pricetype == null)
			pricetype = "1";
		if (op == null) 
			op = "0";
		String _baseDir = request.getSession().getServletContext()
				.getRealPath("")
				+ "/AndroidTemplate/";
		// 根据APPtype 选择模板
		if (apptype != null) {
			int type = Integer.getInteger(apptype);
			switch (type) {
			case 1:
				_baseDir += "XXXX/";
				break;
			default:
				_baseDir += "XXXX/";
				break;
			}
		} else {
			_baseDir += "XXXX/";
		}
		String _baseDir2 = request.getSession().getServletContext()
				.getRealPath("")
				+ "/ProjectTemp/";

		AntUtil move = new AntUtil();
		AntUtil deploy = new AntUtil();
		move.init(_baseDir + File.separator + "build.xml", _baseDir);
		// 移动模板到临时文件夹
		move.runTarget("move");

		// 写入自定义打包的参数
		String antPath = _baseDir2 + "ant.properties";
		String loadPath = _baseDir2 + "/res/raw/load.properties";
		PropertiesUtil pUtil = new PropertiesUtil();
		pUtil.init(antPath, loadPath);
		// 存入ant 的属性
		pUtil.put("app.name", name, PropertiesUtil.SAVEINANT);
		pUtil.put("app.version", version, PropertiesUtil.SAVEINANT);
		pUtil.put("package.new", packagename, PropertiesUtil.SAVEINANT);
		// 存入 load的属性
		pUtil.put("name", name, PropertiesUtil.SAVEINLOAD);
		pUtil.put("chid", chid, PropertiesUtil.SAVEINLOAD);
		pUtil.put("color", color, PropertiesUtil.SAVEINLOAD);
		pUtil.put("typedata", typedata, PropertiesUtil.SAVEINLOAD);
		pUtil.put("price", price, PropertiesUtil.SAVEINLOAD);
		pUtil.put("pricetype", pricetype, PropertiesUtil.SAVEINLOAD);
		pUtil.put("op", op, PropertiesUtil.SAVEINLOAD);

		pUtil.commit();
		// move.init(_baseDir + File.separator + "build.xml", _baseDir);

		NetUtil.readContentFromGet("http://localhost:8080/TestReturn/testreturn?name=begindownloadicon");
		// 下载icon，替换原有图标
		DownloadUtil.downloadNet(iconurl, _baseDir2
				+ "res/drawable-hdpi/icon.png");
		NetUtil.readContentFromGet("http://localhost:8080/TestReturn/testreturn?name=begindownloadwelcome");
		// 下载欢迎页面，替换原有欢迎页面
		DownloadUtil.downloadNet(welcomeimg, _baseDir2
				+ "res/drawable-hdpi/welcome.png");
		NetUtil.readContentFromGet("http://localhost:8080/TestReturn/testreturn?name=begingenRJava");
		// 生成gen目录
		move.runTarget("genRJava");
		// 这时已经组装完成正常的android工程了

		// 将命令行移动到待自动打包的工程目录下
		deploy.init(_baseDir2 + File.separator + "build.xml", _baseDir2);

		// 更换包名
		deploy.runTarget("changePackage");
		// 修改版本号
		deploy.runTarget("changeVersion");
		// 修改軟件名字
		deploy.runTarget("changeAppname");
		// 执行打包操作，如果要按照渠道号打包则需要在ant.properties 文件配置 market_channels属性 然后执行
		// deploychannels
		deploy.runTarget("deploychannels");
		NetUtil.readContentFromGet("http://localhost:8080/TestReturn/testreturn?name=complete");

		String apkUrlpath = "http://" + request.getServerName() + ":"
				+ request.getServerPort() + "/AppAutoCreate/OutApk/" + name
				+ "_" + version + "_" + "lele.apk";
		String apkpath = request.getSession().getServletContext()
				.getRealPath("")
				+ "/OutApk/" + name + "_" + version + "_" + "lele.apk";
		File f = new File(apkpath);

		if (f.exists()) {
			out.print("软件地址" + apkUrlpath);
			NetUtil.readContentFromGet("http://localhost:8080/TestReturn/testreturn?name="
					+ apkUrlpath);
		} else {
			out.print("生成有误");
		}

		out.flush();
		out.close();
	}

}
