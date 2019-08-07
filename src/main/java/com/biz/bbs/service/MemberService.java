package com.biz.bbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.biz.bbs.mapper.MemberDao;
import com.biz.bbs.model.MemberVO;

@Service
public class MemberService {

	@Autowired
	MemberDao mDao;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	public MemberVO login_id_check(String m_userid, String m_password) {

		MemberVO memberVO=mDao.login_id_check(m_userid);
		if(memberVO != null) {
			
			String cryptPassword = memberVO.getM_password();
			if(passwordEncoder.matches(m_password, cryptPassword)) {
				// 암호가 일치하면 vo를 리턴
				return memberVO;
			} 
		}
		
		return null;
	}
}
