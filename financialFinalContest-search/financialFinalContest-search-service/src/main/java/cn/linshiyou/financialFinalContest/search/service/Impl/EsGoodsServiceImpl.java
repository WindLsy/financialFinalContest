package cn.linshiyou.financialFinalContest.search.service.impl;

import cn.linshiyou.financialFinalContest.search.pojo.GoodsDTO;
import cn.linshiyou.financialFinalContest.search.pojo.GoodsInfo;
import cn.linshiyou.financialFinalContest.search.service.EsGoodsService;
import cn.linshiyou.financialFinalContest.search.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

/**
 * @Author: LJ
 * @Description:
 */
@Service
public class EsGoodsServiceImpl implements EsGoodsService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    /**
     * 向es中添加数据
     * @param goodsDTO
     */
    @Override
    public void addOrUpdateEsGoodsDTO(GoodsDTO goodsDTO) {
        elasticsearchRestTemplate.save(goodsDTO, IndexCoordinates.of(Constant.GOODS_INFO));
    }

    /**
     * 创建索引，如果存在就不用创建
     */
    @Override
    public void createIndexIfNot() {

        IndexCoordinates indexCoordinates = IndexCoordinates.of(Constant.GOODS_INFO);
        boolean exists = elasticsearchRestTemplate.indexOps(indexCoordinates).exists();
        if (!exists){
            elasticsearchRestTemplate.indexOps(indexCoordinates).create();
        }
    }


}
