package com.winback.core.dto._case;

import lombok.Getter;
import lombok.Setter;

/**
 * @author RuQ
 * @Title: QNFileInfoDto
 * @ProjectName winback
 * @Description:
 * @date 2019/3/5 15:56
 */
@Getter
@Setter
public class QNFileInfoDto {
    //{"fsize":13999,"uploaded":13999,"hash":"FuxZzxUvZmoWyWg6Zwpe9jRReNfR","mimeType":"image/png"}
    private int fsize;
    private int uploaded;
    private String hash;
    private String mimeType;
}
