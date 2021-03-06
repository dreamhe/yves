package cn.com.yves.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 注意:文件上传方式必须是Post方式
 * 
 * @author User
 * 
 */
public class UploadingFile extends HttpServlet {

    // 判断是否是文件上传的文件。
    private boolean isMultipart;
    // 保存文件的路径
    private String dirPath;
    // 限制上传文件的大小
    private final int maxFileSize = 50 * 1024;
    // 缓存大小
    private final int maxMemSize = 4 * 1024;

    private File file;

    private int count = 0;// 给循环设置参数。

    private final String tempPath = "E:\\git\\yves\\WebRoot\\files\\uploadFile\\temp";// 设置缓存路径

    // 设置文件的保存路径除了在web.xml中配置还可以直接写在代码里
    private static final String UPLOAD_DIRECTORY = "upload";// 定义上传文件存储目录

    @Override
    public void init() throws ServletException {
        // 获取在web.xml中配置的文件保存的位置
        dirPath = this.getServletContext().getInitParameter("file-upload");// getServletContext（）方法来自GenericServlet类
        // 方法二： 直接使用定义的路径
        // filePath = UPLOAD_DIRECTORY;

        // 如果目录不存在则创建
        File uploadDir = new File(dirPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
    }

    /**
     * The doGet method of the servlet. <br>
     * 
     * This method is called when a form has its tag value method equals to get.
     * 
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        throw new ServletException("GET method used with "
                + getClass().getName() + ": POST method required.");
    }

    /**
     * The doPost method of the servlet. <br>
     * 
     * This method is called when a form has its tag value method equals to
     * post.
     * 
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 文件上传的的时候，所有的数据都是通过 第三方的jar中的FileItem对象来拿数据的
        request.getParameter("text");// 验证request中是拿不到数据的

        // 思考： 文件上传为什么不能用get方式
        isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            response.getWriter().print("It isn't  file upload  !");
            return;
        }

        // 配置 磁盘文件条目工厂 factory
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(maxMemSize); //
        // 设置缓存的大小，当上传文件的容量超过该缓存时，直接放到暂时存储室Repository
        factory.setRepository(new File(tempPath));// 原理
                                                  // 它是先存到暂时存储室，
                                                  // 然后在真正写到对应目录的硬盘上，
                                                  // 按理来说当上传一个文件时，其实是上传了两份，
                                                  // 第一个是以.tem
                                                  // 格式的，然后再将其真正写到对应目录的硬盘上

        // 创建一个API文件上传处理 ，以及配置。
        ServletFileUpload upload = new ServletFileUpload(factory);

        // upload.setHeaderEncoding("ISO8859_1");//
        // 用来解决文件内容里面中文乱码，不加也是可以的（加了在fileItem.getName()的时候也要加）

        // upload.setSizeMax(maxFileSize);// 允许上传的文件大小的最大值

        // 解析请求，获取文件项
        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            // 处理上传的文件项，这里也可以用for each来做
            Iterator<FileItem> i = fileItems.iterator();

            while (i.hasNext()) {
                FileItem fi = i.next();
                if (!fi.isFormField()) {// 当该表单类型是不是普通类型时，获取上传文件的参数

                    // FileItem中能获取信息的一些常用方法
                    String fieldName = fi.getFieldName();// 该文件在表中的控件名

                    String contentType = fi.getContentType();// 文档类型

                    boolean isInMemory = fi.isInMemory();// ???

                    long sizeInBytes = fi.getSize();// 上传的文件的总大小字节数

                    // fi.getName() 获取到的是全路径名 ,所以通过下面的方式来只获取文件的名字
                    String fileName = new File(fi.getName()).getName(); // String
                                                                        // fileName
                                                                        // = new
                                                                        // String(fi.getName().getBytes(
                                                                        // "ISO8859_1"),
                                                                        // "UTF-8");不加这个转化也是可以

                    if (!fileName.equals("")) {// 当文件上传控件没有选择文件的时候则 fileName为空
                        file = new File(dirPath + fileName);
                    }

                    // 第三方写文件的方法，该方法会抛出异常
                    // 用exception捕捉，所有的异常可以统一放在外面处理，这里为了方便理解都在内部捕捉了。
                    try {
                        fi.write(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // 自己处理写文件，出现没有内容，该如何解决。
                    // InputStream is = null;
                    // OutputStream os = null;
                    // try {
                    // is = fi.getInputStream();
                    // os = new FileOutputStream(file);
                    // byte[] buffer = new byte[1024];
                    // int len = -1;
                    // while ((len = is.read()) != -1) {
                    // os.write(buffer, 0, len);
                    // }
                    // } catch (Exception e) {
                    // } finally {
                    // if (os != null) {
                    // os.close();
                    // }
                    // if (is != null) {
                    // is.close();
                    // }
                    // }

                } else {// 否则是表单中的一般字符串参数

                    // 获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
                    String message = fi.getString("UTF-8"); // String
                                                            // message=fi.getString();
                                                            // 有中文必须fi.getString("UTF-8");
                    // 将表当中所有的普通表单数据打印
                    System.out.println("普通表单数据" + ++count + ":" + message);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        // 响应数据
        request.getRequestDispatcher("pages/uploadingFileShow.jsp").forward(
                request, response);
    }
}
