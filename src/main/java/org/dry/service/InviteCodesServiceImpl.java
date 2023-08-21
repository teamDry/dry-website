package org.dry.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Vector;

/**
 * 초대 코드 로직을 다루는 Service
 * EmailService, AdminRestController, AdminController 등에서 사용해야해서 Service로 분리
 */
@Service
public class InviteCodesServiceImpl implements InviteCodesService {
    private List inviteCodes;

    public InviteCodesServiceImpl() {
        inviteCodes = new Vector();
    }

    // 코드 추가 후 현재 코드 수 리턴
    @Override
    public int addCode(String code) {
        inviteCodes.add(code);
        return inviteCodes.size();
    }

    // 코드 삭제 후 현재 코드 수 리턴
    @Override
    public int removeCode(String code) {
        inviteCodes.remove(code);
        return inviteCodes.size();
    }

    // 해당 코드의 존재 여부 확인
    @Override
    public boolean isExist(String code) {
        return inviteCodes.contains(code);
    }

    // 현재 들어있는 코드 수
    @Override
    public int countCode() {
        return inviteCodes.size();
    }
}
