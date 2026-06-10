package com.longmao.demo.controller;

import com.longmao.demo.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 异步请求处理示例控制器
 * 展示如何使用 CompletableFuture 处理耗时任务以释放容器线程
 */
@RestController
@RequestMapping("/api/async")
public class AsyncController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AsyncController.class);

    /**
     * 模拟耗时异步任务
     * @return CompletableFuture 包装的结果
     */
    @GetMapping("/demo")
    public CompletableFuture<Result<String>> asyncDemo() {
        log.info("接收到异步请求，开始处理任务...");
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                // 模拟耗时操作，如远程 API 调用或复杂计算
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                log.error("异步任务被中断", e);
                Thread.currentThread().interrupt();
            }
            log.info("异步任务执行完毕");
            return Result.success("异步任务处理完成（模拟延时 2s）");
        });
    }
}
