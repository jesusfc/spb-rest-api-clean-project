package com.jesusfc.spb.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jesus.fdez.caraballo@gmail.com
 * Created on Nov 2024
 */
@Slf4j
@Tag(name = "Test", description = "Testing endPoints for development")
@RestController
@AllArgsConstructor
@RequestMapping("/v1.0/test")
public class TestRestController {


    @Operation(operationId = "logsTest")
    @GetMapping("/logs-test")
    public HttpStatus getLogsTest() {

        try {
            String a = null;
            System.out.println(a.length());
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return HttpStatus.OK;
    }


}
