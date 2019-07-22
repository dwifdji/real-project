package com._360pai.seimi.service;

import org.apache.http.impl.client.CloseableHttpClient;

public interface SoYinService {
    CloseableHttpClient getLoginType();

    void getSoYinContract();

    void sendDataToWin();
}
