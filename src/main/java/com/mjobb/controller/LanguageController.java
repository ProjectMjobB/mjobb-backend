package com.mjobb.controller;

import com.mjobb.service.TagService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/languages/")
@RequiredArgsConstructor
@ApiOperation("Languages API")
public class LanguageController {

}
