package com.wallester.backend;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
@RequestMapping("/")
@Api(tags = {"AppCommon"}, produces = "application/json")
public class BackendApplication {
    public static void main(String[] args) throws UnknownHostException {
        Environment env = SpringApplication.run(BackendApplication.class, args)
                .getEnvironment();
        log.info(
                "\n----------------------------------------------------------\n\t"
                        + "Application '{}' is running! Access URLs:\n\t"
                        + "Local: \t\thttp://127.0.0.1:{}\n\t"
                        + "External: \thttp://{}:{}\n\t"
                        + "Message: \t{}"
                        + "\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                env.getProperty("spring.message")
        );
    }

    @ApiOperation(value = "Application health check", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping("/hc")
    public ResponseEntity<String> hc() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
