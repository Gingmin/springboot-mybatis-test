package com.example.demo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dao.SelectMapper;

@Service("selectService")
public class SelectService {

private SelectMapper selectMapper;
	
	@Autowired
	public SelectService(SelectMapper selectMapper) {
		this.selectMapper = selectMapper;
	}
	
	public int selectMethod() {
		return selectMapper.selectMethod();
	}
}
