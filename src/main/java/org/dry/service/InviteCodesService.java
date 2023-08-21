package org.dry.service;

public interface InviteCodesService {
    int addCode(String code);
    int removeCode(String code);
    boolean isExist(String code);
    int countCode();
}
