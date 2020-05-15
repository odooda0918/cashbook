package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.*;

@Mapper
public interface MemberidMapper {
	void insertMemberid(MemberId memberid);
}
