package cn.linshiyou.financialFinalContest.search;

import cn.linshiyou.financialFinalContest.search.pojo.GoodsInfo;
import cn.linshiyou.financialFinalContest.search.service.EsGoodsService;
import cn.linshiyou.financialFinalContest.search.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: LJ
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class Estest {

    @Autowired
    private EsGoodsService esGoodsService;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * ElasticsearchRestTemplate基本用法
     * https://blog.csdn.net/orange_bug/article/details/117519866
     */

    @Test
    public void createIndex(){
        boolean goodsinfo = elasticsearchRestTemplate.indexOps(IndexCoordinates.of(Constant.GOODS_INFO)).create();
        log.info(""+goodsinfo);
    }
}
