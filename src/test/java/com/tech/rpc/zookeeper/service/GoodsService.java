package com.tech.rpc.zookeeper.service;

import java.util.ArrayList;
import java.util.List;

import com.tech.rpc.frame.annotation.BerServiceImpl;
import com.tech.rpc.model.Goods;
import com.tech.rpc.zookeeper.service.base.BaseGoodsService;
@BerServiceImpl
public class GoodsService implements BaseGoodsService{

	@Override
	public List<Goods> getGoodsList() {
		List<Goods> list = new ArrayList<Goods>();
		
		Goods goods = new Goods();
		goods.setGoodsId("123");
		goods.setGoodsName("goods");
		goods.setGoodsPrice("12.12");
		
		list.add(goods);
		
		return list;
	}

	@Override
	public void save(Goods goods) {
		 
		
	}

	@Override
	public void update(Goods goods) {
		
		
	}
}
