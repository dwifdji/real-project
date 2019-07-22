package com.winback.core.service.assistant;

import java.io.File;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/11/21 10:54
 */
public interface TFileService {
    String watermark(String url);

    String watermark(File file);
}
