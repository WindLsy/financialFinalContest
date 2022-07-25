package cn.linshiyou.financialFinalContest.search.service;

import cn.linshiyou.financialFinalContest.search.pojo.GoodsDTO;
import cn.linshiyou.financialFinalContest.search.pojo.GoodsInfo;

/**
 * @Author: LJ
 * @Description:
 */
public interface EsGoodsService {


    /**
     * 向es中添加数据
     */
    void addOrUpdateEsGoodsDTO(GoodsDTO goodsDTO);

    /**
     * 创建索引，如果存在就不用创建
     */
    void createIndexIfNot();

}
