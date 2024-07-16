package com.demo.service;

import com.demo.model.ShellRequestDTO;
import com.demo.util.BashScriptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @author kpq
 * @date 2022/12/1 9:20
 */
@Service
public class DeployService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeployService.class);

    public synchronized void doExecuteShell(ShellRequestDTO requestDTO, StringBuilder sb) throws Exception {
        //删除旧的容器
        removeContainer(requestDTO.getSimpleImageName(), sb);
        //删除旧的镜像
        removeImage(requestDTO.getImageName(), sb);
        //拉取最新的镜像
        pullImage(requestDTO.getImageName(), requestDTO.getTag(), sb);
        //运行最新镜像
        runImage(requestDTO, sb);
    }

    private void removeContainer(String simpleImageName, StringBuilder result) {
        simpleImageName = simpleImageName.toLowerCase();
        try {
            String command = "containerId=`docker ps -a | grep -w " + simpleImageName + "  | awk '{print $1}'` \n" +
                    "if [ '$containerId' !=  '' ] ; then \n" +
                    "docker stop $containerId \n" +
                    "docker rm $containerId \n" +
                    "echo '成功删除容器' \n" +
                    "fi ";
            BashScriptUtil.execute(command, result);
        } catch (Exception e) {
            LOGGER.error("停止容器失败", e);
        }
    }

    private void removeImage(String simpleImageName, StringBuilder result) {
        simpleImageName = simpleImageName.toLowerCase();
        try {
            String command = "imageIds=`docker images | grep -w " + simpleImageName + " | awk '{print $3}'` \n" +
                    "if [ '$imageIds' !=  '' ] ; then \n" +
                    "   for imageId in $imageIds; \n" +
                    "       do docker rmi -f $imageId;" +
                    "   done \n" +
                    "echo '成功删除镜像'\n" +
                    "fi ";
            BashScriptUtil.execute(command, result);
        } catch (Exception e) {
            LOGGER.error("删除镜像失败", e);
        }
    }

    private void pullImage(String imageName, String tag, StringBuilder sb) throws Exception {
        imageName = imageName.toLowerCase();
        BashScriptUtil.execute(MessageFormat.format("docker pull {0}:{1}", imageName, tag), sb);
    }


    private void runImage(ShellRequestDTO requestDTO, StringBuilder sb) throws Exception {
        StringBuilder shell = new StringBuilder();
        shell.append("docker run");
        String[] portlit = requestDTO.getPort().split(",");
        for (String port : portlit) {
            String[] split = port.split(":");
            if (split.length == 1) {
                shell.append(" -p ").append(split[0]).append(":").append(split[0]);
            } else {
                shell.append(" -p ").append(split[0]).append(":").append(split[1]);
            }
        }
        shell.append(" --network=host ");
        shell.append(" --name=").append(requestDTO.getSimpleImageName());
        shell.append(" --restart=always ");
        shell.append(" -d ");
        formatEnv(requestDTO.getEnvs(), shell);
        formatVolumes(requestDTO.getVolumes(), shell);
        shell.append(requestDTO.getImageName().toLowerCase()).append(":").append(requestDTO.getTag());
        BashScriptUtil.execute(shell.toString(), sb);
    }

    private void formatVolumes(List<String> volumes, StringBuilder shell) {
        if (volumes == null || 0 == volumes.size()) {
            return;
        }
        volumes.forEach(volume -> {
            shell.append(" -v ");
            shell.append(" '").append(volume).append("' ");
        });
    }

    private void formatEnv(Map<String, String> env, StringBuilder shell) {
        if (env == null || env.isEmpty()) {
            return;
        }
        for (Map.Entry<String, String> entry : env.entrySet()) {
            shell.append(" -e ");
            shell.append(entry.getKey()).append("='").append(entry.getValue()).append("' ");
        }
    }


}
