package com.cxstock.utils.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class DataCopy {

 /**
     * @param args
 * @throws IOException 
     */
    public static void main(String[] args) {
        /*
         * 备份和导入是一个互逆的过程。
         * 备份：程序调用mysql的备份命令，读出控制台输入流信息，写入.sql文件；
         * 导入：程序调用mysql的导入命令，把从.sql文件中读出的信息写入控制台的输出流
         * 注意：此时定向符">"和"<"是不能用的
         */
        try {
			//backup("e:/mystock.sql");
			//load("e:/mystock.sql");
			//delete("1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * 备份检验一个sql文件是否可以做导入文件用的一个判断方法：把该sql文件分别用记事本和ultra
     * edit打开，如果看到的中文均正常没有乱码，则可以用来做导入的源文件（不管sql文件的编码格式如何，也不管db的编码格式如何）
     * @throws IOException 
     */
    public static void backup(String pathname) throws Exception {
	    	File filePath = new File("D:/MyStockData");
	    	//判断文件夹是否存在 没有的话创建一个
			if (!filePath.exists()) {
				filePath.mkdir();
			}
    	
            Runtime rt = Runtime.getRuntime();

            // 调用 mysql 的 cmd:
            Process child = rt.exec("mysqldump -uroot -proot --set-charset=utf-8 mystock");// 设置导出编码为utf8。这里必须是utf8
           
            // 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
            InputStream in = child.getInputStream();// 控制台的输出信息作为输入流
                       
            InputStreamReader xx = new InputStreamReader(in, "utf8");// 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码
           
            String inStr;
            StringBuffer sb = new StringBuffer("");
            String outStr;
            // 组合控制台输出信息字符串
            BufferedReader br = new BufferedReader(xx);
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();
           
            // 要用来做导入用的sql目标文件：
            FileOutputStream fout = new FileOutputStream(pathname);
            OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");
            writer.write(outStr);
            // 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
            writer.flush();

            // 别忘记关闭输入输出流
            in.close();
            xx.close();
            br.close();
            writer.close();
            fout.close();
    }

    /**
     * 导入
     * @throws IOException 
     *
     */
    public static void load(String pathname) throws Exception {
            Runtime rt = Runtime.getRuntime();
            // 调用 mysql 的 cmd:
            Process child = rt.exec("mysql -uroot -proot mystock");
            OutputStream out = child.getOutputStream();//控制台的输入信息作为输出流
            String inStr;
            StringBuffer sb = new StringBuffer("");
            String outStr;
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(pathname), "utf8"));
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();
            OutputStreamWriter writer = new OutputStreamWriter(out, "utf8");
            writer.write(outStr);
            // 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
            writer.flush();
            // 别忘记关闭输入输出流
            out.close();
            br.close();
            writer.close();
    }

    /**
     * 系统初始化
     */
    public static void delete(String delstate) throws Exception {
    	Runtime rt = Runtime.getRuntime();
        // 调用 mysql 的 cmd:
        Process child = rt.exec("mysql -uroot -proot mystock");
        OutputStream out = child.getOutputStream();//控制台的输入信息作为输出流
        String outStr = "";
        if("true".equals(delstate)){//删除业务数据
        	outStr = "truncate bsdsp;truncate bydsp;truncate jhdsp;truncate thdsp;truncate ckdsp;truncate tkdsp;" +
        	"truncate bsd;truncate byd;truncate jhd;truncate thd;truncate ckd;truncate tkd;" +
        	"update spxx set kcsl=0,kczj=0,state='0'";
        }else{//删除所有数据
        	outStr = "truncate bsdsp;truncate bydsp;truncate jhdsp;truncate thdsp;truncate ckdsp;truncate tkdsp;" +
        			"truncate bsd;truncate byd;truncate jhd;truncate thd;truncate ckd;truncate tkd;" +
        			"truncate gys;truncate kh;truncate spxx;truncate splb;truncate users;" +
        			"insert into `users` values ('1', 'admin', 'admin', 'admin', '1', '0', '');";
        }
        OutputStreamWriter writer = new OutputStreamWriter(out, "utf8");
        writer.write(outStr);
        // 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
        writer.flush();
        // 别忘记关闭输入输出流
        out.close();
        writer.close();
    }
}