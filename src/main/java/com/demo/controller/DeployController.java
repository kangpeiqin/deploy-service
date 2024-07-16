package com.demo.controller;

import com.demo.model.R;
import com.demo.model.ShellRequestDTO;
import com.demo.service.DeployService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/deploy")
public class DeployController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeployController.class);

    @Resource
    private DeployService deployService;

    @PostMapping
    public R executeShell(@RequestBody @Validated ShellRequestDTO requestDTO) {
        LOGGER.info("当前请求参数：" + requestDTO.toString());
        StringBuilder sb = new StringBuilder();
        try {
            deployService.doExecuteShell(requestDTO, sb);
            return R.success(sb.toString());
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }
}
