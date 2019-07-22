package com.winback.core.service.assistant;

/**
 * @author xdrodger
 * @Title: DataMigrationService
 * @ProjectName winback
 * @Description:
 * @date 2019/2/20 15:05
 */
public interface DataMigrationService {

    void importContractFileToDb(String filePath);

    void findWrongContract();

    void resetContractImage(String filePath);

    void resetContractName(String filePath);
}
