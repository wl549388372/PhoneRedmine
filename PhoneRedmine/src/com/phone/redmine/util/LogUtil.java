package com.phone.redmine.util;

import java.io.File;
import android.util.Log;
import com.phone.redmine.biz.RMConst;

/**
 * 日志工具类
 * 
 * 开启日志写入Sdcard
 */
public class LogUtil
{

	public static char		LOG_PRINT_TYPE				= 'v';								// 输出日志类型，v输出所有信息，i输出（i、d、w、e）信息，d输出（d、w、e信息），w输出（w、e信息），e写入e信息
	public static char		LOG_WRITE_TYPE				= 'w';								// 写入日志类型，v写入所有信息，i写入（i、d、w、e）信息，d写入（d、w、e信息），w写入（w、e信息），e写入e信息
	public static Boolean	LOG_PRINT_SWITCH			= true;							// 日志文件打印输出开关
	public static Boolean	LOG_WRITE_SWITCH			= false;							// 日志写入开关
	public static int		SDCARD_LOG_FILE_SAVE_DAYS	= 30;								// sd卡中日志文件的最多保存天数
	public static String	MYLOGFILEName				= "FCLog.txt";						// 本类输出的日志文件名称
	public static String	PATH_LOG					= RMConst.PATH_REDMINE + "/log";

	public static void initLogConfig(boolean isPrint, boolean isWriteToFie)
	{
		LOG_PRINT_SWITCH = isPrint;
		LOG_WRITE_SWITCH = isWriteToFie;
	}

	/**
	 * 设置文件写入路径
	 * 
	 * @param path			文件的文件夹路径
	 */
	public static void setFilePath(String path)
	{
		PATH_LOG = path;
	}

	/** 
	 * 警告信息
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void w(String tag, Object msg)
	{
		log(tag, msg.toString(), 'w');
	}

	/**
	 * 错误信息
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void e(String tag, Object msg)
	{
		log(tag, msg.toString(), 'e');
	}

	/**
	 * 调试信息
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void d(String tag, Object msg)
	{
		log(tag, msg.toString(), 'd');
	}

	/** 
	 * 一般信息
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void i(String tag, Object msg)
	{
		log(tag, msg.toString(), 'i');
	}

	/**
	 * 所有信息
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void v(String tag, Object msg)
	{
		log(tag, msg.toString(), 'v');
	}

	/** 
	 * 警告信息
	 * 
	 * @param tag
	 * @param text
	 */
	public static void w(String tag, String text)
	{
		log(tag, text, 'w');
	}

	/** 
	 * 错误信息
	 * 
	 * @param tag
	 * @param text
	 */
	public static void e(String tag, String text)
	{
		log(tag, text, 'e');
	}

	/** 
	 * 调试信息
	 * 
	 * @param tag
	 * @param text
	 */
	public static void d(String tag, String text)
	{
		log(tag, text, 'd');
	}

	/** 
	 * 一般信息
	 * 
	 * @param tag
	 * @param text
	 */
	public static void i(String tag, String text)
	{
		log(tag, text, 'i');
	}

	/** 
	 * 所有信息
	 * 
	 * @param tag
	 * @param text
	 */
	public static void v(String tag, String text)
	{
		log(tag, text, 'v');
	}

	/*
	 * 根据tag, msg和等级，输出日志 
	 * 
	 * @param tag
	 * @param msg
	 * @param level				日志等级  e,w,d,i,v
	 */
	private static void log(String tag, String msg, char level)
	{
		if (msg == null)
		{
			msg = "null";
		}

		if (LOG_PRINT_SWITCH)
		{
			if ('e' == level)
			{
				Log.e(tag, msg);
			} else if ('w' == level && ('w' == LOG_PRINT_TYPE || 'd' == LOG_PRINT_TYPE || 'i' == LOG_PRINT_TYPE || 'v' == LOG_PRINT_TYPE))
			{
				Log.w(tag, msg);
			} else if ('d' == level && ('d' == LOG_PRINT_TYPE || 'i' == LOG_PRINT_TYPE || 'v' == LOG_PRINT_TYPE))
			{
				Log.d(tag, msg);
			} else if ('i' == level && ('i' == LOG_PRINT_TYPE || 'v' == LOG_PRINT_TYPE))
			{
				Log.i(tag, msg);
			} else if ('v' == level && 'v' == LOG_PRINT_TYPE)
			{
				Log.v(tag, msg);
			}
		}

		if (LOG_WRITE_SWITCH)
		{
			boolean flag = false;
			if ('e' == level)
				flag = true;
			else if ('w' == level && ('w' == LOG_WRITE_TYPE || 'd' == LOG_WRITE_TYPE || 'i' == LOG_WRITE_TYPE || 'v' == LOG_WRITE_TYPE))
				flag = true;
			else if ('d' == level && ('d' == LOG_WRITE_TYPE || 'i' == LOG_WRITE_TYPE || 'v' == LOG_WRITE_TYPE))
				flag = true;
			else if ('i' == level && ('i' == LOG_WRITE_TYPE || 'v' == LOG_WRITE_TYPE))
				flag = true;
			else if ('v' == level && 'v' == LOG_WRITE_TYPE)
				flag = true;

			if (flag)
			{
				writeLogtoFile(String.valueOf(level), tag, msg);
			}
		}
	}

	/** 
	 * 删除指定日期前的日志文件
	 */
	public static void delFile()
	{
		long beforeTime = CalendarUtil.addDays(-SDCARD_LOG_FILE_SAVE_DAYS).getTime();
		File dirFile = new File(PATH_LOG);
		File files[] = dirFile.listFiles();
		if (files != null)
		{
			for (File file : files)
			{
				if (file.lastModified() < beforeTime)
				{
					FileUtil.delete(file);
				}
			}
		}

	}

	/** 
	 * 将异常实例变为异常详细信息的字符串
	 * 
	 * @param e					异常
	 * 
	 * @return					异常拼接成的字符串
	 */
	public static String ExceptionToString(Throwable e)
	{
		String strError = null;
		if (e == null)
		{
			strError = "unknown error";
			return strError;
		}

		StringBuffer em = new StringBuffer();
		try
		{
			StackTraceElement[] s = e.getStackTrace();
			em.append(e.getCause()).append("\n");
			for (int i = 0; i < s.length; i++)
			{
				StackTraceElement st = s[i];
				em.append("\t\t").append(st.toString()).append("\r\n");
			}
			strError = em.toString();
		} catch (Exception e1)
		{
			e1.printStackTrace();
		}
		return strError;
	}

	/*
	 * 写入日志到文件
	 * 
	 * @param mylogtype			日志等级类型
	 * @param tag				日志标志
	 * @param text				日志内容
	 */
	private static void writeLogtoFile(String mylogtype, String tag, String text)
	{
		String preFileName = TimeUtil.getDate();
		String needWriteMessage = TimeUtil.getDateTime() + "    " + mylogtype + "    " + tag + "\n" + text;
		FileUtil.writeFileStr(PATH_LOG + "/" + preFileName + MYLOGFILEName, needWriteMessage);
	}

}
