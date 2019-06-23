package com.cndym.sendClient.splitService;

import com.cndym.bean.tms.Ticket;
import com.cndym.service.ITicketService;
import com.cndym.utils.FileUtils;
import com.cndym.utils.SpringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.*;

/**
 * User: mcs Date: 12-11-21 Time: 上午11:02
 */
public class TicketSplitUtils {

	private static Logger logger = Logger.getLogger(TicketSplitUtils.class);

	private static final String ENCODING = "utf-8";

	private static final String FILE_SUFIXX = ".txt";
	// 默认
	public static final int DEFAULT_SUFIXX = 0;
	// 发送
	public static final int SENDING_SUFIXX = 2;
	// 成功
	public static final int SUCCESS_SUFIXX = 3;
	// 失败
	public static final int FAILURE_SUFIXX = 4;
	// 系统分隔符
	public static String SEPARATOR = File.separator;

	/**
	 * 创建票文件
	 * 
	 * @param fileDir 文件根目录
	 * @param ticket 票对象
	 * @return
	 */
	public static boolean buildSubTicketFile(String fileDir, Ticket ticket) {
		try {
			String ticketId = ticket.getTicketId();
			fileDir = buildFilePath(fileDir, ticket.getLotteryCode(), ticket.getIssue(), ticketId, DEFAULT_SUFIXX);
			FileUtils.creatDir(fileDir);

			String[] numberArray = null;
			if ("201,200,400".contains(ticket.getLotteryCode())) {
				numberArray = new String[] { ticket.getNumberInfo() };
			} else {
				numberArray = ticket.getNumberInfo().split(";");
			}
			int index = 0;
			for (String subNumber : numberArray) {
				FileUtils.writeFile(subNumber, fileDir + SEPARATOR + ticketId + index + FILE_SUFIXX, true, ENCODING);
				index++;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据彩票，期次，票号读取票文件
	 * 
	 * @param fileDir 文件根路径
	 * @param lotteryCode 彩种编号
	 * @param issue 期次
	 * @param ticketId 票号
	 * @return
	 */
	public static Map<String, String> readSendTicketFile(String fileDir, String lotteryCode, String issue, String ticketId) {
		fileDir = buildFilePath(fileDir, lotteryCode, issue, ticketId, DEFAULT_SUFIXX);
		List<File> fileList = FileUtils.exeCurrFile(fileDir);
		Map<String, String> ticketMap = new HashMap<String, String>();
		for (File file : fileList) {
			String subTicketId = file.getName().replace(FILE_SUFIXX, "");
			String content = FileUtils.readFileByLine(file, ENCODING);
			ticketMap.put(subTicketId, content);
		}
		return ticketMap;
	}

	public static List<String> readSendingTicketFile(String fileDir, String lotteryCode, String issue, String ticketId) {
		fileDir = buildFilePath(fileDir, lotteryCode, issue, ticketId, SENDING_SUFIXX);
		List<String> fileNameList = FileUtils.exeCurrFileName(fileDir);
		List<String> ticketIdList = new ArrayList<String>();
		for (String fileName : fileNameList) {
			String subTicketId = fileName.replace(FILE_SUFIXX, "");
			ticketIdList.add(subTicketId);
		}
		return ticketIdList;
	}

	/**
	 * 修改已发送的票文件(接收成功)
	 * 
	 * @param fileDir 文件根目录
	 * @param lotteryCode 彩种
	 * @param issue 彩期
	 * @param ticketId 票号
	 * @return
	 */
	public static boolean updateSendEndTicket(String fileDir, String lotteryCode, String issue, String ticketId) {
		String targetDir = buildFilePath(fileDir, lotteryCode, issue, ticketId, SENDING_SUFIXX);
		fileDir = buildFilePath(fileDir, lotteryCode, issue, ticketId, DEFAULT_SUFIXX);
		logger.info("源文件夹: " + fileDir);
		logger.info("目标文件夹: " + targetDir);
		boolean success = FileUtils.renameDirectory(fileDir, targetDir);
		logger.info("复制文件到接收成功目录: " + success);
		return success;
	}

	/**
	 * 修改已发送的票文件(接收失败)
	 * 
	 * @param fileDir 文件根目录
	 * @param lotteryCode 彩种
	 * @param issue 彩期
	 * @param ticketId 票号
	 * @return
	 */
	public static boolean updateSendFailureTicket(String fileDir, String lotteryCode, String issue, String ticketId) {
		String targetDir = buildFilePath(fileDir, lotteryCode, issue, ticketId, FAILURE_SUFIXX);
		fileDir = buildFilePath(fileDir, lotteryCode, issue, ticketId, SENDING_SUFIXX);
		logger.info("源文件夹: " + fileDir);
		logger.info("目标文件夹: " + targetDir);
		boolean success = FileUtils.copyFile(fileDir, targetDir);
		logger.info("复制文件到失败目录: " + success);
		return success;
	}

	/**
	 * 复杂成功的票到成功文件夹
	 * 
	 * @param fileDir 文件根目录
	 * @param lotteryCode 彩种
	 * @param issue 彩期
	 * @param ticketId 票号
	 * @param subTicketId 子票号
	 * @return
	 */
	public static boolean ticketSuccess(String fileDir, String lotteryCode, String issue, String ticketId, String subTicketId) {
		String targetDir = buildFilePath(fileDir, lotteryCode, issue, ticketId, SUCCESS_SUFIXX);
		fileDir = buildFilePath(fileDir, lotteryCode, issue, ticketId, SENDING_SUFIXX);
		String filePath = fileDir + SEPARATOR + subTicketId + FILE_SUFIXX;
		logger.info("原文件路径: " + filePath);
		logger.info("目标文件夹路径: " + targetDir);
		boolean success = FileUtils.copyFile(filePath, targetDir);
		logger.info("复制成功的票到成功文件夹【" + subTicketId + "】: " + success);
		return success;
	}

	/**
	 * 复制失败的票到失败文件夹
	 * 
	 * @param fileDir 文件根目录
	 * @param lotteryCode 彩种
	 * @param issue 彩期
	 * @param ticketId 票号
	 * @param subTicketId 子票号
	 * @return
	 */
	public static boolean ticketFailure(String fileDir, String lotteryCode, String issue, String ticketId, String subTicketId) {
		String targetDir = buildFilePath(fileDir, lotteryCode, issue, ticketId, FAILURE_SUFIXX);
		fileDir = buildFilePath(fileDir, lotteryCode, issue, ticketId, SENDING_SUFIXX);
		String filePath = fileDir + SEPARATOR + subTicketId + FILE_SUFIXX;
		boolean success = FileUtils.copyFile(filePath, targetDir);
		logger.info("复制失败的票到失败文件夹【" + subTicketId + "】: " + success);
		return success;
	}

	/**
	 * 成功票数
	 * 
	 * @param fileDir 文件根目录
	 * @param lotteryCode 彩种
	 * @param issue 彩期
	 * @param ticketId 票号
	 * @return
	 */
	public static int successTicketNum(String fileDir, String lotteryCode, String issue, String ticketId) {
		fileDir = buildFilePath(fileDir, lotteryCode, issue, ticketId, SUCCESS_SUFIXX);
		List<String> fileNameList = FileUtils.exeCurrFileName(fileDir);
		return fileNameList.size();
	}

	/**
	 * 失败票数
	 * 
	 * @param fileDir 文件根目录
	 * @param lotteryCode 彩种
	 * @param issue 彩期
	 * @param ticketId 票号
	 * @return
	 */
	public static int failureTicketNum(String fileDir, String lotteryCode, String issue, String ticketId) {
		fileDir = buildFilePath(fileDir, lotteryCode, issue, ticketId, FAILURE_SUFIXX);
		List<String> fileNameList = FileUtils.exeCurrFileName(fileDir);
		return fileNameList.size();
	}

	/**
	 * 总票数
	 * 
	 * @param fileDir 文件根目录
	 * @param lotteryCode 彩种
	 * @param issue 彩期
	 * @param ticketId 票号
	 * @return
	 */
	public static int allTicketNum(String fileDir, String lotteryCode, String issue, String ticketId) {
		fileDir = buildFilePath(fileDir, lotteryCode, issue, ticketId, SENDING_SUFIXX);
		List<String> fileNameList = FileUtils.exeCurrFileName(fileDir);
		return fileNameList.size();
	}

	public static String buildFilePath(String fileDir, String lotteryCode, String issue, String ticketId, int suffixx) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(fileDir).append(SEPARATOR).append(lotteryCode).append(SEPARATOR).append(issue);
		buffer.append(SEPARATOR).append(ticketId).append("_").append(suffixx);
		return buffer.toString();
	}

	public static final void main(String[] args) {
	}
}
