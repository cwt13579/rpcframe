package com.tech.rpc.zookeeper.service.base;

import java.util.List;

import com.tech.rpc.frame.annotation.BerService;
import com.tech.rpc.model.Goods;
@BerService
public interface BaseGoodsService {
     List<Goods> getGoodsList()  ;
	 
	 void save(Goods goods);
	 void update(Goods goods);
}
