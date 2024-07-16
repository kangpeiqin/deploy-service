package com.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author kpq
 * @date 2022/12/1 9:15
 */
public class BashScriptUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BashScriptUtil.class);


    private BashScriptUtil() {
    }

    /**
     * 执行脚本
     *
     * @param command 命令
     * @param sb      执行结果
     * @throws Exception
     */
    public static void execute(String command, StringBuilder sb) throws Exception {
        BufferedReader infoInput = null;
        BufferedReader errorInput = null;
        try {
            LOGGER.info("======================当前执行命令======================");
            LOGGER.info(command);
            LOGGER.info("======================当前执行命令======================");
            //执行脚本并等待脚本执行完成
            String[] commands = {"/bin/sh", "-c", command};
            Process process = Runtime.getRuntime().exec(commands);
            //写出脚本执行中的过程信息
            infoInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while ((line = infoInput.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
                LOGGER.info(line);
            }
            while ((line = errorInput.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
                LOGGER.error(line);
            }
            //阻塞执行线程直至脚本执行完成后返回
            process.waitFor();
        } finally {
            try {
                if (infoInput != null) {
                    infoInput.close();
                }
                if (errorInput != null) {
                    errorInput.close();
                }
            } catch (IOException e) {
                LOGGER.error("shell execute error");
            }
        }
    }

}
