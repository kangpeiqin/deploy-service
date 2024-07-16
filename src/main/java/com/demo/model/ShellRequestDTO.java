package com.demo.model;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

public class ShellRequestDTO {

    @NotBlank
    private String imageName;

    @NotBlank
    private String tag;

    @NotBlank
    private String simpleImageName;

    @NotBlank
    private String port;

    /**
     * 环境变量列表
     */
    private Map<String, String> envs;

    private List<String> volumes;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSimpleImageName() {
        return simpleImageName;
    }

    public void setSimpleImageName(String simpleImageName) {
        this.simpleImageName = simpleImageName;
    }

    public Map<String, String> getEnvs() {
        return envs;
    }

    public void setEnvs(Map<String, String> envs) {
        this.envs = envs;
    }

    public List<String> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<String> volumes) {
        this.volumes = volumes;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ShellRequestDTO{");
        sb.append("imageName='").append(imageName).append('\'');
        sb.append(", tag='").append(tag).append('\'');
        sb.append(", simpleImageName='").append(simpleImageName).append('\'');
        sb.append(", port='").append(port).append('\'');
        sb.append(", envs=").append(envs);
        sb.append(", volumes=").append(volumes);
        sb.append('}');
        return sb.toString();
    }
}
