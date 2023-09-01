package com.douyin.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class PublishDTO implements Serializable {
    private MultipartFile data;
    private String title;
}
